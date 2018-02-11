package apl.zaregoto.org.repeatabletodo;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import apl.zaregoto.org.repeatabletodo.model.Task;
import apl.zaregoto.org.repeatabletodo.model.TaskList;
import apl.zaregoto.org.repeatabletodo.ui.EditTaskDialogFragment;

public class EditTaskListActivity extends Activity implements EditTaskDialogFragment.EditTaskCallback {

    private TaskList mTaskList;
    private ArrayAdapter<Task> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskList = readTaskList();

        setContentView(R.layout.activity_edit_task);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(EditTaskListActivity.this, "add task", Toast.LENGTH_LONG).show();
                Task task = new Task();
                EditTaskDialogFragment dialog = EditTaskDialogFragment.newInstance(task);

                FragmentManager fm = getFragmentManager();

                dialog.show(fm, "");
            }
        });

        ListView lv = findViewById(R.id.task_list);
        adapter = new TaskListAdapter(this, 0, mTaskList.getTasks());
        if (null != lv) {
            lv.setAdapter(adapter);
        }
    }

    // stub function to make dummy task list
    private TaskList readTaskList() {
        TaskList taskList;

        taskList = TaskList.getTaskList(this);

        //taskList = new TaskList("test");
        //taskList.addTask(new Task("aaa", "aaadetail", 1, Task.REPEAT_UNIT.DAILY));
        //taskList.addTask(new Task("bbb", "bbbdetail", 2, Task.REPEAT_UNIT.WEEKLY));

        return taskList;
    }

    @Override
    public void addTask(Task task) {

        mTaskList.addTask(task);

        if (null != adapter) {
            adapter.notifyDataSetChanged();
        }

    }
}
