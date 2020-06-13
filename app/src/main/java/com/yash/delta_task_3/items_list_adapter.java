package com.yash.delta_task_3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class items_list_adapter extends RecyclerView.Adapter<items_list_adapter.item_list_viewHolder> {

    List<Region> regions;
    int mCount;
    View view;
    Context context;
    public items_list_adapter(List<Region> regions,int count_items) {
        this.regions = regions;
        mCount=count_items;
    }

    public class item_list_viewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView imageView;
        private item_list_viewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.tv_item_name);
            imageView=itemView.findViewById(R.id.iv_item);
        }
    }

    @NonNull
    @Override
    public item_list_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_items_list,parent,false);
        return new item_list_viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull item_list_viewHolder holder, int position) {
        Region current_item = regions.get(position);
        holder.mTextView.setText(current_item.getName());
        Picasso.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/"+current_item.getName()+".png").fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
