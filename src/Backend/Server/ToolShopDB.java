package Backend.Server;

import java.sql.*;

public class ToolShopDB implements IDBCredentials {

    private Connection conn;
    private Statement stmt;

    public ToolShopDB() {
        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            createDatabase();
        } catch (SQLException sqle) {
            System.out.println("SQL Problem");
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDatabase() throws SQLException {
        String sql_stmt = "CREATE DATABASE IF NOT EXISTS `toolshop_db`;";
        stmt = conn.createStatement();
        stmt.executeUpdate(sql_stmt);
        System.out.println("toolshop_db has successfully been created");

    }
}
