package org.zaregoto.apl.repeatabletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.zaregoto.apl.repeatabletodo.model.Task;

import java.util.List;

public class TaskListAdapter extends ArrayAdapter<Task> {

    LayoutInflater mInflater;

    public TaskListAdapter(Context context, int resource, List<Task> objects) {
        super(context, resource, objects);
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Task task;
        TextView tv;

        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.adapter_task_item, parent, false);
        }

        task = getItem(position);
        if (null != task) {
            tv = convertView.findViewById(R.id.taskname);
            if (null != tv) {
                tv.setText(task.getName());
            }
            tv = convertView.findViewById(R.id.taskdetail);
            if (null != tv) {
                tv.setText(task.getDetail());
            }
        }

        return convertView;
    }
}
