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
    
    
    /**
     * 
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
    
    public void saveButtonPushed(ActionEvent event) throws IOException, SQLException, Exception {
        //save appointment into database
        createNewAppointment();
        
        //go back to AppointmentTableViewScreen
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/AppointmentTableViewScreen.fxml"));
        Scene mainScene = new Scene(mainPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainScene);
        window.show();
    }
    
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
        
        appt.setStartTime(dateTime);
        
        //convert end date string to LocalDateTime
        str = endDateTimeTextField.getText();
        dateTime = LocalDateTime.parse(str, formatter);
        
        appt.setEndTime(dateTime);
        
        appt.setCustomerId(parseInt(customerIdTextField.getText()));
        appt.setUserId(parseInt(userIdTextField.getText()));
        
        appt.setContactId(getContactNameSelection());
        
        appt.setCreatedBy("test"); //BAD PLS CHANGE
        appt.setLastUpdatedBy("test"); //ALSO BAD PLS CHANGE
        
        //add appointment to database
        AppointmentRepository.addAppointment(appt); 
    }
    
    public LocalDateTime convertTimeToUTC(LocalDateTime dateTime) {
       //change to user's local time zone
       ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
       
       //convert to utc
       zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC);
       
       return zonedDateTime.toLocalDateTime();
    }
    
    public LocalDateTime convertUTCToLocalTime(LocalDateTime dateTime) {
        //put in utc time
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneOffset.UTC);
        
        //convert to local time
        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        
        return zonedDateTime.toLocalDateTime();
    }
    
    public boolean isInBusinessHours(LocalDateTime dateTime) {
        //add time zone to local
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
        
        //move to eastern time
        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("EST"));
        
        int hour = zonedDateTime.getHour();
        
        //check if time is in business hours
        return hour >= 8 && hour < 22;
        
    }
    
    public int getContactNameSelection() throws Exception {
        Contact contact = ContactRepository.getContactByContactName(contactNameComboBox.getValue().toString());
        
        int contactId = contact.getId();
        
        return contactId;
    }
    
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
