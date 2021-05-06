/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import model.Customer;
import model.FirstLevelDivision;
import repos.CustomerRepository;
import repos.FirstLevelDivRepository;

/**
 * FXML Controller class
 *
 * @author kelsey
 */
public class ModifyCustomerScreenController implements Initializable {
    
    @FXML TextField customerIdTextField;
    @FXML TextField nameTextField;
    @FXML TextField addressTextField;
    @FXML ComboBox countryComboBox;
    @FXML ComboBox firstDivisionComboBox;
    @FXML TextField postalCodeTextField;
    @FXML TextField phoneNumberTextField;
    
    @FXML Button saveButton;
    @FXML Button cancelButton;
    
    /**
     * 
     * @param event
     * @throws IOException 
     */
    public void saveButtonPushed(ActionEvent event) throws IOException, Exception {
        updateCustomer();
        
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
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/CustomerTableViewScreen.fxml"));
        Scene mainScene = new Scene(mainPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainScene);
        window.show();
    }
    
    public void initializeTextFields(int id) throws Exception {
        Customer customer = CustomerRepository.getCustomerByCustomerId(id);
        
        customerIdTextField.setText(String.valueOf(customer.getId()));
        nameTextField.setText(customer.getName());
        addressTextField.setText(customer.getAddress());
        //get combo box selections
        postalCodeTextField.setText(customer.getPostalCode());
        phoneNumberTextField.setText(customer.getPhone());
        
    }
    
    public void updateCustomer() throws Exception {
        Customer customer = new Customer();
        
        customer.setName(nameTextField.getText());
        customer.setAddress(addressTextField.getText());
        customer.setPostalCode(postalCodeTextField.getText());
        customer.setDivisionId(getFirstDivSelection());
        customer.setPhone(phoneNumberTextField.getText());
        customer.setCreatedBy("test"); //BAD PLS CHANGE
        customer.setLastUpdatedBy("test"); //ALSO BAD PLS CHANGE
        
        CustomerRepository.updateCustomer(customer);
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
