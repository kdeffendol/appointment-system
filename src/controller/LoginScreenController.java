package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
