package org.zaregoto.apl.repeatabletodo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.zaregoto.apl.repeatabletodo.model.Task;
import org.zaregoto.apl.repeatabletodo.model.TaskList;
import org.zaregoto.apl.repeatabletodo.ui.EditTaskDialogFragment;

public class EditTaskListActivity extends Activity implements EditTaskDialogFragment.EditTaskCallback {

    //private TaskList mTaskList;
    private ArrayAdapter<Task> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_task);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(EditTaskListActivity.this, "add task", Toast.LENGTH_LONG).show();
                Task task = new Task();
                EditTaskDialogFragment dialog = EditTaskDialogFragment.newInstance(task, EditTaskDialogFragment.EDIT_TASK_DIALOG_MODE.NEW_TASK);

                FragmentManager fm = getFragmentManager();

                dialog.show(fm, "");
            }
        });

        TaskList tasklist = ((MainApplication)getApplication()).getTaskList();
        ListView lv = findViewById(R.id.task_list);
        adapter = new TaskListAdapter(this, 0, tasklist.getTasks());
        if (null != lv) {
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

                    Task task = adapter.getItem(pos);
                    EditTaskDialogFragment dialog = EditTaskDialogFragment.newInstance(task, EditTaskDialogFragment.EDIT_TASK_DIALOG_MODE.EDIT_TASK);

                    FragmentManager fm = EditTaskListActivity.this.getFragmentManager();
                    dialog.show(fm, "");
                }
            });
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {

                    final Task task = adapter.getItem(pos);

                    AlertDialog.Builder alertBulider = new AlertDialog.Builder(EditTaskListActivity.this);
                    AlertDialog dialog = alertBulider
                            .setTitle(R.string.remove_task_dialog_title)
                            .setMessage(R.string.remove_task_dialog_message)
                            .setPositiveButton(R.string.remove_task_dialog_ok_btn, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MainApplication ma = (MainApplication) EditTaskListActivity.this.getApplication();
                                    ma.getTaskList().removeTask(task);

                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton(R.string.remove_task_dialog_cancel_btn, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // do nothing
                                }
                            }).create();

                    dialog.show();
                    return true;
                }
            });
        }
    }


    @Override
    public void addTask(Task task) {

        TaskList tasklist = ((MainApplication)getApplication()).getTaskList();
        tasklist.addTask(task);

        if (null != adapter) {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void editTask(Task task) {

        if (null != adapter) {
            adapter.notifyDataSetChanged();
        }

    }
}