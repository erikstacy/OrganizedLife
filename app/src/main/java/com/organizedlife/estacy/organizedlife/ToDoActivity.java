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

public class ToDoActivity extends AppCompatActivity {

    DataHolder dataHolder;
    int dayPosition;
    ListView todoListView;
    ToDoAdapter todoAdapter;

    // Variable to hold the last edited todo
    int lastTodo = -1;

    // Create the swiping object
    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        // Swiping object is initialized
        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        // Get the noteList
        Intent i = getIntent();
        dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");
        dayPosition = (int)i.getSerializableExtra("dayPosition");

        // Get the list open
        todoListView = findViewById(R.id.todo_list_view);
        todoAdapter = new ToDoAdapter(this, dataHolder.getDayList().get(dayPosition).getTodoList());
        todoListView.setAdapter(todoAdapter);
        registerForContextMenu(todoListView);

        // Set new todo button
        TextView newToDoButton = findViewById(R.id.new_todo_button);
        newToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastTodo == -1)
                    dataHolder.getDayList().get(dayPosition).getTodoList().add(new ToDo());
                else {
                    dataHolder.getDayList().get(dayPosition).getTodoList().add(lastTodo + 1, new ToDo());
                    lastTodo++;
                }
                todoListView.setAdapter(todoAdapter);
            }
        });

        // Create bottom bar clickers
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

                Intent moveToButton = new Intent(ToDoActivity.this, DayActivity.class);
                moveToButton.putExtra("dataHolder", dataHolder);
                startActivity(moveToButton);
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

                Intent moveToButton = new Intent(ToDoActivity.this, NoteActivity.class);
                moveToButton.putExtra("dataHolder", dataHolder);
                moveToButton.putExtra("dayPosition", dayPosition);
                startActivity(moveToButton);
            }
        });

        LinearLayout calendarButton = findViewById(R.id.calendar_button);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Save data
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(dataHolder);
                editor.putString("dataHolder", json);
                editor.apply();

                Intent moveToButton = new Intent(ToDoActivity.this, CalendarActivity.class);
                moveToButton.putExtra("dataHolder", dataHolder);
                moveToButton.putExtra("dayPosition", dayPosition);
                startActivity(moveToButton);
            }
        });
    }

    // Creates the context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.dsm_menu, menu);
    }

    // Click on context menu item

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.delete_view:
                if(info.position == 0) {
                    dataHolder.getDayList().get(dayPosition).getTodoList().get(info.position).resetToDo();
                } else {
                    dataHolder.getDayList().get(dayPosition).getTodoList().remove(info.position);
                }
                todoListView.setAdapter(todoAdapter);
                break;
            case R.id.select_view:
                lastTodo = info.position;
                break;
            case R.id.move_view:
                ToDo tempTodo = dataHolder.getDayList().get(dayPosition).getTodoList().get(info.position);
                dataHolder.getDayList().get(dayPosition).getTodoList().remove(info.position);
                dataHolder.getDayList().get(dayPosition).getTodoList().add(lastTodo, tempTodo);
                todoListView.setAdapter(todoAdapter);
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
                Intent moveToButton = new Intent(ToDoActivity.this, NoteActivity.class);
                moveToButton.putExtra("dataHolder", dataHolder);
                moveToButton.putExtra("dayPosition", dayPosition);
                startActivity(moveToButton);
            }
            // Swipe right to left
            else if(event2.getX() < event1.getX()) {
                // Move to calendar
                Intent moveToButton = new Intent(ToDoActivity.this, CalendarActivity.class);
                moveToButton.putExtra("dataHolder", dataHolder);
                moveToButton.putExtra("dayPosition", dayPosition);
                startActivity(moveToButton);
            }

            return true;
        }
    }
}
