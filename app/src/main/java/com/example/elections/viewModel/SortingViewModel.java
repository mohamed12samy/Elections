package com.example.elections.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.elections.model.Candidates;
import com.example.elections.repository.ElectionRepository;

import java.util.List;

public class SortingViewModel extends ViewModel {

    private ElectionRepository repository;
    private MutableLiveData<List<Candidates>> candidates = new MutableLiveData<>();

    public SortingViewModel(){
        repository = ElectionRepository.getInstance();
    }


    public LiveData<List<Candidates>> getCandidates(){
        candidates = (MutableLiveData<List<Candidates>>) repository.getCandidates();

        return candidates;
    }

    public void updateCandidateVotes(int id){
        repository.updateCandidateVotes((id+1));
    }
}
