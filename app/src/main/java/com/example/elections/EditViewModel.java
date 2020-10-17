package com.example.elections;

import androidx.lifecycle.ViewModel;

import com.example.elections.repository.ElectionRepository;

class EditViewModel extends ViewModel {
    private ElectionRepository repository;

    public EditViewModel(){
        repository = ElectionRepository.getInstance();
        repository.getDateFromSharedPreference("lagna");
        repository.conntectWithDataBase();
    }

    public void editPasswords(int governorate_position, int daira_num, String survey_pass, String mandoop_pass, String controller_pass) {
        repository.editPasswords(governorate_position, daira_num, survey_pass, mandoop_pass, controller_pass);
    }
}
