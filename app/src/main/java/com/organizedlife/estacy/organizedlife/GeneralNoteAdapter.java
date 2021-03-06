package com.organizedlife.estacy.organizedlife;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GeneralNoteAdapter extends BaseAdapter {

    private Context context;

    public static ArrayList<GeneralNote> generalList;

    public GeneralNoteAdapter(Context context, ArrayList<GeneralNote> note) {
        this.context = context;
        this.generalList = note;
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
        return generalList.size();
    }

    @Override
    public Object getItem(int position) {
        return generalList.get(position);
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
            convertView = inflater.inflate(R.layout.day_list_item, null, true);

            holder.titleView = (TextView) convertView.findViewById(R.id.day_title_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.titleView.setText(generalList.get(position).getNoteTitle());

        holder.titleView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                generalList.get(position).setNoteTitle(holder.titleView.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return convertView;
    }

    private class ViewHolder {
        protected TextView titleView;
    }
}
