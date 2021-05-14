/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javafx.application.Platform;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.AppointmentViewModel;
import repos.AppointmentRepository;

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
    
    @FXML private ToggleGroup radioButtons;
    @FXML RadioButton monthViewRadioButton;
    @FXML RadioButton weekViewRadioButton;
    
    @FXML Button addAppointmentButton;
    @FXML Button updateAppointmentButton;
    @FXML Button deleteAppointmentButton;
    @FXML Button backButton;
    
    private ObservableList<AppointmentViewModel> apptList = FXCollections.observableArrayList();
    
    
    /**
     * Resets the RadioButtons and sets table to view all Appointments.
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    public void resetButtonPushed(ActionEvent event) throws IOException, SQLException {
        resetRadioButtons();
        updateTable();
    }
    
    /**
     * Filters the TableView when the RadioButton is clicked.
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    public void monthViewRadioButtonPushed(ActionEvent event) throws IOException, SQLException {
        filterTableByMonth();  
    }
    
    /**
     * Filters the TableView when the RadioButton is clicked.
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    public void weekViewRadioButtonPushed(ActionEvent event) throws IOException, SQLException {
        filterTableByWeek();
    }
    
    /**
     * Changes scene to the "AddAppointmentScreen"
     * @param event
     * @throws IOException 
     */
    public void addAppointmentButtonPushed(ActionEvent event) throws IOException {
        Parent addAppointmentPage = FXMLLoader.load(getClass().getResource("/view/AddAppointmentScreen.fxml")); //add in add appointment file
        Scene addAppointmentScene = new Scene(addAppointmentPage);
        
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addAppointmentScene);
        window.show();
    }
    
    /**
     * Uses selected row and populates the UpdateAppointmentScreen with the data while switching the scene
     * @param event
     * @throws IOException
     * @throws Exception 
     */
    public void updateAppointmentButtonPushed(ActionEvent event) throws IOException, Exception {
        //FXMLLoader loader = FXMLLoader.load(getClass().getResource("/view/ModifyAppointmentScreen.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyAppointmentScreen.fxml"));
        Parent updateAppointmentPage = loader.load(); //add in modify appointment file
        Scene updateAppointmentScene = new Scene(updateAppointmentPage);
        
        ModifyAppointmentScreenController controller = loader.getController();
        
        //get id of selected combo box
        AppointmentViewModel selectedAppt = appointmentTableView.getSelectionModel().getSelectedItem();
        controller.initializeTextFields(selectedAppt.getId());
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(updateAppointmentScene);
        window.show();
        
    }
    
    /**
     * Deletes the selected appointment from the database
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    public void deleteAppointmentButtonPushed(ActionEvent event) throws IOException, SQLException {
        //confirmation message
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
            "Are you sure you want to delete this appointment?", 
            ButtonType.YES, 
            ButtonType.NO);
        
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
           //get appt to delete
           AppointmentViewModel apptToBeDeleted = appointmentTableView.getSelectionModel().getSelectedItem();
           
           //delete appointment with matching id
           AppointmentRepository.deleteAppointment(apptToBeDeleted.getId());
        }  
        //update table
        updateTable();
    }
    
    /**
     * Returns the user to the MainScreen
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
   
    /**
     * Filters the TableView by the current month
     * @throws SQLException 
     */
    public void filterTableByMonth() throws SQLException {
        List<AppointmentViewModel> appointmentsInCurrentMonth = AppointmentRepository.getAllAppointmentViewModels()
                .stream()
                .filter(a -> a.getStartDateTime().getMonth() == LocalDate.now().getMonth() && a.getStartDateTime().getYear() == LocalDate.now().getYear())
                .collect(Collectors.toList());
        
        apptList.clear();
        for (AppointmentViewModel a : appointmentsInCurrentMonth) {
            apptList.add(a);
        }
        
        appointmentTableView.setItems(apptList);
    }
    
    /**
     * Filters the table based on the current week
     * @throws SQLException 
     */
    public void filterTableByWeek() throws SQLException {
        LocalDate currentDay = LocalDate.now();
        LocalDate currentPlusWeek = currentDay.plusWeeks(1);
        
        List<AppointmentViewModel> appointmentsInCurrentWeek = AppointmentRepository.getAllAppointmentViewModels()
                .stream()
                .filter(a -> a.getStartDateTime().toLocalDate().isAfter(currentDay) && a.getStartDateTime().toLocalDate().isBefore(currentPlusWeek))
                .collect(Collectors.toList());
        
        apptList.clear();
        for (AppointmentViewModel a : appointmentsInCurrentWeek) {
            apptList.add(a);
        }
        
        appointmentTableView.setItems(apptList);
    }
    
    /**
     * Deselects both of the RadioButtons in the ToggleGroup
     */
    public void resetRadioButtons() {
        monthViewRadioButton.setSelected(false);
        weekViewRadioButton.setSelected(false);
    }
    
    /**
     * Updates TableView with the current database
     * @throws SQLException 
     */
    public void updateTable() throws SQLException {
        apptList = AppointmentRepository.getAllAppointmentViewModels();
        
        appointmentIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactTableColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDateTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        appointmentTableView.setItems(apptList);
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            monthViewRadioButton.setToggleGroup(radioButtons);
            weekViewRadioButton.setToggleGroup(radioButtons);
            
            updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentTableViewScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
