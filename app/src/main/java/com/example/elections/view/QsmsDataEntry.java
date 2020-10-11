package com.example.elections.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elections.R;
import com.example.elections.viewModel.QesmViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class QsmsDataEntry extends AppCompatActivity {

    TextInputEditText qesm_name;
    TextInputEditText dayra_num;
    TextInputEditText qesm_num;
    TextInputEditText schools_num;
    Spinner spinner;

    QesmViewModel viewModel = new QesmViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qsms_data_entry);

        dayra_num = findViewById(R.id.daira_num);
        qesm_name = findViewById(R.id.qesm_name);
        qesm_num = findViewById(R.id.qesm_num);
        schools_num = findViewById(R.id.schools_num);
        spinner = findViewById(R.id.governate_spinner);

        addListenerOnSpinnerItemSelection();

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qesm_name.getText().toString().length() > 0 && dayra_num.getText().toString().length() > 0 &&
                        qesm_num.getText().toString().length() > 0 && schools_num.getText().toString().length() > 0) {

                    viewModel.addNewQesm(qesm_name.getText().toString(), spinner.getSelectedItemPosition() + 1, Integer.parseInt(dayra_num.getText().toString())
                            , Integer.parseInt(schools_num.getText().toString()), Integer.parseInt(qesm_num.getText().toString()));
                    Toast.makeText(QsmsDataEntry.this, "تم الاضافه بنجاح", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QsmsDataEntry.this, "من فضلك املئ الخانات كلها", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.addSchools).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QsmsDataEntry.this,SchoolsDataEntry.class);
                startActivity(i);
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