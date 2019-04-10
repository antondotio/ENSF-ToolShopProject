package Backend.Server;

import java.sql.*;

public class ToolShopDB implements IDBCredentials {

    private Connection conn;
    private ResultSet rs;

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

    public String getItems() {
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
            System.out.println("Error getting item");
            sqle.printStackTrace();
        }
        return "";
    }
}
