package com.example.elections.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elections.ClickListen;
import com.example.elections.R;
import com.example.elections.model.Candidates;

import java.util.List;


public class SortingAdapter extends RecyclerView.Adapter<SortingAdapter.ViewHolder> {

    private List<Candidates> candidates;
    //private Context context;
    private ClickListen listener;
    public SortingAdapter(Context context, List<Candidates> candidates , ClickListen listener) {

        this.listener = listener;
        this.candidates = candidates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sorting_items, parent, false);
        ViewHolder mViewHolder = new ViewHolder(view);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(candidates.get(position) != null) {
            //Log.d("RTRTRT",candidates.get(position).getName()+"");

            holder.can_name.setText(candidates.get(position).getName());
            holder.can_Num.setText(candidates.get(position).getKey()); /********* iiiiiiddddddd *********/
            //holder.vote_edit.setText(candidates.get(position).getVotes()+"");
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView can_Num;
        TextView can_name;
        EditText vote_edit;
        Button done;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            can_Num = itemView.findViewById(R.id.candidate_num);
            can_name = itemView.findViewById(R.id.candidate_name);
            vote_edit = itemView.findViewById(R.id.edittext_vote);
            done = itemView.findViewById(R.id.done);

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clicklisten(candidates.get(getAdapterPosition()).getKey(),
                            Integer.parseInt(vote_edit.getText().toString())
                            );
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

