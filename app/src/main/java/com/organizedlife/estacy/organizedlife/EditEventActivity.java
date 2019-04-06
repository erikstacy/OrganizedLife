package com.organizedlife.estacy.organizedlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class EditEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        // Get the extras
        Intent i = getIntent();
        final DataHolder dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");
        final int dayPosition = (int)i.getSerializableExtra("dayPosition");
        final int position = (int)i.getSerializableExtra("position");

        // Get the EditText views into java
        final EditText titleView = findViewById(R.id.title_view);
        final EditText startView = findViewById(R.id.startView);
        final EditText endView = findViewById(R.id.endView);

        // Set the EditText views text
        titleView.setText(dataHolder.getDayList().get(dayPosition).getEventList().get(position).getEventTitle());
        startView.setText(dataHolder.getDayList().get(dayPosition).getEventList().get(position).getStartTime());
        endView.setText(dataHolder.getDayList().get(dayPosition).getEventList().get(position).getEndTime());

        // Set the save event button
        TextView saveEventButton = findViewById(R.id.save_event_button);
        saveEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the new note into the eventList
                dataHolder.getDayList().get(dayPosition).getEventList().get(position).setEventTitle(titleView.getText().toString());
                dataHolder.getDayList().get(dayPosition).getEventList().get(position).setStartTime(startView.getText().toString());
                dataHolder.getDayList().get(dayPosition).getEventList().get(position).setEndTime(endView.getText().toString());

                // Save data
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(dataHolder);
                editor.putString("dataHolder", json);
                editor.apply();

                Intent saveEvent = new Intent(EditEventActivity.this, CalendarActivity.class);
                saveEvent.putExtra("dataHolder", dataHolder);
                saveEvent.putExtra("dayPosition", dayPosition);
                startActivity(saveEvent);
            }
        });
    }
}
