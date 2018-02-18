package org.zaregoto.apl.repeatabletodo.db;

import android.content.Context;
import org.zaregoto.apl.repeatabletodo.model.Task;
import org.zaregoto.apl.repeatabletodo.model.Todo;

import java.util.ArrayList;

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


    public static void saveData(Context context, ArrayList<Task> tasklist) {

        TodoDBHelper dbhelper = null;

        try {
            dbhelper = new TodoDBHelper(context.getApplicationContext());
            dbhelper.upsertTaskListToday(tasklist);

        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }
    }

}
