package com.example.elections.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elections.ClickListen;
import com.example.elections.R;
import com.example.elections.model.Candidates;
import com.example.elections.model.Dayra;
import com.example.elections.model.DayraObjList;

import java.util.ArrayList;
import java.util.List;

public class CollectingAdapter  extends RecyclerView.Adapter<CollectingAdapter.ViewHolder> {



    private ArrayList<DayraObjList> dayra ;
    //private Context context;

    public CollectingAdapter(Context context, ArrayList<DayraObjList> dayra ) {
        this.dayra = dayra;
    }

    @NonNull
    @Override
    public CollectingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dayra_item, parent, false);
        CollectingAdapter.ViewHolder mViewHolder = new CollectingAdapter.ViewHolder(view);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CollectingAdapter.ViewHolder holder, int position) {

        if(dayra.size()>0) {
            holder.qesm.setText(dayra.get(position).getQesmName());
            holder.school.setText(dayra.get(position).getSchoolName());
            holder.lagna.setText(dayra.get(position).getLagnaNum()+"");
            holder.votes.setText(dayra.get(position).getVoteNum()+"");
            Log.d("TAG", "!@@@nBindViewHolder: "+dayra.get(0).getDayraName());
        }else
        {
            Log.d("TAG", "!@@@nBindViewHolder: 00000");
            holder.qesm.setText("data1");
            holder.school.setText("data");
            holder.lagna.setText(1 + "");
            holder.votes.setText(1 + "");
        }

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView qesm;
        TextView school;
        TextView lagna;
        TextView votes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            qesm = itemView.findViewById(R.id.qesm_name);
            school = itemView.findViewById(R.id.school_name);
            lagna = itemView.findViewById(R.id.lagna_name);
            votes = itemView.findViewById(R.id.votes);
        }
    }

    @Override
    public int getItemCount() {
        return dayra.size();
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
