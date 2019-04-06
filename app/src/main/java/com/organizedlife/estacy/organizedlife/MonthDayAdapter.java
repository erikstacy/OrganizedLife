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

public class MonthDayAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<MonthDay> monthDayList;

    public MonthDayAdapter(Context context, ArrayList<MonthDay> monthDay) {
        this.context = context;
        this.monthDayList = monthDay;
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
        return monthDayList.size();
    }

    @Override
    public Object getItem(int position) {
        return monthDayList.get(position);
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
            convertView = inflater.inflate(R.layout.monthday_list_item, null, true);

            holder.titleView = (TextView) convertView.findViewById(R.id.monthday_title_view);
            holder.eventoneView = (TextView) convertView.findViewById(R.id.monthday_eventone_view);
            holder.eventtwoView = (TextView) convertView.findViewById(R.id.monthday_eventtwo_view);
            holder.eventthreeView = (TextView) convertView.findViewById(R.id.monthday_eventthree_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.titleView.setText(monthDayList.get(position).getTitle());
        if(monthDayList.get(position).getEventOne() != "") {
            holder.eventoneView.setText(monthDayList.get(position).getEventOne());
        } else {
            holder.eventoneView.setVisibility(View.GONE);
        }
        if(monthDayList.get(position).getEventTwo() != "") {
            holder.eventtwoView.setText(monthDayList.get(position).getEventTwo());
        } else {
            holder.eventtwoView.setVisibility(View.GONE);
        }
        if(monthDayList.get(position).getEventThree() != "") {
            holder.eventthreeView.setText(monthDayList.get(position).getEventThree());
        } else {
            holder.eventthreeView.setVisibility(View.GONE);
        }

        holder.titleView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                monthDayList.get(position).setTitle(holder.titleView.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return convertView;
    }

    private class ViewHolder {
        protected TextView titleView;
        protected TextView eventoneView;
        protected TextView eventtwoView;
        protected TextView eventthreeView;
    }
}
