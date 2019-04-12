package Backend.Server;

import java.sql.*;

/**
 * The Database controller. Responsible for updating and creating the database
 * as well as holding the functions for retrieving information form the database
 * 
 * @author Antonio Santos, Julian Pinto
 * @version 1.0
 * @since April 11, 2019
 */
public class ToolShopDB implements IDBCredentials {

    /**
     * the connection to the database.
     */
    private Connection conn;
    /**
     * The result set for the item searched for/ updated.
     */
    private ResultSet rs;

    /**
     * default constructor for the database controller. connects the database to the
     * program.
     */
    public ToolShopDB() {
        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            createDatabase();
            // createItemTable(); commented out once created
            // createSupplierTable();
        } catch (SQLException sqle) {
            System.out.println("SQL Problem");
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * creates the items table
     */
    public void createItemTable() {
        String sql = "CREATE TABLE ITEMS " + "(id INTEGER not NULL, " + " name VARCHAR(255), "
                + " quantity INTEGER not NULL, " + " price DOUBLE not NULL, " + " suppID INTEGER not NULL, "
                + " PRIMARY KEY (id))";

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException sqle) {
            System.out.println("Could NOT create table!");
            sqle.printStackTrace();
        }
        System.out.println("Created item table in given database...");
    }

    /**
     * creates the suppliers table
     */
    public void createSupplierTable() {
        String sql = "CREATE TABLE SUPPLIERS " + "(id INTEGER not NULL, " + " name VARCHAR(255), "
                + " address VARCHAR(255), " + " contName VARCHAR(255), " + " PRIMARY KEY (id))";

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException sqle) {
            System.out.println("Could NOT create table!");
            sqle.printStackTrace();
        }
        System.out.println("Created supplier table in given database...");
    }

    /**
     * creates the database
     */
    public void createDatabase() throws SQLException {
        String sql_stmt = "CREATE DATABASE IF NOT EXISTS `toolshop_db`;";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql_stmt);
        sql_stmt = "USE `toolshop_db`;";
        stmt = conn.createStatement();
        stmt.executeUpdate(sql_stmt);
        stmt.close();
        System.out.println("toolshop_db has successfully been created");
    }

    /**
     * inserts the item into the database.
     * 
     * @param id       id of item
     * @param name     name of item
     * @param quantity quantity of item
     * @param price    price of item
     * @param suppID   supplier id of item
     */
    public void insertItem(int id, String name, int quantity, double price, int suppID) {
        try {
            String query = "INSERT INTO ITEMS (id, name, quantity, price, suppID) values(?, ?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, id);
            pStmt.setString(2, name);
            pStmt.setInt(3, quantity);
            pStmt.setDouble(4, price);
            pStmt.setInt(5, suppID);

            int rowCount = pStmt.executeUpdate();
            System.out.println("row Count = " + rowCount);
            pStmt.close();
        } catch (SQLException sqle) {
            System.out.println("Error inserting in table");
            sqle.printStackTrace();
        }
    }

    /**
     * inserts the supplier to the database
     * 
     * @param id       id of supplier
     * @param name     company name of supplier
     * @param address  address of supplier
     * @param contName contact name for supplier
     */
    public void insertSupplier(int id, String name, String address, String contName) {
        try {
            String query = "INSERT INTO SUPPLIERS (id, name, address, contName) values(?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, id);
            pStmt.setString(2, name);
            pStmt.setString(3, address);
            pStmt.setString(4, contName);

            int rowCount = pStmt.executeUpdate();
            System.out.println("row Count = " + rowCount);
            pStmt.close();
        } catch (SQLException sqle) {
            System.out.println("Error inserting in table");
            sqle.printStackTrace();
        }
    }

    /**
     * Gets all the items from the database and turns it into a list
     * 
     * @return String for the list of items, or an error otherwise
     */
    public String getItemList() {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM ITEMS";
            rs = stmt.executeQuery(query);
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Item ID: " + rs.getInt("id") + ",\tItem Name: " + rs.getString("name") + ",\tItem Quantity: "
                        + rs.getInt("quantity") + "\n");
            }
            return sb.toString();
        } catch (SQLException sqle) {
            System.out.println("Error getting item list");
            sqle.printStackTrace();
        }
        return "Error getting list of tools";
    }

    /**
     * Gets a specific item that is being searched for, using the name
     * 
     * @param n name of item being searched
     * @return String of the information about the item, error otherwise
     */
    public String getItem(String n) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM ITEMS WHERE name = '" + n + "' LIMIT 1";
            rs = stmt.executeQuery(query);
            String item = "";
            if (rs.next()) {
                item = "Item ID: " + rs.getInt("id") + ", Item Name: " + rs.getString("name") + ", Item Quantity: "
                        + rs.getInt("quantity") + ", Item Price: $" + String.format("%.2f", rs.getDouble("price"))
                        + ", Supplier ID: " + rs.getInt("suppID");
            }
            return item;
        } catch (SQLException sqle) {
            System.out.println("Error getting item");
            sqle.printStackTrace();
        }
        return "Error searching of tool";
    }

    /**
     * Gets a specific item that is being searched for, using the id
     * 
     * @param i id of item being searched
     * @return String of the information about the item, error otherwise
     */
    public String getItem(int i) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM ITEMS WHERE id = '" + i + "' LIMIT 1";
            rs = stmt.executeQuery(query);
            String item = "";
            if (rs.next()) {
                item = "Item ID: " + rs.getInt("id") + ", Item Name: " + rs.getString("name") + ", Item Quantity: "
                        + rs.getInt("quantity") + ", Item Price: $" + String.format("%.2f", rs.getDouble("price"))
                        + ", Supplier ID: " + rs.getInt("suppID");
            }
            return item;
        } catch (SQLException sqle) {
            System.out.println("Error getting item");
            sqle.printStackTrace();
        }
        return "Error searching of tool";
    }

    /**
     * Gets the quantity of a certain item using the name
     * 
     * @param n name of item
     * @return String for the quantity, error otherwise
     */
    public String getItemQuantity(String n) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM ITEMS WHERE name = '" + n + "' LIMIT 1";
            rs = stmt.executeQuery(query);
            String itemQ = "";
            if (rs.next()) {
                itemQ = "Item Quantity: " + rs.getInt("quantity");
            }
            return itemQ;
        } catch (SQLException sqle) {
            System.out.println("Error getting item");
            sqle.printStackTrace();
        }
        return "Error searching of tool";
    }

    /**
     * Gets the quantity of a certain item using the id
     * 
     * @param i id of the item
     * @return String for the quantity, error otherwise
     */
    public String getItemQuantity(int i) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM ITEMS WHERE id = '" + i + "' LIMIT 1";
            rs = stmt.executeQuery(query);
            String itemQ = "";
            if (rs.next()) {
                itemQ = "Item Quantity: " + rs.getInt("quantity");
            }
            return itemQ;
        } catch (SQLException sqle) {
            System.out.println("Error getting item");
            sqle.printStackTrace();
        }
        return "Error searching of tool";
    }

    /**
     * Decreases the quantity of a certain item
     * 
     * @param n      name of the item
     * @param amount amount to be decreased
     * @return returns the status of the function.
     */
    synchronized public String decreaseItem(String n, int amount) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM ITEMS WHERE name = '" + n + "' LIMIT 1";
            rs = stmt.executeQuery(query);
            int quantity = 0;
            if (rs.next()) {
                quantity = rs.getInt("quantity");
            }
            if (quantity < amount) {
                return "Invalid";
            }
            quantity -= amount;
            Statement myStmt = conn.createStatement();
            String myQuery = "UPDATE ITEMS SET quantity = '" + quantity + "' WHERE name = '" + n + "'";
            myStmt.executeUpdate(myQuery);
            if (quantity < 40) {
                updateQuantity(n, quantity);
            }

            return "Item Decreased";
        } catch (SQLException sqle) {
            System.out.println("Error decreasing item quantity");
            sqle.printStackTrace();
        }
        return "Error decreasing item quantity";
    }

    /**
     * updates the quantity if the quantity of an item is below 40
     * 
     * @param n        name of item
     * @param quantity quantity of item.
     */
    public void updateQuantity(String n, int quantity) {
        try {
            quantity = 50 - quantity;
            System.out.println("Stock for " + n + " has been replenished.");
            Statement myStmt = conn.createStatement();
            String myQuery = "UPDATE ITEMS SET quantity = '" + quantity + "' WHERE name = '" + n + "'";
            myStmt.executeUpdate(myQuery);
        } catch (SQLException sqle) {
            System.out.println("Error restocking item");
            sqle.printStackTrace();
        }
    }
}
