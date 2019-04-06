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

public class MonthDayActivity extends AppCompatActivity {

    DataHolder dataHolder;
    int monthPosition;
    ListView monthDayListView;
    MonthDayAdapter monthDayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_day);

        // Get the noteList
        Intent i = getIntent();
        dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");
        monthPosition = (int)i.getSerializableExtra("monthPosition");

        // Get the list open
        monthDayListView = findViewById(R.id.monthday_list_view);
        monthDayAdapter = new MonthDayAdapter(this, dataHolder.getMonthList().get(monthPosition).getMonthDayList());
        monthDayListView.setAdapter(monthDayAdapter);
        registerForContextMenu(monthDayListView);

        // Set new todo button
        TextView newToDoButton = findViewById(R.id.new_monthday_button);
        newToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHolder.getMonthList().get(monthPosition).getMonthDayList().add(new MonthDay());
                monthDayListView.setAdapter(monthDayAdapter);
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

                Intent moveToButton = new Intent(MonthDayActivity.this, MonthActivity.class);
                moveToButton.putExtra("dataHolder", dataHolder);
                startActivity(moveToButton);
            }
        });

        LinearLayout todoButton = findViewById(R.id.todo_button);
        todoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Save data
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(dataHolder);
                editor.putString("dataHolder", json);
                editor.apply();

                Intent moveToButton = new Intent(MonthDayActivity.this, MonthTodoActivity.class);
                moveToButton.putExtra("dataHolder", dataHolder);
                moveToButton.putExtra("monthPosition", monthPosition);
                startActivity(moveToButton);
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
                    dataHolder.getMonthList().get(monthPosition).getMonthDayList().get(info.position).resetMonthDay();
                } else {
                    dataHolder.getMonthList().get(monthPosition).getMonthDayList().remove(info.position);
                }
                monthDayListView.setAdapter(monthDayAdapter);
                break;
            case R.id.edit_view:
                Intent editEvent = new Intent(MonthDayActivity.this, EditMonthDayActivity.class);
                editEvent.putExtra("dataHolder", dataHolder);
                editEvent.putExtra("position", info.position);
                editEvent.putExtra("monthPosition", monthPosition);
                startActivity(editEvent);
                break;
        }

        return super.onContextItemSelected(item);
    }
}
