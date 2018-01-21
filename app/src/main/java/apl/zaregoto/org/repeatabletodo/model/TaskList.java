package apl.zaregoto.org.repeatabletodo.model;

import java.util.ArrayList;

public class TaskList {

    private String id = "";
    private ArrayList<Task> tasks;

    private TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(String _id) {
        this();
        this.id = _id;
    }

    public void addTask(Task _task) {
        tasks.add(_task);
    }

    public String getId() {
        return id;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
