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
public class UserDB implements IDBCredentials {

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
    public UserDB() {
        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            createDatabase();
            // createTable();
        } catch (SQLException sqle) {
            System.out.println("SQL Problem");
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDatabase() throws SQLException {
        String sql_stmt = "CREATE DATABASE IF NOT EXISTS `user_db`;";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql_stmt);
        sql_stmt = "USE `user_db`;";
        stmt = conn.createStatement();
        stmt.executeUpdate(sql_stmt);
        stmt.close();
        System.out.println("user_db has successfully been created");
    }

    public void createTable() {
        String sql = "CREATE TABLE USERS " + "(username VARCHAR(225), " + " password VARCHAR(255), "
                + " PRIMARY KEY (username))";

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException sqle) {
            System.out.println("Could NOT create table!");
            sqle.printStackTrace();
        }
        System.out.println("Created users table in given database...");
    }

    public String checkLogin(String u, String p) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM USERS WHERE username = '" + u + "' and password ='" + p + "' LIMIT 1";
            rs = stmt.executeQuery(query);
            String status = "";
            if (rs.next()) {
                status = "Logged In";
            }
            return status;
        } catch (SQLException sqle) {
            System.out.println("Error logging in");
            sqle.printStackTrace();
        }
        return "Invalid";
    }
}