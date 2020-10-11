package com.example.elections.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elections.R;
import com.example.elections.viewModel.dayraViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class DataEntry extends AppCompatActivity {

    TextInputEditText dayra_name;
    TextInputEditText dayra_num;
    TextInputEditText qsm_num;
    TextInputEditText seats_num;
    Spinner spinner;
    dayraViewModel viewModel = new dayraViewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        dayra_num = findViewById(R.id.daira_num);
        dayra_name = findViewById(R.id.daira_name);
        qsm_num = findViewById(R.id.Qesm_counts);
        seats_num = findViewById(R.id.seatsNum);
        spinner = findViewById(R.id.governate_spinner);

        addListenerOnSpinnerItemSelection();



        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dayra_name.getText().toString().length()>0 &&dayra_num.getText().toString().length()>0&&
                qsm_num.getText().toString().length()>0 && seats_num.getText().toString().length()>0){
                    viewModel.addNewDayra(dayra_name.getText().toString(),spinner.getSelectedItemPosition() + 1,Integer.parseInt(dayra_num.getText().toString())
                            ,Integer.parseInt(seats_num.getText().toString()),Integer.parseInt(qsm_num.getText().toString()));
                    Toast.makeText(DataEntry.this, "تم الاضافه بنجاح", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DataEntry.this, "من فضلك املئ الخانات كلها", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.addQesms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DataEntry.this,QsmsDataEntry.class);
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