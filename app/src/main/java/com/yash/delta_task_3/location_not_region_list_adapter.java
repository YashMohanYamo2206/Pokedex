package com.yash.delta_task_3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class location_not_region_list_adapter extends RecyclerView.Adapter<location_not_region_list_adapter.location_not_region_viewHolder> {
    private List<Region> regions;
    int mCount;
    public location_not_region_list_adapter(List<Region> regions,int count) {
        this.mCount=count;
        this.regions = regions;
    }

    class location_not_region_viewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        public location_not_region_viewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_location_name);
        }
    }
    @NonNull
    @Override
    public location_not_region_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_location_list_item,parent,false);
        return new location_not_region_viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull location_not_region_viewHolder holder, int position) {
        Region current_region = regions.get(position);
        holder.mTextView.setTextSize(20);
        holder.mTextView.setText(current_region.getName().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
