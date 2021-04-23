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
import model.User;
import utils.DBConnection;
import utils.DBQuery;

/**
 *
 * @author kelsey
 */
public class AppointmentRepository {
    
    
    public static User getAppointmentByAppointmentId(int appointmentId) throws SQLException, Exception {
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
        int userId = rs.getInt("User_ID");
        String user_name = rs.getString("User_Name");
        String password = rs.getString("Password");
        LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
        String createdBy = rs.getString("Created_By");
        LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = rs.getString("Last_Updated_By");
        
        //make user object
        User user = new User(userId, user_name, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
                       
        DBConnection.closeConnection(); //close connection
        
        return user;       
    }
}
