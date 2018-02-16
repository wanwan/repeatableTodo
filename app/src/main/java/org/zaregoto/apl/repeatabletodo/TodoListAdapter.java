package org.zaregoto.apl.repeatabletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.zaregoto.apl.repeatabletodo.model.Task;
import org.zaregoto.apl.repeatabletodo.model.Todo;

import java.util.List;

public class TodoListAdapter extends ArrayAdapter<Todo> {

    private LayoutInflater mInflater;

    public TodoListAdapter(Context context, int resource, List<Todo> objects) {
        super(context, resource, objects);
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Todo todo;
        TextView tv;

        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.adapter_todo_item, parent, false);
        }

        todo = getItem(position);
        if (null != todo) {
            tv = convertView.findViewById(R.id.todoname);
            if (null != tv) {
                tv.setText(todo.getName());
            }
            tv = convertView.findViewById(R.id.tododetail);
            if (null != tv) {
                tv.setText(todo.getDetail());
            }
        }

        return convertView;
    }
}
