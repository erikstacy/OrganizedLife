package com.organizedlife.estacy.organizedlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    DataHolder dataHolder;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load data
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dataHolder", null);
        Type type = new TypeToken<DataHolder>() {}.getType();
        dataHolder = gson.fromJson(json, type);
        if(dataHolder == null) {
            dataHolder = new DataHolder();
        }

        // Get the activity to change after a second
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent openApp = new Intent(MainActivity.this, DayActivity.class);
                openApp.putExtra("dataHolder", dataHolder);
                startActivity(openApp);
                finish();
            }
        }, 1000);

        /*
        // Move in to the app by clicking on the screen
        TextView title = findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openApp = new Intent(MainActivity.this, DayActivity.class);
                openApp.putExtra("dataHolder", dataHolder);
                startActivity(openApp);
            }
        });
        */
    }
}
