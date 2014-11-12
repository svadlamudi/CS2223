// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

package stock_trading;

import java.util.regex.*;
import java.util.PriorityQueue;
import java.util.ArrayList;

import edu.princeton.cs.introcs.StdIn;

public class Trading {	
	
/* Properties */
	
	/**
	 * Regex Pattern to find the Price and Quantity in read string.
	 */
	private static Pattern priceQuantityInString = Pattern.compile("\\s\\d{1,}");
	
/* Main Method */
	
	/**
	 * Main Loop. Takes input from the command line or a file and checks/performs any
	 * transactions possible to bring market back to equilibrium.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// List to store Transactions performed.
		ArrayList<Bid> transactions = new ArrayList<Bid>(100);
		
		// Buyers and Sellers Queue reflecting the state of the market.
		PriorityQueue<Bid> buyQueue = new PriorityQueue<Bid>(10, new MaxComparator());
		PriorityQueue<Bid> sellQueue = new PriorityQueue<Bid>(10, new MinComparator());
		
		// Read and store all bids entered as strings
		String[] inputBids = StdIn.readAllLines();
		
		// Iterate through all bids entered and add to corresponding buy or sell queue.
		for (String bid : inputBids) {
			if (bid.substring(0, 3).equalsIgnoreCase("buy")) {
				int[] priceQuantity = findPriceQuantityIn(bid);
				buyQueue.add(new Bid(priceQuantity[0], priceQuantity[1]));
			} else if (bid.substring(0, 4).equalsIgnoreCase("sell")) {
				int[] priceQuantity = findPriceQuantityIn(bid);
				sellQueue.add(new Bid(priceQuantity[0], priceQuantity[1]));
			} else {
				System.out.println("Invalid Bid (Buy or Sell Only): " + bid);
				return;
			}
		}
		
		// Perform trades after all bids are entered.
		make_trades(buyQueue, sellQueue, transactions);
		
		// Output the results of the trading.
		System.out.println(transactions.toString());
		System.out.println(sellQueue.toString());
		System.out.println(buyQueue.toString());		
	}

/* Helper Methods */
	
	/**
	 * 
	 * Assume: Input string in valid format and Price and Quantity are integers.
	 * 
	 * Read the input string to find the price and quantity and
	 * return an array with the index of 0 being the price and
	 * index of 1 being the quantity.
	 *
	 * @param inputBidString
	 * @return
	 */
	private static int[] findPriceQuantityIn(String inputBidString) {
		
		int[] priceQuantity = new int[2];
		int i = 0;
		Matcher priceQuantityMatcher = priceQuantityInString.matcher(inputBidString);
		
		while (priceQuantityMatcher.find()) {
			if (priceQuantityMatcher.group().length() != 0) {
				int tempValue = Integer.parseInt(priceQuantityMatcher.group().trim());
				priceQuantity[i] = tempValue;
				if( i < 1 ) { i++; } else { i = 0; }				
			}
		}		
		
		return priceQuantity;
	}
	
	/**
	 * 
	 * Given a buy queue ordered as a MaxPQ and a sell queue ordered as a MinPQ make any
	 * possible trades and add transactions to the given transactions list. This will make
	 * the market in equilibrium.
	 *
	 * @param buyQueue
	 * @param sellQueue
	 * @param transactions
	 * @return
	 */
	public static void make_trades(PriorityQueue<Bid> buyQueue, PriorityQueue<Bid> sellQueue, ArrayList<Bid> transactions) {		
		
		// While the buy and sell queues are not empty try and make trades and record transactions.
		while(buyQueue.peek() != null && sellQueue.peek() != null) {
			
			Bid buyersBid = buyQueue.peek();
			Bid sellersBid = sellQueue.peek();
			
			// Check that there is a seller with the same or lower price than the buyers price.
			if (buyersBid.compareTo(sellersBid) >= 0) {
				
				// Retrieve compatible buyer and seller
				buyersBid = buyQueue.poll();
				sellersBid = sellQueue.poll();
				
				int minimumQuantity = Math.min(buyersBid.quantity(), sellersBid.quantity());
				buyersBid.setQuantity(buyersBid.quantity()-minimumQuantity);
				sellersBid.setQuantity(sellersBid.quantity()-minimumQuantity);
				
				if (buyersBid.quantity() > 0) {
					buyQueue.add(buyersBid);
				}
				if (sellersBid.quantity() > 0) {
					sellQueue.add(sellersBid);
				}
				
				transactions.add(new Bid(sellersBid.price(), minimumQuantity));				
				
			} else {
				// Market is in equilibrium so no more trades possible. Break out of loop.
				break;
			}			
		}
	}
}
