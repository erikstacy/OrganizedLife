package com.organizedlife.estacy.organizedlife;

import java.io.Serializable;

public class Note implements Serializable {

    // Create the note
    private String noteString;

    // Constructors
    public Note() {noteString = null;}
    public Note(String noteString) {
        this.noteString = noteString;
    }

    // Getters
    public String getNoteString() {
        return noteString;
    }

    // Setters
    public void setNoteString(String noteString) {
        this.noteString = noteString;
    }

    // Reset Note
    public void resetNote() {
        this.noteString = null;
    }
}
