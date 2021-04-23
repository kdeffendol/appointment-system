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
import model.Country;
import model.User;
import utils.DBConnection;
import utils.DBQuery;

/**
 *
 * @author kelsey
 */
public class CountryRepository {
    public static Country getCountryByCountryId(int countryId) throws SQLException, Exception {
        Connection conn = DBConnection.startConnection(); 
        
        String selectStatement = "SELECT * FROM countries WHERE Country_ID = ?";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setInt(1, countryId);
        ps.execute();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        if(rs.next() == false) {
            throw new Exception("CountryId not found");
        }
        
                
        //mapping
        int id = rs.getInt("Country_ID");
        String countryName = rs.getString("Country");
        LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
        String createdBy = rs.getString("Created_By");
        LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = rs.getString("Last_Updated_By");
        
        //make user object
        Country country = new Country(id, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);
                       
        DBConnection.closeConnection(); //close connection

        
        return country;
        
    }
    
}
