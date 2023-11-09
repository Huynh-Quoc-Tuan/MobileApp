package com.example.a1786;

public class Image {
    private int id;
    private String path;
    private int hikeId; // Foreign key to relate image to a Hike

    // Constructor
    public Image(int id, String path, int hikeId) {
        this.id = id;
        this.path = path;
        this.hikeId = hikeId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getHikeId() {
        return hikeId;
    }

    public void setHikeId(int hikeId) {
        this.hikeId = hikeId;
    }
}

