package com.example.a1786;

import java.io.Serializable;

public class Hiking implements Serializable{
    private int id;
    private String name;
    private String location;
    private String date;
    private String length;
    private String level;
    private String parking;
    private String description;

    public Hiking(String name, String location, String date, String length, String level, String parking, String description) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.length = length;
        this.level = level;
        this.parking = parking;
        this.description = description;
    }

    public Hiking() {
        this.name = "";
        this.location = "";
        this.date = "";
        this.length = "";
        this.level = "";
        this.parking = "";
        this.description = "";
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Hike{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", length='" + length + '\'' +
                ", level='" + level + '\'' +
                ", parking='" + parking + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
