package com.organizedlife.estacy.organizedlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class NoteActivity extends AppCompatActivity {

    private static final String BULLET_LOG = NoteActivity.class.getSimpleName();

    DataHolder dataHolder;
    int dayPosition;
    ListView noteListView;
    NoteAdapter noteAdapter;

    // Variable to hold the last edited bullet note
    int lastBullet = -1;

    // Create the swiping object
    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Swiping object initialized
        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        // get noteList
        Intent i = getIntent();
        dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");
        dayPosition = (int)i.getSerializableExtra("dayPosition");

        // note list adapter
        noteListView = findViewById(R.id.note_list_view);
        noteAdapter = new NoteAdapter(this, dataHolder.getDayList().get(dayPosition).getNoteList());
        noteListView.setAdapter(noteAdapter);
        registerForContextMenu(noteListView);

        // new note button
        TextView newNoteButton = findViewById(R.id.new_note_button);
        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastBullet == -1)
                    dataHolder.getDayList().get(dayPosition).getNoteList().add(new Note());
                else {
                    dataHolder.getDayList().get(dayPosition).getNoteList().add(lastBullet + 1, new Note());
                    lastBullet++;
                }
                noteListView.setAdapter(noteAdapter);
            }
        });

        noteListView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Toast.makeText(NoteActivity.this, "This is a toast", Toast.LENGTH_SHORT).show();

                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    switch(keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            dataHolder.getDayList().get(dayPosition).getNoteList().add(new Note());
                            noteListView.setAdapter(noteAdapter);
                            return true;
                    }
                }

                return false;
            }
        });

        // bottom bar
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

                Intent toDoIntent = new Intent(NoteActivity.this, DayActivity.class);
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

                Intent toDoIntent = new Intent(NoteActivity.this, ToDoActivity.class);
                toDoIntent.putExtra("dataHolder", dataHolder);
                toDoIntent.putExtra("dayPosition", dayPosition);
                startActivity(toDoIntent);
            }
        });

        LinearLayout calendarButton = findViewById(R.id.calendar_button);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // save data
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(dataHolder);
                editor.putString("dataHolder", json);
                editor.apply();

                Intent toDoIntent = new Intent(NoteActivity.this, CalendarActivity.class);
                toDoIntent.putExtra("dataHolder", dataHolder);
                toDoIntent.putExtra("dayPosition", dayPosition);
                startActivity(toDoIntent);
            }
        });
    }

    // context menu

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.dsm_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.delete_view:
                if(info.position == 0) {
                    dataHolder.getDayList().get(dayPosition).getNoteList().get(info.position).resetNote();
                } else {
                    dataHolder.getDayList().get(dayPosition).getNoteList().remove(info.position);
                }
                noteListView.setAdapter(noteAdapter);
                break;
            case R.id.select_view:
                lastBullet = info.position;
                break;
            case R.id.move_view:
                Note tempNote = dataHolder.getDayList().get(dayPosition).getNoteList().get(info.position);
                dataHolder.getDayList().get(dayPosition).getNoteList().remove(info.position);
                dataHolder.getDayList().get(dayPosition).getNoteList().add(lastBullet, tempNote);
                noteListView.setAdapter(noteAdapter);
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
                Intent moveToButton = new Intent(NoteActivity.this, DayActivity.class);
                moveToButton.putExtra("dataHolder", dataHolder);
                moveToButton.putExtra("dayPosition", dayPosition);
                startActivity(moveToButton);
            }
            // Swipe right to left
            else if(event2.getX() < event1.getX()) {
                // Move to calendar
                Intent moveToButton = new Intent(NoteActivity.this, ToDoActivity.class);
                moveToButton.putExtra("dataHolder", dataHolder);
                moveToButton.putExtra("dayPosition", dayPosition);
                startActivity(moveToButton);
            }

            return true;
        }
    }
}
