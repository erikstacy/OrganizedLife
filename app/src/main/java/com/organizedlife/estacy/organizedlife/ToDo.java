package com.organizedlife.estacy.organizedlife;

import java.io.Serializable;

public class ToDo implements Serializable {

    // Create the To Do text
    private String todoString;
    // To Do Checkmark
    private boolean isChecked;

    // Constructors
    public ToDo() {
        todoString = null;
        isChecked = false;
    }
    public ToDo(String todoString) {
        this.todoString = todoString;
        isChecked = false;
    }

    // Getters
    public String getToDoString() {
        return todoString;
    }
    public boolean getIsChecked() {
        return isChecked;
    }

    // Setters
    public void setToDoString(String todoString) {
        this.todoString = todoString;
    }
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    // Reset ToDo
    public void resetToDo() {
        this.todoString = null;
        this.isChecked = false;
    }
}
