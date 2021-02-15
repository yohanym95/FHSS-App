package com.example.myapp.Models;

import android.support.v7.widget.CardView;

public class CallItem {

    private String staffNumber;
    private String staffName;

    public CallItem(String staff_Name, String staff_Number){
        staffName = staff_Name;
        staffNumber = staff_Number;
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
