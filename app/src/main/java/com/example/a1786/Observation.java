package com.example.a1786;

import java.io.Serializable;
import java.util.Arrays;

public class Observation implements Serializable {
    private int observationId;
    private int hikeId;
    private String observation;
    private String timeOfObservation;
    private String additionalComments;
    private byte[] imageBytes;

    private Hiking hike;


    public Observation() {

    }

    public Observation(int hikeId, String observation, String timeOfObservation, String additionalComments, byte[] imageBytes) {
        this.hikeId = hikeId;
        this.observation = observation;
        this.timeOfObservation = timeOfObservation;
        this.additionalComments = additionalComments;
        this.imageBytes = imageBytes;
    }

    public int getObservationId() {
        return observationId;
    }

    public void setObservationId(int observationId) {
        this.observationId = observationId;
    }

    public int getHikeId() {
        return hikeId;
    }

    public void setHikeId(int hikeId) {
        this.hikeId = hikeId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getTimeOfObservation() {
        return timeOfObservation;
    }

    public void setTimeOfObservation(String timeOfObservation) {
        this.timeOfObservation = timeOfObservation;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
    public void setHike(Hiking hike) {
        this.hike = hike;
    }
    public Hiking getHike(Hiking hike) {
        return hike;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "observationId=" + observationId +
                ", hikeId=" + hikeId +
                ", observation='" + observation + '\'' +
                ", timeOfObservation='" + timeOfObservation + '\'' +
                ", additionalComments='" + additionalComments + '\'' +
                ", imageBytes=" + Arrays.toString(imageBytes) +
                '}';
    }

}
