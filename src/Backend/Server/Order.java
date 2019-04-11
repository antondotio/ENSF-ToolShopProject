package Backend.Server;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Contains a list of OrderLines and order information
 * 
 * @author Antonio Santos, Julian Pinto
 * @version 1.0
 * @since April 5, 2019
 */
public class Order {
	/**
	 * date of order
	 */
	private Date today;
	/**
	 * id number of the order
	 */
	private int orderId;
	/**
	 * list of item orders
	 */
	private ArrayList <OrderLine> orderLines;
	
	/**
	 * creates a list for the orderlines and sets the date to the current date
	 */
	public Order () {
		today = Calendar.getInstance().getTime();
		orderLines = new ArrayList <OrderLine> ();
	}
	
	/**
	 * adds an OrderLine to the list
	 * @param ol item OrderLine
	 */
	public void addOrderLine (OrderLine ol) {
		orderLines.add(ol);
	}
	
	/**
	 * gets the list of OrderLine
	 * @return list of OrderLine
	 */
	public ArrayList <OrderLine> getOrderLines (){
		return orderLines;
	}

	/**
	 * sets the list of OrderLines
	 * @param lines list of OrderLines
	 */
	public void setOrderLines (ArrayList <OrderLine> lines){
		orderLines = lines;
	}

	/**
	 * gets the orders id number
	 * @return order number
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * sets the orders id number
	 * @param orderId order id number
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**
	 * returns a string with the order
	 * @return string with all orders information
	 */
	public String toString (){
		String order = "Order Date: " + today.toString() + "\n\n";
		String str = "";
		for (OrderLine ol: orderLines) {
			str += ol;
			str += "------------------------\n";
		}
		if (str == "")
			str = "here are corrently no orderlines";
		
		order += str;
		order += "\n";
		return order;
	}

}
