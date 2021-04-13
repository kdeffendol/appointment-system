/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author kelsey
 */
public class DBQuery {
    
    private static Statement statement; //Statement reference

    /**
     * Create statement object
     * @param conn - Connection object
     * @throws SQLException 
     */
    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }
    
    /**
     * @return Statement object
     */
    public static Statement getStatement() {
        return statement;
    }
    
}
