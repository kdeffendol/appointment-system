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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import repos.CustomerRepository;

/**
 * FXML Controller class
 *
 * @author kelsey
 */
public class CustomerTableViewScreenController implements Initializable {
    
    //table column variables
    @FXML TableView<Customer> customerTableView;
    @FXML TableColumn<Customer, Integer> customerIdColumn;
    @FXML TableColumn<Customer, String> nameTableColumn;
    @FXML TableColumn<Customer, String> addressTableColumn;
    @FXML TableColumn<Customer, String> firstDivisionTableColumn;
    @FXML TableColumn<Customer, String> countryTableColumn;
    @FXML TableColumn<Customer, String> postalCodeTableColumn;
    @FXML TableColumn<Customer, String> phoneNumberTableColumn;
    
    //other
    @FXML Button addCustomerButton;
    @FXML Button updateCustomerButton;
    @FXML Button deleteCustomerButton;
    @FXML Button backButton;
    
    
    //BUTTON FUNCTIONS------------------------------------------------------------
    
    public void addCustomerButtonPushed(ActionEvent event) throws IOException {
        Parent addCustomerPage = FXMLLoader.load(getClass().getResource("/view/AddCustomerScreen.fxml"));
        Scene addCustomerScene = new Scene(addCustomerPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addCustomerScene);
        window.show();
    }
    
    public void updateCustomerButtonPushed(ActionEvent event) throws IOException, Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyCustomerScreen.fxml"));
        Parent updateCustomerPage = loader.load(); //add in modify appointment file
        Scene updateCustomerScene = new Scene(updateCustomerPage);
        
        ModifyCustomerScreenController controller = loader.getController();
        
        //get id of selected combo box
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        controller.initializeTextFields(selectedCustomer.getId());
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(updateCustomerScene);
        window.show();
    }
    
    /**
     * Deletes the selected customer from the database when the Delete button is pressed
     * @param event
     * @throws IOException 
     */
    public void deleteCustomerButtonPushed(ActionEvent event) throws IOException, SQLException {
        //check if customer has any appointments

        //confirmation message
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
            "Are you sure you want to delete this customer?", 
            ButtonType.YES, 
            ButtonType.NO);
        
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
           //get customer to delete
           Customer customerToBeDeleted = customerTableView.getSelectionModel().getSelectedItem();
           
           //delete customer with matching id
           CustomerRepository.deleteCustomer(customerToBeDeleted.getId());
        }  
        //update table
        updateTable();
    }
    
    /**
     * Returns user to the MainScreen scene
     * @param event
     * @throws IOException 
     */
    public void backButtonPushed(ActionEvent event) throws IOException {
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene mainScene = new Scene(mainPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainScene);
        window.show();
    }
    
    
    //OTHER FUNCTIONS-------------------------------------------------------------
    
    
    /**
     * Updates TableView with the database items
     * @throws SQLException 
     */
    public void updateTable() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        customerList = CustomerRepository.getAllCustomers();
        
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        firstDivisionTableColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        //countryTableColumn.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        postalCodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        
        customerTableView.setItems(customerList);
   
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //set up table
            updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTableViewScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
