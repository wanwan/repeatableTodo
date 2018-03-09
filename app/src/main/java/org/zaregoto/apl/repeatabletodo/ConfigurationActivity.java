package org.zaregoto.apl.repeatabletodo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

public class ConfigurationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration);

        TextView tv = findViewById(R.id.configuration_time_select);
        if (null != tv) {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TimePickerDialog.Builder builder = new TimePickerDialog.Builder();
                    //AlertDialog dialog = builder.create();
                    TimePickerDialog dialog = new TimePickerDialog(
                            ConfigurationActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                                }
                            },
                            0,
                            0,
                            true
                    );
                    dialog.show();
                }
            });
        }
    }
}
