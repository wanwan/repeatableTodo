package org.zaregoto.apl.repeatabletodo.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import org.zaregoto.apl.repeatabletodo.MainApplication;
import org.zaregoto.apl.repeatabletodo.db.TodoDB;
import org.zaregoto.apl.repeatabletodo.model.Configuration;
import org.zaregoto.apl.repeatabletodo.model.Todo;

import java.util.*;

public class TimerService extends Service {

    private static final String TAG = "TimerService";
    private long TIMER_DELAY_MS = (1*60*1000);
    private long TIMER_PERIOD_MS = (1*60*1000);

    private static Timer mTimer;

    @Override
    public IBinder onBind(Intent intent) {

        TodoMakeTimerTask timerTask;

        if (null == mTimer) {
            //タイマーの初期化処理

            timerTask = new TodoMakeTimerTask();
            mTimer = new Timer(true);
            mTimer.schedule( timerTask, TIMER_DELAY_MS, TIMER_PERIOD_MS);
        }

        return null;
    }

    private class TodoMakeTimerTask extends TimerTask {

        @Override
        public void run() {

            ArrayList<Todo> todolist = null;
            MainApplication context = (MainApplication) getApplicationContext();
            Configuration configuration = null;

            if (null != context) {
                configuration = context.getConfiguration();
            }

            if (null != configuration && configuration.isTimeOverThreshold()) {

                Date day = new Date();
                Calendar c = GregorianCalendar.getInstance();
                c.setTime(day);
                c.add(Calendar.DATE, 1);
                Date tomorrow = c.getTime();

                todolist = TodoDB.createTodoListFromTaskList(TimerService.this, tomorrow);
            }
            else {

                Date today = new Date();
                todolist = TodoDB.createTodoListFromTaskList(TimerService.this, today);
            }
            TodoDB.saveData(TimerService.this, todolist);
        }
    }
}
