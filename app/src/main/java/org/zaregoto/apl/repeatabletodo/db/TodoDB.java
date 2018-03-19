package org.zaregoto.apl.repeatabletodo.db;

import android.content.Context;
import org.zaregoto.apl.repeatabletodo.model.Todo;

import java.util.ArrayList;
import java.util.Calendar;

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

        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }
    }

}
