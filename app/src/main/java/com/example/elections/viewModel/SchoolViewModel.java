package com.example.elections.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.elections.repository.ElectionRepository;

public class SchoolViewModel extends ViewModel {
    private ElectionRepository repository;

    public SchoolViewModel(){
        repository = ElectionRepository.getInstance();
    }
    public void addNewSchool(String school_name,int governorate_position , int dayra_number ,int school_num,int qesm_num,int lagan_num)
    {
        repository.addNewSchool(school_name,governorate_position,dayra_number,school_num,qesm_num,lagan_num);
    }
}