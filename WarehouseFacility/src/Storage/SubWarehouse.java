package Storage;


import java.util.ArrayList;
import java.util.Collections;
public class SubWarehouse {
	
	
	int maxCapacity;
	ArrayList<Integer> itemWeights = new ArrayList<>() ;
	ArrayList<Integer> itemProfits = new ArrayList<>(); 					
	int maxChosenProfit;
	int availableItems;
	
	
	
	
	public static ArrayList<Double>getRatio(ArrayList<Integer> itemWeights,
			ArrayList<Integer> itemProfits) {
		ArrayList<Double> ratio = new ArrayList<>();
		int length = itemProfits.size();
		for(int i=0;i<length;i++) {
			ratio.add((double)itemProfits.get(i)/itemWeights.get(i));
		}
		
		return ratio;
		
	}
	
	public static int getCurrentWeight(ArrayList<Integer> chosenIndex,
			ArrayList<Integer> itemWeights) {
		int sum=0;
		for(int i=0; i<chosenIndex.size();i++) {
			sum+= itemWeights.get(chosenIndex.get(i));
		}
		return sum;
	}
	
	public static int getCurrentProfit(ArrayList<Integer> chosenIndex,
			ArrayList<Integer> itemProfits) {
		int sum=0;
		for(int i=0; i<chosenIndex.size();i++) {
			sum+= itemProfits.get(chosenIndex.get(i));
		}
		return sum;
	}
	
	public static boolean replace(ArrayList<Integer> chosenIndex,
			ArrayList<Integer> itemProfits,ArrayList<Integer> itemWeights,int index,int maxCapacity) {
		int leastRatioIndex = chosenIndex.get(chosenIndex.size()-1);
		int previousProfit =getCurrentProfit(chosenIndex,itemProfits);
		chosenIndex.remove(chosenIndex.size()-1);
		chosenIndex.add(index);
		if(getCurrentWeight(chosenIndex,itemWeights)==maxCapacity) {
			if(getCurrentProfit(chosenIndex,itemProfits)>previousProfit) {
				return true;
			}
		}
		chosenIndex.remove(chosenIndex.size()-1);
		chosenIndex.add(leastRatioIndex);
		
		return false;
	}
	
	public void optimizeSelection()
	{
		ArrayList<Double> ratio=getRatio(itemWeights,itemProfits);
		ArrayList<Integer> chosenIndex=new ArrayList<>();
		double maxRatio=-1;
		int index=-1;
		for(int i=0;i<availableItems;i++) {
			maxRatio=Collections.max(ratio);
			index=ratio.indexOf(maxRatio);
			
			if(getCurrentWeight(chosenIndex,itemWeights)+itemWeights.indexOf(index)==maxCapacity) {
				chosenIndex.add(index);
				maxChosenProfit=getCurrentProfit(chosenIndex,itemProfits);
				return;
			}
			
			if((getCurrentWeight(chosenIndex,itemWeights)+itemWeights.get(index))>maxCapacity) {
				if(replace(chosenIndex,itemProfits,itemWeights,index,maxCapacity)) {
				}
			}
			
			if((getCurrentWeight(chosenIndex,itemWeights)+itemWeights.get(index))<maxCapacity) {
				chosenIndex.add(index);
				
			}
			
			
			ratio.set(i, -1.0);
			
		}
		maxChosenProfit=getCurrentProfit(chosenIndex,itemProfits);
		
	
        
 
        maxChosenProfit =  getCurrentProfit(chosenIndex,itemProfits);; //Update with your answer.
        
        //TODO: Enter your GUC mail here
        String email = "alfarouk.sabry@student.guc.edu.eg";
        System.out.println("Email: " + email);
    }
	
	
	
	

	public static void main(String[] args) {
		
		SubWarehouse SW = new SubWarehouse();
		
		//populating the ArrayLists   
		SW.itemWeights.add(56);
		SW.itemWeights.add(59);
		SW.itemWeights.add(80);       // itemWeights = {56 , 59 , 80 , 64, 75 , 17};
		SW.itemWeights.add(64);
		SW.itemWeights.add(75);
		SW.itemWeights.add(17);	
		
		
		SW.itemProfits.add(50);                                
		SW.itemProfits.add(50);
		SW.itemProfits.add(64);
		SW.itemProfits.add(46);		  // itemProfits = {50 , 50 , 64 , 46 , 50 , 5}; 
		SW.itemProfits.add(50);
		SW.itemProfits.add(5);

		SW.maxCapacity = 190;          	   //maximum warehouse capacity = 190 weight unit
		SW.availableItems = 6; 			   //available items are 6
		
		
		
		
		
		//Step 1 :
		//TODO: Call your optimization method here and Print your results.
		//Expected Results : Populate maxChosenProfit with the total final Profit
		
		SW.optimizeSelection();
		System.out.println( "Max Profit " + SW.maxChosenProfit); //optimum answer should be maxChosenProfit = 150

		
		
		
		//Step 2 :
		//TODO: Try out your solution on more complex datasets that are used in practical world
		
		
		//Uncomment to load the dataset before re-running your method again
		
		
		
		//DataLoader.loadArray("kp100.txt", SW.itemWeights, SW.itemProfits, SW.maxCapacity);
		 
		//SW.maxCapacity= 49877;
		//SW.availableItems = 10000 ;
		//SW.optimizeSelection();
	//	System.out.println( "Max Profit " + SW.maxChosenProfit); // should be Max Profit 594669   
		


		
		
		
		
		
	}

	
	
}