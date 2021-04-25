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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.DBConnection;
import utils.DBQuery;

/**
 *
 * @author kelsey
 */
public class AppointmentRepository {
    
    /**
     * 
     * @param appointment
     * @throws SQLException 
     */
    public static void addAppointment(Appointment appointment) throws SQLException { //not tested
        Connection conn = DBConnection.startConnection();
        
        String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, "
                + "Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, "
                + "Customer_ID, User_ID, Contact_ID) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        
        DBQuery.setPreparedStatement(conn, insertStatement); //create prepared statement  
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setString(5, appointment.getStartTime().toString());
        ps.setString(6, appointment.getEndTime().toString());
        ps.setString(7, appointment.getCreateDate().toString());
        ps.setString(8, appointment.getCreatedBy());
        ps.setString(9, appointment.getLastUpdate().toString());
        ps.setString(10, appointment.getLastUpdatedBy());
        ps.setInt(11, appointment.getCustomerId());
        ps.setInt(12, appointment.getUserId());
        ps.setInt(13, appointment.getContactId());
        
        ps.execute();
        
    }
    
    /**
     * 
     * @param apptId
     * @throws SQLException 
     */
    public static void deleteAppointment(int apptId) throws SQLException {
        Connection conn = DBConnection.startConnection();
        
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
        
        DBQuery.setPreparedStatement(conn, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setInt(1, apptId);
        
        ps.execute();
        
    }
    
    public static void updateAppointment(Appointment appt) throws SQLException {
        Connection conn = DBConnection.startConnection();
        
        String updateStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?"
                + " WHERE Appointment_ID = ?";
        
        DBQuery.setPreparedStatement(conn, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setString(1, appt.getTitle()); 
        ps.setString(2, appt.getDescription());
        ps.setString(3, appt.getLocation());
        ps.setString(4, appt.getType());
        ps.setString(5, appt.getStartTime().toString());
        ps.setString(6, appt.getEndTime().toString());
        ps.setString(7, appt.getCreateDate().toString());
        ps.setString(8, appt.getCreatedBy());
        ps.setString(9, appt.getLastUpdatedBy());
        ps.setInt(10, appt.getCustomerId());
        ps.setInt(11, appt.getUserId());
        ps.setInt(12, appt.getContactId());
        ps.setInt(13, appt.getAppointmentId());
        
        ps.execute();
    }
    
    
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
    
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        Connection conn = DBConnection.startConnection(); 
        
        String selectStatement = "SELECT * FROM appointments";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.execute();
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        //while loop to add to Appointments List
        while (rs.next() == true) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            
            Appointment appt = new Appointment(id, title, description, location, type, startTime, endTime, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
            
            appointments.add(appt);
        }
        
        return appointments;
        
    }
}
