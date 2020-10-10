package com.example.elections.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elections.R;
import com.example.elections.viewModel.SchoolViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SchoolsDataEntry extends AppCompatActivity {

    TextInputEditText school_name;
    TextInputEditText dayra_num;
    TextInputEditText qesm_num;
    TextInputEditText school_num;
    TextInputEditText lagan_num;
    Spinner spinner;
    SchoolViewModel viewModel = new SchoolViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools_data_entry);
        dayra_num = findViewById(R.id.daira_num);
        school_name = findViewById(R.id.school_name);
        qesm_num = findViewById(R.id.qesm_num);
        school_num = findViewById(R.id.school_num);
        lagan_num = findViewById(R.id.lagan_num);
        spinner = findViewById(R.id.governate_spinner);

        addListenerOnSpinnerItemSelection();

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (school_name.getText().toString().length() > 0 && school_num.getText().toString().length() > 0 && dayra_num.getText().toString().length() > 0 &&
                        qesm_num.getText().toString().length() > 0
                        && lagan_num.getText().toString().length() > 0) {

                    viewModel.addNewSchool(school_name.getText().toString(), spinner.getSelectedItemPosition() + 1, Integer.parseInt(dayra_num.getText().toString())
                            , Integer.parseInt(school_num.getText().toString()), Integer.parseInt(qesm_num.getText().toString()), Integer.parseInt(lagan_num.getText().toString()));
                } else {
                    Toast.makeText(SchoolsDataEntry.this, "من فضلك املئ الخانات كلها", Toast.LENGTH_SHORT).show();
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