package com.example.elections.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.elections.R;
import com.example.elections.adapter.CollectingAdapter;
import com.example.elections.adapter.SortingAdapter;
import com.example.elections.model.Dayra;
import com.example.elections.model.DayraObjList;
import com.example.elections.viewModel.CollectingAdminViewModel;

import java.util.ArrayList;

public class CollectingAdmin extends AppCompatActivity {

    TextView textView;
    ArrayList<DayraObjList> dayraObj = new ArrayList<>();
    RecyclerView recyclerView;
    private CollectingAdapter mSortingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collecting_admin);

        textView = findViewById(R.id.daira_name);
        recyclerView = findViewById(R.id.collect_admin_recycler);


        //------------------------------------
        CollectingAdminViewModel viewModel = new CollectingAdminViewModel();
        viewModel = ViewModelProviders.of(this).get(CollectingAdminViewModel.class);

        //This values have to get it from the input --->
        viewModel.getDayra(18, 1).observe(this, new Observer<ArrayList<DayraObjList>>() {
            @Override
            public void onChanged(ArrayList<DayraObjList> ob) {

                dayraObj = ob;

                textView.setText(dayraObj.get(0).getDayraName());
                recyclerView.setLayoutManager(new LinearLayoutManager(CollectingAdmin.this, RecyclerView.VERTICAL, true));
                mSortingAdapter = new CollectingAdapter(getApplicationContext(), dayraObj);
                recyclerView.setAdapter(mSortingAdapter);
                mSortingAdapter.notifyDataSetChanged();
                    //      Log.d("OPOPOP", ob.getName());
            }
        });


    }
}
