package org.zaregoto.apl.repeatabletodo.db;

import android.content.Context;
import org.zaregoto.apl.repeatabletodo.model.Task;

import java.util.ArrayList;
import java.util.Date;

public class TaskDB {

//    public static ArrayList<Todo> loadData(Context context) {
//
//        ArrayList<Todo> ret;
//        TodoDBHelper dbhelper = null;
//
//        try {
//            dbhelper = new TodoDBHelper(context.getApplicationContext());
//            ret = dbhelper.queryTodoListToday();
//
//        } finally {
//            if (null != dbhelper) {
//                dbhelper.close();
//            }
//        }
//
//        return ret;
//    }

    public static int insertNewTask(Context context, String _name, String _detail, int _repeatCount, Task.REPEAT_UNIT _repeatUnit, boolean _repeatFlag, Date _lastDate, boolean _enableTask) {

        TodoDBHelper dbhelper = null;
        int id;

        try {
            dbhelper = new TodoDBHelper(context.getApplicationContext());
            id = dbhelper.insertTaskListToday(_name, _detail, _repeatCount, _repeatUnit, _repeatFlag, _lastDate, _enableTask);

        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }

        return id;
    }

    public static void saveData(Context context, ArrayList<Task> tasklist) {

        TodoDBHelper dbhelper = null;

        try {
            dbhelper = new TodoDBHelper(context.getApplicationContext());
            dbhelper.insertTaskListToday(tasklist);

        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }
    }

}
