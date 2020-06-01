package com.yash.deltatask3;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;



public class pokemon_list_adapter extends RecyclerView.Adapter<pokemon_list_adapter.pokemon_list_ViewHolder> {

    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private Context mContext;
    private List<Pokemon> pokemon;

    public pokemon_list_adapter(Context mContext, List<Pokemon> pokemon) {
        this.mContext = mContext;
        this.pokemon = pokemon;
    }

    public class pokemon_list_ViewHolder extends RecyclerView.ViewHolder {
        View mItemView;
        TextView tv_pokemon_name;
        ImageView iv_pokemon;
        public pokemon_list_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_pokemon_name=itemView.findViewById(R.id.tv_pokemon_name);
            iv_pokemon=itemView.findViewById(R.id.iv_pokemon);
            mItemView=itemView.findViewById(R.id.pokemon_Linear_layout);

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
    public pokemon_list_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pokemon_list_item, parent, false);
        return new pokemon_list_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull pokemon_list_ViewHolder holder, int position) {
        Pokemon current_pokemon_name = pokemon.get(position);
        holder.tv_pokemon_name.setText(current_pokemon_name.getShortName().toUpperCase());
        Picasso.with(mContext).load(current_pokemon_name.getSprites().getFrontShine()).fit().centerInside().into(holder.iv_pokemon);
    }

    @Override
    public int getItemCount() {
        return pokemon.size();
    }
}
