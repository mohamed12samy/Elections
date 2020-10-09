package com.example.elections.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.elections.repository.ElectionRepository;

public class dayraViewModel extends ViewModel {
    private ElectionRepository repository;

    public dayraViewModel(){
        repository = ElectionRepository.getInstance();
    }
    public void addNewDayra(String dayra_name,int governorate_position , int dayra_number ,int seats_number,int Qesm_counts)
    {
        repository.add_dayra(governorate_position,dayra_name,dayra_number,seats_number,Qesm_counts);
    }
}
