package apl.zaregoto.org.repeatabletodo.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import apl.zaregoto.org.repeatabletodo.R;
import apl.zaregoto.org.repeatabletodo.model.Task;

import java.util.IllegalFormatException;

public class EditTaskDialogFragment extends DialogFragment {

    private static final String ARGS_TASK_ID = "task";

    private Task task = null;
    private View dialogView = null;
    private RepeatUnitAdapter adapter = null;

    private EditTaskCallback callback = null;

    public static EditTaskDialogFragment newInstance(Task _task) {
        
        Bundle args = new Bundle();
        
        EditTaskDialogFragment fragment = new EditTaskDialogFragment();
        args.putSerializable(ARGS_TASK_ID, _task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (null != getArguments()) {
            task = (Task) getArguments().getSerializable(ARGS_TASK_ID);
        }

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //final View content;
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogView = inflater.inflate(R.layout.task_dialog_fragment, null);

        Spinner repeatUnitSpinner = dialogView.findViewById(R.id.repeatUnitSpinner);
        adapter = new RepeatUnitAdapter(getContext(), android.R.layout.simple_list_item_1, Task.REPEAT_UNIT.values());
        repeatUnitSpinner.setAdapter(adapter);

        builder.setView(dialogView);
        if (null != task) {
            EditText name = dialogView.findViewById(R.id.nameEdit);
            name.setText(task.getName());

            EditText detail = dialogView.findViewById(R.id.detailEdit);
            detail.setText(task.getName());

            EditText repeatUnitEdit = dialogView.findViewById(R.id.repeatCountEdit);
            repeatUnitEdit.setText(String.valueOf(task.getRepeatCount()));

            repeatUnitSpinner = dialogView.findViewById(R.id.repeatUnitSpinner);
            repeatUnitSpinner.setSelection(task.getRepeatUnit().ordinal());

            CheckBox repeatFlagCheck = dialogView.findViewById(R.id.repeatFlag);
            repeatFlagCheck.setChecked(task.isRepeatFlag());
        }
        builder.setTitle(R.string.task_dialog_title);
        builder.setPositiveButton(R.string.task_dialog_btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                EditText name = dialogView.findViewById(R.id.nameEdit);
                task.setName(name.getText().toString());

                EditText detail = dialogView.findViewById(R.id.detailEdit);
                task.setDetail(detail.getText().toString());

                EditText repeatUnitEdit = dialogView.findViewById(R.id.repeatCountEdit);
                int repeatCount;
                try {
                    repeatCount = Integer.parseInt(repeatUnitEdit.getText().toString());
                } catch (IllegalFormatException e) {
                    repeatCount = 0;
                }
                task.setRepeatCount(repeatCount);

                Spinner repeatUnitSpinner = dialogView.findViewById(R.id.repeatUnitSpinner);
                task.setRepeatUnit(Task.REPEAT_UNIT.getUnitFromString(repeatUnitSpinner.getSelectedItem().toString()));

                CheckBox repeatFlagCheck = dialogView.findViewById(R.id.repeatFlag);
                task.setRepeatFlag(repeatFlagCheck.isChecked());

                if (null != callback) {
                    callback.addTask(task);
                }
            }
        });

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (null != context && context instanceof EditTaskCallback) {
            this.callback = (EditTaskCallback) context;
        }
    }

    @Override
    public void onDetach() {
        this.callback = null;
        super.onDetach();
    }

    public interface EditTaskCallback {
        void addTask(Task task);
    }

}
