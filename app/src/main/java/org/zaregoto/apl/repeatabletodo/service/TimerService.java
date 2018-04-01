package org.zaregoto.apl.repeatabletodo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

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

        }
    }
}
