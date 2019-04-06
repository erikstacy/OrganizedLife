package com.organizedlife.estacy.organizedlife;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.ArrayList;

public class ToDoAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<ToDo> todoList;

    public ToDoAdapter(Context context, ArrayList<ToDo> todo) {
        this.context = context;
        this.todoList = todo;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.edit_todo_list_item, null, true);

            holder.editText = (EditText) convertView.findViewById(R.id.todo_text);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.todo_check_box);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.editText.setText(todoList.get(position).getToDoString());
        holder.checkBox.setChecked(todoList.get(position).getIsChecked());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                todoList.get(position).setIsChecked(isChecked);
            }
        });

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                todoList.get(position).setToDoString(holder.editText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return convertView;
    }

    private class ViewHolder {
        protected EditText editText;
        protected CheckBox checkBox;
    }
}
