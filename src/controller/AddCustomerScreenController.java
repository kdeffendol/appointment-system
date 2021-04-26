/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
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
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import repos.CountryRepository;
import repos.CustomerRepository;
import repos.FirstLevelDivRepository;

/**
 * FXML Controller class
 *
 * @author Kelsey Deffendol - kdeffen@wgu.edu
 */
public class AddCustomerScreenController implements Initializable {
    
    @FXML TextField customerIdTextField;
    @FXML TextField nameTextField;
    @FXML TextField addressTextField;
    @FXML TextField address2TextField;
    @FXML ComboBox countryComboBox;
    @FXML ComboBox firstDivisionComboBox;
    @FXML TextField postalCodeTextField;
    @FXML TextField phoneNumberTextField;
    
    @FXML Button saveButton;
    @FXML Button cancelButton;

     /**
     * Creates a new Customer in database then returns user to the CustomerTableViewScreen.
     * @param event
     * @throws IOException 
     */
    public void saveButtonPushed(ActionEvent event) throws IOException, Exception {
        createNewCustomer();
        
        
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/CustomerTableViewScreen.fxml"));
        Scene mainScene = new Scene(mainPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainScene);
        window.show();
    }
    
    /**
     * 
     * @param event
     * @throws IOException 
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException {
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/AppointmentTableViewScreen.fxml"));
        Scene mainScene = new Scene(mainPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainScene);
        window.show();
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
    /**
     * Populates the FirstLevelDivision ComboBox using first-level division data from the First-Level Divisions table.
     * It will only populate the table with Divisions with the given countryId.
     * @param countryId
     * @throws Exception 
     */
    public void populateFirstDivisionComboBox(int countryId) throws Exception {
        ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        
        //get divisions with matching country id
        divisions = FirstLevelDivRepository.getDivisionsbyCountryId(countryId);
        
        for (FirstLevelDivision c : divisions) {
            divisionNames.add(c.getName());
        }
        
        firstDivisionComboBox.setItems(divisionNames);
    }
    
    /**
     * Creates new customer based on the information given in the GUI fields.
     * @throws Exception 
     */
    public void createNewCustomer() throws Exception {
        Customer customer = new Customer();
        
        customer.setName(nameTextField.getText());
        customer.setAddress(addressTextField.getText());
        customer.setPostalCode(postalCodeTextField.getText());
        customer.setDivisionId(getFirstDivSelection());
        customer.setPhone(phoneNumberTextField.getText());
        customer.setCreatedBy("test"); //BAD PLS CHANGE
        customer.setLastUpdatedBy("test"); //ALSO BAD PLS CHANGE
        
        CustomerRepository.addCustomer(customer);
        
    }
    
    /**
     * Finds the ID of the selected first-level division ComboBox
     * @return First-level division id of the selected item in firstDivision ComboBox.
     * @throws Exception 
     */
    public int getFirstDivSelection() throws Exception {
        //search divisions by name
        FirstLevelDivision selectedDivision = FirstLevelDivRepository.getDivisionbyName(firstDivisionComboBox.getValue().toString());
        
        int divId = selectedDivision.getId();
        
        return divId;
    }
    
    /**
     * Populates the first-division ComboBox after country is selected in Country ComboBox.
     * @throws Exception 
     */
    public void firstDivisionComboBoxClicked() throws Exception {
        //check country selected
        if (countryComboBox.getValue() != null) {
            String selectedCountry = countryComboBox.getValue().toString();
            
            Country selectedCountryObject = CountryRepository.getCountryByCountryName(selectedCountry);
            
            int selectedCountryId = selectedCountryObject.getId();
            
            populateFirstDivisionComboBox(selectedCountryId);
        }   
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateCountryComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
