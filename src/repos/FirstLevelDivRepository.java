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
import model.Country;
import model.FirstLevelDivision;
import utils.DBConnection;
import utils.DBQuery;

/**
 *
 * @author kelsey
 */
public class FirstLevelDivRepository {
    public static FirstLevelDivision getDivisionbyId(int divisionId) throws SQLException, Exception {
        Connection conn = DBConnection.startConnection(); 
        
        String selectStatement = "SELECT * FROM first_level_divisions WHERE Divison_ID = ?";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setInt(1, divisionId);
        ps.execute();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        if(rs.next() == false) {
            throw new Exception("DivisionId not found");
        }
        
                
        //mapping
        int id = rs.getInt("Division_ID");
        String divisionName = rs.getString("Divison");
        LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
        String createdBy = rs.getString("Created_By");
        LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = rs.getString("Last_Updated_By");
        int countryId = rs.getInt("Country_ID");
        
        //make user object
        FirstLevelDivision division = new FirstLevelDivision(id, divisionName, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
                       
        DBConnection.closeConnection(); //close connection

        
        return division;
    }
    
    public static ObservableList<FirstLevelDivision> getAllDivisions() throws SQLException {
        Connection conn = DBConnection.startConnection(); 
        
        String selectStatement = "SELECT * FROM first_level_divisions";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.execute();
        
        ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        //while loop to add to Divisions List
        while (rs.next() == true) {
            int divisonId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryId = rs.getInt("Country_ID");
            
            FirstLevelDivision division = new FirstLevelDivision(countryId, divisionName, createDate, createdBy,
                                            lastUpdate, lastUpdatedBy, countryId);
            
            divisions.add(division);
        }
        
        return divisions;
        
    }
}
