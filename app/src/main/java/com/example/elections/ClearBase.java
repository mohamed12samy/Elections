package com.example.elections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.elections.view.DataEntry;
import com.google.android.material.textfield.TextInputEditText;

public class ClearBase extends AppCompatActivity {

    TextInputEditText dayra_num;

    TextInputEditText controller_pass;
    Spinner spinner;

    ClearViewModel clearViewModel = new ClearViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_base);

        dayra_num = findViewById(R.id.daira_num);
        controller_pass = findViewById(R.id.controller_pass);
        spinner = findViewById(R.id.governate_spinner);

        addListenerOnSpinnerItemSelection();

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dayra_num.getText().toString().length()>0&&
                        controller_pass.getText().toString().length()>0){

                    clearViewModel.clear(spinner.getSelectedItemPosition() + 1, dayra_num.getText().toString(),
                            controller_pass.getText().toString());
                }else {
                    Toast.makeText(ClearBase.this, "من فضلك املئ الخانات كلها", Toast.LENGTH_SHORT).show();
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
