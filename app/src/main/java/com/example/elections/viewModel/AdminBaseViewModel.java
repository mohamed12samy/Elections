package com.example.elections.viewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.elections.MyApplication;
import com.example.elections.repository.ElectionRepository;

import java.util.Vector;

import static android.content.ContentValues.TAG;

public class AdminBaseViewModel extends ViewModel {
    private ElectionRepository repository;

    public AdminBaseViewModel(){
        repository = ElectionRepository.getInstance();
    }

    public boolean checkLogin(int governorate_position , int daira_num , String password){

        Vector<Integer> result = repository.checkLogin(governorate_position,daira_num,password);
        Log.d(TAG, "checkLogin: "+result.size());
      /*  if(result.get(0)==7)
        {
            return true;
        }
        else{
            return false;
        }*/
      return true;
    }
}
