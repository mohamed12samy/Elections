package com.example.elections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.elections.view.DataEntry;
import com.google.android.material.textfield.TextInputEditText;

public class EditPassword extends AppCompatActivity {

    TextInputEditText dayra_num;
    TextInputEditText mandoop_pass;
    TextInputEditText survey_pass;
    TextInputEditText controller_pass;
    Spinner spinner;

    EditViewModel viewModel = new EditViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        dayra_num = findViewById(R.id.daira_num);
        mandoop_pass = findViewById(R.id.mandoop_pass);
        survey_pass = findViewById(R.id.survey_pass);
        controller_pass = findViewById(R.id.controller_pass);
        spinner = findViewById(R.id.governate_spinner);

        addListenerOnSpinnerItemSelection();



        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dayra_num.getText().toString().length()>0&&
                       mandoop_pass.getText().toString().length()>0 && survey_pass.getText().toString().length()>0 &&
                controller_pass.getText().toString().length()>0){

                    viewModel.editPasswords(spinner.getSelectedItemPosition() + 1,
                            Integer.parseInt(dayra_num.getText().toString()),
                            survey_pass.getText().toString(),
                            mandoop_pass.getText().toString(),
                            controller_pass.getText().toString());

                }else {
                    Toast.makeText(EditPassword.this, "من فضلك املئ الخانات كلها", Toast.LENGTH_SHORT).show();
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
