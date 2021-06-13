package Storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DataLoader {

	public static void loadArray(String file, ArrayList<Integer> weightArray, ArrayList<Integer> priceArray,int maxWeight) {
		int ctr = 0;
		System.out.println(ctr);

		try {
			Scanner s1 = new Scanner(new File(file));
			while (s1.hasNextLine()) {
				ctr = ctr + 1;
				s1.nextLine();
				// System.out.println(ctr);
			}
			s1.close();

			Scanner s2 = new Scanner(new File(file));

			// Empty the lists
			weightArray.clear();
			priceArray.clear();

			for (int i = 0; i < ctr; i++) {

				weightArray.add(s2.nextInt());
				priceArray.add(s2.nextInt());
			}
			maxWeight = 997;

			s2.close();
			return;

		}

		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Couldn't load file");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
