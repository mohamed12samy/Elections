package com.example.elections.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elections.ClickListen;
import com.example.elections.Ichange;
import com.example.elections.R;
import com.example.elections.model.Candidates;

import java.util.List;

public class SortingAdminAdapter extends RecyclerView.Adapter<SortingAdminAdapter.ViewHolder> implements Ichange {
    private List<Candidates> candidates;
    private int total_votes = 1;

    public SortingAdminAdapter(Context applicationContext, List<Candidates> candidates, int votes) {
        this.candidates = candidates;
        this.total_votes = votes;
    }
    @NonNull
    @Override
    public SortingAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sorting_admin_item, parent, false);
        SortingAdminAdapter.ViewHolder mViewHolder = new SortingAdminAdapter.ViewHolder(view);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SortingAdminAdapter.ViewHolder holder, int position) {

        if(candidates.get(position) != null) {

            holder.can_name.setText(candidates.get(position).getName());
            holder.votesNum.setText(candidates.get(position).getVotes()+"");
            holder.can_Num.setText(candidates.get(position).getKey()); /********* iiiiiiddddddd *********/
            holder.percentage.setText( "%"+ candidates.get(position).getPercentage() );
        }
    }

    @Override
    public void totalVotes(int votes) {
        this.total_votes = votes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView can_Num;
        TextView votesNum;
        TextView can_name;
        TextView percentage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            can_Num = itemView.findViewById(R.id.candidate_num);
            can_name = itemView.findViewById(R.id.candidate_name);
            votesNum = itemView.findViewById(R.id.votesNum);
            percentage = itemView.findViewById(R.id.percent);
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


