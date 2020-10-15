package com.example.elections.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.elections.CandidateBase;
import com.example.elections.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.lagna_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LagnaBase.class);
                startActivity(i);
            }
        });

        findViewById(R.id.admin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AdminBase.class);
                startActivity(i);
            }
        });

        findViewById(R.id.survey_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SurveyBase.class);
                startActivity(i);
            }
        });

        findViewById(R.id.candidate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CandidateBase.class);
                startActivity(i);
            }
        });

        findViewById(R.id.empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("lagna", MODE_PRIVATE).edit().clear().apply();
                getSharedPreferences("mandoop", MODE_PRIVATE).edit().clear().apply();
                getSharedPreferences("survey", MODE_PRIVATE).edit().clear().apply();
                getSharedPreferences("candidate", MODE_PRIVATE).edit().clear().apply();
            }
        });
    }
}
