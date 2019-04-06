package com.organizedlife.estacy.organizedlife;

import java.io.Serializable;

public class Event implements Serializable {

    // Create the variables
    private String eventTitle;
    private String startTime;
    private String endTime;

    // Constructors
    public Event() {
        this.eventTitle = null;
        this.startTime = null;
        this.endTime = null;
    }

    public Event(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Event(String eventTitle, String startTime, String endTime) {
        this.eventTitle = eventTitle;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    // Reset Event
    public void resetEvent() {
        this.eventTitle = null;
        this.startTime = null;
        this.endTime = null;
    }
}
