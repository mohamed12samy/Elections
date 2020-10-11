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
import com.example.elections.repository.ElectionRepository;
import com.example.elections.viewModel.AdminBaseViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AdminBase extends AppCompatActivity implements ResultForView {

    private SharedPreferences sharedPreferences;
    TextInputEditText daira_num;
    TextInputEditText password;
    Spinner spinner;
    AdminBaseViewModel viewModel = new AdminBaseViewModel(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_base);
        daira_num = findViewById(R.id.daira_number);
        password = findViewById(R.id.Password);
        spinner = findViewById(R.id.governate_spinner);

        sharedPreferences = getSharedPreferences("lagna", Context.MODE_PRIVATE);
        addListenerOnSpinnerItemSelection();
        if(sharedPreferences.getBoolean("in",false))
        {
            Intent i = new Intent(AdminBase.this, CollectingAdmin.class);// lessa ma3mltsh page.
            startActivity(i);
            Toast.makeText(AdminBase.this, "مرحبا بك", Toast.LENGTH_SHORT).show();
        }


        findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String daira =daira_num.getText().toString();
                String pass =password.getText().toString();
                int governate_postion = spinner.getSelectedItemPosition() + 1;
                //DataEntry
                if(pass.equals("dataEntry123"))
                {
                    Intent i = new Intent(AdminBase.this, DataEntryBase.class);
                    startActivity(i);
                }
                else if(daira.length() > 0 && pass.length()>0) {
                    //check if the data is correct first.
                    viewModel.checkLogin(governate_postion,Integer.parseInt(daira),pass);
                }
                else{
                    Toast.makeText(AdminBase.this, "من فضلك املئ الخانات كلها", Toast.LENGTH_SHORT).show();
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
        editor.putString("password", password.getText().toString()).apply();
        editor.putBoolean("in", true);
        editor.commit();

        Intent i = new Intent(AdminBase.this, CollectingAdmin.class);// lessa ma3mltsh page.
        startActivity(i);
        Toast.makeText(AdminBase.this, "مرحبا بك", Toast.LENGTH_SHORT).show();

    }
}
