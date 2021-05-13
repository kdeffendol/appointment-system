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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import repos.AppointmentRepository;
import repos.ContactRepository;

/**
 * FXML Controller class
 *
 * @author kelsey
 */
public class ModifyAppointmentScreenController implements Initializable {
    
    @FXML TextField appointmentIdTextField;
    @FXML TextField titleTextField;
    @FXML TextField descriptionTextField;
    @FXML TextField typeTextField;
    @FXML TextField locationTextField;
    @FXML TextField startDateTimeTextField;
    @FXML TextField endDateTimeTextField;
    @FXML TextField customerIdTextField;
    @FXML TextField userIdTextField;
    @FXML ComboBox contactNameComboBox;
    
    @FXML Button saveButton;
    @FXML Button cancelButton;

    
    /**
     * Returns user to the AppointmentTableViewScreen
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
    
    public void saveButtonPushed(ActionEvent event) throws IOException, Exception {
        String str = startDateTimeTextField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(str, formatter);
        
        str = endDateTimeTextField.getText();
        LocalDateTime endDateTime = LocalDateTime.parse(str, formatter);
        
        if (isInBusinessHours(startDateTime) == false || isInBusinessHours(endDateTime) == false) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                "Appointment time is outside of business hours, try again.");
             
             alert.showAndWait();
        } else {
            //save modified appointment back into database
            updateAppointment();
        
            //go back to AppointmentTableViewScreen
            Parent mainPage = FXMLLoader.load(getClass().getResource("/view/AppointmentTableViewScreen.fxml"));
            Scene mainScene = new Scene(mainPage);
        
            //this line gets the stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
            window.setScene(mainScene);
            window.show();
        }
    }
    /**
     * Fills the text fields with the information from the database
     * @param id
     * @throws Exception 
     */
    public void initializeTextFields(int id) throws Exception {
        //find appointment from id
        Appointment appt = AppointmentRepository.getAppointmentByAppointmentId(id);
        
        //populate fields from appointment data
        appointmentIdTextField.setText(String.valueOf(appt.getAppointmentId()));
        titleTextField.setText(appt.getTitle());
        descriptionTextField.setText(appt.getDescription());
        locationTextField.setText(appt.getLocation());
        typeTextField.setText(appt.getType());
        
        startDateTimeTextField.setText(formatDateTime(convertUTCToLocalTime(appt.getStartTime())));
        endDateTimeTextField.setText(formatDateTime(convertUTCToLocalTime(appt.getEndTime())));
        
        
        customerIdTextField.setText(String.valueOf(appt.getCustomerId()));
        userIdTextField.setText(String.valueOf(appt.getUserId()));
        //contactNameComboBox.setValue(appt.getC);   
        
    }
    
    /**
     * Updates the appointment in the database with the information from the text fields
     * @throws Exception 
     */
    public void updateAppointment() throws Exception {
        //create appointment object w/ the existing appointment id
        Appointment appt = new Appointment();
        //grab data from textfields
        appt.setAppointmentId(parseInt(appointmentIdTextField.getText()));
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
        
        //update in database
        AppointmentRepository.updateAppointment(appt);
    }
    
    /**
     * Format the DateTime to take out the 'T'
     * @param oldDateTime
     * @return string of the LocalDateTime without the T to separate
     */
    public String formatDateTime(LocalDateTime oldDateTime) {
        String oldDateTimeString = oldDateTime.toString();
        String newDateTimeString;
        
        String[] strings = oldDateTimeString.split("T");
        
        newDateTimeString = strings[0] + " " + strings[1];
        
        return newDateTimeString;
        
    }
    
    /**
     * Converts the dateTime to be in UTC
     * @param dateTime
     * @return LocalDateTime in UTC
     */
    public LocalDateTime convertTimeToUTC(LocalDateTime dateTime) {
       //change to user's local time zone
       ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
       
       //convert to utc
       zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC);
       
       return zonedDateTime.toLocalDateTime();
    }
    
    /**
     * converts the LocalDateTime into the user's local timezone
     * @param dateTime
     * @return LocalDateTime in the user's local timezone
     */
    public LocalDateTime convertUTCToLocalTime(LocalDateTime dateTime) {
        //put in utc time
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneOffset.UTC);
        
        //convert to local time
        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        
        return zonedDateTime.toLocalDateTime();
    }
    
    /**
     * Checks if given time is within business hours
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
    
    /**
     * Alerts the user if start time or end time is not within the business hours
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
     * Gets the contact name from database by the ID
     * @return
     * @throws Exception 
     */
    public int getContactNameSelection() throws Exception {
        Contact contact = ContactRepository.getContactByContactName(contactNameComboBox.getValue().toString());
        
        int contactId = contact.getId();
        
        return contactId;
    }
    
    /**
     * Populates the ContactNamesComboBox with Contacts from the database
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
            Logger.getLogger(ModifyAppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
