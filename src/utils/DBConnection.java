
package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Kelsey Deffendol kdeffen@wgu.edu
 */
public class DBConnection {
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ079z9";
    
    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    
    //Driver and Connection Interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;
    
    private static final String username = "U079z9"; //Username
    private static final String password = "53688966985"; //Password
    
    
    /**
     * creates connection to the database
     * @return conn - Connection object
     */
    public static Connection startConnection() {
        try {
           Class.forName(MYSQLJDBCDriver); 
           conn = DriverManager.getConnection(jdbcURL, username, password);
           System.out.println("Connection successful");
           
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
    
    
    /**
     * Closes the connection to the database
     */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed.");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }


    
}
