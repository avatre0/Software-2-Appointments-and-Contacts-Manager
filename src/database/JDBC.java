package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Helper class to manage connection to the db
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location +databaseName + "?connectionTimeZone = SERVER"; //LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver Reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; //Password
    private static Connection connection = null;

    /**
     * Opens a connection to the DB
     * @return a connection object
     */
    public static Connection openConnection()
    {
        try
        {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection Object
            System.out.println("Connection Successful!");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    /**
     * Closes the connection to the DB
     */
    public static void closeConnection()
    {
        try
        {
         connection.close();
         System.out.println("Connection Closed");
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Holds the connection to the DB
     * @return Connection to the DB
     */
    public  static Connection getConnection()
    {
        return connection;
    }
}
