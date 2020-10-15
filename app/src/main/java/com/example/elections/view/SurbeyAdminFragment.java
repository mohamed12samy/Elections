package com.example.elections.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elections.R;
import com.example.elections.VoteSorting;
import com.example.elections.adapter.SortingAdminAdapter;
import com.example.elections.adapter.SurvayAdminAdapter;
import com.example.elections.model.Candidates;
import com.example.elections.viewModel.SortingViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class SurbeyAdminFragment extends Fragment implements VoteSorting {
    RecyclerView survey_admin_recycler;
    BarChart barChart;

    BarData barData = new BarData();

    ArrayList<BarEntry> dataEnteries = new ArrayList<>();
    ArrayList<String> labels;
    BarDataSet barDataSet = new BarDataSet(dataEnteries,null);

    private SortingViewModel sortingViewModel = new SortingViewModel();


    List<Candidates> candidates = new ArrayList<>();
    private SurvayAdminAdapter mSortingAdminAdapter;
    private SharedPreferences sP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_surbey_admin, container, false);


        survey_admin_recycler = view.findViewById(R.id.survey_admin_recycler);

        barChart = view.findViewById(R.id.barChart);

        sP = getActivity().getSharedPreferences("lagna", Context.MODE_PRIVATE);
        int governorate_position = sP.getInt("governorate_position", 0);
        int daira = sP.getInt("daira_num", 0);

        sortingViewModel = ViewModelProviders.of(this).get(SortingViewModel.class);

        sortingViewModel.getCandidates2(governorate_position,daira).observe(this, new Observer<List<Candidates>>() {
            @Override
            public void onChanged(List<Candidates> ob) {
                candidates = ob;
                if(candidates.size() >0 && candidates != null){
                    mSortingAdminAdapter = new SurvayAdminAdapter(getActivity(), candidates);
                    survey_admin_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                    survey_admin_recycler.setAdapter(mSortingAdminAdapter);

                    mSortingAdminAdapter.notifyDataSetChanged();

                    setChart(barChart);
                }
            }
        });
        return view;
    }
    private void setChart(BarChart barChart){

    dataEnteries.clear();
    labels = new ArrayList<>();
    int x = candidates.size()<10?candidates.size():10;

    for(int i=0; i<x; i++) {
        dataEnteries.add(new BarEntry(i, candidates.get(i).getVotes_survay(), candidates.get(i).getName()));
        labels.add(candidates.get(i).getName());
    }

    barDataSet = new BarDataSet(dataEnteries,"المرشحين");
    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
    barDataSet.setValueTextColor(Color.BLACK);
    barDataSet.setValueTextSize(16f);

    //barData = new BarData(barDataSet);
    barData.removeDataSet(0);
    barData.addDataSet(barDataSet);

    barChart.setFitBars(true);
    barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
    barChart.getDescription().setEnabled(false);
    barChart.animateY(500);


    XAxis xAxis = barChart.getXAxis();


    barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
    barChart.getXAxis().setGranularityEnabled(true);
    barChart.getXAxis().setGranularity(1);
    barChart.getXAxis().setLabelCount(barDataSet.getEntryCount());
    barChart.setData(barData);

}

    @Override
    public void ValidVotes(String validVotes) {

    }

    @Override
    public void InValidVotes(String inValidVotes) {

    }

    @Override
    public void totalvotes(String votes) {

    }

    @Override
    public void setSeats(int seats) {

    }
}
