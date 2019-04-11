package Backend.Server;

/**
 * A single order for an item
 * 
 * @author Antonio Santos, Julian Pinto
 * @version 1.0
 * @since April 5, 2019
 */
public class OrderLine {
	/**
	 * item being ordered
	 */
	private Item theItem;
	/**
	 * quantity to order
	 */
	private int orderQuantity;
	
	/**
	 * creates an order for the item
	 * @param item item being ordered
	 * @param quantity quantity being ordered
	 */
	public OrderLine (Item item, int quantity) {
		theItem = item;
		setOrderQuantity(quantity); 
		
	}

	/**
	 * gets the item being ordered
	 * @return item being ordered
	 */
	public Item getTheItem() {
		return theItem;
	}

	/**
	 * sets the item being ordered
	 * @param theItem item being ordered
	 */
	public void setTheItem(Item theItem) {
		this.theItem = theItem;
	}

	/**
	 * gets the order quantity
	 * @return order quantity
	 */
	public int getOrderQuantity() {
		return orderQuantity;
	}

	/**
	 * sets the order quantity
	 * @param orderQuantity order quantity
	 */
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	/**
	 * returns a string with the description of the order
	 * @return string with orders information
	 */
	public String toString (){
		return  "Item Name: " + theItem.getItemName() +
				", Item ID: " + theItem.getItemId()+ "\n" + 
				"Order Quantity: " + orderQuantity + "\n";
	}

}
