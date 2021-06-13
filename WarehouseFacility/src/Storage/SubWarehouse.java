package Storage;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
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
		
				
		
		TreeMap<Integer,Integer> t = new TreeMap<>();
	
		
		
		for (int i = 0; i<itemWeights.size(); i++) {
			
			t.put(itemWeights.get(i),itemProfits.get(i));
			
		}
		

		
		int maxprof = Integer.MIN_VALUE;
		
		long pow_set_size = (long)Math.pow(2, itemWeights.size());
		
		
		for(int counter = 0; counter < pow_set_size; counter++)
        {
			Iterator<Map.Entry<Integer, Integer>> iterator1 = t.entrySet().iterator();		 
			Map.Entry<Integer, Integer> entry1 = null;
			
			int j = 0;
			int wincell = 0;
			int pincell = 0;
			
			TreeMap<Integer, Integer> cell = new TreeMap<>();
			
			while(iterator1.hasNext()){
			    
			    entry1 = iterator1.next();
			    
			    if((counter & (1 << j)) > 0) {
	                cell.put(entry1.getKey(), entry1.getValue());
				    wincell += entry1.getKey();
				    pincell+= entry1.getValue();
				    
			    }
			    j++;
			    
			    
			    
			}

		   if (wincell<=190) {
			   maxprof = max(pincell,maxprof);
		   }
			
        }
		
		
		
		

 
        maxChosenProfit =  maxprof; //Update with your answer.
        
        //TODO: Enter your GUC mail here
        String email = "maryam.eloraby@gstudent.guc.edu.eg";
        System.out.println("Email: " + email);
    }
		
	
	
	
	
	public static int max(int a, int b)
	    {
	      return (a > b) ? a : b;
	    }
	
	

	@SuppressWarnings("static-access")
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
		
		
//		DataLoader dl = new DataLoader();
//		
//		SW.maxCapacity= 49877;
//		SW.availableItems = 10000 ;
//		dl.loadArray("kp100.txt" ,SW.itemWeights,SW.itemProfits,0);
//		SW.optimizeSelection();
//		System.out.println( "Max Profit " + SW.maxChosenProfit); // should be Max Profit 594669   
//

		
		
		
		
		
	}

	
	
}
