package com.example.elections.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.elections.R;
import com.example.elections.ResultForView;
import com.example.elections.VoteSorting;
import com.example.elections.viewModel.AdminBaseViewModel;
import com.example.elections.viewModel.LagnaBaseViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LagnaBase extends AppCompatActivity implements ResultForView {

    private SharedPreferences sharedPreferences;
    TextInputEditText daira_num;
    TextInputEditText qesm_num;
    TextInputEditText school_num;
    TextInputEditText lagna_num;
    TextInputEditText mandoop_pass;
    Spinner spinner;

    LagnaBaseViewModel viewModel = new LagnaBaseViewModel(this);

    String daira ;
    String qesm ;
    String school;
    String lagna;
    String mandoopPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lagna_base);

        sharedPreferences = getSharedPreferences("mandoop", Context.MODE_PRIVATE);


        if(sharedPreferences.getBoolean("in", false)) {
            Intent i = new Intent(LagnaBase.this, LagnaFlow.class);
            startActivity(i);
            finish();
        }

        daira_num = findViewById(R.id.daira_number);
        qesm_num = findViewById(R.id.qesm_num);
        school_num = findViewById(R.id.school_num);
        lagna_num = findViewById(R.id.lagna_num);
        mandoop_pass = findViewById(R.id.mandoop_pass);
        spinner = findViewById(R.id.governate_spinner);

        addListenerOnSpinnerItemSelection();

        findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**/
                daira = daira_num.getText().toString();
                qesm = qesm_num.getText().toString();
                school = school_num.getText().toString();
                lagna = lagna_num.getText().toString();
                mandoopPass = mandoop_pass.getText().toString();

                if(daira.length() > 0 && qesm.length()>0 && school.length()>0 && lagna.length()>0 && mandoopPass.length()>0) {
                    viewModel.checkLoginLagna((spinner.getSelectedItemPosition() + 1),daira, qesm, school, lagna, mandoopPass);
                }else{
                    Toast.makeText(LagnaBase.this, "من فضلك املئ الخانات كلها", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void addListenerOnSpinnerItemSelection() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void getIn() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("governorate_position", spinner.getSelectedItemPosition() + 1).apply();
        editor.putInt("daira_num", Integer.parseInt(daira_num.getText().toString())).apply();
        editor.putInt("qesm_num", Integer.parseInt(qesm_num.getText().toString())).apply();
        editor.putInt("school_num", Integer.parseInt(school_num.getText().toString())).apply();
        editor.putInt("lagna_num", Integer.parseInt(lagna_num.getText().toString())).apply();
        editor.putBoolean("in", true);
        editor.commit();

        Intent i = new Intent(LagnaBase.this, LagnaFlow.class);
        startActivity(i);
        finish();
    }
}
