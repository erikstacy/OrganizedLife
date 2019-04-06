package com.organizedlife.estacy.organizedlife;

import java.io.Serializable;
import java.util.ArrayList;

public class Month implements Serializable {

    // Variables
    private String monthTitle;
    private ArrayList<MonthDay> monthDayList;
    private ArrayList<ToDo> todoList;

    // Constructor
    public Month() {
        this.monthTitle = "2019";
        this.monthDayList = new ArrayList<>();
        this.todoList = new ArrayList<>();

        monthDayList.add(new MonthDay());
        todoList.add(new ToDo());
    }

    public String getMonthTitle() {
        return monthTitle;
    }

    public void setMonthTitle(String monthTitle) {
        this.monthTitle = monthTitle;
    }

    public ArrayList<MonthDay> getMonthDayList() {
        return monthDayList;
    }

    public void setMonthDayList(ArrayList<MonthDay> monthDayList) {
        this.monthDayList = monthDayList;
    }

    public ArrayList<ToDo> getTodoList() {
        return todoList;
    }

    public void setTodoList(ArrayList<ToDo> todoList) {
        this.todoList = todoList;
    }

    public void resetMonth() {
        this.monthTitle = "";
        this.monthDayList = new ArrayList<>();
        this.todoList = new ArrayList<>();

        monthDayList.add(new MonthDay());
        todoList.add(new ToDo());
    }
}
