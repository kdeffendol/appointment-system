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
public class MonthTypeReport {
    int count;
    String type;
    int month;
    
    public MonthTypeReport() {        
    }
    
    public MonthTypeReport(int count, String type, int month) {
        this.count = count;
        this.type = type;
        this.month = month;
    } 

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
    
    
}
