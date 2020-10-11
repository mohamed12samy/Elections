package com.example.elections.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.example.elections.R;
import com.example.elections.Votes;
import com.example.elections.adapter.SortingAdminAdapter;
import com.example.elections.model.Candidates;
import com.example.elections.viewModel.SortingAdminViewModel;
import com.example.elections.viewModel.SortingViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class SortingAdmin extends AppCompatActivity implements Votes {

    TextView valid_votes;
    TextView invalid_votes;
    RecyclerView sorting_admin_recycler;

    private SortingViewModel sortingViewModel = new SortingViewModel();
    SortingAdminViewModel viewModel = new SortingAdminViewModel(this);


    List<Candidates> candidates = new ArrayList<>();
    private SortingAdminAdapter mSortingAdminAdapter;
    private SharedPreferences sP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting_admin);

        invalid_votes = findViewById(R.id.invalid_votes);
        valid_votes = findViewById(R.id.valid_votes);
        sorting_admin_recycler = findViewById(R.id.sorting_admin_recycler);

        sP = getSharedPreferences("lagna", Context.MODE_PRIVATE);
        int governorate_position = sP.getInt("governorate_position", 1);
        int daira = sP.getInt("daira_num", 1);

        sortingViewModel = ViewModelProviders.of(this).get(SortingViewModel.class);

        sortingViewModel.getCandidates2(governorate_position,daira).observe(this, new Observer<List<Candidates>>() {
            @Override
            public void onChanged(List<Candidates> ob) {
                candidates = ob;

                mSortingAdminAdapter = new SortingAdminAdapter(getApplicationContext(), candidates);
                sorting_admin_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                sorting_admin_recycler.setAdapter(mSortingAdminAdapter);

                mSortingAdminAdapter.notifyDataSetChanged();
            }
        });

/*
        viewModel = ViewModelProviders.of(this).get(SortingAdminViewModel.class);
        viewModel.getCandidates().observe(this, new Observer<Candidates>() {
            @Override
            public void onChanged(Candidates ob) {

                candidates.add(ob);
                mSortingAdminAdapter.notifyDataSetChanged();
            }
        });
*/
        viewModel.getValidVotes(governorate_position, daira);
        viewModel.getInvalidVotes(governorate_position, daira);


        findViewById(R.id.addBarChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SortingAdmin.this, BarChartActivity.class);
           /*     Bundle bundle = new Bundle();
                bundle.putParcelable("data", (Parcelable) candidates);
                intent.putExtras(bundle);
             */
                startActivity(intent);
            }
        });
        findViewById(R.id.addBieChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SortingAdmin.this, PieChartActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void ValidVotes(String validVotes) {
        valid_votes.setText(validVotes);
    }

    @Override
    public void InValidVotes(String inValidVotes) {
        invalid_votes.setText(inValidVotes);
    }
}

// try to make dynamic chart.
/*
ArrayList<PieEntry> visitors = new ArrayList<>();
for(int i=0; i< candidates.size();i++)
                {
                    visitors.add(new PieEntry(candidates.get(i).getVotes(),candidates.get(i).getName()));
                }
                if(visitors.size()>0) {
                    PieDataSet pieDataSet = new PieDataSet(visitors,"المرشحين");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);


                    PieData pieData = new PieData(pieDataSet);
                    pieData.addEntry(new Entry(1,14),1);

                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("المرشحين ل سنه 2020");
                    pieChart.animate();
                }
                pieChart.notifyDataSetChanged();

 */