// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

import java.util.Comparator;

/**
 * Comparator object to pass in a method that accepts a comparator.
 * This comparator will sort data structures in an ascending order.
 */
public class MinComparator implements Comparator<Edge> {

	// Default Constructor
	public MinComparator() {}
	
	/**
	 * Comparable<T> required compareTo Method.
	 * 
	 * Returns 1 if the price of this bid is greater than the passed in bid.
	 * Returns 0 if the price of this bid is equal to the passed in bid.
	 * Returns -1 if the price of this bid is smaller than the passed in bid.
	 * 
	 * @param edgeOne
	 * @param edgeTwo
	 */
	@Override
	public int compare(Edge edgeOne, Edge edgeTwo) {
		if (edgeOne.weight() > edgeTwo.weight()){
			return 1;
		} else if (edgeOne.weight() < edgeTwo.weight()) {
			return -1;
		} else {
			return 0;
		}
	}
	
}
