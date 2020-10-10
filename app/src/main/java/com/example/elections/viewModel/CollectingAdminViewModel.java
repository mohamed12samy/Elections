package com.example.elections.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.elections.model.Candidates;
import com.example.elections.model.Dayra;
import com.example.elections.model.DayraObjList;
import com.example.elections.repository.ElectionRepository;

import java.util.ArrayList;

public class CollectingAdminViewModel extends ViewModel {

    private ElectionRepository repository;
    private MutableLiveData<ArrayList<DayraObjList>> dayra = new MutableLiveData<>();

    public CollectingAdminViewModel(){
        repository = ElectionRepository.getInstance();
    }


    public LiveData<ArrayList<DayraObjList>> getDayra(int governorate_position, int daira_number){
        dayra = (MutableLiveData<ArrayList<DayraObjList>>) repository.getDayra(governorate_position,daira_number);

        return dayra;
    }

}
