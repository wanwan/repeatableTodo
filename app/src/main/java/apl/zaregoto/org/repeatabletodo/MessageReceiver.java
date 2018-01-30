package apl.zaregoto.org.repeatabletodo;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MessageReceiver extends BroadcastReceiver {

    private static final String TAG = "MessageReceiver";

    public static String UPDATE_TODO_TRIGGER = "UPDATE_TODO";

    @Override
    public void onReceive(Context context, Intent intent) {

        //端末起動時にサービスを起動する
        if (intent.getAction().equals(UPDATE_TODO_TRIGGER)) {

            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.JAPAN);
            ArrayList<ClipData.Item> items;

            String str;
            str = sdf.format(now);
            Log.d(TAG, "receive update intent: " + str + " " + intent.getAction());

            MainApplication ma =  (MainApplication) context.getApplicationContext();
            if (null != ma) {

            }

        }
        else {
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String str;

            str = sdf.format(now);
            Log.d(TAG, "[else case]: " + str + " " + intent.getAction());
            Log.d(TAG, "***** MessageReceiver *****");
        }

    }
}
