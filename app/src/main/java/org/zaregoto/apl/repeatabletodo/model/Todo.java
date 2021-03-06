package org.zaregoto.apl.repeatabletodo.model;

import java.util.Date;

public class Todo {

    private boolean done;

    private Task task;
    private Date date;

    private String name;
    private String detail;

    private Todo() {
        done = false;
    }

    public Todo(Task _task, Date _date) {
        this.task = _task;
        this.date = _date;
        if (null != task) {
            name = task.getName();
            detail = task.getDetail();
        }
    }


    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isDone() {
        return done;
    }

    public int getTaskId() {
        return task.getId();
    }

    public Date getDate() {
        return date;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Task getTask() {
        return task;
    }

}


