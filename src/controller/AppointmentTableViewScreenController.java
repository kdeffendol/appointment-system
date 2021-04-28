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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import repos.AppointmentRepository;
import repos.CustomerRepository;

/**
 * FXML Controller class
 *
 * @author kelsey
 */
public class AppointmentTableViewScreenController implements Initializable {
    
    @FXML TableView appointmentTableView;
    @FXML TableColumn appointmentIdTableColumn;
    @FXML TableColumn titleTableColumn;
    @FXML TableColumn descriptionTableColumn;
    @FXML TableColumn locationTableColumn;
    @FXML TableColumn contactTableColumn;
    @FXML TableColumn typeTableColumn;
    @FXML TableColumn startDateTimeTableColumn;
    @FXML TableColumn endDateTimeTableColumn;
    
    @FXML RadioButton monthViewRadioButton;
    @FXML RadioButton weekViewRadioButton;
    
    @FXML Button addAppointmentButton;
    @FXML Button updateAppointmentButton;
    @FXML Button deleteAppointmentButton;
    @FXML Button backButton;
    
    public void addAppointmentButtonPushed(ActionEvent event) throws IOException {
        Parent addAppointmentPage = FXMLLoader.load(getClass().getResource("/view/AddAppointmentScreen.fxml")); //add in add appointment file
        Scene addAppointmentScene = new Scene(addAppointmentPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addAppointmentScene);
        window.show();
    }
    
    public void updateAppointmentButtonPushed(ActionEvent event) throws IOException {
        
    }
    
    public void deleteAppointmentButtonPushed(ActionEvent event) throws IOException {
        
    }
    
    public void backButtonPushed(ActionEvent event) throws IOException {
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene mainScene = new Scene(mainPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainScene);
        window.show();
    }
    
    public void updateTable() throws SQLException {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        apptList = AppointmentRepository.getAllAppointments();
        
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
