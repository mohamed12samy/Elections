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
import android.widget.TextView;

import com.example.elections.R;
import com.example.elections.VoteSorting;
import com.example.elections.adapter.SortingAdminAdapter;
import com.example.elections.model.Candidates;
import com.example.elections.viewModel.SortingAdminViewModel;
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


public class SortingAdminFragment extends Fragment implements VoteSorting {

    TextView valid_votes;
    TextView invalid_votes;
    RecyclerView sorting_admin_recycler;
    BarChart barChart;

    BarData barData = new BarData();

    ArrayList<BarEntry> dataEnteries = new ArrayList<>();
    ArrayList<String> labels;
    BarDataSet barDataSet = new BarDataSet(dataEnteries,null);

    private SortingViewModel sortingViewModel = new SortingViewModel();
    SortingAdminViewModel viewModel = new SortingAdminViewModel(this);


    List<Candidates> candidates = new ArrayList<>();
    private SortingAdminAdapter mSortingAdminAdapter;
    private SharedPreferences sP;

    int seats=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sorting_admin, container, false);

        invalid_votes = view.findViewById(R.id.invalid_votes);
        valid_votes = view.findViewById(R.id.valid_votes);
        sorting_admin_recycler = view.findViewById(R.id.sorting_admin_recycler);

        barChart = view.findViewById(R.id.barChart);

        sP = getActivity().getSharedPreferences("lagna", Context.MODE_PRIVATE);
        int governorate_position = sP.getInt("governorate_position", 0);
        int daira = sP.getInt("daira_num", 0);


        viewModel.getValidVotes(governorate_position, daira);
        viewModel.getInvalidVotes(governorate_position, daira);
        viewModel.getSeats(governorate_position, daira);

        sortingViewModel = ViewModelProviders.of(this).get(SortingViewModel.class);

        sortingViewModel.getCandidates2(governorate_position,daira).observe(this, new Observer<List<Candidates>>() {
            @Override
            public void onChanged(List<Candidates> ob) {
                candidates = ob;

                mSortingAdminAdapter = new SortingAdminAdapter(getActivity(), candidates);
                sorting_admin_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                sorting_admin_recycler.setAdapter(mSortingAdminAdapter);

                mSortingAdminAdapter.notifyDataSetChanged();

                setChart(barChart);
            }
        });

        view.findViewById(R.id.clear_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> c = new ArrayList<>();

                for(int i=0; i<(seats*2); i++){
                    c.add(candidates.get(i).getKey());
                }
                viewModel.clearDB(governorate_position, daira, c);
            }
        });
        return view;
    }

    private void setChart(BarChart barChart){

        dataEnteries.clear();
        labels = new ArrayList<>();
        for(int i=0; i<(seats*2); i++) {
            dataEnteries.add(new BarEntry(i, candidates.get(i).getVotes(), candidates.get(i).getName()));
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
        valid_votes.setText(validVotes);
    }

    @Override
    public void InValidVotes(String inValidVotes) {
        invalid_votes.setText(inValidVotes);
    }

    @Override
    public void setSeats(int seats) {
        this.seats = seats;
    }
}

