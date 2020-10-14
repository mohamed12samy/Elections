package com.example.elections.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.elections.model.Candidates;
import com.example.elections.repository.ElectionRepository;

import java.util.List;

public class SurveyViewModel extends ViewModel {

    private ElectionRepository repository;
    private MutableLiveData<List<Candidates>> candidates = new MutableLiveData<>();

    public SurveyViewModel(){
        repository = ElectionRepository.getInstance();
        repository.getDateFromSharedPreference("mandoop");
        repository.conntectWithDataBase();
    }
    public LiveData<List<Candidates>> getCandidates(){
        candidates = (MutableLiveData<List<Candidates>>) repository.getCandidates();

        return candidates;
    }

    public void updateVotes(String key1, String key2){
        repository.updateCandidateVotesSurvey(key1, key2);
    }

}
