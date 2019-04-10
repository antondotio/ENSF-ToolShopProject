package Backend.Server;

import java.sql.*;

public class ToolShopDB implements IDBCredentials {

    private Connection conn;

    public ToolShopDB() {
        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            createDatabase();
            createItemTable();
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
        System.out.println("Created table in given database...");
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
}
