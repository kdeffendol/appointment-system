/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import repos.AppointmentRepository;
import repos.ContactRepository;
import repos.CustomerRepository;

/**
 * FXML Controller class
 *
 * @author kelsey
 */
public class AddAppointmentScreenController implements Initializable {
    
    @FXML TextField appointmentIdTextField;
    @FXML TextField titleTextField;
    @FXML TextField descriptionTextField;
    @FXML TextField locationTextField;
    @FXML TextField startDateTimeTextField;
    @FXML TextField endDateTimeTextField;
    @FXML TextField customerIdTextField;
    @FXML TextField userIdTextField;
    @FXML TextField typeTextField;
    @FXML ComboBox contactNameComboBox;
    
    @FXML Button saveButton;
    @FXML Button cancelButton;
    
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    
    /**
     * Changes UI back to the AppointmentTableViewScreen
     * @param event
     * @throws IOException 
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException {
        //add confirmation message?
        
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/AppointmentTableViewScreen.fxml"));
        Scene mainScene = new Scene(mainPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainScene);
        window.show();
    }
    
    /**
     * Saves the appointment and sends user back to the AppointmentTableViewScreen
     * @param event
     * @throws IOException
     * @throws SQLException
     * @throws Exception 
     */
    public void saveButtonPushed(ActionEvent event) throws IOException, SQLException, Exception {
        String str = startDateTimeTextField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        startDateTime = LocalDateTime.parse(str, formatter);
        
        str = endDateTimeTextField.getText();
        endDateTime = LocalDateTime.parse(str, formatter);
        
        if (isInBusinessHours(startDateTime) == false || isInBusinessHours(endDateTime) == false) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                "Appointment time is outside of business hours, try again.");
             
             alert.showAndWait();
        } else {
            if (isOverlapping() == false) {
                //save appointment into database
                createNewAppointment();
        
                //go back to AppointmentTableViewScreen
                Parent mainPage = FXMLLoader.load(getClass().getResource("/view/AppointmentTableViewScreen.fxml"));
                Scene mainScene = new Scene(mainPage);
        
                //this line gets the stage information
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
                window.setScene(mainScene);
                window.show();
            } else {
                alertUserIfOverlap();
            }
        }
            
    }
    
    /**
     * Creates new appointment with data from TextFields.
     * @throws SQLException
     * @throws Exception 
     */
    public void createNewAppointment() throws SQLException, Exception {
        //create empty appointment
        Appointment appt = new Appointment();
        
        //grab data from textfields
        appt.setTitle(titleTextField.getText());
        appt.setDescription(descriptionTextField.getText());
        appt.setLocation(locationTextField.getText());
        appt.setType(typeTextField.getText());
        
        //convert start date string to LocalDateTime
        String str = startDateTimeTextField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        
        appt.setStartTime(convertTimeToUTC(dateTime));
        
        //convert end date string to LocalDateTime
        str = endDateTimeTextField.getText();
        dateTime = LocalDateTime.parse(str, formatter);
        
        appt.setEndTime(convertTimeToUTC(dateTime));
        
        appt.setCustomerId(parseInt(customerIdTextField.getText()));
        appt.setUserId(parseInt(userIdTextField.getText()));
        
        appt.setContactId(getContactNameSelection());
        
        appt.setCreatedBy("test"); //BAD PLS CHANGE
        appt.setLastUpdatedBy("test"); //ALSO BAD PLS CHANGE
        
        
        
        //add appointment to database
        AppointmentRepository.addAppointment(appt);
    }
    
    /**
     * Converts the given time to UTC
     * @param dateTime
     * @return 
     */
    public LocalDateTime convertTimeToUTC(LocalDateTime dateTime) {
       //change to user's local time zone
       ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
       
       //convert to utc
       zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC);
       
       return zonedDateTime.toLocalDateTime();
    }
    
    /**
     * Converts the time given in UTC to the user's local time
     * @param dateTime
     * @return 
     */
    public LocalDateTime convertUTCToLocalTime(LocalDateTime dateTime) {
        //put in utc time
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneOffset.UTC);
        
        //convert to local time
        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        
        return zonedDateTime.toLocalDateTime();
    }
    
    /**
     * Checks if the given time is within business hours
     * @param dateTime
     * @return 
     */
    public boolean isInBusinessHours(LocalDateTime dateTime) {
        //add time zone to local
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
        
        //move to eastern time
        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("US/Eastern"));
        
        int hour = zonedDateTime.getHour();
        
        //check if time is in business hours
        return hour >= 8 && hour < 22;
        
    }
    
    public boolean isOverlapping() throws SQLException {
        boolean isOverlap = false;
        LocalDateTime startDateTime = convertUTCToLocalTime(this.startDateTime);
        LocalDateTime endDateTime = convertUTCToLocalTime(this.endDateTime);
        
        //get all appointments
        ObservableList<Appointment> appts = AppointmentRepository.getAllAppointments();
        
        for (Appointment a : appts) {
            if ((endDateTime.isAfter(a.getStartTime()) && startDateTime.isBefore(a.getStartTime())) || (startDateTime.isBefore(a.getEndTime()) && endDateTime.isAfter(a.getEndTime()))) {              
                isOverlap = true;
            }
        }
        
        return isOverlap;
    }
    
    public void alertUserIfOverlap() throws SQLException {
        if (isOverlapping() == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                "Appointment time is overlapping with existing appointment. Please try again.");
             
             alert.showAndWait();
        }
    }
    
    /**
     * Alerts the user if the given time is outside of business hours
     * @param startDateTime
     * @param endDateTime 
     */
    public void checkIfTimesInBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (isInBusinessHours(startDateTime) == false || isInBusinessHours(endDateTime) == false) {
             Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                "Appointment time is outside of business hours, try again.");
             
             alert.showAndWait();
        }
    }
    
    /**
     * Gets the contact name selection from the combo box.
     * @return
     * @throws Exception 
     */
    public int getContactNameSelection() throws Exception {
        Contact contact = ContactRepository.getContactByContactName(contactNameComboBox.getValue().toString());
        
        int contactId = contact.getId();
        
        return contactId;
    }
    
    /**
     * Populates the contact names ComboBox from the data in the database.
     * @throws SQLException 
     */
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
        try {
            populateContactNamesComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(AddAppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
