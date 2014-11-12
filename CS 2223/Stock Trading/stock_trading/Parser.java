// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

package stock_trading;

import java.util.PriorityQueue;
import java.util.ArrayList;

public class Parser {	
	
	static ArrayList<Bid> transactions = new ArrayList<Bid>(100);
	static PriorityQueue<Bid> buyQueue = new PriorityQueue<Bid>(10, new MaxComparator());
	static PriorityQueue<Bid> sellQueue = new PriorityQueue<Bid>(10, new MinComparator());
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Bid a = new Bid(10, 10);
		Bid b = new Bid(6, 20);
		Bid c = new Bid(8, 15);
		Bid d = new Bid(9, 12);
		Bid e = new Bid(10, 20);
		Bid f = new Bid(9, 16);
		
		
		
		buyQueue.add(a);
		//make_trades(buyQueue, sellQueue);
		buyQueue.add(b);
		//make_trades(buyQueue, sellQueue);
		sellQueue.add(c);
		//make_trades(buyQueue, sellQueue);
		buyQueue.add(d);
		//make_trades(buyQueue, sellQueue);
		sellQueue.add(e);
		//make_trades(buyQueue, sellQueue);
		sellQueue.add(f);
		make_trades();
		System.out.println(buyQueue.toString());
		System.out.println(sellQueue.toString());
		System.out.println(transactions.toString());
		
	}
	
	
	public static ArrayList<Bid> make_trades() {
		
		while(buyQueue.peek() != null && sellQueue.peek() != null) {
			
			Bid buyersBid = buyQueue.peek();
			Bid sellersBid = sellQueue.peek();
			
			if (buyersBid.compareTo(sellersBid) >= 0) {
				
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
				break;
			}
			
		}
		
		return transactions;
		
	}

}
