/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195_software2;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import utils.DBQuery;
import java.sql.Statement;

/**
 *
 * @author kelsey
 */
public class C195_Software2 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        //connect to the database
        Connection conn = DBConnection.startConnection();
        
        DBQuery.setStatement(conn); //create statement object
        Statement statement = DBQuery.getStatement(); // get Statement reference
        
        // Raw SQL insert statement
        String insertStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Updated_By) "
                + "VALUES('US', '2021-04-12 00:00:00', 'admin', 'admin')";
        
        //Execute SQL statement
        statement.execute(insertStatement);
        
        //Confirm rows affected
        if(statement.getUpdateCount() > 0) {
            System.out.println(statement.getUpdateCount() + " row(s) affected.");
        } else {
            System.out.println("No change.");
        }
        
        
        
        
        launch(args);
        
        //close connection to the database when window is closed
        DBConnection.closeConnection();
    }
}
