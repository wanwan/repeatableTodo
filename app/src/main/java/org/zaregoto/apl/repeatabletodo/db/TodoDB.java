package org.zaregoto.apl.repeatabletodo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.zaregoto.apl.repeatabletodo.model.Todo;

import java.util.ArrayList;

public class TodoDB {

    public static ArrayList<Todo> loadData(Context context) {

        TodoDBHelper dbhelper = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            dbhelper = new TodoDBHelper(context.getApplicationContext());
            db = dbhelper.getReadableDatabase();

            cursor = db.query(TodoDBHelper.TODO_TABLE_NAME);


        if (null != cursor) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                item = getItemFromCursor(context, cursor);

                if (null != items) {
                    items.add(item);
                }
                cursor.moveToNext();
            }
        }
    } finally {
        if (null != cursor) {
            cursor.close();
        }
        if (null != db) {
            db.close();
        }
        if (null != dbhelper) {
            dbhelper.close();
        }
    }



}
