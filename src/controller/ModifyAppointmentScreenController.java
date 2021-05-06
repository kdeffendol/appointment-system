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
    
    public void initializeTextFields(int id) throws Exception {
        //find appointment from id
        Appointment appt = AppointmentRepository.getAppointmentByAppointmentId(id);
        
        //populate fields from appointment data
        appointmentIdTextField.setText(String.valueOf(appt.getAppointmentId()));
        titleTextField.setText(appt.getTitle());
        descriptionTextField.setText(appt.getDescription());
        locationTextField.setText(appt.getLocation());
        typeTextField.setText(appt.getType());
        
        startDateTimeTextField.setText(formatDateTime(appt.getStartTime()));
        endDateTimeTextField.setText(formatDateTime(appt.getEndTime()));
        
        
        customerIdTextField.setText(String.valueOf(appt.getCustomerId()));
        userIdTextField.setText(String.valueOf(appt.getUserId()));
        //contactNameComboBox.setValue(appt.getC);   
        
    }
    
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
        
        //update in database
        AppointmentRepository.updateAppointment(appt);
    }
    
    public String formatDateTime(LocalDateTime oldDateTime) {
        String oldDateTimeString = oldDateTime.toString();
        String newDateTimeString;
        
        String[] strings = oldDateTimeString.split("T");
        
        newDateTimeString = strings[0] + " " + strings[1];
        
        return newDateTimeString;
        
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
            Logger.getLogger(ModifyAppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
