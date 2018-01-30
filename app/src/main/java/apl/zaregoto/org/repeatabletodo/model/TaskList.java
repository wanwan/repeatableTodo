package apl.zaregoto.org.repeatabletodo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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


    public ArrayList<Todo> generateTodoList() {

        ArrayList<Todo> todoList = new ArrayList<>();
        Iterator<Task> it;
        Task task;
        Date date = new Date();

        if (null != tasks) {
            it = tasks.iterator();
            while (it.hasNext()) {
                task = it.next();
                Date lastDate = task.getLastDate();

                if (task.lastDatePlusRepeatCountIsOver(date)) {

                }

            }
        }

        return todoList;
    }


}