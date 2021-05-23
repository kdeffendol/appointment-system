/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author kelsey
 */
public class CustomerEditModel {
    int id;
    String name;
    String address;
    int countryId;
    int firstDivisionId;
    String postalCode;
    String phoneNumber;

    public CustomerEditModel() {
    }

    public CustomerEditModel(int id, String name, String address, int countryId, int firstDivisionId, String postalCode, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.countryId = countryId;
        this.firstDivisionId = firstDivisionId;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getFirstDivisionId() {
        return firstDivisionId;
    }

    public void setFirstDivisionId(int firstDivisionId) {
        this.firstDivisionId = firstDivisionId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    
}
