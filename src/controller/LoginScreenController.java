package controller;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import repos.AppointmentRepository;
import repos.UserRepository;

/**
 * FXML Controller class
 *
 * @author Kelsey Deffendol - kdeffen@wgu.edu
 */
public class LoginScreenController implements Initializable {
    
    @FXML TextField usernameTextField;
    @FXML PasswordField passwordField;
    @FXML Label zoneIdTextField;
    @FXML Button loginButton;
    
    @FXML Label usernameLabel;
    @FXML Label passwordLabel;
    @FXML Label zoneIdLabel;
    
    private Appointment upcomingAppt;
    
    /**
     * Checks if user credentials are correct and will login user if they are.
     * @param event
     * @throws IOException 
     */
    public void loginButtonPressed(ActionEvent event) throws IOException, SQLException {
        //Language translation
        ResourceBundle resourceBundle = ResourceBundle.getBundle("language_files/rb", Locale.getDefault());
        
        appendLoginActivityReport();
        
        if (checkLoginValidation() == true) {
            
            alertUserOfAppt();
            
            Parent mainPage = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Scene mainScene = new Scene(mainPage);

            //this line gets the stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(mainScene);
            window.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                resourceBundle.getString("error"));
        
            alert.showAndWait();
        }
    }
    
    /**
     * Checks if the user's credentials are in the database.
     * @return 
     */
    public boolean checkLoginValidation() {
        boolean isValid = false;
        //check if username is in database
        String username = usernameTextField.getText();
        
        try {
            User user = UserRepository.getUserByUsername(username);
            
            //check if password is correct
            if (user.getPassword().equals(passwordField.getText())) {
                isValid = true;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return isValid;
    }
    
    /**
     * Adds login attempt information to the text file
     * @throws IOException 
     */
    public void appendLoginActivityReport() throws IOException {
        String filename = "login_activity.txt";
        
        FileWriter fw = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fw);
        
        outputFile.println(usernameTextField.getText() + " | " + Instant.now() + " | " + checkLoginValidation());
        
        outputFile.close();
    }
    
    public void alertUserOfAppt() throws SQLException {
        if (checkForUpcomingApppointment() == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                "User has an upcoming appointment at " + upcomingAppt.getStartTime() +
                        ". Appointment ID: " + upcomingAppt.getAppointmentId());
        
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                "There are no upcoming appointments.");
        
            alert.showAndWait();
        }
    }
    
    //TODO:
    public boolean checkForUpcomingApppointment() throws SQLException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime fifteenMinutesLaterTime = currentDateTime.plusMinutes(15);
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        apptList = AppointmentRepository.getAllAppointments();
        boolean isAppointment = false;
        
        for (Appointment a : apptList) {
            //get start time
            LocalDateTime startTime = a.getStartTime();
            //convert it
            startTime = convertUTCToLocalTime(startTime);
            //compare it
            if (startTime.isAfter(currentDateTime) && startTime.isBefore(fifteenMinutesLaterTime)) {
                isAppointment = true;
                upcomingAppt = a;
            }
        }   
        
        return isAppointment;
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Language translation
        ResourceBundle resourceBundle = ResourceBundle.getBundle("language_files/rb", Locale.getDefault());
        
        usernameLabel.setText(resourceBundle.getString("username"));
        passwordLabel.setText(resourceBundle.getString("password"));
        loginButton.setText(resourceBundle.getString("login"));
        String zoneId = ZoneId.systemDefault().getDisplayName(TextStyle.FULL, Locale.getDefault());
        zoneIdLabel.setText(zoneId);
    }    
    
}
