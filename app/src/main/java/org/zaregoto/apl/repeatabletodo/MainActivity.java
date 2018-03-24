package org.zaregoto.apl.repeatabletodo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import org.zaregoto.apl.repeatabletodo.db.TaskDB;
import org.zaregoto.apl.repeatabletodo.db.TodoDB;
import org.zaregoto.apl.repeatabletodo.model.Task;
import org.zaregoto.apl.repeatabletodo.model.TaskList;
import org.zaregoto.apl.repeatabletodo.model.Todo;
import org.zaregoto.apl.repeatabletodo.util.Utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private ArrayList<Todo> mTodoList = null;
    private TodoListAdapter mAdapter = null;
    private RecyclerView mRecyclerView = null;

    private Calendar mShowDate = new GregorianCalendar();

    private int RESULT_CODE_EDIT_TASKS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//                Intent intent = new Intent(MainActivity.this, EditTaskListActivity.class);
//                startActivityForResult(intent, RESULT_CODE_EDIT_TASKS);
//
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        InputStream is;
        File outputFile;
        FileOutputStream out;

        outputFile = new File(getFilesDir(), TaskList.DEFAULT_TASKLIST_FILENAME);
        if (!outputFile.exists()) {
            AssetManager am = getAssets();
            if (null != am) {
                try {
                    is = am.open(TaskList.DEFAULT_TASKLIST_FILENAME);

                    if (!outputFile.exists()) {
                        outputFile.createNewFile();
                    }
                    out = new FileOutputStream(outputFile);
                    Utilities.copyFile(is, out);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //TaskList tasklist = TaskList.readTaskListFromFile(this);
        TaskList tasklist = TaskDB.readAllTaskList(this);
        ((MainApplication)getApplication()).setTaskList(tasklist);

        mRecyclerView = findViewById(R.id.todoListView);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        //mTodoList = new ArrayList<>();
        mTodoList = TodoDB.loadData(this, mShowDate);
        mAdapter = new TodoListAdapter(mTodoList);
        if (null != mRecyclerView) {
            mRecyclerView.setAdapter(mAdapter);

            (new ItemTouchHelper(callback)).attachToRecyclerView(mRecyclerView);
        }

        updateListViewType();

    }

    private void updateListViewType() {

        TextView tv = findViewById(R.id.todoListType);
        String s;

        if (null != mShowDate && null != tv) {
            s = Utilities.calendarToStr(mShowDate);
            tv.setText(s);
        }
    }


    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {


            // 横にスワイプされたら要素を消す
            int swipedPosition = viewHolder.getAdapterPosition();
            TodoListAdapter adapter = (TodoListAdapter) mRecyclerView.getAdapter();

            Todo todo = mTodoList.get(swipedPosition);
            todo.setDone(true);
            TodoDB.update(MainActivity.this, todo);

            mAdapter.remove(swipedPosition);
        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_show_by_date) {

            DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                            mShowDate.set(year, month, dayOfMonth);
                        }
                    },
                    mShowDate.get(Calendar.YEAR),
                    mShowDate.get(Calendar.MONTH),
                    mShowDate.get(Calendar.DATE)
            );
            dialog.show();

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(MainActivity.this, EditTaskListActivity.class);
            startActivityForResult(intent, RESULT_CODE_EDIT_TASKS);

        } else if (id == R.id.nav_configuration) {

            Intent intent = new Intent(MainActivity.this, ConfigurationActivity.class);
            startActivityForResult(intent, RESULT_CODE_EDIT_TASKS);

        } else if (id == R.id.nav_test_make_todo) {
            mTodoList.clear();
            ArrayList<Todo> todolist = createTodoListFromTaskList();
            mTodoList.addAll(todolist);
            if (null != mAdapter) {
                mAdapter.notifyDataSetChanged();
            }

            TodoDB.saveData(this, todolist);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // TODO: これをどこにおくかは要検討
    private ArrayList<Todo> createTodoListFromTaskList() {
        Date today = new Date();
        TaskList tasklist = ((MainApplication)getApplication()).getTaskList();
        ArrayList<Todo> todolist = new ArrayList<>();

        if (null != tasklist && tasklist.getTasks().size() > 0) {

            Todo todo;
            for (Task task : tasklist.getTasks()) {
                if (isTaskOver(task, today)) {
                    todo = createTodoFromTask(task, today);
                    todolist.add(todo);
                }
            }
        }

        return todolist;
    }

    private Todo createTodoFromTask(Task task, Date today) {

        Todo todo = new Todo(task, today);
        return todo;
    }

    private boolean isTaskOver(Task task, Date today) {

        boolean ret = false;
        Calendar cLastday = new GregorianCalendar();
        Calendar cToday = new GregorianCalendar();

        if (null == task.getLastDate()) {
            ret = true;
        }
        else {
            if (task.isRepeatFlag()) {
                cLastday.setTime(task.getLastDate());
                switch (task.getRepeatUnit()) {
                    case DAILY:
                        cLastday.add(Calendar.DAY_OF_MONTH, task.getRepeatCount());
                        break;
                    case WEEKLY:
                        cLastday.add(Calendar.DAY_OF_MONTH, task.getRepeatCount()*7);
                        break;
                    case MONTHLY:
                        cLastday.add(Calendar.MONTH, task.getRepeatCount());
                        break;
                    default:
                        break;
                }

                if (cToday.after(cLastday)) {
                    ret = true;
                }
            }
            else {
                // TODO:
            }
        }

        return ret;
    }

}
