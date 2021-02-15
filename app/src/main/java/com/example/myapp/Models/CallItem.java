package com.example.myapp.Models;

import android.support.v7.widget.CardView;

public class CallItem {

    private String staffNumber;
    private String staffName;
    private int id;

    public CallItem(String staff_Name, String staff_Number, int iD){
        staffName = staff_Name;
        staffNumber = staff_Number;
        id = iD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
