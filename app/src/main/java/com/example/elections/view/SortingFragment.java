package com.example.elections.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.elections.ClickListen;
import com.example.elections.R;
import com.example.elections.adapter.SortingAdapter;
import com.example.elections.model.Candidates;
import com.example.elections.viewModel.SortingViewModel;

import java.util.ArrayList;
import java.util.List;

public class SortingFragment extends Fragment implements ClickListen {


    List<Candidates> candidates = new ArrayList<>();
    private RecyclerView sortingRecycler;
    private SortingAdapter mSortingAdapter = new SortingAdapter(getActivity(), candidates, this);

    private SortingViewModel sortingViewModel = new SortingViewModel();
    EditText valid;
    EditText invalid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sorting, container, false);

        sortingRecycler = view.findViewById(R.id.sorting_recycler);
        sortingRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true ));
        sortingRecycler.setAdapter(mSortingAdapter);


        valid = view.findViewById(R.id.edittext_valid_vote);
        invalid = view.findViewById(R.id.edittext_invalid_vote);


        sortingViewModel = ViewModelProviders.of(this).get(SortingViewModel.class);

        sortingViewModel.getCandidates().observe(this, new Observer<Candidates>() {
            @Override
            public void onChanged(Candidates ob) {

                candidates.add(ob);
                mSortingAdapter.notifyDataSetChanged();
                //Log.d("OPOPOP", ob.get(1).getName());
            }
        });



        view.findViewById(R.id.done_votes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vValid = valid.getText().toString();
                String vInvalid = invalid.getText().toString();;

                if(vValid.length() >0 && vInvalid.length()>0){
                    int valid = Integer.parseInt(vValid);
                    int invalid = Integer.parseInt(vInvalid);

                    sortingViewModel.updateValidInvalidVotes(valid, invalid);
                }

            }
        });
        return view;
    }

    @Override
    public void clicklisten(String key, int votes) {
        Log.d("HHHHHHOOOOOO", " fuck it "+key);
        sortingViewModel.updateCandidateVotes(key, votes);
    }

    @Override
    public void clickListenSurvey(String key1, int idx) {

    }

    @Override
    public void handleKey(int idx) {

    }


}
