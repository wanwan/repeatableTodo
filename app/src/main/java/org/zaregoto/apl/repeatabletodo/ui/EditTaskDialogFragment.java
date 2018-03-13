package org.zaregoto.apl.repeatabletodo.ui;

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
import org.zaregoto.apl.repeatabletodo.R;
import org.zaregoto.apl.repeatabletodo.model.Task;

import java.util.Date;
import java.util.IllegalFormatException;

public class EditTaskDialogFragment extends DialogFragment {

    private static final String ARGS_TASK_ID = "task";
    private static final String ARGS_MODE_ID = "mode";

    public enum EDIT_TASK_DIALOG_MODE {
        NEW_TASK,
        EDIT_TASK,
    }

    private Task task = null;
    private EDIT_TASK_DIALOG_MODE mode = EDIT_TASK_DIALOG_MODE.NEW_TASK;
    private View dialogView = null;
    private RepeatUnitAdapter adapter = null;

    private EditTaskCallback callback = null;

    public static EditTaskDialogFragment newInstance(EDIT_TASK_DIALOG_MODE _mode) {

        Bundle args = new Bundle();

        EditTaskDialogFragment fragment = new EditTaskDialogFragment();
        args.putSerializable(ARGS_TASK_ID, null);
        args.putSerializable(ARGS_MODE_ID, _mode);
        fragment.setArguments(args);
        return fragment;
    }

    public static EditTaskDialogFragment newInstance(Task _task, EDIT_TASK_DIALOG_MODE _mode) {

        Bundle args = new Bundle();

        EditTaskDialogFragment fragment = new EditTaskDialogFragment();
        args.putSerializable(ARGS_TASK_ID, _task);
        args.putSerializable(ARGS_MODE_ID, _mode);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (null != getArguments()) {
            task = (Task) getArguments().getSerializable(ARGS_TASK_ID);
            mode = (EDIT_TASK_DIALOG_MODE) getArguments().getSerializable(ARGS_MODE_ID);
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
            detail.setText(task.getDetail());

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

                String _name;
                String _detail;
                int _repeatCount;
                Task.REPEAT_UNIT _repeatUnit;
                boolean _repeatFlag;
                boolean _enableTask;
                Date _lastDate;

                EditText name = dialogView.findViewById(R.id.nameEdit);
                _name = name.getText().toString();

                EditText detail = dialogView.findViewById(R.id.detailEdit);
                _detail = detail.getText().toString();

                EditText repeatUnitEdit = dialogView.findViewById(R.id.repeatCountEdit);
                try {
                    _repeatCount = Integer.parseInt(repeatUnitEdit.getText().toString());
                } catch (NumberFormatException | IllegalFormatException e) {
                    _repeatCount = 0;
                }

                Spinner repeatUnitSpinner = dialogView.findViewById(R.id.repeatUnitSpinner);
                _repeatUnit = Task.REPEAT_UNIT.getUnitFromString(repeatUnitSpinner.getSelectedItem().toString());

                CheckBox repeatFlagCheck = dialogView.findViewById(R.id.repeatFlag);
                _repeatFlag = repeatFlagCheck.isChecked();

                _enableTask = true;
                if (mode == EDIT_TASK_DIALOG_MODE.NEW_TASK) {
                    _lastDate = null;
                }
                else {
                    _lastDate = new Date();
                }

                task = Task.generateTask(getActivity(), _name, _detail, _repeatCount, _repeatUnit, _repeatFlag, _enableTask, _lastDate);
                if (null != task) {
                    if (null != callback && mode == EDIT_TASK_DIALOG_MODE.NEW_TASK) {
                        callback.addTask(task);
                    } else if (null != callback && mode == EDIT_TASK_DIALOG_MODE.EDIT_TASK) {
                        callback.editTask(task);
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    AlertDialog dialog = builder.setTitle(R.string.task_illegaldialog_title)
                            .setMessage(R.string.task_illegaldialog_msg)
                            .setPositiveButton(R.string.task_illegaldialog_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).create();
                    dialog.show();
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
        void editTask(Task task);
    }

}
