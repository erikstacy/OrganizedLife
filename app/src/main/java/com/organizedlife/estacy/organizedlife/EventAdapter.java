package com.organizedlife.estacy.organizedlife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<Event> eventList;

    public EventAdapter(Context context, ArrayList<Event> event) {
        this.context = context;
        this.eventList = event;
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
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
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
            convertView = inflater.inflate(R.layout.calendar_list_item, null, true);

            holder.eventTitleText = (TextView) convertView.findViewById(R.id.eventTitle);
            holder.eventStartText = (TextView) convertView.findViewById(R.id.eventStartTime);
            holder.eventEndText = (TextView) convertView.findViewById(R.id.eventEndTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.eventTitleText.setText(eventList.get(position).getEventTitle());
        holder.eventStartText.setText(eventList.get(position).getStartTime());
        holder.eventEndText.setText(eventList.get(position).getEndTime());

        return convertView;
    }

    private class ViewHolder {
        protected TextView eventTitleText;
        protected TextView eventStartText;
        protected TextView eventEndText;
    }
}
