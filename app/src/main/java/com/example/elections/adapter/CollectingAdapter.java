package com.example.elections.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CollectingAdapter  extends RecyclerView.Adapter<CollectingAdapter.ViewHolder> {

    @NonNull
    @Override
    public CollectingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CollectingAdapter.ViewHolder holder, int position) {

    }
    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
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
