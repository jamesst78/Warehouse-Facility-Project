package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import buildings.Building;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import exceptions.FriendlyCityException;
import exceptions.FriendlyFireException;
import exceptions.MaxCapacityException;
import exceptions.TargetNotReachedException;
import units.Archer;
import units.Army;
import units.ArmyListener;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;

public class Game implements PlayerListenr, ArmyListener{
	private Player player;
	private ArrayList<City> availableCities;
	private ArrayList<Distance> distances;
	private final int maxTurnCount = 50;
	private int currentTurnCount;
	private GameListener listener;

	public Game(String playerName, String playerCity) throws IOException {

		player = new Player(playerName);
		player.setListener(this);
		availableCities = new ArrayList<City>();
		distances = new ArrayList<Distance>();
		currentTurnCount = 1;
		loadCitiesAndDistances();
		for (City c : availableCities) {
			c.getDefendingArmy().setListener(this);
			if (c.getName().toLowerCase().equals(playerCity.toLowerCase()))
				player.getControlledCities().add(c);
			else {
				loadArmy(c.getName(), c.getName().toLowerCase() + "_army.csv");
			}
		}
	}

	private void loadCitiesAndDistances() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("distances.csv"));
		String currentLine = br.readLine();
		ArrayList<String> names = new ArrayList<String>();

		while (currentLine != null) {

			String[] content = currentLine.split(",");
			if (!names.contains(content[0])) {
				availableCities.add(new City(content[0]));
				names.add(content[0]);
			} else if (!names.contains(content[1])) {
				availableCities.add(new City(content[1]));
				names.add(content[1]);
			}
			distances.add(new Distance(content[0], content[1], Integer.parseInt(content[2])));
			currentLine = br.readLine();

		}
		br.close();
	}

	public void loadArmy(String cityName, String path) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(path));
		String currentLine = br.readLine();
		Army resultArmy = new Army(cityName);
		while (currentLine != null) {
			String[] content = currentLine.split(",");
			String unitType = content[0].toLowerCase();
			int unitLevel = Integer.parseInt(content[1]);
			Unit u = null;
			if (unitType.equals("archer")) {

				if (unitLevel == 1)
					u = (new Archer(1, 60, 0.4, 0.5, 0.6));

				else if (unitLevel == 2)
					u = (new Archer(2, 60, 0.4, 0.5, 0.6));
				else
					u = (new Archer(3, 70, 0.5, 0.6, 0.7));
			} else if (unitType.equals("infantry")) {
				if (unitLevel == 1)
					u = (new Infantry(1, 50, 0.5, 0.6, 0.7));

				else if (unitLevel == 2)
					u = (new Infantry(2, 50, 0.5, 0.6, 0.7));
				else
					u = (new Infantry(3, 60, 0.6, 0.7, 0.8));
			} else if (unitType.equals("cavalry")) {
				if (unitLevel == 1)
					u = (new Cavalry(1, 40, 0.6, 0.7, 0.75));

				else if (unitLevel == 2)
					u = (new Cavalry(2, 40, 0.6, 0.7, 0.75));
				else
					u = (new Cavalry(3, 60, 0.7, 0.8, 0.9));
			}
			u.setParentArmy(resultArmy);
			resultArmy.getUnits().add(u);
			currentLine = br.readLine();
		}
		br.close();
		for (City c : availableCities) {
			if (c.getName().toLowerCase().equals(cityName.toLowerCase()))
				c.setDefendingArmy(resultArmy);
		}

	}

	public ArrayList<City> getAvailableCities() {
		return availableCities;
	}

	public ArrayList<Distance> getDistances() {
		return distances;
	}

	public int getCurrentTurnCount() {
		return currentTurnCount;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getMaxTurnCount() {
		return maxTurnCount;
	}

	public void setCurrentTurnCount(int currentTurnCount) {
		this.currentTurnCount = currentTurnCount;
	}
	
	public GameListener getListener() {
		return listener;
	}

	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	public void targetCity(Army army, String targetName) {
		if (army.getCurrentStatus() == Status.MARCHING)
			return;
		City city = null;
		for(City c: this.getAvailableCities()) {
			if(c.getName().toLowerCase().equals(targetName.toLowerCase()))
				city = c;
		}
		if(city!=null) {
			if(player.getControlledCities().contains(city))
				return;
		}
		String from = army.getCurrentLocation();
		Distance a = null;
		for (Distance d : getDistances()) {
			if ((d.getFrom().toLowerCase().equals(from.toLowerCase())
					&& d.getTo().toLowerCase().equals(targetName.toLowerCase()))
					|| (d.getTo().toLowerCase().equals(from.toLowerCase())
							&& d.getFrom().toLowerCase().equals(targetName.toLowerCase()))) {
				a = d;
			}
		}
		army.setDistancetoTarget(a.getDistance());
		army.setTarget(targetName);
		army.setCurrentStatus(Status.MARCHING);
		army.setCurrentLocation("onRoad");
		if(listener!= null) {
			listener.OnTargetCity(army, targetName);
		}
	}

	public void endTurn() throws TargetNotReachedException, FriendlyCityException {
		boolean x = false;
		Army ab = null;
		setCurrentTurnCount(getCurrentTurnCount() + 1);
		for (City c : getPlayer().getControlledCities()) {
			for (EconomicBuilding b : c.getEconomicalBuildings()) {
				b.setCoolDown(false);
				if (b instanceof Market)
					getPlayer().setTreasury(getPlayer().getTreasury() + b.harvest());
				if(b instanceof Farm)
					getPlayer().setFood(getPlayer().getFood()+b.harvest());
			}
			for (MilitaryBuilding b : c.getMilitaryBuildings()) {
				b.setCoolDown(false);
				b.setCurrentRecruit(0);
			}
		}

		double FoodNeeded = 0.0;
		for (Army a : getPlayer().getControlledArmies()) {
			FoodNeeded += a.foodNeeded();
			if (a.getCurrentStatus() == Status.MARCHING) {
				a.setDistancetoTarget(a.getDistancetoTarget() - 1);
				if (a.getDistancetoTarget() == 0) {
					a.setCurrentStatus(Status.IDLE);
					a.setCurrentLocation(a.getTarget());
					a.setTarget("");
				}
			}	
		}
		for (City city: getPlayer().getControlledCities()) {
			Army a= city.getDefendingArmy();
			FoodNeeded += a.foodNeeded();	
		}
		getPlayer().setFood(getPlayer().getFood() - FoodNeeded);
		if (getPlayer().getFood() <= 0) {
			getPlayer().setFood(0);
			for (Army t : getPlayer().getControlledArmies()) {
				for (Unit u : t.getUnits())
					u.setCurrentSoldierCount((int) (u.getCurrentSoldierCount() * 0.9));
			}
			for (City city: getPlayer().getControlledCities()) {
				Army a= city.getDefendingArmy();
				for (Unit u : a.getUnits())
					u.setCurrentSoldierCount((int) (u.getCurrentSoldierCount() * 0.9));
			}
		}
		
		for (City c1 : getAvailableCities()) {
			if (c1.isUnderSiege()) {
				if (c1.getTurnsUnderSiege() == 3) {
					for (Army army : getPlayer().getControlledArmies()) {
						if (army.getCurrentLocation().toLowerCase().equals(c1.getName().toLowerCase())
								&& army.getCurrentStatus() == Status.BESIEGING) {
							army.setShouldAttack(true);
							army.setCurrentStatus(Status.IDLE);
							c1.setUnderSiege(false);
						}
					}
				} else {
					for (Unit u : c1.getDefendingArmy().getUnits()) {
						u.setCurrentSoldierCount((int) (u.getCurrentSoldierCount() * 0.9));
					}
					c1.setTurnsUnderSiege(c1.getTurnsUnderSiege() + 1);
				}
			}
		}
		if(listener!=null) {
			listener.OnEndTurn();
		}
	}

	public void occupy(Army a, String cityName) throws MaxCapacityException {
		City f = null;
		for (City c3 : getAvailableCities()) {
			if (c3.getName().toLowerCase().equals(cityName.toLowerCase())) {
				f = c3;
				break;
			}
		}
		if (f == null)
			return;
		getPlayer().getControlledCities().add(f);
		f.setDefendingArmy(a);
		getPlayer().getControlledArmies().remove(a);
		a.setCurrentStatus(Status.IDLE);
		f.setTurnsUnderSiege(-1);
		f.setUnderSiege(false);
		if(listener!=null) {
			listener.OnOccupy(a, cityName);
		}
	}

	public void autoResolve(Army attacker, Army defender) throws FriendlyFireException, MaxCapacityException {
		boolean flag = true;
		while (true) {
			int index1 = (int) (Math.random() * attacker.getUnits().size());
			int index2 = (int) (Math.random() * defender.getUnits().size());
			Unit defend = defender.getUnits().get(index2);
			Unit attack = attacker.getUnits().get(index1);
			if (flag) {
				int past = defend.getCurrentSoldierCount();
				attack.attack(defend);
				//JOptionPane.showMessageDialog(null,"attack");
				if(listener!=null) {
					listener.Onattack(attack,attacker,defend,defender,past,true);
				}
				if (defender.getUnits().size() == 0) {
					occupy(attacker, defender.getCurrentLocation());
					if(listener!=null) {
						listener.OnOccupy(attacker, defender.getCurrentLocation());
					}
					break;
				}

			} else {
				int past = attack.getCurrentSoldierCount();
				defend.attack(attack);
				//JOptionPane.showMessageDialog(null,"defend");
				if(listener!=null) {
					listener.Onattack(defend,defender,attack,attacker,past,false);
				}
				if (attacker.getUnits().size() == 0) {
					getPlayer().getControlledArmies().remove(attacker);
					if(listener!=null) {
						listener.OnRemoveArmy(attacker);
					}
					break;
				}
			}
			flag = !flag;
		}
	}
	
	public void ManualAttack(Army attacker, Army defender,int index1,int index2) throws FriendlyFireException, MaxCapacityException {
			Unit defend = defender.getUnits().get(index2);
			Unit attack = attacker.getUnits().get(index1);
			String city = defender.getCurrentLocation();
			int past = defend.getCurrentSoldierCount();
			attack.attack(defend);
				if(listener!=null) {
					listener.Onattack(attack,attacker,defend,defender,past,true);
				}
				if (defender.getUnits().size() == 0) {
					occupy(attacker, defender.getCurrentLocation());
					if(listener!=null) {
						listener.OnOccupy(attacker, city);
					}
					return;
				}
				
				int index11 = (int) (Math.random() * attacker.getUnits().size());
				int index21 = (int) (Math.random() * defender.getUnits().size());
				Unit defend1 = defender.getUnits().get(index21);
				Unit attack1 = attacker.getUnits().get(index11);
				past = attack1.getCurrentSoldierCount();
				defend1.attack(attack1);
				//JOptionPane.showMessageDialog(null, "this????1st" + attacker.getUnits().size());
				if(listener!=null) {
					listener.Onattack(defend1,defender,attack1,attacker,past,false);
					//JOptionPane.showMessageDialog(null, "this????2nd" + attacker.getUnits().size());
				}
				//JOptionPane.showMessageDialog(null, "this????3rd" + attacker.getUnits().size());
				if (attacker.getUnits().size() == 0) {
					//JOptionPane.showMessageDialog(null, "game");
					getPlayer().getControlledArmies().remove(attacker);
					//JOptionPane.showMessageDialog(null,"engine remove army");
					if(listener!=null) {
						listener.OnRemoveArmy(attacker);
					}
				}
	}

	public boolean isGameOver() {
		if (getAvailableCities().size() == getPlayer().getControlledCities().size())
			return true;
		else if (getCurrentTurnCount() > getMaxTurnCount())
			return true;
		return false;
	}

	@Override
	public void OnRecruitUnit(String type, String cityName) {
		listener.OnRecruitUnit(type, cityName);
		
	}

	@Override
	public void OnBuilt(Building b) {
		listener.OnBuilt(b);
		
	}

	@Override
	public void OnUpgradeBuilding(Building b) {
		listener.OnUpgradeBuilding(b);
		
	}

	@Override
	public void OnLaySiege(Army army, City city) {
		listener.OnLaySiege(army, city);
	}

	@Override
	public void OnHandleAttackedUnit(Unit u, Army a,int index) {
		if(listener!=null) {
			listener.OnHandleAttackedUnit(u,a,index);
			//JOptionPane.showMessageDialog(null,"Game listens");
		}
	}

	@Override
	public void OnInitiateArmy(Army army, Unit unit) {
		army.setListener(this);
		//JOptionPane.showMessageDialog(null,"Army listener    " + army.getUnits().size());
		if(listener!=null) {
			listener.OnInitiateArmy(army, unit);
		}
	}

	@Override
	public void onRelocateUnit(Army army) {
		if(listener!=null) {
			listener.onRelocateUnit(army);
			//JOptionPane.showMessageDialog(null,"Game listens");
		}
	}

}
