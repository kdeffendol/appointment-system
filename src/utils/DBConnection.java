/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kelsey
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


    
}
