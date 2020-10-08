package com.example.elections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SortingAdapter extends RecyclerView.Adapter<SortingAdapter.ViewHolder> {

    //private List<Address> myAddresses;
    //private Context context;
    private ClickListen listener;
    public SortingAdapter(Context context, List<String> myAddresses , ClickListen listener) {

        this.listener = listener;
        // this.mClickListener = clickListener;
    }

    @NonNull
    @Override
    public SortingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sorting_items, parent, false);
        ViewHolder mViewHolder = new ViewHolder(view);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SortingAdapter.ViewHolder holder, int position) {

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView can_Num;
        TextView can_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            can_Num = itemView.findViewById(R.id.candidate_num);
            can_name = itemView.findViewById(R.id.candidate_name);

            itemView.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clicklisten();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
