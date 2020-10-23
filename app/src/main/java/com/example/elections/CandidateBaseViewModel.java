package com.example.elections;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.elections.repository.ElectionRepository;

class CandidateBaseViewModel extends ViewModel implements ICandidateBaseViewModel {
    private ElectionRepository repository;

    private CheckLoginForCandidate resultForView;

    public CandidateBaseViewModel(CheckLoginForCandidate result){
        repository = ElectionRepository.getInstance();
        resultForView = result;
    }

    public void checkLogin(int governorate_position , int daira_num , String password, int can_num){

        repository.checkLogin(governorate_position,daira_num,password,  can_num, this);
    }

    @Override
    public void changeFlag(boolean result) {
        //resultForView.getResult(result);
        if(result){
            resultForView.getIn();
        }else
            Toast.makeText(MyApplication.getInstance(), "البيانات خاطئه", Toast.LENGTH_SHORT).show();
    }


}
