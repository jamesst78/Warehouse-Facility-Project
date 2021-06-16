package Storage;

import java.io.Console;
import java.util.ArrayList;
public class SubWarehouse {
	
	
	int maxCapacity;
	ArrayList<Integer> itemWeights = new ArrayList<>() ;
	ArrayList<Integer> itemProfits = new ArrayList<>(); 					
	int maxChosenProfit;
	int availableItems;
	
	
	
	
	
	public void optimizeSelection()
	{
		//making sure values are set right
		if(itemProfits.size()==0 || itemWeights.size()==0 || itemWeights.size()!=itemProfits.size() || maxCapacity<=0){
			maxChosenProfit = 0;
			return;
		}

		int n = itemProfits.size();
		int[][] table = new int[n][maxCapacity + 1];

		for(int i=0; i < n; i++) {
			table[i][0] = 0;
		}

		for(int i=0; i<=maxCapacity; i++){
			if(itemWeights.get(0)<=i){
				table[0][i] = itemProfits.get(0);
			}
		}

		for(int i=1; i<n; i++){
			for(int j=1; j<=maxCapacity; j++){
				int newProfit = 0;
				int prevProfit = 0;
				if(itemWeights.get(i)<=j){
					newProfit = itemProfits.get(i) + table[i-1][j-itemWeights.get(i)];
				}
				prevProfit = table[i-1][j];
				table[i][j] = max(prevProfit, newProfit);
			}
		}

        maxChosenProfit =  table[n-1][maxCapacity]; //Update with your answer.

        //TODO: Enter your GUC mail here
        String email = "shehabeldin.solyman@student.guc.edu.eg";
        System.out.println("Email: " + email);
    }

	public static int max(int a, int b) { return (a > b) ? a : b; }

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
		


		DataLoader.loadArray("kp100.txt", SW.itemWeights, SW.itemProfits, SW.maxCapacity);

		SW.maxCapacity= 49877;
		SW.availableItems = 10000 ;
		SW.optimizeSelection();
		System.out.println( "Max Profit " + SW.maxChosenProfit); // should be Max Profit 594669




		
		
		
		
	}

	
	
}
