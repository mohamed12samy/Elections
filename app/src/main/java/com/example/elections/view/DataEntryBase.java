package com.example.elections.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.elections.R;

public class DataEntryBase extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry_base);

        findViewById(R.id.addDayra).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DataEntryBase.this,DataEntry.class);
                startActivity(i);
            }
        });
        findViewById(R.id.addQesm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DataEntryBase.this,QsmsDataEntry.class);
                startActivity(i);
            }
        });
        findViewById(R.id.addSchool).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DataEntryBase.this,SchoolsDataEntry.class);
                startActivity(i);
            }
        });
        findViewById(R.id.addCandidate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DataEntryBase.this,CandidatesDataEntry.class);
                startActivity(i);
            }
        });

    }
}