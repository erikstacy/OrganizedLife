package com.organizedlife.estacy.organizedlife;

import java.io.Serializable;
import java.util.ArrayList;

public class DataHolder implements Serializable {

    // Variables
    private ArrayList<Day> dayList;
    private ArrayList<GeneralNote> generalList;
    private ArrayList<Month> monthList;

    // Constructors
    public DataHolder() {
        dayList = new ArrayList<>();
        generalList = new ArrayList<>();
        monthList = new ArrayList<>();

        dayList.add(new Day());
        generalList.add(new GeneralNote());
        monthList.add(new Month());
    }

    public void setDayList(ArrayList<Day> dayList) {
        this.dayList = dayList;
    }

    public ArrayList<Day> getDayList() {
        return dayList;
    }

    public void setGeneralList(ArrayList<GeneralNote> noteList) {
        this.generalList = noteList;
    }

    public ArrayList<GeneralNote> getGeneralList() {
        return generalList;
    }

    public void setMonthList(ArrayList<Month> monthList) {
        this.monthList = monthList;
    }

    public ArrayList<Month> getMonthList() {
        return monthList;
    }
}
