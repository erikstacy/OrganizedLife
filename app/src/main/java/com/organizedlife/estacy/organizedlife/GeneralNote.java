package com.organizedlife.estacy.organizedlife;

import java.io.Serializable;
import java.util.ArrayList;

public class GeneralNote implements Serializable {

    // Variables
    private String noteTitle;
    private ArrayList<Note> noteList;
    private ArrayList<NestedNote> nestedNoteList;

    // Constructors
    public GeneralNote() {
        noteTitle = "Note";
        noteList = new ArrayList<>();
        noteList.add(new Note(""));
        nestedNoteList = new ArrayList<>();
        nestedNoteList.add(new NestedNote());
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
    public void resetGeneral() {
        noteTitle = "";
        noteList = new ArrayList<>();
        noteList.add(new Note(""));
        nestedNoteList = new ArrayList<>();
        nestedNoteList.add(new NestedNote());
    }

    public ArrayList<NestedNote> getNestedNoteList() {
        return nestedNoteList;
    }

    public void setNestedNoteList(ArrayList<NestedNote> nestedNoteList) {
        this.nestedNoteList = nestedNoteList;
    }
}
