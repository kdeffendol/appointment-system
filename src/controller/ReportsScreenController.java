/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Contact;
import model.Country;
import repos.ContactRepository;
import repos.CountryRepository;

/**
 * FXML Controller class
 *
 * @author kelsey
 */
public class ReportsScreenController implements Initializable {
    @FXML ComboBox reportTypeComboBox;
    
    @FXML HBox appointmentTypeHBox;
    @FXML TextField appointmentTypeTextField;
    
    @FXML HBox monthHBox;
    @FXML ComboBox monthComboBox;
    
    @FXML HBox contactNameHBox;
    @FXML ComboBox contactNameComboBox;
    
    @FXML HBox countryHBox;
    @FXML ComboBox countryComboBox;
    
    @FXML Button backButton;
    @FXML Button runReportButton;
    
    
    public void backButtonPushed(ActionEvent event) throws IOException {
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene mainScene = new Scene(mainPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainScene);
        window.show();
    }
    
    public void populateMonthComboBox() {
        ObservableList<String> monthNames = FXCollections.observableArrayList();
        monthNames.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        
        monthComboBox.setItems(monthNames);
    }
    
    /**
     * Populates the CountryComboBox using country data from the Countries table
     * @throws SQLException 
     */
    public void populateCountryComboBox() throws SQLException {
        //get all countries from database
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        ObservableList<Country> countries = FXCollections.observableArrayList();
        
        countries = CountryRepository.getAllCountries();
        
        //loop thru countries list and get names
        for (Country c : countries) {
            countryNames.add(c.getCountryName());
        }
        
        countryComboBox.setItems(countryNames);
    }
    
    public void populateContactNamesComboBox() throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        
        contacts = ContactRepository.getAllContacts();
        
        //loop thru contact objects to get names
        for (Contact c : contacts) {
            contactNames.add(c.getName());
        }
        
        //populate combo box
        contactNameComboBox.setItems(contactNames);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //populate combo boxes
        populateMonthComboBox();
        try {
            populateCountryComboBox();
            populateContactNamesComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
