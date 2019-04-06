package com.organizedlife.estacy.organizedlife;

import java.io.Serializable;

public class MonthDay implements Serializable {

    // Variables
    private String title;
    private String eventOne;
    private String eventTwo;
    private String eventThree;

    // Constructors
    public MonthDay() {
        this.title = "1";
        this.eventOne = "";
        this.eventTwo = "";
        this.eventThree = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventOne() {
        return eventOne;
    }

    public void setEventOne(String eventOne) {
        this.eventOne = eventOne;
    }

    public String getEventTwo() {
        return eventTwo;
    }

    public void setEventTwo(String eventTwo) {
        this.eventTwo = eventTwo;
    }

    public String getEventThree() {
        return eventThree;
    }

    public void setEventThree(String eventThree) {
        this.eventThree = eventThree;
    }

    // Reset monthday
    public void resetMonthDay() {
        title = "";
        this.eventOne = "";
        this.eventTwo = "";
        this.eventThree = "";
    }
}
