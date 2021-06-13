package Storage;

import java.io.Console;
import java.util.ArrayList;

public class SubWarehouse {

	int maxCapacity;
	ArrayList<Integer> itemWeights = new ArrayList<>();
	ArrayList<Integer> itemProfits = new ArrayList<>();
	int maxChosenProfit;
	int availableItems;

	public void optimizeSelection() {
		// TODO: Create a method that optimizes the selection of items in order to
		// produce the most profit and make the most out of the storage space.

		// Your code here
		// You're allowed to use any other pre-defined methods.
		maxChosenProfit = 0; // Update with your answer.
		int currentCapacity = 0;
		ArrayList<ArrayList<Integer>> subsets = new ArrayList<>();
		ArrayList<Integer> result = new ArrayList<>();
		if (itemProfits.size() == 10000)
		{
				System.out.println("This code is not optimal for step 2 at all, generating 2^10000 arrays takes some time.");
				return;
			}
		for (int i = 0; i < 1 << itemProfits.size(); i++) {
			ArrayList<Integer> subset = new ArrayList<>();
			for (int j = 0; j < itemProfits.size(); j++) {
				if ((i & (1 << j)) > 0)
					subset.add(itemProfits.get(j));
			}
			subsets.add(subset);

		}
		for (ArrayList curSet : subsets) {
			int capacity = getCapacity(curSet);
			int profit = getProfit(curSet);

			if (profit > maxChosenProfit && curSet.size() <= availableItems && capacity <= maxCapacity
					&& capacity > currentCapacity) {
				currentCapacity = capacity;
				result = curSet;
				maxChosenProfit = profit;
			}

		}
		System.out.println(currentCapacity + "   " + maxChosenProfit + "  " + result);

		// TODO: Enter your GUC mail here
		String email = "donia.sharaf@student.guc.edu.eg";
		System.out.println("Email: " + email);
	}

	public static int max(int a, int b) {
		return (a > b) ? a : b;
	}

	public int getCapacity(ArrayList<Integer> set) {
		ArrayList<Integer> alteredProfits = new ArrayList<>();
		alteredProfits.addAll(itemProfits);
		int result = 0;
		for (int i : set) {
			result += itemWeights.get(alteredProfits.indexOf(i));
			alteredProfits.set(alteredProfits.indexOf(i), -10);
		}
		return result;
	}

	public int getProfit(ArrayList<Integer> profits) {
		int result = 0;
		for (int i : profits) {
			result += i;
		}
		return result;
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

		// DataLoader.loadArray("WarehouseFacility/kp100.txt", SW.itemWeights, SW.itemProfits,SW.maxCapacity);

		// SW.maxCapacity = 49877;
		// SW.availableItems = 10000 ; 
		//  SW.optimizeSelection();
		// System.out.println( "Max Profit " + SW.maxChosenProfit); // should be Max Profit 594669

	}

}
