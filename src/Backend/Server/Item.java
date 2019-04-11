package Backend.Server;

/**
 * information about a single item
 * 
 * @author Antonio Santos, Julian Pinto
 * @version 1.0
 * @since April 5, 2019
 */
public class Item {

	/**
	 * id number of item
	 */
	private int itemId;
	/**
	 * name of item
	 */
	private String itemName;
	/**
	 * quantity of item
	 */
	private int itemQuantity;
	/**
	 * price of the item
	 */
	private double itemPrice;
	/**
	 * if the items been ordered or not
	 */
	private boolean alreadyOrdered;
	/**
	 * supplier of the item
	 */
	private Supplier theSupplier;
	/**
	 * quantity to order
	 */
	private static final int ORDERQUANTITY = 40;
	/**
	 * minimum quantity before it needs to be ordered
	 */
	private static final int MINIMUMUMBER = 20;

	/**
	 * creates an item
	 * @param id id of item
	 * @param name name of item
	 * @param quanitiy quantity of item
	 * @param price price of item
	 * @param sup supplier of the item
	 */
	public Item(int id, String name, int quanitiy, double price, Supplier sup) {

		itemId = id;
		itemName = name;
		itemQuantity = quanitiy;
		itemPrice = price;
		sup = theSupplier;
		setAlreadyOrdered(false);
	}

	/**
	 * decreases the item quantity by 1
	 * @return true if the item was decreased, false if it was unable to
	 */
	public boolean decreaseItemQuantity() {
		if (itemQuantity > 0) {
			itemQuantity--;
			return true;
		} else
			return false;

	}

	/**
	 * places an order for the item
	 * @return OrderLine of the items order
	 */
	public OrderLine placeOrder() {
		OrderLine ol;
		if (getItemQuantity() < MINIMUMUMBER && alreadyOrdered == false) {
			ol = new OrderLine(this, ORDERQUANTITY);
			alreadyOrdered = true;
			return ol;
		}
		return null;
	}

	/**
	 * gets items id
	 * @return items id number
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * sets item id 
	 * @param itemId item id
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * gets name of string 
	 * @return items name
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * sets item name
	 * @param itemName items name
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * gets items quantity
	 * @return items quantity
	 */
	public int getItemQuantity() {
		return itemQuantity;
	}

	/**
	 * sets items quantity
	 * @param itemQuantity new item quantity
	 */
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	/**
	 * gets the items price
	 * @return item price
	 */
	public double getItemPrice() {
		return itemPrice;
	}

	/**
	 * sets items price
	 * @param itemPrice items price
	 */
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	/**
	 * sets items supplier
	 * @param sup item supplier
	 */
	public void setTheSupplier(Supplier sup) {
		theSupplier = sup;
	}

	/**
	 * gets supplier of item
	 * @return items supplier
	 */
	public Supplier getTheSupplier() {
		return theSupplier;
	}

	/**
	 * returns a string of the items information
	 * @return string of items information
	 */
	public String toString() {
		return "Item ID: " + itemId + ",\tItem Name: " + itemName + ",\tItem Quantity: " + itemQuantity + "\n";
	}

	/**
	 * checks if the items been ordered
	 * @return state of alreadyOrdered
	 */
	public boolean isAlreadyOrdered() {
		return alreadyOrdered;
	}

	/**
	 * sets alreadyOrdered
	 * @param alreadyOrdered new state of alreadyOrdered
	 */
	public void setAlreadyOrdered(boolean alreadyOrdered) {
		this.alreadyOrdered = alreadyOrdered;
	}
}
