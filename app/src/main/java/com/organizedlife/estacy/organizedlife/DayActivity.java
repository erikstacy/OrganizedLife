package com.organizedlife.estacy.organizedlife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;

public class DayActivity extends AppCompatActivity {

    DataHolder dataHolder;
    ListView dayListView;
    DayAdapter dayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        // Get the dayList
        Intent i = getIntent();
        dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");

        // Hook up the adapter
        dayListView = findViewById(R.id.day_list_view);
        dayAdapter = new DayAdapter(this, dataHolder.getDayList());
        dayListView.setAdapter(dayAdapter);
        registerForContextMenu(dayListView);

        // Enter into the clicked day
        dayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent enterDay = new Intent(DayActivity.this, ToDoActivity.class);
                enterDay.putExtra("dataHolder", dataHolder);
                enterDay.putExtra("dayPosition", position);
                startActivity(enterDay);
            }
        });

        // Create new day
        TextView newDayButton = findViewById(R.id.new_day_button);
        newDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHolder.getDayList().add(new Day());
                Collections.sort(dataHolder.getDayList(), new DayComparator());
                dayListView.setAdapter(dayAdapter);
            }
        });

        // Move to Month Activity
        LinearLayout monthButton = findViewById(R.id.month_button);
        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monthActivity = new Intent(DayActivity.this, MonthActivity.class);
                monthActivity.putExtra("dataHolder", dataHolder);
                startActivity(monthActivity);
            }
        });

        // Move to General Note Activity
        LinearLayout generalButton = findViewById(R.id.general_button);
        generalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generalActivity = new Intent(DayActivity.this, GeneralNoteListActivity.class);
                generalActivity.putExtra("dataHolder", dataHolder);
                startActivity(generalActivity);
            }
        });

    }

    // Create the context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.basic_list_menu, menu);
    }

    // When selecting a context menu item
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.delete_view:
                if(info.position == 0) {
                    dataHolder.getDayList().get(info.position).resetDay();
                } else {
                    dataHolder.getDayList().remove(info.position);
                }
                dayListView.setAdapter(dayAdapter);
                break;
            case R.id.edit_view:
                Intent editDay = new Intent(DayActivity.this, EditDayActivity.class);
                editDay.putExtra("dataHolder", dataHolder);
                editDay.putExtra("position", info.position);
                startActivity(editDay);
                break;
        }

        return super.onContextItemSelected(item);
    }
}
