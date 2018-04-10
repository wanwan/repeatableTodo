package org.zaregoto.apl.repeatabletodo.db;

import android.content.Context;
import org.zaregoto.apl.repeatabletodo.MainApplication;
import org.zaregoto.apl.repeatabletodo.model.Task;
import org.zaregoto.apl.repeatabletodo.model.TaskList;
import org.zaregoto.apl.repeatabletodo.model.Todo;
import org.zaregoto.apl.repeatabletodo.util.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TodoDB {

    public static ArrayList<Todo> loadData(Context context, Calendar today) {

        ArrayList<Todo> ret;
        DBHelper dbhelper = null;

        try {
            dbhelper = new DBHelper(context.getApplicationContext());
            ret = dbhelper.queryTodoListToday(today);

        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }

        return ret;
    }


    public static void saveData(Context context, ArrayList<Todo> todolist) {

        DBHelper dbhelper = null;

        try {
            dbhelper = new DBHelper(context.getApplicationContext());
            dbhelper.insertTodoListToday(todolist);
            for (Todo todo: todolist) {
                Task task = todo.getTask();
                dbhelper.updateTask(task);
            }
        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }
    }


    public static void update(Context context, Todo todo) {

        DBHelper dbhelper = null;

        if (null != todo) {
            todo.setDone(true);
            try {
                dbhelper = new DBHelper(context.getApplicationContext());
                dbhelper.updateTodo(todo);

            } finally {
                if (null != dbhelper) {
                    dbhelper.close();
                }
            }
        }
    }


    public static ArrayList<Todo> createTodoListFromTaskList(Context context, Date day) {
        TaskList tasklist = ((MainApplication)context.getApplicationContext()).getTaskList();
        ArrayList<Todo> todolist = new ArrayList<>();

        if (null != tasklist && tasklist.getTasks().size() > 0) {

            Todo todo;
            for (Task task : tasklist.getTasks()) {
                if (Utilities.isTaskOver(task, day)) {
                    task.setLastDate(day);
                    todo = createTodoFromTask(task, day);
                    todolist.add(todo);
                }
            }
        }

        return todolist;
    }

    private static Todo createTodoFromTask(Task task, Date today) {

        Todo todo = new Todo(task, today);
        return todo;
    }


}
