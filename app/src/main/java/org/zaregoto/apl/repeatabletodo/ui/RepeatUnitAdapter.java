package org.zaregoto.apl.repeatabletodo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.zaregoto.apl.repeatabletodo.model.Task;

public class RepeatUnitAdapter extends ArrayAdapter<Task.REPEAT_UNIT> {

    private LayoutInflater inflater;

    public RepeatUnitAdapter(Context context, int resource, Task.REPEAT_UNIT[] objects) {
        super(context, resource, objects);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        if (null == convertView) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        Task.REPEAT_UNIT unit = getItem(position);
        TextView text = convertView.findViewById(android.R.id.text1);
        text.setText(unit.getName());

        return convertView;
    }
}
