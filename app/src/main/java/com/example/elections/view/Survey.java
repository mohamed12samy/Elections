package com.example.elections.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.elections.ClickListen;
import com.example.elections.R;
import com.example.elections.SortingAdapter;
import com.example.elections.SurveyAdapter;
import com.example.elections.model.Candidates;
import com.example.elections.viewModel.SortingViewModel;
import com.example.elections.viewModel.SurveyViewModel;

import java.util.ArrayList;
import java.util.List;

public class Survey extends AppCompatActivity implements ClickListen {


    List<Candidates> candidates = new ArrayList<>();
    private RecyclerView surveyRecycler;
    private SurveyAdapter mSurveyAdapter = new SurveyAdapter(this , candidates, this);

    private SurveyViewModel surveyViewModel = new SurveyViewModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        surveyRecycler = findViewById(R.id.survey_recycler);
        surveyRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true ));
        surveyRecycler.setAdapter(mSurveyAdapter);

        surveyViewModel = ViewModelProviders.of(this).get(SurveyViewModel.class);

        surveyViewModel.getCandidates().observe(this, new Observer<List<Candidates>>() {
            @Override
            public void onChanged(List<Candidates> ob) {
                candidates.clear();
                candidates.addAll(ob);
                mSurveyAdapter.notifyDataSetChanged();
                Log.d("OPOPOP", ob.toString());
            }
        });
    }

    @Override
    public void clicklisten(int id) {

    }
}
