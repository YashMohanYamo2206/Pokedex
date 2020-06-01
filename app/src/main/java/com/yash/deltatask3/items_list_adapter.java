package com.yash.deltatask3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class items_list_adapter extends RecyclerView.Adapter<items_list_adapter.item_list_viewHolder> {

    List<Region> regions;
    int mCount;
    public items_list_adapter(List<Region> regions,int count_items) {
        this.regions = regions;
        mCount=count_items;
    }

    public class item_list_viewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        private item_list_viewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.tv_item_name);
        }
    }

    @NonNull
    @Override
    public item_list_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_items_list,parent,false);
        return new item_list_viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull item_list_viewHolder holder, int position) {
        Region current_item = regions.get(position);
        holder.mTextView.setText(current_item.getName());
    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
