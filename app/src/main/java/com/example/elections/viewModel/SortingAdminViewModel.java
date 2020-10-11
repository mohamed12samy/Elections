package com.example.elections.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.elections.Votes;
import com.example.elections.model.Candidates;
import com.example.elections.model.DayraObjList;
import com.example.elections.repository.ElectionRepository;
import com.example.elections.view.SortingAdmin;

import java.util.ArrayList;

public class SortingAdminViewModel extends ViewModel implements Votes {

    private Votes view;
    private ElectionRepository repository;
    private MutableLiveData<Candidates> candidates = new MutableLiveData<>();

    public SortingAdminViewModel(Votes view) {
        repository = ElectionRepository.getInstance();
        this.view = view;
    }

    public LiveData<Candidates> getCandidates(){
        candidates = (MutableLiveData<Candidates>) repository.getCandidates();
        return candidates;
    }

    public void getValidVotes(int governorate_position, int daira) {
        repository.getValidVotes(governorate_position,daira,this);
    }

    public void getInvalidVotes(int governorate_position, int daira) {
        repository.getInValidVotes(governorate_position,daira,this);
    }


    @Override
    public void ValidVotes(String validVotes) {
        view.ValidVotes(validVotes);
    }

    @Override
    public void InValidVotes(String inValidVotes) {
        view.InValidVotes(inValidVotes);
    }
}
