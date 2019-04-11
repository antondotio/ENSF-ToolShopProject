package Backend.Server;

import java.util.ArrayList;

/**
 * Contains information of a single supplier
 * 
 * @author Antonio Santos, Julian Pinto
 * @version 1.0
 * @since April 5, 2019
 */
public class Supplier {
	/**
	 * suppliers unique id number
	 */
	private int supId;
	/**
	 * suppliers name
	 */
	private String supName;
	/**
	 * suppliers address
	 */
	private String supAddress;
	/**
	 * suppliers contact name
	 */
	private String supContactName;
	/**
	 * list of items supplied by the supplier
	 */
	private ArrayList <Item> itemList;
	
	/**
	 * Creates a supplier
	 * @param id suppliers id number
	 * @param name suppliers name
	 * @param address suppliers address
	 * @param cont supplier contact 
	 */
	public Supplier (int id, String name, String address, String contactName) {
		
		supId = id;
		supName = name;
		supAddress = address;
		supContactName = contactName;
		itemList = new ArrayList <Item>();
	}
	/**
	 * returns suppliers id number
	 * @return suppliers id number
	 */
	public int getSupId() {
		return supId;
	}

	/**
	 * sets the suppliers id number
	 * @param supId suppliers id number
	 */
	public void setSupId(int supId) {
		this.supId = supId;
	}

	/**
	 * returns suppliers name
	 * @return suppliers name
	 */
	public String getSupName() {
		return supName;
	}

	/**
	 * sets suppliers name
	 * @param supName suppliers name
	 */
	public void setSupName(String supName) {
		this.supName = supName;
	}

	/**
	 * returns address of the supplier
	 * @return suppliers address
	 */
	public String getSupAddress() {
		return supAddress;
	}

	/**
	 * sets thesuppliers address
	 * @param supAddress supllier address
	 */
	public void setSupAddress(String supAddress) {
		this.supAddress = supAddress;
	}

	/**
	 * returns suppliers contact name
	 * @return suppliers contact name
	 */
	public String getSupContactName() {
		return supContactName;
	}

	/**
	 * sets the suppliers contact name
	 * @param supContactName suppliers contact name
	 */
	public void setSupContactName(String supContactName) {
		this.supContactName = supContactName;
	}

	/**
	 * returns a string with the suppliers name and id
	 * @return string with suppliers name and id
	 */
	public String toString () {
		return supName + " Supplier ID: " + supId+ "\n";
		
	}

	/**
	 * returns list of items supplied by the supplier
	 * @return list of items supplied
	 */
	public ArrayList <Item> getItemList() {
		return itemList;
	}

	/**
	 * sets the list of items supplied 
	 * @param itemList list of items supplied
	 */
	public void setItemList(ArrayList <Item> itemList) {
		this.itemList = itemList;
	}
}
