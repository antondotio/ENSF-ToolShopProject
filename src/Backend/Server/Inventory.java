package Backend.Server;

import java.util.ArrayList;

/**
 * Contains the list of items and methods associated with item procedures
 * 
 * @author Antonio Santos, Julian Pinto
 * @version 1.0
 * @since April 5, 2019
 */
public class Inventory {
	/**
	 * list of items
	 */
	private ArrayList<Item> itemList;
	/**
	 * orders associated with the inventory
	 */
	private Order myOrder;

	/**
	 * assigns a list of items to the inventory and creates an Order object for the inventory
	 * @param itemList item list
	 */
	public Inventory(ArrayList<Item> itemList) {
		this.itemList = itemList;
		myOrder = new Order();
	}

	/**
	 * returns the list of items
	 * @return list of items
	 */
	public ArrayList<Item> getItemList() {
		return itemList;
	}

	/**
	 * sets the list of items
	 * @param itemList list of items
	 */
	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

	/**
	 * decreases the quantity of the item named and places an order of the item if needed
	 * @param name name of item
	 * @return the item decreased
	 */
	public Item manageItem(String name) {
		Item theItem = decreaseItem(name);

		if (theItem != null) {
			placeOrder(theItem);
		}
		return theItem;
	}

	/**
	 * creates an OrderLine for the item given
	 * @param theItem item to be ordered
	 */
	public void placeOrder(Item theItem) {
		OrderLine ol = theItem.placeOrder();
		if (ol != null) {
			myOrder.addOrderLine(ol);
		}
	}

	/**
	 * decreases the item quantity by 1
	 * @param name name of item 
	 * @return refernce to the decreased item
	 */
	private Item decreaseItem(String name) {

		Item theItem = searchForItem(name);

		if (theItem == null)
			return null;

		if (theItem.decreaseItemQuantity() == true) {

			return theItem;
		}
		return null;

	}

	/**
	 * gets the quantity of an item searched
	 * @param name name of item
	 * @return quanity of said item, -1 if the item doesn't exist in the inventory
	 */
	public int getItemQuantity(String name) {
		Item theItem = searchForItem(name);
		if (theItem == null)
			return -1;
		else
			return theItem.getItemQuantity();
	}

	/**
	 * searches for an item in the inventory by name
	 * @param name name of item being searched
	 * @return reference to the item found
	 */
	public Item searchForItem(String name) {
		for (Item i : itemList) {
			if (i.getItemName().equals(name))
				return i;
		}
		return null;
	}

	/**
	 * returns a string of the whole inventory
	 * @return string of all items in the inventory
	 */
	public String toString() {
		String str = "";
		for (Item i : itemList) {
			str += i;
		}
		return str;
	}

	/**
	 * searches for an item in the inventory by id number
	 * @param id id number being searched
	 * @return reference to the item found
	 */
	public Item searchForItem(int id) {
		for (Item i: itemList) {
			if (i.getItemId() == id)
				return i;
		}
		return null;
	}

	/**
	 * returns a string of all orders placed
	 * @return string of all orders from the inventory
	 */
	public String printOrder() {
		return myOrder.toString();
	}

}
