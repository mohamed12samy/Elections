package com.example.elections.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.elections.R;
import com.example.elections.viewModel.CandidateViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class CandidatesDataEntry extends AppCompatActivity {

    TextInputEditText candidate_name;
    TextInputEditText dayra_num;
    TextInputEditText candidate_pass;
    Spinner spinner;

    CandidateViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates_data_entry);

        dayra_num = findViewById(R.id.daira_num);
        candidate_name = findViewById(R.id.candidate_name);
        candidate_pass = findViewById(R.id.candidate_password);
        spinner = findViewById(R.id.governate_spinner);
        viewModel = new CandidateViewModel();
        addListenerOnSpinnerItemSelection();


        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (candidate_name.getText().toString().length() > 0 && dayra_num.getText().toString().length() > 0 && candidate_pass.getText().toString().length() > 0) {
                    viewModel.addNewCandidate(candidate_name.getText().toString(), spinner.getSelectedItemPosition() + 1,
                            Integer.parseInt(dayra_num.getText().toString()), candidate_pass.getText().toString());
                    Toast.makeText(CandidatesDataEntry.this, "تم الاضافه بنجاح", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CandidatesDataEntry.this, "من فضلك املئ الخانات كلها", Toast.LENGTH_SHORT).show();
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


}