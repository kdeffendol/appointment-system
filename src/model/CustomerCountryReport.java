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
public class CustomerCountryReport {
    int count;
    String country;

    public CustomerCountryReport(int count, String country) {
        this.count = count;
        this.country = country;
    }

    public CustomerCountryReport() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    
}
