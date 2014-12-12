// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

import java.util.Comparator;

/**
 * Comparator object to pass in a method that accepts a comparator.
 * This comparator will sort data structures in a descending order.
 */
public class MaxComparator implements Comparator<Bid> {
	
	// Default Constructor
	public MaxComparator() {}
	
	/**
	 * Comparable<T> required compareTo Method.
	 * 
	 * Returns -1 if the price of this bid is greater than the passed in bid.
	 * Returns 0 if the price of this bid is equal to the passed in bid.
	 * Returns 1 if the price of this bid is smaller than the passed in bid.
	 * 
	 * @param bidOne
	 * @param bidTwo
	 */
	@Override
	public int compare(Bid bidOne, Bid bidTwo) {
		if (bidOne.price() > bidTwo.price()){
			return -1;
		} else if (bidOne.price() < bidTwo.price()) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
