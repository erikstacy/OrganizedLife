package com.organizedlife.estacy.organizedlife;

import java.io.Serializable;
import java.util.ArrayList;

public class NestedNote implements Serializable {

    // Variables
    private String noteTitle;
    private ArrayList<Note> noteList;

    // Constructors
    public NestedNote() {
        noteTitle = "Note";
        noteList = new ArrayList<>();
        noteList.add(new Note(""));
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public ArrayList<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(ArrayList<Note> noteList) {
        this.noteList = noteList;
    }

    // Reset the general note
    public void resetNested() {
        noteTitle = "";
        noteList = new ArrayList<>();
        noteList.add(new Note(""));
    }
}
