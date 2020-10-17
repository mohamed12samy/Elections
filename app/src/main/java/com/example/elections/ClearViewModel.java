package com.example.elections;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.elections.model.Candidates;
import com.example.elections.repository.ElectionRepository;

class ClearViewModel extends ViewModel {

    private ElectionRepository repository;

    public ClearViewModel() {
        repository = ElectionRepository.getInstance();

        repository.getDateFromSharedPreference("lagna");
        repository.conntectWithDataBase();
    }

    public void clear(int governorate, String daira_num, String controller_pass) {

        repository.clearDB(governorate, Integer.parseInt(daira_num), controller_pass, null);
    }
}
