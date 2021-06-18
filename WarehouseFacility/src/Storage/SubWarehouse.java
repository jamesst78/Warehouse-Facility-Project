package Storage;

import java.io.Console;
import java.util.ArrayList;

public class SubWarehouse {

	int maxCapacity;
	ArrayList<Integer> itemWeights = new ArrayList<>();
	ArrayList<Integer> itemProfits = new ArrayList<>();
	int maxChosenProfit;
	int availableItems;

	int[][] memo;
	
	
	private int dp() {
		memo = new int[itemWeights.size()][maxCapacity + 1];
		
		for (int i = 0; i < memo.length; i++)
			for (int j = 0; j < memo[i].length; j++)
				memo[i][j] = -1;
		
		return dp(0, maxCapacity);
	}
	
	private int dp(int id, int w) {
		
		if (id == itemWeights.size() || w == 0)
			return 0;
		
		if (memo[id][w] != -1)
			return memo[id][w];
		
		if (itemWeights.get(id) > w)
			return memo[id][w] = value(id + 1, w);
		
		return memo[id][w] = Math.max(value(id + 1, w), itemProfits.get(id) + value(id + 1, w - itemWeights.get(id)));
	}

	
	
	private int value(int id, int w) {
		
		if (id == itemWeights.size() || w == 0)
			return 0;
		
		int v1; 
		int v2;
		
		if (itemWeights.get(id) > w) {
			return value(id + 1, w);
		}
		
		v1 = itemProfits.get(id) + value(id + 1, w - itemWeights.get(id));
		v2 = value(id + 1, w);
		
		return Math.max(v1, v2);
	}
	
	
	
	
	public void optimizeSelection() {
		// TODO: Create a method that optimizes the selection of items in order to
		// produce the most profit and make the most out of the storage space.

		// Your code here
		// You're allowed to use any other pre-defined methods.


		maxChosenProfit = 0; // Update with your answer.
		maxChosenProfit = value(0, maxCapacity); // basic recursive brute force approach wont work on large set
		// maxChosenProfit = dp(0, maxCapacity); // a dp approach but wont work on large sets

		// TODO: Enter your GUC mail here
		String email = "youssef.kersha@student.guc.edu.eg";
		System.out.println("Email: " + email);
	}

	public static int max(int a, int b) {
		return (a > b) ? a : b;
	}

	public static void main(String[] args) {

		SubWarehouse SW = new SubWarehouse();

		// populating the ArrayLists
		SW.itemWeights.add(56);
		SW.itemWeights.add(59);
		SW.itemWeights.add(80); // itemWeights = {56 , 59 , 80 , 64, 75 , 17};
		SW.itemWeights.add(64);
		SW.itemWeights.add(75);
		SW.itemWeights.add(17);

		SW.itemProfits.add(50);
		SW.itemProfits.add(50);
		SW.itemProfits.add(64);
		SW.itemProfits.add(46); // itemProfits = {50 , 50 , 64 , 46 , 50 , 5};
		SW.itemProfits.add(50);
		SW.itemProfits.add(5);

		SW.maxCapacity = 190; // maximum warehouse capacity = 190 weight unit
		SW.availableItems = 6; // available items are 6

		// Step 1 :
		// TODO: Call your optimization method here and Print your results.
		// Expected Results : Populate maxChosenProfit with the total final Profit

		SW.optimizeSelection();
		System.out.println("Max Profit " + SW.maxChosenProfit); // optimum answer should be maxChosenProfit = 150

		// Step 2 :
		// TODO: Try out your solution on more complex datasets that are used in
		// practical world

		// Uncomment to load the dataset before re-running your method again

		
		  DataLoader.loadArray("kp100.txt", SW.itemWeights, SW.itemProfits,
		  SW.maxCapacity);
		  
		  SW.maxCapacity= 49877; SW.availableItems = 10000 ; SW.optimizeSelection();
		  System.out.println( "Max Profit " + SW.maxChosenProfit); // should be Max
		 // Profit 594669
		 
		 
		 

		// If this gives you a memory heap error / stack overflow , try with a lower
		// number dataset

//		DataLoader.loadArray("kp20.txt", SW.itemWeights, SW.itemProfits, SW.maxCapacity);
//		  
//		SW.maxCapacity= 9819;
//		SW.availableItems = 2000  ;
//		SW.optimizeSelection();
//		System.out.println( "Max Profit " + SW.maxChosenProfit); // should be Max Profit 594669   
//		
//	

		// if it still gives you stack overflow then your code is not optimized enough
		// =)

	}

}
