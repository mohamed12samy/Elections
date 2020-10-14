package com.example.elections.viewModel;

import com.example.elections.repository.ElectionRepository;

public class QesmViewModel {
    private ElectionRepository repository;

    public QesmViewModel(){
        repository = ElectionRepository.getInstance();
        repository.getDateFromSharedPreference("lagna");
        repository.conntectWithDataBase();
    }
    public void addNewQesm(String qesm_name,int governorate_position , int dayra_number ,int school_num,int qesm_num)
    {
        repository.addNewQesm(qesm_name,governorate_position,dayra_number,school_num,qesm_num);
    }
}
