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

public class MonthTodoActivity extends AppCompatActivity {

    DataHolder dataHolder;
    int monthPosition;
    ListView todoListView;
    ToDoAdapter todoAdapter;

    // Variable to hold the last edited todo
    int lastTodo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_todo);

        // Get the noteList
        Intent i = getIntent();
        dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");
        monthPosition = (int)i.getSerializableExtra("monthPosition");

        // Get the list open
        todoListView = findViewById(R.id.todo_list_view);
        todoAdapter = new ToDoAdapter(this, dataHolder.getMonthList().get(monthPosition).getTodoList());
        todoListView.setAdapter(todoAdapter);
        registerForContextMenu(todoListView);

        // Set new todo button
        TextView newToDoButton = findViewById(R.id.new_todo_button);
        newToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastTodo == -1)
                    dataHolder.getMonthList().get(monthPosition).getTodoList().add(new ToDo());
                else {
                    dataHolder.getMonthList().get(monthPosition).getTodoList().add(lastTodo + 1, new ToDo());
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

                Intent moveToButton = new Intent(MonthTodoActivity.this, MonthActivity.class);
                moveToButton.putExtra("dataHolder", dataHolder);
                startActivity(moveToButton);
            }
        });

        LinearLayout eventButton = findViewById(R.id.event_button);
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Save data
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(dataHolder);
                editor.putString("dataHolder", json);
                editor.apply();

                Intent moveToButton = new Intent(MonthTodoActivity.this, MonthDayActivity.class);
                moveToButton.putExtra("dataHolder", dataHolder);
                moveToButton.putExtra("monthPosition", monthPosition);
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
                    dataHolder.getMonthList().get(monthPosition).getTodoList().get(info.position).resetToDo();
                } else {
                    dataHolder.getMonthList().get(monthPosition).getTodoList().remove(info.position);
                }
                todoListView.setAdapter(todoAdapter);
                break;
            case R.id.select_view:
                lastTodo = info.position;
                break;
            case R.id.move_view:
                ToDo tempTodo = dataHolder.getMonthList().get(monthPosition).getTodoList().get(info.position);
                dataHolder.getMonthList().get(monthPosition).getTodoList().remove(info.position);
                dataHolder.getMonthList().get(monthPosition).getTodoList().add(lastTodo, tempTodo);
                todoListView.setAdapter(todoAdapter);
                break;
        }

        return super.onContextItemSelected(item);
    }
}
