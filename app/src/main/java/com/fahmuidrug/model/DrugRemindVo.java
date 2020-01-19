package com.fahmuidrug.model;

public class DrugRemindVo {
    private String drugname;
    private String druguse;

    public DrugRemindVo(String drugname,String druguse) {
        this.drugname = drugname;
        this.druguse = druguse;

    }

    public String getDrugname() {
        return drugname;
    }

    public void setDrugname(String drugname) {
        this.drugname = drugname;
    }

    public String getDruguse() {
        return druguse;
    }

    public void setDruguse(String druguse) {
        this.druguse = druguse;
    }
}
