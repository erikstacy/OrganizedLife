package com.organizedlife.estacy.organizedlife;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<Note> noteList;

    public NoteAdapter(Context context, ArrayList<Note> note) {
        this.context = context;
        this.noteList = note;
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
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
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
            convertView = inflater.inflate(R.layout.edit_note_list_item, null, true);

            holder.editText = (EditText) convertView.findViewById(R.id.actual_note);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.editText.setText(noteList.get(position).getNoteString());

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noteList.get(position).setNoteString(holder.editText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return convertView;
    }

    private class ViewHolder {
        protected EditText editText;
    }
}
