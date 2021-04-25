/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import model.Contact;
import model.Country;
import utils.DBConnection;
import utils.DBQuery;

/**
 *
 * @author kelsey
 */
public class ContactRepository {
    
    /**
     * 
     * @param contactId
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public static Contact getContactByContactId(int contactId) throws SQLException, Exception {
        Connection conn = DBConnection.startConnection(); 
        
        String selectStatement = "SELECT * FROM contacts WHERE Contact_ID = ?";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setInt(1, contactId);
        ps.execute();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        if(rs.next() == false) {
            throw new Exception("ContactId not found");
        }
        
                
        //mapping
        int id = rs.getInt("Contact_ID");
        String contactName = rs.getString("Contact_Name");
        String email = rs.getString("Email");

        
        //make user object
        Contact contact = new Contact(id, contactName, email);
                       
        DBConnection.closeConnection(); //close connection

        
        return contact;  
    }
    
    
}
