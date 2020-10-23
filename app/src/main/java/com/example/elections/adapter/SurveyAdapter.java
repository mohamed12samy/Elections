package com.example.elections.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elections.ClickListen;
import com.example.elections.R;
import com.example.elections.model.Candidates;

import java.util.ArrayList;
import java.util.List;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder> {

    int idx;
    List<String> checked = new ArrayList<>();

    private List<Candidates> candidates;
    //private Context context;
    private ClickListen listener;

    public SurveyAdapter(Context context, List<Candidates> candidates , ClickListen listener) {

        idx = 0;
        this.listener = listener;
        this.candidates = candidates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_item, parent, false);
        ViewHolder mViewHolder = new ViewHolder(view);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.can_name.setText(candidates.get(position).getName());
        holder.can_Num.setText(candidates.get(position).getKey());


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView can_Num;
        TextView can_name;
        CheckBox check;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            can_Num = itemView.findViewById(R.id.candidate_num);
            can_name = itemView.findViewById(R.id.candidate_name);
            check = itemView.findViewById(R.id.check);


            check.setOnCheckedChangeListener(null);

            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){

                        if(!checked.contains(getAdapterPosition())){
                            if(checked.size()<2) {
                                listener.clickListenSurvey(candidates.get(getAdapterPosition()).getKey(),++idx);
                                checked.add(getAdapterPosition() + "");
                            }else{
                                check.setChecked(false);
                            }
                        }
                        }else {
                        listener.handleKey(0);
                        idx--;
                        checked.remove(getAdapterPosition()+"");

                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
