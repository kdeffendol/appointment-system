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
import model.Appointment;
import model.User;
import utils.DBConnection;
import utils.DBQuery;

/**
 *
 * @author kelsey
 */
public class AppointmentRepository {
    
    
    
    
    /**
     * 
     * @param appointmentId
     * @return the Appointment object created
     * @throws SQLException
     * @throws Exception if the appointmentId is not found
     */
    public static Appointment getAppointmentByAppointmentId(int appointmentId) throws SQLException, Exception {
        Connection conn = DBConnection.startConnection(); 
        
        String selectStatement = "SELECT * FROM appointment WHERE Appointment_ID = ?";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setInt(1, appointmentId);
        ps.execute();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        if(rs.next() == false) {
            throw new Exception("AppointmentId not found");
        }
        
                
        //mapping
        int id = rs.getInt("Appointment_ID");
        String title = rs.getString("Title");
        String description = rs.getString("Description");
        String location = rs.getString("Location");
        String type = rs.getString("Type");
        LocalDateTime startDate = rs.getTimestamp("Start").toLocalDateTime();
        LocalDateTime endDate = rs.getTimestamp("End").toLocalDateTime();
        LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
        String createdBy = rs.getString("Created_By");
        LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = rs.getString("Last_Updated_By");
        int customerId = rs.getInt("Customer_ID");
        int userId = rs.getInt("User_ID");
        int contactId = rs.getInt("Contact_ID");
       
        
        //make user object
        Appointment appointment = new Appointment(id, title, description, location, type, startDate, endDate, 
                createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
                       
        DBConnection.closeConnection(); //close connection
        
        return appointment;       
    }
}
