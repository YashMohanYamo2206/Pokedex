package com.yash.delta_task_3;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class pokemon_types_adapter extends RecyclerView.Adapter<pokemon_types_adapter.pokemon_types_ViewHolder> {
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    private Context mContext;
    private List<String> pokemon;

    public pokemon_types_adapter(Context mContext, List<String> pokemon) {
        this.mContext = mContext;
        this.pokemon = pokemon;
    }

    public class pokemon_types_ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_pokemon_name;
        ImageView iv_pokemon_type;

        public pokemon_types_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_pokemon_name = itemView.findViewById(R.id.tv_pokemon_types_name);
            iv_pokemon_type = itemView.findViewById(R.id.iv_pokemon_type);
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
    public pokemon_types_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pokemon_type_list_item, parent, false);
        return new pokemon_types_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull pokemon_types_ViewHolder holder, int position) {
        String current_pokemon_name = pokemon.get(position);
        holder.tv_pokemon_name.setText(current_pokemon_name.toUpperCase());
        switch (current_pokemon_name) {
            case "bug":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_bug);
                break;
            case "water":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_water);
                break;
            case "dark":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_dark);
                break;
            case "dragon":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_dragon);
                break;
            case "electric":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_electric);
                break;
            case "fairy":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_fairy);
                break;
            case "fighting":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_fighting);
                break;
            case "fire":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_fire);
                break;
            case "flying":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_flying);
                break;
            case "ghost":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_ghost);
                break;
            case "grass":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_grass);
                break;
            case "ground":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_ground);
                break;
            case "ice":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_ice);
                break;
            case "normal":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_normal);
                break;
            case "poison":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_poison);
                break;
            case "psychic":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_psychic);
                break;
            case "rock":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_rock);
                break;
            case "shadow":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_shadow);
                break;
            case "steel":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_steel);
                break;
            case "unknown":
                holder.iv_pokemon_type.setImageResource(R.drawable.ic_unknown);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return pokemon.size();
    }
}

