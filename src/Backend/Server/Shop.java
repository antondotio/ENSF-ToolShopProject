package Backend.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * manages the stores inventory, suppliers and communicates with the client
 * 
 * @author Antonio Santos, Julian Pinto
 * @version 1.0
 * @since April 5, 2019
 */
public class Shop implements Runnable {
	/**
	 * inventory of the store
	 */
	private Inventory theInventory;
	/**
	 * list of the stores suppliers
	 */
	private ArrayList<Supplier> supplierList;
	/**
	 * readers that recieves strings sent by the client
	 */
	private BufferedReader socketIn;
	/**
	 * writer that send strings back the the client
	 */
	private PrintWriter socketOut;
	/**
	 * socket that connects to the client
	 */
	private Socket socket;
	/**
	 * stores database
	 */
	private ToolShopDB database;
	private UserDB users;

	/**
	 * assigns the inventory, supplier list and assigns the connection of the socket
	 * 
	 * @param inventory inventory of the store
	 * @param suppliers supplier list of the store
	 * @param s         socket that connects to the client
	 */
	public Shop(Inventory inventory, ArrayList<Supplier> suppliers, Socket s) {

		theInventory = inventory;
		supplierList = suppliers;
		socket = s;
		try {
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * returns the inventory of the store
	 * 
	 * @return inventory of store
	 */
	public Inventory getTheInventory() {
		return theInventory;
	}

	/**
	 * sets the inventory of the store
	 * 
	 * @param inventory inventory of store
	 */
	public void setTheInventory(Inventory inventory) {
		theInventory = inventory;
	}

	/**
	 * returns list of store suppliers
	 * 
	 * @return supplier list
	 */
	public ArrayList<Supplier> getSupplierList() {
		return supplierList;
	}

	/**
	 * sets stores supplier list
	 * 
	 * @param suppliers supplier list
	 */
	public void setSupplierList(ArrayList<Supplier> suppliers) {
		supplierList = suppliers;
	}

	/**
	 * returns a string with a list of the inventories items
	 * 
	 * @return string of all items in the store
	 */
	public String listAllItems() {
		return theInventory.toString();

	}

	/**
	 * decreases the quantity of the item by 1
	 * 
	 * @param name name of item
	 * @return string confirming if the item was decreased or not
	 */
	public String decreaseItem(String name) {
		if (theInventory.manageItem(name) == null)
			return "Couldn't not decrease item quantity!\n";
		else
			return "Item quantity was decreased!\n";
	}

	/**
	 * prints list of suppliers to the console
	 */
	public void listAllSuppliers() {
		for (Supplier s : supplierList) {
			System.out.println(s);
		}

	}

	/**
	 * returns string of information about the item being searched by name
	 * 
	 * @param name name of item being searched
	 * @return string of items information or a message that the item wasn't found
	 */
	public String getItem(String name) {
		Item theItem = theInventory.searchForItem(name);
		if (theItem == null)
			return "Item " + name + " could not be found!";
		else
			return outputItem(theItem);

	}

	/**
	 * returns string of information about the item being searched by id number
	 * 
	 * @param id id number of the item being searched
	 * @return string of items information or a message that the item wasn't found
	 */
	public String getItem(int id) {
		Item theItem = theInventory.searchForItem(id);
		if (theItem == null)
			return "Item number " + id + " could not be found!";
		else
			return outputItem(theItem);

	}

	/**
	 * returns a string with an items information
	 * 
	 * @param theItem item to be output
	 * @return string of items information
	 */
	private String outputItem(Item theItem) {
		return "The item information is as follows: \n" + theItem;
	}

	/**
	 * returns a string of an items quantity
	 * 
	 * @param name item being searched
	 * @return string with items name and quantity or a message saying the item
	 *         wasn't found
	 */
	public String getItemQuantity(String name) {
		int quantity = theInventory.getItemQuantity(name);
		if (quantity < 0)
			return "Item " + name + " could not be found!";
		else
			return "The quantity of Item " + name + " is: " + quantity + "\n";
	}

	/**
	 * returns a string of the orders
	 * 
	 * @return string of orders
	 */
	public String printOrder() {

		return theInventory.printOrder();
	}

	/**
	 * sets the databse
	 * 
	 * @param db database
	 */
	public void setDatabase(ToolShopDB db) {
		this.database = db;
	}

	public void setUsers(UserDB userdb) {
		users = userdb;
	}

	/**
	 * helper function for the search function to identify if it needs to search by
	 * name of id
	 * 
	 * @param in input given by client
	 * @return true if it needs to search by id, false if it needs to search by name
	 */
	private boolean searchByID(String in) {
		try {
			Integer.parseInt(in);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	/**
	 * runs the shop and communicates with the client
	 */
	public void run() {
		String input = "";
		while (true) {
			try {
				input = socketIn.readLine();
				System.out.println(input);
				if (input.equals("GET/TOOL/LIST")) {
					String output = database.getItemList();
					socketOut.println(output);
					socketOut.println("DONE");
				}

				else if (input.equals("GET/TOOL/SEARCH")) {
					String search = socketIn.readLine();
					String output;
					if (searchByID(search)) {
						output = database.getItem(Integer.parseInt(search));
					} else {
						output = database.getItem(search);
					}
					socketOut.println(output);
					socketOut.println("DONE");
				}

				else if (input.equals("GET/TOOL/QUANTITY")) {
					String itemQ = socketIn.readLine();
					String output;
					if (searchByID(itemQ)) {
						output = database.getItemQuantity(Integer.parseInt(itemQ));
					} else {
						output = database.getItemQuantity(itemQ);
					}
					socketOut.println(output);
					socketOut.println("DONE");
				}

				else if (input.equals("TOOL/DECREASE")) {
					String[] split = socketIn.readLine().split("-");
					String itemName = split[0];
					int amount = Integer.parseInt(split[1]);
					String output = database.decreaseItem(itemName, amount);
					socketOut.println(output);
					socketOut.println("DONE");
				}

				else if (input.equals("LOGIN")) {
					String[] split = socketIn.readLine().split("-");
					String username = split[0];
					String password = split[1];
					String output = users.checkLogin(username, password);
					System.out.println(username + password);
					socketOut.println(output);
					socketOut.println("DONE");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
