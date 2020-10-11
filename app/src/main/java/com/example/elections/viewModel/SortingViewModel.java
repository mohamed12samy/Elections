package com.example.elections.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.elections.model.Candidates;
import com.example.elections.repository.ElectionRepository;

import java.util.List;

public class SortingViewModel extends ViewModel {

    private ElectionRepository repository;
    private MutableLiveData<Candidates> candidates = new MutableLiveData<>();
    private MutableLiveData<List<Candidates>> candidates2 = new MutableLiveData<>();

    public SortingViewModel(){
        repository = ElectionRepository.getInstance();
    }


    public LiveData<Candidates> getCandidates(){
        candidates = (MutableLiveData<Candidates>) repository.getCandidates();
        return candidates;
    }
    public LiveData<List<Candidates>> getCandidates2(int governorate_position, int daira){
        candidates2 = (MutableLiveData<List<Candidates>>) repository.getCandidates2(governorate_position,daira);
        return candidates2;
    }

    public void updateCandidateVotes(String key, int votes){
        repository.updateCandidateVotes(key, votes);
    }

    public void updateValidInvalidVotes(int valid, int invalid){
        repository.updateValidInvalidVotes(valid, invalid);
    }
}
