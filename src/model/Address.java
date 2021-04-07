/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.lang.String;

/**
 * Address class
 * @author kelsey
 */
public class Address {
    private int addressId;
    private String address;
    private String address2;
    private int cityId;
    private String postalCode;
    private String phone;
    //private datetime createDate;
    private String createdBy;
    //private timestamp lastUpdate;
    private String lastUpdateBy;

    /* * 
    Address constructor
    */
    public Address() {

    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getAddress() {
        return addressId;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
