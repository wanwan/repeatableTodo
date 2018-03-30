package org.zaregoto.apl.repeatabletodo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TimerService extends Service {

    private static final String TAG = "TimerService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
