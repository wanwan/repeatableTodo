package org.zaregoto.apl.repeatabletodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.zaregoto.apl.repeatabletodo.model.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ContactViewHolder> {

    private LayoutInflater mInflater;
    private List<Task> contactList;

    public TaskListAdapter(List<Task> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Task task = contactList.get(i);
        contactViewHolder.taskName.setText(task.getName());
        contactViewHolder.taskDetail.setText(task.getDetail());
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.adapter_task_item, viewGroup, false);

        return new ContactViewHolder(itemView);
    }


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        Task task;
//        TextView tv;
//
//        if (null == convertView) {
//            convertView = mInflater.inflate(R.layout.adapter_task_item, parent, false);
//        }
//
//        task = getItem(position);
//        if (null != task) {
//            tv = convertView.findViewById(R.id.taskname);
//            if (null != tv) {
//                tv.setText(task.getName());
//            }
//            tv = convertView.findViewById(R.id.taskdetail);
//            if (null != tv) {
//                tv.setText(task.getDetail());
//            }
//        }
//
//        return convertView;
//    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView taskName;
        protected TextView taskDetail;

        public ContactViewHolder(View v) {
            super(v);
            taskName =  (TextView) v.findViewById(R.id.taskname);
            taskDetail = (TextView)  v.findViewById(R.id.taskdetail);
        }
    }
}
