package controller;

import java.io.*;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
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
import model.User;
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
    
    /**
     * Checks if user credentials are correct and will login user if they are.
     * @param event
     * @throws IOException 
     */
    public void loginButtonPressed(ActionEvent event) throws IOException {
        appendLoginActivityReport();
        
        if (checkLoginValidation() == true) {
            Parent mainPage = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Scene mainScene = new Scene(mainPage);

            //this line gets the stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(mainScene);
            window.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                "Username/Password is not correct. Try again.");
        
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
            System.out.println(passwordField.getText());
            System.out.println(user.getPassword());
            
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
