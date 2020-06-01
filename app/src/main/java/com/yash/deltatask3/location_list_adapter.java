package com.yash.deltatask3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class location_list_adapter extends RecyclerView.Adapter<location_list_adapter.location_list_viewHolder> {
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private List<Location> locations;

    public location_list_adapter(List<Location> locations) {
        this.locations = locations;
    }

    public class location_list_viewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public location_list_viewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_location_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public location_list_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_location_list_item, parent, false);
        return new location_list_viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull location_list_viewHolder holder, int position) {
        Location location = locations.get(position);
        holder.mTextView.setText(location.getRegion().getName());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
