package com.example.elections.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elections.ClickListen;
import com.example.elections.R;
import com.example.elections.SortingAdapter;

import java.util.ArrayList;
import java.util.List;

public class SortingFragment extends Fragment implements ClickListen {


    List<String> l = new ArrayList<String>();
    private RecyclerView sortingRecycler;
    private SortingAdapter mSortingAdapter = new SortingAdapter(getActivity(), l, this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sorting, container, false);

        sortingRecycler = view.findViewById(R.id.sorting_recycler);
        sortingRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        sortingRecycler.setAdapter(mSortingAdapter);

        return view;
    }

    @Override
    public void clicklisten() {
        Log.d("HHHHHHOOOOOO", " fuck it ");
    }
}
