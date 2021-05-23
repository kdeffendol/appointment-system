/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.CustomerEditModel;
import utils.DBConnection;
import utils.DBQuery;

/**
 * CustomerRepository class
 * @author Kelsey
 */
public class CustomerRepository {
    
    /**
     * Adds a new customer to the database.
     * @param customer object to add to the database
     * @throws SQLException 
     */
    public static void addCustomer(Customer customer) throws SQLException {
        Connection conn = DBConnection.startConnection();
        
        String insertStatement = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        
        DBQuery.setPreparedStatement(conn, insertStatement); //create prepared statement  
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setString(5, customer.getCreatedBy());
        ps.setString(6, customer.getLastUpdatedBy());
        ps.setInt(7, customer.getDivisionId());

        
        ps.execute();
        
        DBConnection.closeConnection();
        
    }
    
    /**
     * Deletes a customer given its customer ID from the database
     * @param customerId
     * @throws SQLException 
     */
    public static void deleteCustomer(int customerId) throws SQLException {
        Connection conn = DBConnection.startConnection();
        
        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";
        
        DBQuery.setPreparedStatement(conn, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setInt(1, customerId);
        
        ps.execute();
        
        DBConnection.closeConnection();
        
    }
    
    /**
     * Updates a customer data in the database
     * @param customer
     * @throws SQLException 
     */
    public static void updateCustomer(Customer customer) throws SQLException {
        Connection conn = DBConnection.startConnection();
        
        String updateStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Created_By = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        
        DBQuery.setPreparedStatement(conn, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setString(5, customer.getCreatedBy());
        ps.setString(6, customer.getLastUpdatedBy());
        ps.setInt(7, customer.getDivisionId());
        ps.setInt(8, customer.getId());
        
        ps.execute();
        
        DBConnection.closeConnection();
    }
    
    
    /**
     * 
     * @param customerId
     * @return the customer object created
     * @throws SQLException
     * @throws Exception if the customerId is not found
     */
    public static Customer getCustomerByCustomerId(int customerId) throws SQLException, Exception {
        Connection conn = DBConnection.startConnection(); 
        
        String selectStatement = "SELECT * FROM customers WHERE Customer_ID = ?";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setInt(1, customerId);
        ps.execute();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        if(rs.next() == false) {
            throw new Exception("customerId not found");
        }
        
                
        //mapping
        int id = rs.getInt("Customer_ID");
        String name = rs.getString("Customer_Name");
        String address = rs.getString("Address");
        String postalCode = rs.getString("Postal_Code");
        String phone = rs.getString("Phone");
        LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
        String createdBy = rs.getString("Created_By");
        LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = rs.getString("Last_Updated_By");
        int divisionId = rs.getInt("Division_ID");
        
        
        //make customer object
        Customer customer = new Customer(id, name, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
                       
        DBConnection.closeConnection(); //close connection
        
        return customer;       
    }
    
    public static CustomerEditModel getCustomerEditModelbyCustomerId(int customerId) throws SQLException, Exception {
        Connection conn = DBConnection.startConnection();
        
        String selectStatement = "SELECT customers.Customer_ID, customers.Customer_Name, "
                + "customers.Address, "
                + "customers.Postal_Code, customers.Phone, "
                + "customers.Division_ID, first_level_divisions.COUNTRY_ID "
                + "FROM customers "
                + "INNER JOIN first_level_divisions "
                + "ON customers.Division_ID = first_level_divisions.Division_ID "
                + "WHERE customers.Customer_ID = ?";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.setInt(1, customerId);
        ps.execute();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        if(rs.next() == false) {
            throw new Exception("customerId not found");
        }
        
        //mapping
        int id = rs.getInt("Customer_ID");
        String name = rs.getString("Customer_Name");
        String address = rs.getString("Address");
        String postalCode = rs.getString("Postal_Code");
        String phone = rs.getString("Phone");
        int firstDivisionId = rs.getInt("Division_ID");
        int countryId = rs.getInt("COUNTRY_ID");
                
        DBConnection.closeConnection();
        
        //Make CustomerEditModel object
        CustomerEditModel customer = new CustomerEditModel(id, name, address, countryId, firstDivisionId, postalCode, phone);
        
        return customer;
        
    }
    
    /**
     * Returns all customers that are in the database
     * @return ObservableList of Customer objects
     * @throws SQLException 
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        Connection conn = DBConnection.startConnection(); 
        
        String selectStatement = "SELECT * FROM customers";
        
        DBQuery.setPreparedStatement(conn, selectStatement); //create prepared statement       
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ps.execute();
        
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        
        ResultSet rs = ps.getResultSet(); //get result set
        
        //while loop to add to Customers List
        while (rs.next() == true) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");
            
            Customer customer = new Customer(id, name, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
            
            customers.add(customer);
        }
        
        DBConnection.closeConnection();
        
        return customers;
        
    }
}
