/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 *
 * @author kelsey
 */
public class DBQuery {
    
    private static PreparedStatement statement; //Statement reference

    /**
     * Create statement object
     * @param conn - Connection object
     * @throws SQLException 
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }
    
    /**
     * @return Statement object
     */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
    
}
