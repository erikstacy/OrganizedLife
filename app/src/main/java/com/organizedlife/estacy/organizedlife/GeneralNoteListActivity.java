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

public class GeneralNoteListActivity extends AppCompatActivity {

    DataHolder dataHolder;
    ListView generalListView;
    GeneralNoteAdapter generalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_note_list);

        // Get the dayList
        Intent i = getIntent();
        dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");

        // Hook up the adapter
        generalListView = findViewById(R.id.general_list_view);
        generalAdapter = new GeneralNoteAdapter(this, dataHolder.getGeneralList());
        generalListView.setAdapter(generalAdapter);
        registerForContextMenu(generalListView);

        // Enter into the clicked general note
        generalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent enterDay = new Intent(GeneralNoteListActivity.this, GeneralNoteActivity.class);
                enterDay.putExtra("dataHolder", dataHolder);
                enterDay.putExtra("generalPosition", position);
                startActivity(enterDay);
            }
        });

        // Create new General Note
        TextView newGeneralButton = findViewById(R.id.new_general_button);
        newGeneralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHolder.getGeneralList().add(new GeneralNote());
                generalListView.setAdapter(generalAdapter);
            }
        });

        // Move to Day Activity
        LinearLayout generalButton = findViewById(R.id.day_button);
        generalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dayActivity = new Intent(GeneralNoteListActivity.this, DayActivity.class);
                dayActivity.putExtra("dataHolder", dataHolder);
                startActivity(dayActivity);
            }
        });

        // Move to Month Activity
        LinearLayout monthButton = findViewById(R.id.month_button);
        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monthActivity = new Intent(GeneralNoteListActivity.this, MonthActivity.class);
                monthActivity.putExtra("dataHolder", dataHolder);
                startActivity(monthActivity);
            }
        });
    }

    // Create context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.delete_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.delete_view:
                if(info.position == 0) {
                    dataHolder.getGeneralList().get(info.position).resetGeneral();
                } else {
                    dataHolder.getGeneralList().remove(info.position);
                }
                generalListView.setAdapter(generalAdapter);
                break;
        }

        return super.onContextItemSelected(item);
    }
}
