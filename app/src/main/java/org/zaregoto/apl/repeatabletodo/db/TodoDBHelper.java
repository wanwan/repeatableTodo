package org.zaregoto.apl.repeatabletodo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.zaregoto.apl.repeatabletodo.model.Task;
import org.zaregoto.apl.repeatabletodo.model.Todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class TodoDBHelper extends SQLiteOpenHelper {

    private static final String DB = "todo";
    private static final int DB_VERSION = 1;

    static String TODO_TABLE_NAME = "todolist";
    static String TASK_TABLE_NAME = "tasklist";

    private static final String ENABLE_FOREGN_KEY_CONSTRAINT = "PRAGMA foreign_keys=ON;";

    private static final String CREATE_TASK_TABLE = "create table " + TASK_TABLE_NAME + " (" +
            "task_id integer primary key autoincrement, " +
            "task_name string not null," +
            "task_detail string," +
            "task_enable BOOLEAN" +
            ");";

    private static final String CREATE_TODO_TABLE = "create table " + TODO_TABLE_NAME + " (" +
            "task_id integer, " +
            "todo_date string not null check (todo_date like '____-__-__')," +
            "todo_name string not null," +
            "todo_detail string," +
            "todo_done BOOLEAN," +
            "primary key (task_id, todo_date), " +
            "foreign key (task_id) references tasklist(task_id) " +
            ");";

    private static final String REPLACE_TASK_TABLE_BY_DAY = "replace into " +
            TASK_TABLE_NAME + "(task_name, task_detail, task_enable) " +
            " values (?, ?, ?);" ;

    private static final String QUERY_TODO_TABLE_BY_DAY = "select date, name, detail, done " +
            " from " + TODO_TABLE_NAME +
            " where date = '?';" ;
    private static final String REPLACE_TODO_TABLE_BY_DAY = "replace into " +
            TODO_TABLE_NAME + "(date, name, detail, done) " +
            " values (?, ?, ?, ?);" ;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


    public TodoDBHelper(Context applicationContext) {
        super(applicationContext, DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ENABLE_FOREGN_KEY_CONSTRAINT);

        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL(ENABLE_FOREGN_KEY_CONSTRAINT);
        }
    }


    public ArrayList<Todo> queryTodoListToday() {

        ArrayList<Todo> ret = new ArrayList();
        Todo todo;

        Cursor cursor = null;
        SQLiteDatabase db = null;
        Date today = new Date();
        String[] args = new String[]{dateToDBStr(today)};

        try {
            db = getReadableDatabase();
            cursor = db.rawQuery(QUERY_TODO_TABLE_BY_DAY, args);

            if (null != cursor) {
                cursor.moveToFirst();
                do {
                    todo = getTodoFromCursor(cursor);
                    ret.add(todo);
                } while (cursor.moveToNext());
            }
        }
        finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
            if (null != db) {
                db.close();
            }
        }

        return ret;
    }


    public void upsertTodoListToday(ArrayList<Todo> todolist) {

        Iterator<Todo> it;
        Todo todo;
        SQLiteDatabase db = null;
        String[] args = new String[5];

        if (null != todolist) {

            try {
                it = todolist.iterator();
                db = getWritableDatabase();

                while (it.hasNext()) {
                    todo = it.next();
                    args[0] = String.valueOf(todo.getTaskId());
                    args[1] = dateToDBStr(todo.getDate());
                    args[2] = todo.getName();
                    args[3] = todo.getDetail();
                    args[4] = String.valueOf(booleanToDBInt(todo.isDone()));
                    db.execSQL(REPLACE_TODO_TABLE_BY_DAY, args);
                }
            }
            finally {
                if (null != db && db.isOpen()) {
                    db.close();
                }
            }
        }
    }


    // TODO: task の初回作成時に id が無い場合と二回目以降の有る場合の相違をどうとりあつかうか?
    public void upsertTaskListToday(ArrayList<Task> tasklist) {

        Iterator<Task> it;
        Task task;
        SQLiteDatabase db = null;
        String[] args = new String[3];

        if (null != tasklist) {

            try {
                it = tasklist.iterator();
                db = getWritableDatabase();

                while (it.hasNext()) {
                    task = it.next();
                    args[0] = task.getName();
                    args[1] = task.getDetail();
                    args[2] = String.valueOf(booleanToDBInt(task.isEnableTask()));
                    db.execSQL(REPLACE_TASK_TABLE_BY_DAY, args);
                }
            }
            finally {
                if (null != db && db.isOpen()) {
                    db.close();
                }
            }
        }
    }



    private Todo getTodoFromCursor(Cursor cursor) {

        String name = cursor.getString(cursor.getColumnIndex("name"));
        String detail = cursor.getString(cursor.getColumnIndex("detail"));
        Date date = dBStrToDate(cursor.getString(cursor.getColumnIndex("date")));
        boolean done = DBIntToBoolean(cursor.getInt(cursor.getColumnIndex("done")));

        Todo todo = new Todo(name, detail, date, done);

        return todo;
    }


    private static boolean DBIntToBoolean(int done) {

        boolean ret = false;

        if (done > 0) {
            ret = true;
        }
        return ret;
    }


    private static int booleanToDBInt(boolean done) {

        int ret = 0;

        if (done) {
            ret = 1;
        }

        return ret;
    }


    private static String dateToDBStr(Date date) {

        String ret = null;

        if (null != date) {
            ret = sdf.format(date);
        }
        return ret;
    }


    private static Date dBStrToDate(String str) {

        Date date = null;
        if (null != str) {
            try {
                date = sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date;
    }

}
