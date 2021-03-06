/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.User;
import utils.DBConnection;
import utils.DBQuery;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.lang.Exception;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;


/**
 *
 * @author Kelsey Deffendol - kdeffen@wgu.edu
 */
public class UserRepository {
    public UserRepository() {
              
    }
    
    public static User getUserByUsername(String username) throws SQLException, Exception {
        Connection conn = DBConnection.startConnection(); 
        
        String selectStatement = "SELECT * FROM users WHERE User_Name = ?";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setString(1, username);
        ps.execute();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        if(rs.next() == false) {
            throw new Exception("UserId not found");
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
    
        public static ObservableList<User> getAllUsers() throws SQLException {
        Connection conn = DBConnection.startConnection(); 
        
        String selectStatement = "SELECT * FROM users";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.execute();
        
        ObservableList<User> users = FXCollections.observableArrayList();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        //while loop to add to Users List
        while (rs.next() == true) {
            int id = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String password = rs.getString("Password");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            
            User user = new User(id, username, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
            
            users.add(user);
        }
        
        DBConnection.closeConnection();
        
        return users;
        
    }
    
    
    
}
