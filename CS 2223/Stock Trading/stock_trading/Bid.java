// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

package stock_trading;

public class Bid implements Comparable<Bid> {
	
/* Interface Required Methods */
	
	/**
	 * Comparable<T> compareTo Method
	 * 
	 * @param otherBid
	 */
	@Override
	public int compareTo(Bid otherBid) {
		if (this.price > otherBid.price) {
			return 1;
		} else if (this.price < otherBid.price) {
			return -1;
		} else {
			return 0;
		}
	}
	
/* Object Properties */
	
	private int price;
	private int quantity;
	
/* Constructors */	
	
	/**
	 * Default Constructor.
	 */
	public Bid() {
		this(0, 0);
	}
	
	/**
	 * Constructor which sets the object properties based on the parameter values.
	 * 
	 * @param price
	 * @param quantity
	 */
	public Bid(int price, int quantity) {
		this.price = price;
		this.quantity = quantity;
	}
	
/* Accessors and Mutators */
	
	/**
	 * Accessor for the price property.
	 * 
	 * @return
	 */
	public int price() {
		return this.price;
	}
	
	/**
	 * Mutator for the price property.
	 * 
	 * @param price
	 */
	public void setPrice(int price) {
		this.price = price;
	}	
	
	/**
	 * Accessor for the quantity property.
	 * 
	 * @return
	 */
	public int quantity() {
		return quantity;
	}

	/**
	 * Mutator for the quantity property.
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
/* Helper Methods */
		
	@Override
	public String toString() {
		return "Bid has Price: " + this.price + " and Quantity: " + this.quantity;
	}
}
