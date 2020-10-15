package com.example.elections.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.elections.Votes;
import com.example.elections.model.Candidates;
import com.example.elections.repository.ElectionRepository;
import com.example.elections.VoteSorting;

import java.util.ArrayList;

public class SortingAdminViewModel extends ViewModel implements Votes {

    private VoteSorting view;
    private ElectionRepository repository;
    private MutableLiveData<Candidates> candidates = new MutableLiveData<>();

    public SortingAdminViewModel(VoteSorting view) {
        repository = ElectionRepository.getInstance();
        this.view = view;
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

    @Override
    public void TotalVotes(String votes) {
        view.totalvotes(votes);
    }

    @Override
    public void getSeats(int governorate_position, int daira) {
        repository.getSeats(governorate_position,daira,this);
    }

    @Override
    public void setSeats(int s) {
        view.setSeats(s);
    }

    public void clearDB(int governorate_position, int daira, ArrayList<String> c) {
        repository.clearDB(governorate_position, daira, c);
    }

    public void getTotalVotes(int governorate_position, int daira) {
        repository.getTotalVotesSort(governorate_position, daira, this);
    }
}
