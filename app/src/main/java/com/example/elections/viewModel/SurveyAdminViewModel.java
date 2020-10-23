package com.example.elections.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.elections.model.Candidates;
import com.example.elections.repository.ElectionRepository;

import java.util.List;

public class SurveyAdminViewModel extends ViewModel {

    private ElectionRepository repository;
    private MutableLiveData<List<Candidates>> candidates = new MutableLiveData<>();
    private MutableLiveData<List<Candidates>> candidates2 = new MutableLiveData<>();

    public SurveyAdminViewModel(){
        repository = ElectionRepository.getInstance();
    }


    public LiveData<List<Candidates>> getCandidates(){
        candidates = (MutableLiveData<List<Candidates>>) repository.getCandidates();
        return candidates;
    }
    public LiveData<List<Candidates>> getCandidatesSurvey(int governorate_position, int daira){
        candidates2 = (MutableLiveData<List<Candidates>>) repository.getCandidatesSurvey(governorate_position,daira);
        return candidates2;
    }

    public LiveData<String> getSurveyVotes() {
        return repository.getSurveyVotes();
    }

}
