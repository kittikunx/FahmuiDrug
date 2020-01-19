package com.fahmuidrug.model;

import com.google.firebase.database.PropertyName;

public class ProfileVo {

    @PropertyName("name")
    String name;
    @PropertyName("lastName")
    String lastName;
    @PropertyName("hn")
    String hn;
    @PropertyName("bd")
    String bd;
    @PropertyName("weight")
    String weight;
    @PropertyName("high")
    String high;
    @PropertyName("bloodGroup")
    String bloodGroup;
    @PropertyName("congential")
    String congential;
    @PropertyName("allergic")
    String allergic;
    @PropertyName("phone")
    String phone;
    @PropertyName("geneResist")
    String geneResist;
    @PropertyName("geneAllergy")
    String geneAllergy;

    public ProfileVo(String name, String lastName ,String hn, String bd , String weight , String high , String bloodGroup , String congential , String allergic , String phone , String geneResist, String geneAllergy) {
        this.name = name;
        this.lastName = lastName;
        this.hn = hn;
        this.bd = bd;
        this.weight = weight;
        this.high = high;
        this.bloodGroup = bloodGroup;
        this.congential = congential;
        this.allergic = allergic;
        this.phone = phone;
        this.geneResist = geneResist;
        this.geneAllergy = geneAllergy;
    }

    public ProfileVo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHn() {
        return hn;
    }

    public void setHn(String hn) {
        this.hn = hn;
    }

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCongential() {
        return congential;
    }

    public void setCongential(String congential) {
        this.congential = congential;
    }

    public String getAllergic() {
        return allergic;
    }

    public void setAllergic(String allergic) {
        this.allergic = allergic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGeneResist() {
        return geneResist;
    }

    public void setGeneResist(String geneResist) {
        this.geneResist = geneResist;
    }

    public String getGeneAllergy() {
        return geneAllergy;
    }

    public void setGeneAllergy(String geneAllergy) {
        this.geneAllergy = geneAllergy;
    }
}
