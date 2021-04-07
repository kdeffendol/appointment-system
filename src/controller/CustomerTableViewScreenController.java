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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kelsey
 */
public class CustomerTableViewScreenController implements Initializable {
    
    //table column variables
    @FXML TableView customerTableView;
    @FXML TableColumn customerIdColumn;
    @FXML TableColumn nameTableColumn;
    @FXML TableColumn addressTableColumn;
    @FXML TableColumn firstDivisionTableColumn;
    @FXML TableColumn countryTableColumn;
    @FXML TableColumn postalCodeTableColumn;
    @FXML TableColumn phoneNumberTableColumn;
    
    //other
    @FXML Button addCustomerButton;
    @FXML Button updateCustomerButton;
    @FXML Button deleteCustomerButton;
    @FXML Button backButton;
    
    
    public void addCustomerButtonPushed(ActionEvent event) throws IOException {
        Parent addCustomerPage = FXMLLoader.load(getClass().getResource("/view/AddCustomerScreen.fxml"));
        Scene addCustomerScene = new Scene(addCustomerPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addCustomerScene);
        window.show();
    }
    
    public void updateCustomerButtonPushed(ActionEvent event) throws IOException {
        Parent modifyCustomerPage = FXMLLoader.load(getClass().getResource("/view/ModifyCustomerScreen.fxml"));
        Scene modifyCustomerScene = new Scene(modifyCustomerPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(modifyCustomerScene);
        window.show();
        
        //pass in data of selected Customer
    }
    
    public void deleteCustomerButtonPushed(ActionEvent event) throws IOException {
        
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException {
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene mainScene = new Scene(mainPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainScene);
        window.show();
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
