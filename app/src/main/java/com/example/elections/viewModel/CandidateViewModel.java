package com.example.elections.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.elections.repository.ElectionRepository;

public class CandidateViewModel extends ViewModel {
    private ElectionRepository repository;

    public CandidateViewModel() {
        repository = ElectionRepository.getInstance();
        repository.getDateFromSharedPreference("lagna");
        repository.conntectWithDataBase();
    }

    public void addNewCandidate(String candidate_name, int governorate_position, int dayra_number) {
        repository.add_Candidate(governorate_position, candidate_name, dayra_number);
    }
}
