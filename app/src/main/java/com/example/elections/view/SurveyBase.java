package com.example.elections.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.elections.R;
import com.example.elections.ResultForView;
import com.example.elections.viewModel.LagnaBaseViewModel;
import com.example.elections.viewModel.SurveyBaseViewModel;
import com.example.elections.viewModel.SurveyViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SurveyBase extends AppCompatActivity implements ResultForView {


    private SharedPreferences sharedPreferences;
    TextInputEditText daira_num;
    TextInputEditText qesm_num;
    TextInputEditText school_num;
    TextInputEditText surveyPass;
    Spinner spinner;

    SurveyBaseViewModel viewModel = new SurveyBaseViewModel(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_base);


        sharedPreferences = getSharedPreferences("survey", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("in", false)) {
            Intent i = new Intent(SurveyBase.this, Survey.class);
            startActivity(i);
            finish();
        }

        daira_num = findViewById(R.id.daira_number);
        qesm_num = findViewById(R.id.qesm_num);
        school_num = findViewById(R.id.school_num);
        surveyPass = findViewById(R.id.survey_pass);
        spinner = findViewById(R.id.governate_spinner);

        addListenerOnSpinnerItemSelection();

        findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**/
                String daira = daira_num.getText().toString();
                String qesm = qesm_num.getText().toString();
                String school = school_num.getText().toString();
                String survey_pass = surveyPass.getText().toString();

                if(daira.length() > 0 && qesm.length()>0 && school.length()>0 && survey_pass.length()>0)
                {
                    viewModel.checkLoginLagna((spinner.getSelectedItemPosition() + 1),daira, qesm, school,"", survey_pass);
                }else{
                    Toast.makeText(SurveyBase.this, "من فضلك املئ الخانات كلها", Toast.LENGTH_SHORT).show();
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
        editor.putBoolean("in", true);
        editor.commit();

        Intent i = new Intent(SurveyBase.this, Survey.class);
        startActivity(i);
        finish();
    }
}
