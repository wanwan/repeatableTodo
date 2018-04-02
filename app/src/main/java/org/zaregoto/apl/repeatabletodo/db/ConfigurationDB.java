package org.zaregoto.apl.repeatabletodo.db;

import android.content.Context;
import org.zaregoto.apl.repeatabletodo.model.Configuration;

public class ConfigurationDB {

        public static int setUpdateTime(Context context, int hourOfDay, int minute) {

        DBHelper dbhelper = null;
        int id;

        try {
            dbhelper = new DBHelper(context.getApplicationContext());
            id = dbhelper.updateUpdateTime(hourOfDay, minute);

        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }

        return id;
    }


    public static void setTodoCronFlag(Context context, boolean flag) {

        DBHelper dbhelper = null;

        try {
            dbhelper = new DBHelper(context.getApplicationContext());
            dbhelper.updateTodoCronFlag(flag);

        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }
    }

    public static Configuration queryConfiguration(Context context) {

        DBHelper dbhelper = null;
        Configuration ret = null;

        try {
            dbhelper = new DBHelper(context.getApplicationContext());
            ret = dbhelper.queryConfiguration();

        } finally {
            if (null != dbhelper) {
                dbhelper.close();
            }
        }
        return ret;
    }

}
