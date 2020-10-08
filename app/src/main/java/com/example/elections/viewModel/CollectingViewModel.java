package com.example.elections.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.elections.repository.ElectionRepository;

public class CollectingViewModel extends ViewModel {

    private ElectionRepository repository;

    public CollectingViewModel(){
        repository = ElectionRepository.getInstance();
    }

    public void updateCounter(int votes){
        repository.updateCounter(votes);
    }
}
