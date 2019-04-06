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

public class NestedNoteListActivity extends AppCompatActivity {

    DataHolder dataHolder;
    int generalPosition;
    ListView nestedListView;
    NestedNoteAdapter nestedNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_note_list);

        // Get the dayList
        Intent i = getIntent();
        dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");
        generalPosition = (int)i.getSerializableExtra("generalPosition");

        // Hook up the adapter
        nestedListView = findViewById(R.id.nested_list_view);
        nestedNoteAdapter = new NestedNoteAdapter(this, dataHolder.getGeneralList().get(generalPosition).getNestedNoteList());
        nestedListView.setAdapter(nestedNoteAdapter);
        registerForContextMenu(nestedListView);

        // Enter into the clicked nested note
        nestedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent enterDay = new Intent(NestedNoteListActivity.this, NestedNoteActivity.class);
                enterDay.putExtra("dataHolder", dataHolder);
                enterDay.putExtra("nestedPosition", position);
                enterDay.putExtra("generalPosition", generalPosition);
                startActivity(enterDay);
            }
        });

        // Create new General Note
        TextView newGeneralButton = findViewById(R.id.new_nested_button);
        newGeneralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHolder.getGeneralList().get(generalPosition).getNestedNoteList().add(new NestedNote());
                nestedListView.setAdapter(nestedNoteAdapter);
            }
        });

        // Move to Day Activity
        LinearLayout generalButton = findViewById(R.id.home_button);
        generalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dayActivity = new Intent(NestedNoteListActivity.this, GeneralNoteListActivity.class);
                dayActivity.putExtra("dataHolder", dataHolder);
                startActivity(dayActivity);
            }
        });

        // Move to Note Activity
        LinearLayout noteButton = findViewById(R.id.note_button);
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dayActivity = new Intent(NestedNoteListActivity.this, GeneralNoteActivity.class);
                dayActivity.putExtra("dataHolder", dataHolder);
                dayActivity.putExtra("generalPosition", generalPosition);
                startActivity(dayActivity);
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
                    dataHolder.getGeneralList().get(generalPosition).getNestedNoteList().get(info.position).resetNested();
                } else {
                    dataHolder.getGeneralList().get(generalPosition).getNestedNoteList().remove(info.position);
                }
                nestedListView.setAdapter(nestedNoteAdapter);
                break;
        }

        return super.onContextItemSelected(item);
    }
}
