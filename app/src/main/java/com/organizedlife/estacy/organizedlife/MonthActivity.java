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

public class MonthActivity extends AppCompatActivity {

    DataHolder dataHolder;
    ListView monthListView;
    MonthAdapter monthAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        // Get the dayList
        Intent i = getIntent();
        dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");

        // Hook up the adapter
        monthListView = findViewById(R.id.month_list_view);
        monthAdapter = new MonthAdapter(this, dataHolder.getMonthList());
        monthListView.setAdapter(monthAdapter);
        registerForContextMenu(monthListView);

        // Enter into the clicked month
        monthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent enterDay = new Intent(MonthActivity.this, MonthDayActivity.class);
                enterDay.putExtra("dataHolder", dataHolder);
                enterDay.putExtra("monthPosition", position);
                startActivity(enterDay);
            }
        });

        // Create new day
        TextView newMonthButton = findViewById(R.id.new_month_button);
        newMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHolder.getMonthList().add(new Month());
                monthListView.setAdapter(monthAdapter);
            }
        });

        // Move to Day Activity
        LinearLayout dayButton = findViewById(R.id.day_button);
        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monthActivity = new Intent(MonthActivity.this, DayActivity.class);
                monthActivity.putExtra("dataHolder", dataHolder);
                startActivity(monthActivity);
            }
        });

        // Move to General Note Activity
        LinearLayout generalButton = findViewById(R.id.general_button);
        generalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generalActivity = new Intent(MonthActivity.this, GeneralNoteListActivity.class);
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
                    dataHolder.getMonthList().get(info.position).resetMonth();
                } else {
                    dataHolder.getMonthList().remove(info.position);
                }
                monthListView.setAdapter(monthAdapter);
                break;
            case R.id.edit_view:
                Intent editMonth = new Intent(MonthActivity.this, EditMonthActivity.class);
                editMonth.putExtra("dataHolder", dataHolder);
                editMonth.putExtra("position", info.position);
                startActivity(editMonth);
                break;
        }

        return super.onContextItemSelected(item);
    }
}
