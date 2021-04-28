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
import model.AppointmentViewModel;
import model.Customer;
import repos.AppointmentRepository;
import repos.CustomerRepository;

/**
 * FXML Controller class
 *
 * @author kelsey
 */
public class AppointmentTableViewScreenController implements Initializable {
    
    @FXML TableView <AppointmentViewModel>appointmentTableView;
    @FXML TableColumn <AppointmentViewModel, Integer>appointmentIdTableColumn;
    @FXML TableColumn <AppointmentViewModel, String>titleTableColumn;
    @FXML TableColumn <AppointmentViewModel, String>descriptionTableColumn;
    @FXML TableColumn <AppointmentViewModel, String>locationTableColumn;
    @FXML TableColumn <AppointmentViewModel, String>contactTableColumn;
    @FXML TableColumn <AppointmentViewModel, String>typeTableColumn;
    @FXML TableColumn <AppointmentViewModel, String>startDateTimeTableColumn;
    @FXML TableColumn <AppointmentViewModel, String>endDateTimeTableColumn;
    @FXML TableColumn <AppointmentViewModel, String>customerIdTableColumn;
    
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
        
        appointmentIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactTableColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDateTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
