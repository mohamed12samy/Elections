package com.example.elections.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elections.R;
import com.example.elections.adapter.CollectingAdapter;
import com.example.elections.model.DayraObjList;
import com.example.elections.viewModel.CollectingAdminViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;


public class CollectingAdminFragment extends Fragment {

    TextView textView;
    ArrayList<DayraObjList> dayraObj = new ArrayList<>();
    RecyclerView recyclerView;
    private CollectingAdapter mSortingAdapter;
    private SharedPreferences sP;

    BottomNavigationView bottomNavigationView;
    NavHostFragment navHostFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collecting_admin, container, false);


        textView = view.findViewById(R.id.daira_name);
        recyclerView = view.findViewById(R.id.collect_admin_recycler);
        sP = getActivity().getSharedPreferences("lagna", Context.MODE_PRIVATE);
        int governorate_position = sP.getInt("governorate_position", 0);
        int daira = sP.getInt("daira_num", 0);

        //------------------------------------
        CollectingAdminViewModel viewModel = new CollectingAdminViewModel();
        viewModel = ViewModelProviders.of(this).get(CollectingAdminViewModel.class);

        //This values have to get it from the input --->

        Log.d("8989898",governorate_position+"  "+daira);
        viewModel.getDayra(governorate_position, daira).observe(this, new Observer<ArrayList<DayraObjList>>() {

            @Override
            public void onChanged(ArrayList<DayraObjList> ob) {

                if(ob.size()>0 && ob != null){
                    dayraObj.clear();
                    dayraObj.addAll(ob);
                    Collections.reverse(dayraObj);
                    textView.setText(dayraObj.get(0).getDayraName());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true));
                    mSortingAdapter = new CollectingAdapter(getActivity(), dayraObj);
                    recyclerView.setAdapter(mSortingAdapter);
                    mSortingAdapter.notifyDataSetChanged();
                    //      Log.d("OPOPOP", ob.getName());
                }
            }
        });
        TextView totalVotes = view.findViewById(R.id.votes_num);
        viewModel.getTotalVotes().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                totalVotes.setText(integer+"");
            }
        });


       /* view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SortingAdmin.class);
                startActivity(intent);
            }
        });*/
        return view;
    }

}
