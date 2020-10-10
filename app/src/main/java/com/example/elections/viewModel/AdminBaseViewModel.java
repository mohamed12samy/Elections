package com.example.elections.viewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.elections.MyApplication;
import com.example.elections.ResultForView;
import com.example.elections.ResultOfLogin;
import com.example.elections.repository.ElectionRepository;
import com.example.elections.view.AdminBase;

import java.util.Vector;

import static android.content.ContentValues.TAG;

public class AdminBaseViewModel extends ViewModel implements ResultOfLogin {
    private ElectionRepository repository;

    private ResultForView resultForView;

    public AdminBaseViewModel(ResultForView result){
        repository = ElectionRepository.getInstance();
        resultForView = result;
    }

    public void checkLogin(int governorate_position , int daira_num , String password){

        repository.checkLogin(governorate_position,daira_num,password, this);
    }

    @Override
    public void changeFlag(boolean result) {
        resultForView.getResult(result);
        Log.d("###", "##onClick: "+ result);
    }
}
