package org.zaregoto.apl.repeatabletodo;

import android.app.Application;
import android.util.Log;
import org.zaregoto.apl.repeatabletodo.model.Configuration;
import org.zaregoto.apl.repeatabletodo.model.TaskList;

public class MainApplication extends Application {

    private static final String TAG = "MainApplication";

    private TaskList mTaskList;
    private Configuration mConfiguration;

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
        return mTaskList;
    }

    public void setTaskList(TaskList _taskList) {
        this.mTaskList = _taskList;
    }

    public Configuration getConfiguration() {
        return mConfiguration;
    }

    public void setConfiguration(Configuration mConfiguration) {
        this.mConfiguration = mConfiguration;
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
