/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.AppointmentViewModel;
import model.Contact;
import model.Country;
import model.MonthTypeReport;
import model.ScheduleReport;
import repos.ContactRepository;
import repos.CountryRepository;
import repos.ReportRepository;

/**
 * FXML Controller class
 *
 * @author kelsey
 */
public class ReportsScreenController implements Initializable {
    
    @FXML TableView <MonthTypeReport> typeMonthTableView;
    @FXML TableColumn <MonthTypeReport, Integer> countTableColumn;
    @FXML TableColumn <MonthTypeReport, String> typeTableColumn;
    @FXML TableColumn <MonthTypeReport, String> monthTableColumn;
    
    @FXML TableView <ScheduleReport> scheduleTableView;
    @FXML TableColumn <ScheduleReport, String> contactNameTableColumn;
    @FXML TableColumn <ScheduleReport, String> appointmentIdTableColumn;
    @FXML TableColumn <ScheduleReport, String> titleTableColumn;
    @FXML TableColumn <ScheduleReport, String> type2TableColumn;
    @FXML TableColumn <ScheduleReport, String> descriptionTableColumn;
    @FXML TableColumn <ScheduleReport, String> startDateTimeTableColumn;
    @FXML TableColumn <ScheduleReport, String> endDateTimeTableColumn;
    @FXML TableColumn <ScheduleReport, String> customerIdTableColumn;
    
    @FXML Button backButton;
    
    public void backButtonPushed(ActionEvent event) throws IOException {
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene mainScene = new Scene(mainPage);
        
        //this line gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(mainScene);
        window.show();
    }
    
    public void updateTables() throws SQLException {
        ObservableList<MonthTypeReport> monthTypeList = ReportRepository.getAllMonthTypeReports();
            
        countTableColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthTableColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
            
        typeMonthTableView.setItems(monthTypeList);
        
        ObservableList<ScheduleReport> scheduleReportList = ReportRepository.getAllScheduleReports();
        
        contactNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        appointmentIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        type2TableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDateTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        scheduleTableView.setItems(scheduleReportList);
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            updateTables();
            
        } catch (SQLException ex) {
            Logger.getLogger(ReportsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
