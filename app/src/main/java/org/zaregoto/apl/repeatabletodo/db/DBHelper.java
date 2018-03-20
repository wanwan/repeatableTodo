package org.zaregoto.apl.repeatabletodo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.zaregoto.apl.repeatabletodo.model.Task;
import org.zaregoto.apl.repeatabletodo.model.TaskList;
import org.zaregoto.apl.repeatabletodo.model.Todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB = "todo";
    private static final int DB_VERSION = 1;

    private static final int DEFAULT_CONFIGURATION_UPDATE_HOUR = 23;
    private static final int DEFAULT_CONFIGURATION_UPDATE_MIN = 0;
    private static final boolean DEFAULT_CONFIGURATION_UPDATE_FLAG = true;

    static String TODO_TABLE_NAME = "todolist";
    static String TASK_TABLE_NAME = "tasklist";
    static String CONFIGURATION_TABLE_NAME = "configuration";

    private static final String ENABLE_FOREGN_KEY_CONSTRAINT = "PRAGMA foreign_keys=ON;";

    private static final String CREATE_TASK_TABLE =
            "create table " + TASK_TABLE_NAME + " (" +
            " task_id integer primary key autoincrement, " +
            " task_name string not null," +
            " task_detail string," +
            " task_repeat_count int, " +
            " task_repeat_unit string, " +
            " task_repeat_flag BOOLEAN not null, " +
            " task_last_date string check (task_last_date like '____-__-__'), " +
            " task_enable BOOLEAN not null" +
            ");";

    private static final String CREATE_TODO_TABLE =
            "create table " + TODO_TABLE_NAME + " (" +
            " task_id integer, " +
            " todo_date string not null check (todo_date like '____-__-__')," +
            " todo_name string not null," +
            " todo_detail string," +
            " todo_done BOOLEAN," +
            " primary key (task_id, todo_date), " +
            " foreign key (task_id) references tasklist(task_id) " +
            ");";

    private static final String CREATE_CONFIGURATION_TABLE =
            "create table " + CONFIGURATION_TABLE_NAME + " (" +
            " update_time string check (update_time like '__:__'), " +
            " todo_cron_flag BOOLEAN " +
            ");";

    private static final String QUERY_SEQ_NO = "select seq from sqlite_sequence where name = ?";

    private static final String QUERY_TASK_TABLE =
            "select task_id, task_name, task_detail, task_repeat_count, task_repeat_unit, task_repeat_flag, task_last_date, task_enable" +
            " from " + TASK_TABLE_NAME + " order by task_id";

    private static final String INSERT_TASK_TABLE
            = "insert into " + TASK_TABLE_NAME + "(task_name, task_detail, task_repeat_count, task_repeat_unit, task_repeat_flag, task_last_date, task_enable) " +
            " values (?, ?, ?, ?, ?, ?, ?);" ;
    private static final String UPDATE_TASK_TABLE
            = "update " + TASK_TABLE_NAME + " set task_name=?, task_detail=?, task_repeat_count=?, task_repeat_unit=?, task_repeat_flag=?, task_last_date=?, task_enable=? " +
            " where id=?;" ;


    private static final String QUERY_TODO_TABLE_BY_DAY
            = "select " +
            "   " + TODO_TABLE_NAME + ".task_id as task_id, task_name, task_detail, task_repeat_count, task_repeat_unit, task_repeat_flag, task_last_date, task_enable," +
            "   todo_date, todo_name, todo_detail, todo_done " +
            " from " + TODO_TABLE_NAME + " left outer join " + TASK_TABLE_NAME + " on " + TODO_TABLE_NAME + ".task_id = " + TASK_TABLE_NAME + ".task_id " +
            " where todo_date = ?;" ;
    private static final String REPLACE_TODO_TABLE_BY_DAY
            = "replace into " + TODO_TABLE_NAME + "(task_id, todo_date, todo_name, todo_detail, todo_done) " +
            " values (?, ?, ?, ?, ?);" ;


    private static final String QUERY_CONFIGURATION =
            "select update_time, todo_cron_flag from " + CONFIGURATION_TABLE_NAME;

    private static final String INSERT_CONFIGURATION_VALUE =
            "insert into " + CONFIGURATION_TABLE_NAME + "(update_time, todo_cron_flag)" +
            " values (?, ?)";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


    public DBHelper(Context applicationContext) {
        super(applicationContext, DB, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ENABLE_FOREGN_KEY_CONSTRAINT);

        db.execSQL(CREATE_CONFIGURATION_TABLE);

        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_TODO_TABLE);

        setDefaultConfigurationValue(db);
    }

    private void setDefaultConfigurationValue(SQLiteDatabase db) {

        String args[] = new String[2];

        args[0] = String.format("%02d", DEFAULT_CONFIGURATION_UPDATE_HOUR) + ":" + String.format("%02d", DEFAULT_CONFIGURATION_UPDATE_MIN);
        args[1] = String.valueOf(booleanToDBInt(DEFAULT_CONFIGURATION_UPDATE_FLAG));

        db.execSQL(INSERT_CONFIGURATION_VALUE, args);
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


    public ArrayList<Todo> queryTodoListToday(Calendar today) {

        ArrayList<Todo> ret = new ArrayList();
        Todo todo;

        Cursor cursor = null;
        SQLiteDatabase db = null;
        //Date today = new Date();
        String[] args = new String[]{calendarToDBStr(today)};

        try {
            db = getReadableDatabase();
            cursor = db.rawQuery(QUERY_TODO_TABLE_BY_DAY, args);

            if (null != cursor) {
                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    do {
                        todo = getTodoFromCursor(cursor);
                        ret.add(todo);
                    } while (cursor.moveToNext());
                }
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


    public void insertTodoListToday(ArrayList<Todo> todolist) {

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


    public TaskList queryAllTaskList() {

        TaskList ret = new TaskList("");
        SQLiteDatabase db = null;
        Cursor cursor = null;

        int taskId;
        String taskName;
        String taskDetail;
        int taskRepeatCount;
        Task.REPEAT_UNIT taskRepeatUnit;
        boolean taskRepeatFlag;
        boolean taskEnable;
        Date taskLastDate;

        Task task;

        try {
            db = getReadableDatabase();

            cursor = db.rawQuery(QUERY_TASK_TABLE, null);
            if (null != cursor && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    taskId = cursor.getInt(cursor.getColumnIndex("task_id"));
                    taskName = cursor.getString(cursor.getColumnIndex("task_name"));
                    taskDetail = cursor.getString(cursor.getColumnIndex("task_detail"));
                    taskRepeatCount = cursor.getInt(cursor.getColumnIndex("task_repeat_count"));
                    taskRepeatUnit = Task.REPEAT_UNIT.getUnitFromString(cursor.getString(cursor.getColumnIndex("task_repeat_unit")));
                    taskRepeatFlag = dBIntToBoolean(cursor.getInt(cursor.getColumnIndex("task_repeat_flag")));
                    taskLastDate = dBStrToDate(cursor.getString(cursor.getColumnIndex("task_last_date")));
                    taskEnable = dBIntToBoolean(cursor.getInt(cursor.getColumnIndex("task_enable")));

                    task = new Task(taskId, taskName, taskDetail, taskRepeatCount, taskRepeatUnit, taskRepeatFlag, taskEnable, taskLastDate);

                    ret.addTask(task);

                } while (cursor.moveToNext());
            }

        }
        finally {
            if (null != db && db.isOpen()) {
                db.close();
            }
        }

        return ret;
    }

    public int insertTask(String _name, String _detail, int _repeatCount, Task.REPEAT_UNIT _repeatUnit, boolean _repeatFlag, Date _lastDate, boolean _enableTask) {

        SQLiteDatabase db = null;
        int ret = -1;

        try {
            db = getWritableDatabase();
            db.beginTransaction();

            String[] args1 = new String[7];
            args1[0] = _name;
            args1[1] = _detail;
            args1[2] = String.valueOf(_repeatCount);
            args1[3] = _repeatUnit.getName();
            args1[4] = String.valueOf(booleanToDBInt(_repeatFlag));
            args1[5] = dateToDBStr(_lastDate);
            args1[6] = String.valueOf(booleanToDBInt(_enableTask));
            db.execSQL(INSERT_TASK_TABLE, args1);

            String[] args2 = new String[1];
            args2[0] = TASK_TABLE_NAME;
            Cursor c = db.rawQuery(QUERY_SEQ_NO, args2);
            if (null != c && c.getCount() > 0) {
                c.moveToFirst();
                ret = c.getInt(c.getColumnIndex("seq"));
            }

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
            if (null != db && db.isOpen()) {
                db.close();
            }
        }

        return ret;
    }


    public int updateTask(Task task) {

        SQLiteDatabase db = null;
        int ret = -1;

        try {
            db = getWritableDatabase();
            db.beginTransaction();

            String[] args1 = new String[8];
            args1[0] = task.getName();
            args1[1] = task.getDetail();
            args1[2] = String.valueOf(task.getRepeatCount());
            args1[3] = task.getRepeatUnit().getName();
            args1[4] = String.valueOf(booleanToDBInt(task.isRepeatFlag()));
            args1[5] = dateToDBStr(task.getLastDate());
            args1[6] = String.valueOf(booleanToDBInt(task.isEnableTask()));
            args1[7] = String.valueOf(task.getId());
            db.execSQL(UPDATE_TASK_TABLE, args1);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
            if (null != db && db.isOpen()) {
                db.close();
            }
        }

        return ret;
    }



    private Todo getTodoFromCursor(Cursor cursor) {

        Task task;
        Todo todo;

        int taskId = cursor.getInt(cursor.getColumnIndex("task_id"));
        String taskName = cursor.getString(cursor.getColumnIndex("task_name"));
        String taskDetail = cursor.getString(cursor.getColumnIndex("task_detail"));
        int taskRepeatCount = cursor.getInt(cursor.getColumnIndex("task_repeat_count"));
        Task.REPEAT_UNIT taskRepeatUnit = Task.REPEAT_UNIT.valueOf(cursor.getString(cursor.getColumnIndex("task_repeat_unit")));
        boolean taskRepeatFlag = dBIntToBoolean(cursor.getInt(cursor.getColumnIndex("task_repeat_flag")));
        Date taskLastDate = dBStrToDate(cursor.getString(cursor.getColumnIndex("task_last_date")));
        boolean taskEnable = dBIntToBoolean(cursor.getInt(cursor.getColumnIndex("task_enable")));

        task = new Task(taskId, taskName, taskDetail, taskRepeatCount, taskRepeatUnit, taskRepeatFlag, taskEnable, taskLastDate);

        Date date = dBStrToDate(cursor.getString(cursor.getColumnIndex("todo_date")));
        String name = cursor.getString(cursor.getColumnIndex("todo_name"));
        String detail = cursor.getString(cursor.getColumnIndex("todo_detail"));
        boolean done = dBIntToBoolean(cursor.getInt(cursor.getColumnIndex("todo_done")));

        todo = new Todo(task, date);

        return todo;
    }


    private static boolean dBIntToBoolean(int done) {

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


    private static String calendarToDBStr(Calendar date) {

        String ret = null;

        if (null != date) {
            //ret = sdf.format(date);
            ret = String.format("%04d-%02d-%02d", date.get(Calendar.YEAR), date.get(Calendar.MONTH)+1, date.get(Calendar.DATE));
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

    public int updateUpdateTime(int hourOfDay, int minute) {
        return 0;
    }

    public void updateTodoCronFlag(boolean flag) {

    }


}
