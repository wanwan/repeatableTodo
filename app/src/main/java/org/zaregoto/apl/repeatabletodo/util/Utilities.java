package org.zaregoto.apl.repeatabletodo.util;

import android.util.Log;
import org.zaregoto.apl.repeatabletodo.MainActivity;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utilities {

    private static final String TAG = "Utilities";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

    public static void copyFile(InputStream in, OutputStream out) {

        try {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static String dateToStr(Date date) {

        String str = null;

        if (null != date) {
            str = sdf.format(date);
        }
        else {
            str = "";
        }
        return str;
    }

    public static Date strToDate(String str) throws ParseException {
        Date date = sdf.parse(str);
        return date;
    }


    public static String calendarToStr(Calendar c) {
        String ret = "";

        if (null != c) {
            ret = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
        }

        return ret;
    }

}
