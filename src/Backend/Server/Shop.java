package Backend.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Shop implements Runnable {

	private Inventory theInventory;
	private ArrayList<Supplier> supplierList;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private Socket socket;
	private ToolShopDB database;
	private UserDB users;

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

	public Inventory getTheInventory() {
		return theInventory;
	}

	public void setTheInventory(Inventory inventory) {
		theInventory = inventory;
	}

	public ArrayList<Supplier> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(ArrayList<Supplier> suppliers) {
		supplierList = suppliers;
	}

	public String listAllItems() {
		return theInventory.toString();

	}

	public String decreaseItem(String name) {
		if (theInventory.manageItem(name) == null)
			return "Couldn't not decrease item quantity!\n";
		else
			return "Item quantity was decreased!\n";
	}

	public void listAllSuppliers() {
		for (Supplier s : supplierList) {
			System.out.println(s);
		}

	}

	public String getItem(String name) {
		Item theItem = theInventory.searchForItem(name);
		if (theItem == null)
			return "Item " + name + " could not be found!";
		else
			return outputItem(theItem);

	}

	public String getItem(int id) {
		Item theItem = theInventory.searchForItem(id);
		if (theItem == null)
			return "Item number " + id + " could not be found!";
		else
			return outputItem(theItem);

	}

	private String outputItem(Item theItem) {
		return "The item information is as follows: \n" + theItem;
	}

	public String getItemQuantity(String name) {
		int quantity = theInventory.getItemQuantity(name);
		if (quantity < 0)
			return "Item " + name + " could not be found!";
		else
			return "The quantity of Item " + name + " is: " + quantity + "\n";
	}

	public String printOrder() {

		return theInventory.printOrder();
	}

	public void setDatabase(ToolShopDB db) {
		this.database = db;
	}

	public void setUsers(UserDB userdb) {
		users = userdb;
	}

	private boolean searchByID(String in) {
		try {
			Integer.parseInt(in);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public void run() {
		String input = "";
		while (true) {
			try {
				input = socketIn.readLine();
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
					socketOut.println(output);
					socketOut.println("DONE");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
