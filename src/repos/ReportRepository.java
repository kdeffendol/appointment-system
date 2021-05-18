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
    
    public static 
}
