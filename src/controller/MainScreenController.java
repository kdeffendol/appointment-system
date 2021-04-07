/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kelsey
 */
public class MainScreenController implements Initializable {
    
    @FXML Button customersButton;
    @FXML Button appointmentsButton;
    @FXML Button logoutButton;
    
    
    /**
     * 
     * @param event
     * @throws IOException 
     */
    public void customersButtonPushed(ActionEvent event) throws IOException {
        
        Parent partPage = FXMLLoader.load(getClass().getResource("/view/CustomerTableViewScreen.fxml"));
        Scene partScene = new Scene(partPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(partScene);
        window.show();
    }
    
    /**
     * 
     * @param event
     * @throws IOException 
     */
    public void appointmentsButtonPushed(ActionEvent event) throws IOException {
        Parent partPage = FXMLLoader.load(getClass().getResource("/view/AppointmentTableViewScreen.fxml"));
        Scene partScene = new Scene(partPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(partScene);
        window.show();
    }
    
    /**
     * 
     * @param event
     * @throws IOException 
     */
    public void logoutButtonPushed(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
            "Are you sure you want to log out of the program?", 
            ButtonType.OK, 
            ButtonType.CANCEL);
        
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
           Platform.exit(); 
        }  
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
