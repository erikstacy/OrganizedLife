package com.organizedlife.estacy.organizedlife;

import java.io.Serializable;
import java.util.ArrayList;

public class Day implements Serializable {

    // Variables
    private String dayTitle;
    private ArrayList<Note> noteList;
    private ArrayList<ToDo> todoList;
    private ArrayList<Event> eventList;

    // Constructors
    public Day() {
        dayTitle = "9";
        noteList = new ArrayList<>();
        todoList = new ArrayList<>();
        eventList = new ArrayList<>();

        noteList.add(new Note(""));
        todoList.add(new ToDo(""));
        eventList.add(new Event("","",""));
    }

    public ArrayList<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(ArrayList<Note> noteList) {
        this.noteList = noteList;
    }

    public ArrayList<ToDo> getTodoList() {
        return todoList;
    }

    public void setTodoList(ArrayList<ToDo> todoList) {
        this.todoList = todoList;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public String getDayTitle() {
        return dayTitle;
    }

    public void setDayTitle(String dayTitle) {
        this.dayTitle = dayTitle;
    }

    // Reset the day
    public void resetDay() {
        dayTitle = "";
        noteList = new ArrayList<>();
        todoList = new ArrayList<>();
        eventList = new ArrayList<>();

        noteList.add(new Note(""));
        todoList.add(new ToDo(""));
        eventList.add(new Event("","",""));
    }
}
