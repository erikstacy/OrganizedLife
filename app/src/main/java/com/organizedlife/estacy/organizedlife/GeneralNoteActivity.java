package com.organizedlife.estacy.organizedlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Collections;

public class GeneralNoteActivity extends AppCompatActivity {

    DataHolder dataHolder;
    int generalPosition;
    ListView noteListView;
    NoteAdapter noteAdapter;

    // Variable to hold the last edited bullet note
    int lastBullet = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_note);

        // Get the noteList
        Intent i = getIntent();
        dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");
        generalPosition = (int)i.getSerializableExtra("generalPosition");

        // Create the note list adapter
        noteListView = findViewById(R.id.note_list_view);
        noteAdapter = new NoteAdapter(this, dataHolder.getGeneralList().get(generalPosition).getNoteList());
        noteListView.setAdapter(noteAdapter);
        registerForContextMenu(noteListView);

        // Set the title
        final TextView titleView = findViewById(R.id.title_view);
        titleView.setText(dataHolder.getGeneralList().get(generalPosition).getNoteTitle());

        // Set on click for the new note button
        TextView newNoteButton = findViewById(R.id.new_note_button);
        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastBullet == -1)
                    dataHolder.getGeneralList().get(generalPosition).getNoteList().add(new Note());
                else {
                    dataHolder.getGeneralList().get(generalPosition).getNoteList().add(lastBullet + 1, new Note());
                    lastBullet++;
                }
                noteListView.setAdapter(noteAdapter);
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

                // Set the dataHolder
                dataHolder.getGeneralList().get(generalPosition).setNoteTitle(titleView.getText().toString());

                // Sort the notes
                Collections.sort(dataHolder.getGeneralList(), new GeneralNoteComparator());

                Intent toDoIntent = new Intent(GeneralNoteActivity.this, GeneralNoteListActivity.class);
                toDoIntent.putExtra("dataHolder", dataHolder);
                startActivity(toDoIntent);
            }
        });

        LinearLayout nestedButton = findViewById(R.id.nested_button);
        nestedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Save data
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(dataHolder);
                editor.putString("dataHolder", json);
                editor.apply();

                // Set the dataHolder
                dataHolder.getGeneralList().get(generalPosition).setNoteTitle(titleView.getText().toString());

                Intent toDoIntent = new Intent(GeneralNoteActivity.this, NestedNoteListActivity.class);
                toDoIntent.putExtra("dataHolder", dataHolder);
                toDoIntent.putExtra("generalPosition", generalPosition);
                startActivity(toDoIntent);
            }
        });
    }

    // Create context menu
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
                    dataHolder.getGeneralList().get(generalPosition).getNoteList().get(info.position).resetNote();
                } else {
                    dataHolder.getGeneralList().get(generalPosition).getNoteList().remove(info.position);
                }
                noteListView.setAdapter(noteAdapter);
                break;
            case R.id.select_view:
                lastBullet = info.position;
                break;
            case R.id.move_view:
                Note tempNote = dataHolder.getGeneralList().get(generalPosition).getNoteList().get(info.position);
                dataHolder.getGeneralList().get(generalPosition).getNoteList().remove(info.position);
                dataHolder.getGeneralList().get(generalPosition).getNoteList().add(lastBullet, tempNote);
                noteListView.setAdapter(noteAdapter);
                break;
        }

        return super.onContextItemSelected(item);
    }
}
