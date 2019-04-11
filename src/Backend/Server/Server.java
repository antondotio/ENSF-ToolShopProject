package Backend.Server;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private ServerSocket serverSocket;
	private ExecutorService pool;
	private ToolShopDB database;

	public Server(int serv) {
		try {
			database = new ToolShopDB();
			serverSocket = new ServerSocket(serv);
			pool = Executors.newCachedThreadPool();
			System.out.println("Shop is running");

		} catch (IOException e) {
			System.err.println("Server Error");
			e.printStackTrace();
		}
	}

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

	private void communicate() {
		ArrayList<Supplier> suppliers = new ArrayList<>();
		suppliers = this.readSuppliers(suppliers);
		Inventory theInventory = new Inventory(this.readItems(suppliers));
		while (true) {
			try {
				Shop theShop = new Shop(theInventory, suppliers, serverSocket.accept());
				theShop.setDatabase(database);
				pool.execute(theShop);
			} catch (IOException e) {
				e.printStackTrace();
				pool.shutdown();
			}
		}
	}

	public static void main(String[] args) {
		Server shopServer = new Server(8897);
		shopServer.communicate();

	}
}