package org.zaregoto.apl.repeatabletodo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

public class TodoDBHelper extends SQLiteOpenHelper {

    private static final String DB = "todo";
    private static final int DB_VERSION = 1;

    static String TODO_TABLE_NAME = "todolist";

    private final String CREATE_TODO_TABLE = "create table " + TODO_TABLE_NAME + " " +
            "id integer primary key autoincrement, " +
            "date string not null check (date like '____-__-__')," +
            "name string not null," +
            "detail string," +
            "done BOOLEAN," +
            ");";


    public TodoDBHelper(Context applicationContext) {
        super(applicationContext, DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

}
