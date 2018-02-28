package org.zaregoto.apl.repeatabletodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.zaregoto.apl.repeatabletodo.model.Task;
import org.zaregoto.apl.repeatabletodo.model.Todo;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ContactViewHolder> {

    private LayoutInflater mInflater;
    private List<Todo> contactList;

    public TodoListAdapter(List<Todo> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(TodoListAdapter.ContactViewHolder contactViewHolder, int i) {
        Todo todo = contactList.get(i);
        contactViewHolder.todoName.setText(todo.getName());
        contactViewHolder.todoDetail.setText(todo.getDetail());
    }

    @Override
    public TodoListAdapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.adapter_todo_item, viewGroup, false);

        return new TodoListAdapter.ContactViewHolder(itemView);
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView todoName;
        TextView todoDetail;

        public ContactViewHolder(View v) {
            super(v);
            todoName =  (TextView) v.findViewById(R.id.todoname);
            todoDetail = (TextView)  v.findViewById(R.id.tododetail);
        }
    }
}
