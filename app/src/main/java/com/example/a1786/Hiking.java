package com.example.a1786;

public class Hiking {
    private int id;
    private String name;
    private String location;
    private String date;
    private String length;
    private String level;

    public Hiking() {
    }

    private String parking;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getDiscrip() {
        return discrip;
    }

    public void setDiscrip(String discrip) {
        this.discrip = discrip;
    }

    private String discrip;

    public Hiking(int id, String name, String location, String date, String length, String level, String parking, String discrip) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.length = length;
        this.level = level;
        this.parking = parking;
        this.discrip = discrip;
    }
}
