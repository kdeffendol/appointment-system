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
import javafx.collections.ObservableList;
import model.Country;
import model.User;
import repos.CountryRepository;
import repos.UserRepository;

/**
 *
 * @author Kelsey Deffendol - kdeffen@wgu.edu
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
    public static void main(String[] args) throws SQLException, Exception {
        ObservableList<Country> testCountryList = CountryRepository.getAllCountries();
        
        System.out.println(testCountryList);
        
        launch(args);
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

