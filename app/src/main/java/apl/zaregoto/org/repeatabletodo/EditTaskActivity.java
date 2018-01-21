package apl.zaregoto.org.repeatabletodo;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import apl.zaregoto.org.repeatabletodo.model.Task;
import apl.zaregoto.org.repeatabletodo.model.TaskList;

public class EditTaskActivity extends Activity {

    private TaskList mTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskList = readTaskList();

        setContentView(R.layout.activity_edit_task);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditTaskActivity.this, "add task", Toast.LENGTH_LONG).show();
            }
        });

        ListView lv = findViewById(R.id.task_list);
        ArrayAdapter<Task> adapter = new TaskListAdapter(this, 0, mTaskList.getTasks());
        if (null != lv) {
            lv.setAdapter(adapter);
        }
    }

    // stub function to make dummy task list
    private TaskList readTaskList() {
        TaskList taskList;

        taskList = new TaskList("test");
        taskList.addTask(new Task("aaa", "aaadetail", 1, Task.REPEAT_UNIT.DAILY));
        taskList.addTask(new Task("bbb", "bbbdetail", 2, Task.REPEAT_UNIT.WEEKLY));

        return taskList;
    }
}
