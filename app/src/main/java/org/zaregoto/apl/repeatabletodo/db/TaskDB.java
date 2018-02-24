package org.zaregoto.apl.repeatabletodo.db;

import android.content.Context;
import org.zaregoto.apl.repeatabletodo.model.Task;

import java.util.Date;

public class TaskDB {

    public static int insertNewTask(Context context, String _name, String _detail, int _repeatCount, Task.REPEAT_UNIT _repeatUnit, boolean _repeatFlag, Date _lastDate, boolean _enableTask) {

        DBHelper dbhelper = null;
        int id;

        try {
            dbhelper = new DBHelper(context.getApplicationContext());
            id = dbhelper.insertTask(_name, _detail, _repeatCount, _repeatUnit, _repeatFlag, _lastDate, _enableTask);

        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }

        return id;
    }


    public static void updateTask(Context context, Task task) {

        DBHelper dbhelper = null;

        try {
            dbhelper = new DBHelper(context.getApplicationContext());
            dbhelper.updateTask(task);

        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }
    }
}
