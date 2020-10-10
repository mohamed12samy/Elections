package com.example.elections.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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


    String key1; String key2; int vote1; int vote2;
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

        surveyViewModel.getCandidates().observe(this, new Observer<Candidates>() {
            @Override
            public void onChanged(Candidates ob) {

                candidates.add(ob);
                mSurveyAdapter.notifyDataSetChanged();
                //Log.d("OPOPOP", ob.get(1).getName());
            }
        });

        findViewById(R.id.done_survey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key1 != null && key2 != null){
                    surveyViewModel.updateVotes(key1, key2);
                    recreate();
                }
            }
        });
    }

    @Override
    public void clicklisten(String key, int  votes) {

    }

    @Override
    public void clickListenSurvey(String key, int idx) {
        if(idx == 1){
            Log.d("CHECKBOXX", key+" --  "+idx);
            this.key1 = key;
//            this.vote1 = vote;
        }else if(idx == 2){

            Log.d("CHECKBOXX2", key+" --  "+idx);
            this.key2 = key;
  //          this.vote2 = vote;
        }

        Log.d("CHECKBOXX232", "*-*-*-*   "+ idx);
    }

    @Override
    public void handleKey(int idx) {

    }
}
