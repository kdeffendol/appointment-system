package controller;

import java.io.IOException;
import java.net.URL;
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
    
    public void loginButtonPressed(ActionEvent event) throws IOException {
        
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
                System.out.println("Hello there");
                isValid = true;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return isValid;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
