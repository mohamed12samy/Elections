package com.example.elections.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.elections.repository.ElectionRepository;

public class dayraViewModel extends ViewModel {
    private ElectionRepository repository;

    public dayraViewModel(){
        repository = ElectionRepository.getInstance();
        repository.getDateFromSharedPreference("lagna");
        repository.conntectWithDataBase();
    }
    public void addNewDayra(String dayra_name,int governorate_position , int dayra_number ,int seats_number,int Qesm_counts,String survey_pass,
                            String mandoop_pass, String cotroller_pass)
    {
        repository.add_dayra(governorate_position,dayra_name,dayra_number,seats_number,Qesm_counts, survey_pass, mandoop_pass, cotroller_pass);
    }
}
