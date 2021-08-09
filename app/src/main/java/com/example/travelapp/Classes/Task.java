package com.example.travelapp.Classes;

import java.util.Date;

public class Task {

    String taskName;
    String string_date;
    int id;
    boolean is_completed;
    //int is_completed;

    public Task(String taskName, String string_date, int id, boolean is_completed) {
        this.taskName = taskName;
        this.string_date = string_date;
        this.id = id;
        this.is_completed=is_completed;
    }

/*    public Task(String taskName, String string_date, int id, int is_completed) {
        this.taskName = taskName;
        this.string_date = string_date;
        this.id = id;
        this.is_completed=is_completed;
    }*/

    public Task() {
    }

    //setter and getter
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStringDate() {
        return string_date;
    }

    public void setIs_completed(boolean is_completed) {
        this.is_completed = is_completed;
    }
/*    public void setIs_completed(int is_completed) {
        this.is_completed = is_completed;
    }*/

    public boolean get_is_completed() {
        return is_completed;
    }
/*    public int get_is_completed() {
        return is_completed;
    }*/

    public void setDate(String string_date) {
        this.string_date = string_date;
    }

    public int getTaskId() {
        return id;
    }

    //printing out member variables.
/*    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", date=" + date +
                ", id=" + id +
                '}';
    }*/
}
