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
import utils.DBConnection;
import utils.DBQuery;

/**
 *
 * @author Kelsey Deffendol - kdeffen@wgu.edu
 */
public class CountryRepository {
    
    /**
     * Returns a Country from its given Country ID
     * @param countryId
     * @return a Country object from its given Country ID
     * @throws SQLException
     * @throws Exception 
     */
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
    
    /**
     * Find and return a country based on its name.
     * @param countryName
     * @return country object by the given country name
     * @throws SQLException
     * @throws Exception 
     */
    public static Country getCountryByCountryName(String countryName) throws SQLException, Exception {
        Connection conn = DBConnection.startConnection(); 
        
        String selectStatement = "SELECT * FROM countries WHERE Country = ?";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setString(1, countryName);
        ps.execute();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        if(rs.next() == false) {
            throw new Exception("CountryName not found");
        }
        
                
        //mapping
        int id = rs.getInt("Country_ID");
        String country_Name = rs.getString("Country");
        LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
        String createdBy = rs.getString("Created_By");
        LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = rs.getString("Last_Updated_By");
        
        //make user object
        Country country = new Country(id, country_Name, createDate, createdBy, lastUpdate, lastUpdatedBy);
                       
        DBConnection.closeConnection(); //close connection

        
        return country;
        
    }
    
    /**
     * Returns all the countries in the database.
     * @return ObservableList of Country objects
     * @throws SQLException 
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        Connection conn = DBConnection.startConnection(); 
        
        String selectStatement = "SELECT * FROM countries";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.execute();
        
        ObservableList<Country> countries = FXCollections.observableArrayList();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        //while loop to add to Country List
        while (rs.next() == true) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            
            Country country = new Country(countryId, countryName, createDate, createdBy,
                                            lastUpdate, lastUpdatedBy);
            
            countries.add(country);
        }
        
        DBConnection.closeConnection();
        
        return countries;
        
    }
    
}
