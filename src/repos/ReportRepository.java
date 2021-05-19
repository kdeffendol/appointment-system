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
import model.MonthTypeReport;
import model.ScheduleReport;
import utils.DBConnection;
import utils.DBQuery;

/**
 *
 * @author kelsey
 */
public class ReportRepository {
    public static ObservableList<MonthTypeReport> getAllMonthTypeReports() throws SQLException {
        Connection conn = DBConnection.startConnection();
        
        String selectStatement = "SELECT COUNT(*), Type, month(Start) " +
            "FROM appointments " +
            "GROUP BY Type, month(Start)";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement  
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.execute();
        
        ObservableList <MonthTypeReport> monthTypeReports = FXCollections.observableArrayList();   
        ResultSet rs = ps.getResultSet(); //get result set
        
        while (rs.next() == true) {
            int count = rs.getInt("COUNT(*)");
            String type = rs.getString("Type");
            int month = rs.getInt("month(Start)");
            
            
            MonthTypeReport monthTypeReport = new MonthTypeReport(count, type, month);
            
            monthTypeReports.add(monthTypeReport);
        }
        
        return monthTypeReports;
    }
    
    public static ObservableList<ScheduleReport> getAllScheduleReports() throws SQLException {
        Connection conn = DBConnection.startConnection();
        
        String selectStatement = "SELECT contacts.Contact_Name, appointments.Appointment_ID, appointments.Title, appointments.Type, appointments.Description, appointments.Start, appointments.End, appointments.Customer_ID " +
            "FROM appointments " +
            "INNER JOIN contacts " +
            "ON contacts.Contact_ID = appointments.Contact_ID " +
            "ORDER BY contacts.Contact_Name, appointments.Start";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement  
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.execute();
        
        ObservableList <ScheduleReport> scheduleReports = FXCollections.observableArrayList();   
        ResultSet rs = ps.getResultSet(); //get result set
        
        while (rs.next() == true) {
            String contactName = rs.getString("Contact_Name");
            int apptId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String type = rs.getString("Type");
            String description = rs.getString("Description");
            LocalDateTime startDate = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDate = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            
            
            ScheduleReport scheduleReport = new ScheduleReport(contactName, apptId, title, type, description, startDate, endDate, customerId);
            
            scheduleReports.add(scheduleReport);
        }
        
        return scheduleReports;
    }
}
