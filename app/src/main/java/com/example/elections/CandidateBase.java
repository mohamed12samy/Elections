package com.example.elections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.elections.view.AdminBase;
import com.example.elections.view.CollectingAdmin;
import com.example.elections.view.DataEntryBase;
import com.example.elections.viewModel.AdminBaseViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class CandidateBase extends AppCompatActivity implements CheckLoginForCandidate{

    private SharedPreferences sharedPreferences;
    TextInputEditText daira_num;
    TextInputEditText candidate_num;
    TextInputEditText password;
    Spinner spinner;
    CandidateBaseViewModel viewModel = new CandidateBaseViewModel(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_base);

        daira_num = findViewById(R.id.daira_number);
        password = findViewById(R.id.Password);
        candidate_num = findViewById(R.id.candidate_num);
        spinner = findViewById(R.id.governate_spinner);

        sharedPreferences = getSharedPreferences("candidate", Context.MODE_PRIVATE);
        addListenerOnSpinnerItemSelection();
        if(sharedPreferences.getBoolean("in",false))
        {
            Intent i = new Intent(CandidateBase.this, CollectingAdmin.class);// lessa ma3mltsh page.
            startActivity(i);
            finish();
            Toast.makeText(CandidateBase.this, "مرحبا بك", Toast.LENGTH_SHORT).show();
        }


        findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String daira =daira_num.getText().toString();
                String pass =password.getText().toString();
                String can_num = candidate_num.getText().toString();
                int governate_postion = spinner.getSelectedItemPosition() + 1;
                //DataEntry
                 if(daira.length() > 0 && pass.length()>0 && can_num.length()>0) {
                    //check if the data is correct first.
                    viewModel.checkLogin(governate_postion,Integer.parseInt(daira),pass, Integer.parseInt(can_num));
                }
                else{
                    Toast.makeText(CandidateBase.this, "من فضلك املئ الخانات كلها", Toast.LENGTH_SHORT).show();
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
    public void getIn(){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("governorate_position", spinner.getSelectedItemPosition() + 1).apply();
        editor.putInt("daira_num", Integer.parseInt(daira_num.getText().toString())).apply();
        editor.putInt("can_num", Integer.parseInt(candidate_num.getText().toString())).apply();
        editor.putString("password", password.getText().toString()).apply();
        editor.putBoolean("in", true);
        editor.commit();

        Intent i = new Intent(CandidateBase.this, CollectingAdmin.class);// lessa ma3mltsh page.
        startActivity(i);
        finish();
        Toast.makeText(CandidateBase.this, "مرحبا بك", Toast.LENGTH_SHORT).show();

    }

}
