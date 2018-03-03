package org.zaregoto.apl.repeatabletodo;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import org.zaregoto.apl.repeatabletodo.model.Task;
import org.zaregoto.apl.repeatabletodo.ui.EditTaskDialogFragment;

import java.util.List;

import static android.content.ContentValues.TAG;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ContactViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<Task> contactList;

    public TaskListAdapter(Context context, List<Task> contactList) {
        this.mContext = context;
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        final Task task = contactList.get(i);
        contactViewHolder.taskName.setText(task.getName());
        contactViewHolder.taskDetail.setText(task.getDetail());
        contactViewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "***** onBindViewHolder:onClickListener *****");

                EditTaskDialogFragment dialog = EditTaskDialogFragment.newInstance(task, EditTaskDialogFragment.EDIT_TASK_DIALOG_MODE.EDIT_TASK);

                FragmentManager fm = ((Activity)mContext).getFragmentManager();

                dialog.show(fm, "");
            }
        });
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
        protected View root;

        public ContactViewHolder(View v) {
            super(v);
            root = v;
            taskName =  (TextView) v.findViewById(R.id.taskname);
            taskDetail = (TextView)  v.findViewById(R.id.taskdetail);
        }
    }
}
