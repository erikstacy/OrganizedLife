package com.organizedlife.estacy.organizedlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Collections;

public class EditMonthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_month);

        // Get extra
        Intent i = getIntent();
        final DataHolder dataHolder = (DataHolder) i.getSerializableExtra("dataHolder");
        final int position = (int)i.getSerializableExtra("position");

        final EditText editTitleView = findViewById(R.id.edit_title_view);
        editTitleView.setText(dataHolder.getMonthList().get(position).getMonthTitle());

        TextView saveDayButton = findViewById(R.id.save_month_button);
        saveDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHolder.getMonthList().get(position).setMonthTitle(editTitleView.getText().toString());

                // Sort the months
                Collections.sort(dataHolder.getMonthList(), new MonthComparator());

                // Save data
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(dataHolder);
                editor.putString("dataHolder", json);
                editor.apply();

                Intent toSelect = new Intent(EditMonthActivity.this, MonthActivity.class);
                toSelect.putExtra("dataHolder", dataHolder);
                startActivity(toSelect);
            }
        });
    }
}
