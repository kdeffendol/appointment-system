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