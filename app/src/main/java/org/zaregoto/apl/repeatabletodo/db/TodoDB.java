package org.zaregoto.apl.repeatabletodo.db;

import android.content.Context;
import org.zaregoto.apl.repeatabletodo.model.Todo;

import java.util.ArrayList;

public class TodoDB {

    public static ArrayList<Todo> loadData(Context context) {

        ArrayList<Todo> ret;
        TodoDBHelper dbhelper = null;

        try {
            dbhelper = new TodoDBHelper(context.getApplicationContext());
            ret = dbhelper.queryTodoListToday();

        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }

        return ret;
    }


    public static void saveData(Context context, ArrayList<Todo> todolist) {

        TodoDBHelper dbhelper = null;

        try {
            dbhelper = new TodoDBHelper(context.getApplicationContext());
            dbhelper.insertTodoListToday(todolist);

        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }
    }

}
