package com.example.elections.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elections.R;


public class CollectingFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    TextView counter_text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_collecting, container, false);

        sharedPreferences = getActivity().getSharedPreferences("election", Context.MODE_PRIVATE);

        counter_text = view.findViewById(R.id.counter_text);

        if(sharedPreferences.getString("counter",null) != null){
            counter_text.setText(sharedPreferences.getString("counter",null));
        }


        view.findViewById(R.id.counter_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter_text.setText((Integer.parseInt(counter_text.getText().toString()) + 1)+"");

                // call database
            }
        });

        view.findViewById(R.id.navigate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_collectingFragment_to_sortingFragment);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("counter", counter_text.getText().toString()).apply();
        editor.commit();

    }
}
