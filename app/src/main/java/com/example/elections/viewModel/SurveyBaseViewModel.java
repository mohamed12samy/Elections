package com.example.elections.viewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.elections.MyApplication;
import com.example.elections.ResultForView;
import com.example.elections.ResultOfLogin;
import com.example.elections.model.Candidates;
import com.example.elections.repository.ElectionRepository;

import java.util.List;

public class SurveyBaseViewModel extends ViewModel implements ResultOfLogin {

    private ElectionRepository repository;
    private ResultForView resultForView;

    public SurveyBaseViewModel(ResultForView result){
        repository = ElectionRepository.getInstance();
        repository.getDateFromSharedPreference("survey");
        repository.conntectWithDataBase();
        resultForView = result;
    }

    @Override
    public void changeFlag(boolean result) {
        //resultForView.getResult(result);
        Log.d("###", "##onClick: " + result);
        if (result) {
            resultForView.getIn();
        } else
            Toast.makeText(MyApplication.getInstance(), "البيانات خاطئه", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkLoginLagna(int governrate,String daira, String qesm, String school, String lagna, String survey_pass) {
        repository.checkLoginServey(governrate, daira, qesm, school, survey_pass,  this);
    }

}
