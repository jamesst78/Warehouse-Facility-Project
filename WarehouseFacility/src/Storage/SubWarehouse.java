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
		//TODO: Create a method that optimizes the selection of items in order to produce the most profit and make the most out of the storage space.
		
		//Your code here
		//You're allowed to use any other pre-defined methods.
		
		
		int i, w;
	        int K[][] = new int[availableItems + 1][maxCapacity+ 1];
	 
	        // Build table K[][] in bottom up manner
	        for (i = 0; i <= availableItems; i++)
	        {
	            for (w = 0; w <= maxCapacity; w++)
	            {
	                if (i == 0 || w == 0)
	                    K[i][w] = 0;
	                else if (itemWeights.get(i - 1) <= w)
	                    K[i][w]
	                        = max(itemProfits.get(i-1)
	                         + K[i - 1][w - itemWeights.get(i-1)],
	                         K[i - 1][w]);
	                else
	                    K[i][w] = K[i - 1][w];
	            }
	        }
	
        
 
        maxChosenProfit =  K[availableItems][maxCapacity]; //Update with your answer.
        
        //TODO: Enter your GUC mail here
        String email = "ahmed.kaddah@student.guc.edu.eg";
        System.out.println("Email: " + email);
    }
		
	
	
	public static int max(int a, int b)
	    {
	      return (a > b) ? a : b;
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
		
		
		
		DataLoader.loadArray("kp100.txt", SW.itemWeights, SW.itemProfits, SW.maxCapacity);
		 
		SW.maxCapacity= 49877;
		SW.availableItems = 10000 ;
		SW.optimizeSelection();
		System.out.println( "Max Profit " + SW.maxChosenProfit); // should be Max Profit 594669   
		
		
		
		
		//If this gives you a memory heap error / stack overflow , try with a lower number dataset
		
		
		
//		DataLoader.loadArray("kp20.txt", SW.itemWeights, SW.itemProfits, SW.maxCapacity);
//		  
//		SW.maxCapacity= 9819;
//		SW.availableItems = 2000  ;
//		SW.optimizeSelection();
//		System.out.println( "Max Profit " + SW.maxChosenProfit); // should be Max Profit 594669   
//		
//	
		
		
		
		//if it still gives you stack overflow then your code is not optimized enough =)
		


		
		
		
		
		
	}

	
	
}
