package com.organizedlife.estacy.organizedlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class EditMonthDayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_month_day);

        // Get the extras
        Intent i = getIntent();
        final DataHolder dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");
        final int monthPosition = (int)i.getSerializableExtra("monthPosition");
        final int position = (int)i.getSerializableExtra("position");

        // Get the EditText views into java
        final EditText titleView = findViewById(R.id.title_view);
        final EditText eventoneView = findViewById(R.id.eventoneView);
        final EditText eventtwoView = findViewById(R.id.eventtwoView);
        final EditText eventthreeView = findViewById(R.id.eventthreeView);

        // Set the EditText views text
        titleView.setText(dataHolder.getMonthList().get(monthPosition).getMonthDayList().get(position).getTitle());
        eventoneView.setText(dataHolder.getMonthList().get(monthPosition).getMonthDayList().get(position).getEventOne());
        eventtwoView.setText(dataHolder.getMonthList().get(monthPosition).getMonthDayList().get(position).getEventTwo());
        eventthreeView.setText(dataHolder.getMonthList().get(monthPosition).getMonthDayList().get(position).getEventThree());

        // Set the save event button
        TextView saveEventButton = findViewById(R.id.save_monthday_button);
        saveEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the new note into the eventList
                dataHolder.getMonthList().get(monthPosition).getMonthDayList().get(position).setTitle(titleView.getText().toString());
                dataHolder.getMonthList().get(monthPosition).getMonthDayList().get(position).setEventOne(eventoneView.getText().toString());
                dataHolder.getMonthList().get(monthPosition).getMonthDayList().get(position).setEventTwo(eventtwoView.getText().toString());
                dataHolder.getMonthList().get(monthPosition).getMonthDayList().get(position).setEventThree(eventthreeView.getText().toString());

                // Save data
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(dataHolder);
                editor.putString("dataHolder", json);
                editor.apply();

                Intent saveEvent = new Intent(EditMonthDayActivity.this, MonthDayActivity.class);
                saveEvent.putExtra("dataHolder", dataHolder);
                saveEvent.putExtra("monthPosition", monthPosition);
                startActivity(saveEvent);
            }
        });
    }
}
