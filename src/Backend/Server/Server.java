package Backend.Server;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server side of the application. Will create thread pool for the clients to
 * use and create the database for the application
 * 
 * @author Antonio Santos, Julian Pinto
 * @version 1.0
 * @since April 5, 2019
 */
public class Server {
	/**
	 * servers socket
	 */
	private ServerSocket serverSocket;
	/**
	 * pool of threads for the clients to connect to
	 */
	private ExecutorService pool;
	/**
	 * database of the shop
	 */
	private ToolShopDB database;
	private UserDB users;

	/**
	 * creates the database, assigns the serversocket to its port, and creates the
	 * pool of threads for the clients
	 * 
	 * @param serv port number of the server
	 */
	public Server(int serv) {
		try {
			database = new ToolShopDB();
			users = new UserDB();
			serverSocket = new ServerSocket(serv);
			pool = Executors.newCachedThreadPool();
			System.out.println("Shop is running");

		} catch (IOException e) {
			System.err.println("Server Error");
			e.printStackTrace();
		}
	}

	/**
	 * reads the items from a text file, connects the items to the suppliers and
	 * transfers it to the database
	 * 
	 * @param suppliers list of suppliers
	 * @return list of items
	 */
	private ArrayList<Item> readItems(ArrayList<Supplier> suppliers) {

		ArrayList<Item> items = new ArrayList<Item>();

		try {
			FileReader fr = new FileReader("items.txt");
			BufferedReader br = new BufferedReader(fr);

			String line = "";
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(";");
				int supplierId = Integer.parseInt(temp[4]);

				Supplier theSupplier = findSupplier(supplierId, suppliers);
				if (theSupplier != null) {
					Item myItem = new Item(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]),
							Double.parseDouble(temp[3]), theSupplier);
					items.add(myItem);
					theSupplier.getItemList().add(myItem);
					// database.insertItem(Integer.parseInt(temp[0]), temp[1],
					// Integer.parseInt(temp[2]),
					// Double.parseDouble(temp[3]), theSupplier.getSupId());

					// items already inserted in database
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return items;
	}

	/**
	 * searches for a supplier by id number
	 * 
	 * @param supplierId suppliers id
	 * @param suppliers  list of the suppliers
	 * @return reference to the suppler if found
	 */
	private Supplier findSupplier(int supplierId, ArrayList<Supplier> suppliers) {
		Supplier theSupplier = null;
		for (Supplier s : suppliers) {
			if (s.getSupId() == supplierId) {
				theSupplier = s;
				break;
			}
		}
		return theSupplier;
	}

	/**
	 * reads the suppliers from the text file and transfers the information to the
	 * database
	 * 
	 * @param suppliers list of suppliers to add to
	 * @return list of updated suppliers
	 */
	private ArrayList<Supplier> readSuppliers(ArrayList<Supplier> suppliers) {

		try {
			FileReader fr = new FileReader("suppliers.txt");
			BufferedReader br = new BufferedReader(fr);

			String line = "";
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(";");
				suppliers.add(new Supplier(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3]));
				// database.insertSupplier(Integer.parseInt(temp[0]), temp[1], temp[2],
				// temp[3]);
				// suppliers already inserted in the DB
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return suppliers;
	}

	/**
	 * creates teh suppliers and inventory then runs the server and creates threads
	 * for any new client that connects to it
	 */
	private void communicate() {
		ArrayList<Supplier> suppliers = new ArrayList<>();
		suppliers = this.readSuppliers(suppliers);
		Inventory theInventory = new Inventory(this.readItems(suppliers));
		while (true) {
			try {
				Shop theShop = new Shop(theInventory, suppliers, serverSocket.accept());
				theShop.setDatabase(database);
				theShop.setUsers(users);
				pool.execute(theShop);
			} catch (IOException e) {
				e.printStackTrace();
				pool.shutdown();
			}
		}
	}

	/**
	 * creates the server and runs it
	 * 
	 * @param args console inputs
	 */
	public static void main(String[] args) {
		Server shopServer = new Server(9788);
		shopServer.communicate();

	}
}