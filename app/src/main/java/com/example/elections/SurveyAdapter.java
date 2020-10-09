package com.example.elections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elections.model.Candidates;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Map;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder> {

    private List<Candidates> candidates;
    //private Context context;
    private ClickListen listener;

    public SurveyAdapter(Context context, List<Candidates> candidates , ClickListen listener) {

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
        holder.can_Num.setText(position); /********* iiiiiiddddddd *********/
        holder.done.setText(candidates.get(position).getVotes());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView can_Num;
        TextView can_name;
        TextInputEditText done;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            can_Num = itemView.findViewById(R.id.candidate_num);
            can_name = itemView.findViewById(R.id.candidate_name);
            done = itemView.findViewById(R.id.done);

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, Object> postValues = candidates.get(getAdapterPosition()).toMap();
                    //listener.clicklisten(getAdapterPosition(), );
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
