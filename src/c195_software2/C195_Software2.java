/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195_software2;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import utils.DBQuery;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.Set;
import java.sql.PreparedStatement;

/**
 *
 * @author kelsey
 */
public class C195_Software2 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        
        Connection conn = DBConnection.startConnection();
        String insertStatement = "UPDATE countries SET Country = ?, Created_By = ? WHERE Country = ?";
        
        DBQuery.setPreparedStatement(conn, insertStatement); //create prepared statement
        
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        
          String country, newCountry, createdBy;
//        String createDate = "2021-04-21 00:00:00";
//        String createdBy = "admin";
//        String lastUpdatedBy = "admin";
        
        //scanner
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter country to update: ");
        country = sc.nextLine();
        
        System.out.print("Enter new country: ");
        newCountry = sc.nextLine();
        
        System.out.print("Enter user: ");
        createdBy = sc.nextLine();
        
        //key value mapping  
        ps.setString(1, newCountry);
        ps.setString(2, createdBy);
        ps.setString(3, country);
        
        ps.execute(); //execute PreparedStatement
        
        //check rows affected
        if(ps.getUpdateCount() > 0)
            System.out.println(ps.getUpdateCount() + " rows affected");
        else
            System.out.println("no change");
        
        
        
        
        launch(args);
        
        //close connection to the database when window is closed
        DBConnection.closeConnection();
    }
}



//        // Raw SQL insert statement
////        String insertStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Updated_By) "
////                                + "VALUES('US', '2021-04-12 00:00:00', 'admin', 'admin')";
//        
////        //Variable Insert
////        String countryName = "Canada";
////        String createDate = "2021-04-12 00:00:00";
////        String createdBy = "admin";
////        String lastUpdateBy = "admin";
//        
//        
//        //Execute SQL statement
//        statement.execute(insertStatement);
//        
//        //Confirm rows affected
//        if(statement.getUpdateCount() > 0) {
//            System.out.println(statement.getUpdateCount() + " row(s) affected.");
//        } else {
//            System.out.println("No change.");
//        }
//        
////connect to the database
//        Connection conn = DBConnection.startConnection();
//        
//        DBQuery.setStatement(conn); //create statement object
//        Statement statement = DBQuery.getStatement(); // get Statement reference
//              
//        String selectStatement = "SELECT * FROM countriesx"; //select statement
//        
//        
//        try {
//            statement.execute(selectStatement); //execute statement
//            ResultSet rs = statement.getResultSet(); //get result set
//
//            //Forward scroll ResultSet
//            while(rs.next() == true) {
//                int countryId = rs.getInt("Country_ID");
//                String countryName = rs.getString("Country");
//                LocalDate date = rs.getDate("Create_Date").toLocalDate();
//                LocalTime time = rs.getTime("Create_Date").toLocalTime();
//                String createdBy = rs.getString("Created_By");
//                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
//
//                //Display record
//                System.out.println(countryId + " | " + countryName + " | " + date + " " + time
//                                     + " | " + createdBy + " | " + lastUpdate);
//
//            }
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//        }

