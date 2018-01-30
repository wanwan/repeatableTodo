package apl.zaregoto.org.repeatabletodo;

import android.app.Application;
import android.util.Log;
import apl.zaregoto.org.repeatabletodo.model.TaskList;

public class MainApplication extends Application {

    private static final String TAG = "MainApplication";

    private TaskList taskList;

    @Override
    public void onCreate() {
        /** Called when the Application-class is first created. */
        Log.v(TAG,"--- onCreate() in ---");
    }

    @Override
    public void onTerminate() {
        /** This Method Called when this Application finished. */
        Log.v(TAG,"--- onTerminate() in ---");
    }


    public TaskList getTaskList() {
        return taskList;
    }


    public void setTaskList(TaskList _taskList) {
        this.taskList = _taskList;
    }


    public boolean loadTodoList() {
        boolean ret = false;

        return ret;
    }

    public boolean savdTodoList() {
        boolean ret = false;

        return ret;
    }

}
