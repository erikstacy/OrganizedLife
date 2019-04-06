package com.organizedlife.estacy.organizedlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

public class CalendarActivity extends AppCompatActivity {

    DataHolder dataHolder;
    int dayPosition;
    ListView eventListView;
    EventAdapter eventAdapter;

    // Create the swiping object
    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Swiping object is initialized
        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        // Get the noteList
        Intent i = getIntent();
        dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");
        dayPosition = (int)i.getSerializableExtra("dayPosition");

        // Link the adapter
        eventListView = findViewById(R.id.event_list_view);
        eventAdapter = new EventAdapter(this, dataHolder.getDayList().get(dayPosition).getEventList());
        eventListView.setAdapter(eventAdapter);
        registerForContextMenu(eventListView);

        // New Event button
        TextView newEventButton = findViewById(R.id.new_event_button);
        newEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHolder.getDayList().get(dayPosition).getEventList().add(new Event());
                eventListView.setAdapter(eventAdapter);
            }
        });

        // Set Bottom Bar clicks
        LinearLayout homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Save data
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(dataHolder);
                editor.putString("dataHolder", json);
                editor.apply();

                Intent toDoIntent = new Intent(CalendarActivity.this, DayActivity.class);
                toDoIntent.putExtra("dataHolder", dataHolder);
                startActivity(toDoIntent);
            }
        });

        LinearLayout toDoButton = findViewById(R.id.todo_button);
        toDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Save data
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(dataHolder);
                editor.putString("dataHolder", json);
                editor.apply();

                Intent toDoIntent = new Intent(CalendarActivity.this, ToDoActivity.class);
                toDoIntent.putExtra("dataHolder", dataHolder);
                toDoIntent.putExtra("dayPosition", dayPosition);
                startActivity(toDoIntent);
            }
        });

        LinearLayout noteButton = findViewById(R.id.note_button);
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Save data
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(dataHolder);
                editor.putString("dataHolder", json);
                editor.apply();

                Intent toDoIntent = new Intent(CalendarActivity.this, NoteActivity.class);
                toDoIntent.putExtra("dataHolder", dataHolder);
                toDoIntent.putExtra("dayPosition", dayPosition);
                startActivity(toDoIntent);
            }
        });
    }

    // Create the context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.basic_list_menu, menu);
    }

    // Get the context item clicks

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.delete_view:
                if(info.position == 0) {
                    dataHolder.getDayList().get(dayPosition).getEventList().get(info.position).resetEvent();
                } else {
                    dataHolder.getDayList().get(dayPosition).getEventList().remove(info.position);
                }
                eventListView.setAdapter(eventAdapter);
                break;
            case R.id.edit_view:
                Intent editEvent = new Intent(CalendarActivity.this, EditEventActivity.class);
                editEvent.putExtra("dataHolder", dataHolder);
                editEvent.putExtra("position", info.position);
                editEvent.putExtra("dayPosition", dayPosition);
                startActivity(editEvent);
                break;
        }

        return super.onContextItemSelected(item);
    }

    // Create the onTouch
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    // Create the Gesture class
    class LearnGesture extends GestureDetector.SimpleOnGestureListener {

        // Gesture side by side
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

            // Swipe left to right
            if(event2.getX() > event1.getX()) {
                // Move to note
                Intent moveToButton = new Intent(CalendarActivity.this, ToDoActivity.class);
                moveToButton.putExtra("dataHolder", dataHolder);
                moveToButton.putExtra("dayPosition", dayPosition);
                startActivity(moveToButton);
            }
            // Swipe right to left
            else if(event2.getX() < event1.getX()) {
                // Move to calendar
                return true;
            }

            return true;
        }
    }
}
