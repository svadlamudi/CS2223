// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

package stock_trading;

import java.util.Comparator;

public class MaxComparator implements Comparator<Bid> {

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
