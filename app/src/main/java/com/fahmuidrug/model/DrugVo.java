package com.fahmuidrug.model;

import com.google.firebase.database.PropertyName;

public class DrugVo {

    @PropertyName("name")
    String name;
    @PropertyName("url")
    String url;
    @PropertyName("level")
    String level;
    @PropertyName("image")
    String image;
    @PropertyName("howTo")
    String howTo;
    @PropertyName("properties")
    String properties;
    @PropertyName("adr")
    String adr;
    @PropertyName("contra")
    String contra;

    public DrugVo() {} //no-argument constructor

    public DrugVo(String name, String url, String level, String image, String howTo, String properties, String adr, String contra) {
        this.name = name;
        this.url = url;
        this.level = level;
        this.image = image;
        this.howTo = howTo;
        this.properties = properties;
        this.adr = adr;
        this.contra = contra;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHowTo() {
        return howTo;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
}
