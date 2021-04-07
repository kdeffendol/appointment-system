/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.lang.String;

/**
 * Customer class
 * @author kelsey
 */
public class Customer {
    private int customerId;
    private String customerName;
    private int addressId;
    private boolean active;
    //private datetime createDate;
    private String createdBy;
    //private timestamp lastUpdate;
    private String lastUpdateBy;

    /**
     * Customer constructor - creates a new customer
     */
    public Customer() {

    }

    public int getCustomerId() {
        return customerId;
    }

    public void setId(int customerId) {
        this.customerId = customerId;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerId = customerId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    } 

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdateBy;
    }

    public void setLastUpdatedBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
