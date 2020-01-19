package com.fahmuidrug.model;

import com.google.firebase.database.PropertyName;

public class LabVo {

    @PropertyName("date")
    String date;
    @PropertyName("lab")
    String lab;
    String keySnap;

    public LabVo(String date, String lab ,String keySnap) {
        this.date = date;
        this.lab = lab;
        this.keySnap = keySnap;
    }

    public LabVo() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public String getKeySnap() {
        return keySnap;
    }

    public void setKeySnap(String keySnap) {
        this.keySnap = keySnap;
    }
}
