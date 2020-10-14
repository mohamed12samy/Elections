package com.example.elections.viewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.elections.MyApplication;
import com.example.elections.ResultForView;
import com.example.elections.ResultOfLogin;
import com.example.elections.repository.ElectionRepository;

public class LagnaBaseViewModel extends ViewModel implements ResultOfLogin {
    private ElectionRepository repository;

    private ResultForView resultForView;

    public LagnaBaseViewModel(ResultForView result) {
        repository = ElectionRepository.getInstance();
        resultForView = result;
    }

    public void checkLogin(int governorate_position, int daira_num, String password) {
        repository.checkLogin(governorate_position, daira_num, password, this);
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
    public void checkLoginLagna(int governrate,String daira, String qesm, String school, String lagna) {
        repository.checkLoginLagna(governrate, daira, qesm, school, lagna, this);
    }
}