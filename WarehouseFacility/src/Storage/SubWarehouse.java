package engine;

import java.util.ArrayList;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyCityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;
import units.Army;
import units.ArmyListener;
import units.Status;
import units.Unit;

public class Player{
	private String name;
	private ArrayList<City> controlledCities;
	private ArrayList<Army> controlledArmies;
	private double treasury = 5000;
	private double food;
	private PlayerListenr listener;

	public Player(String name) {
		this.name = name;
		this.controlledCities = new ArrayList<City>();
		this.controlledArmies = new ArrayList<Army>();
	}

	public double getTreasury() {
		return treasury;
	}

	public void setTreasury(double treasury) {
		this.treasury = treasury;
	}

	public double getFood() {
		return food;
	}

	public void setFood(double food) {
		this.food = food;
	}

	public String getName() {
		return name;
	}

	public ArrayList<City> getControlledCities() {
		return controlledCities;
	}

	public ArrayList<Army> getControlledArmies() {
		return controlledArmies;
	}
	
	public PlayerListenr getListener() {
		return listener;
	}

	public void setListener(PlayerListenr listener) {
		this.listener = listener;
	}
	
	 public void recruitUnit(String type,String cityName) throws
	 BuildingInCoolDownException, MaxRecruitedException, NotEnoughGoldException{
		 City city = null;
		 for (City c : getControlledCities()) {
				if (c.getName().toLowerCase().equals(cityName.toLowerCase())) {
					city = c;break;
				}
	    }
		 if(city != null) {
		 MilitaryBuilding m=null;
		for(MilitaryBuilding mb:city.getMilitaryBuildings()) {
		if(type.toLowerCase().equals("archer")) {
			if(mb instanceof ArcheryRange)
				m=mb;
		}
		if(type.toLowerCase().equals("infantry")) {
			if(mb instanceof Barracks)
				m=mb;
		}
		if(type.toLowerCase().equals("cavalry")) {
			if(mb instanceof Stable)
				m=mb;
		}
		}
		if(m.getRecruitmentCost()>getTreasury())
			throw new NotEnoughGoldException();
		Unit u=m.recruit();
		u.setParentArmy(city.getDefendingArmy());
		city.getDefendingArmy().getUnits().add(u);
		setTreasury(getTreasury()-m.getRecruitmentCost());
		 }
		 if(listener!=null) {
			 listener.OnRecruitUnit(type, cityName);
		 }
	 }
	 public void build(String type,String cityName) throws NotEnoughGoldException{
		 City city = null;
		 for (City c : getControlledCities()) {
				if (c.getName().toLowerCase().equals(cityName.toLowerCase())) {
					city = c;break;
				}
	    }
		 if(city == null)
			 return;
		 Building a=null;
		 if(type.toLowerCase().equals("archeryrange")) {
			 for(Building building: city.getMilitaryBuildings())
				 if(building instanceof ArcheryRange)
					 return;
			  a = new ArcheryRange();
			  if(a.getCost()>getTreasury())
					throw new NotEnoughGoldException();
			 city.getMilitaryBuildings().add((MilitaryBuilding)a);
			 }
		 if(type.toLowerCase().equals("barracks")) {
			 for(Building building: city.getMilitaryBuildings())
				 if(building instanceof Barracks)
					 return;
			 a = new Barracks();
			 if(a.getCost()>getTreasury())
					throw new NotEnoughGoldException();
			 city.getMilitaryBuildings().add((MilitaryBuilding)a);
			 }
		 if(type.toLowerCase().equals("farm")) {
			 for(Building building: city.getEconomicalBuildings())
				 if(building instanceof Farm)
					 return;
			 a = new Farm();
			 if(a.getCost()>getTreasury())
					throw new NotEnoughGoldException();
			 city.getEconomicalBuildings().add((EconomicBuilding)a);
			 }
		 if(type.toLowerCase().equals("market")) {
			 for(Building building: city.getEconomicalBuildings())
				 if(building instanceof Market)
					 return;
			  a = new Market();
			  if(a.getCost()>getTreasury())
					throw new NotEnoughGoldException();
			 city.getEconomicalBuildings().add((EconomicBuilding)a);
			 }
		 if(type.toLowerCase().equals("stable")) {
			 for(Building building: city.getMilitaryBuildings())
				 if(building instanceof Stable)
					 return;
			a = new Stable();
			if(a.getCost()>getTreasury())
				throw new NotEnoughGoldException();
			 city.getMilitaryBuildings().add((MilitaryBuilding)a);
		     }
		 setTreasury(getTreasury()-a.getCost());
		 a.setCoolDown(true);
		 if(listener!=null) {
			 listener.OnBuilt(a);
		 }
	 }
	 public void upgradeBuilding(Building b) throws NotEnoughGoldException,
	 BuildingInCoolDownException, MaxLevelException {
		 if(b.getUpgradeCost()>getTreasury()) 
			 throw new NotEnoughGoldException();
		 int initialValue = b.getUpgradeCost();
		 b.upgrade();
		 setTreasury(getTreasury()-initialValue);
		 if(listener!=null) {
			 listener.OnUpgradeBuilding(b);
		 }
	 } 
	 public void initiateArmy(City city,Unit unit) {
		 Army a = new Army(city.getName());
		 a.getUnits().add(unit);
		 city.getDefendingArmy().getUnits().remove(unit);
		 unit.setParentArmy(a);
		 controlledArmies.add(a);
		 if(listener!=null) {
			 listener.OnInitiateArmy(a, unit);
		 }
	 }
	 public void laySiege(Army army,City city) throws TargetNotReachedException,
	 FriendlyCityException{
		 if (!army.getCurrentLocation().toLowerCase().equals(city.getName().toLowerCase()))
			 throw new TargetNotReachedException();
		 if(controlledCities.contains(city))
			 throw new FriendlyCityException();
		 army.setCurrentStatus(Status.BESIEGING);
		 city.setUnderSiege(true);
		 city.setTurnsUnderSiege(0);
		 if(listener!=null) {
			 listener.OnLaySiege(army, city);
		 }
	 }
}
		
