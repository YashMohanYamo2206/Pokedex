package com.yash.deltatask3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yash.deltatask3.Database.pokemon_contract;

public class favourite_pokemon_adapter extends RecyclerView.Adapter<favourite_pokemon_adapter.favourite_pokemon_ViewHolder> {

    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    private Context mContext;
    private Cursor mCursor;

    public favourite_pokemon_adapter(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    public class favourite_pokemon_ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        public favourite_pokemon_ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_pokemon);
            mTextView = itemView.findViewById(R.id.tv_pokemon_name);
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
    public favourite_pokemon_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pokemon_list_item, parent, false);
        return new favourite_pokemon_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull favourite_pokemon_ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String p1_name = mCursor.getString(mCursor.getColumnIndex(pokemon_contract.PokemonEntry.COLUMN_POKEMON_NAME)).toUpperCase();
        String img_url = mCursor.getString(mCursor.getColumnIndex(pokemon_contract.PokemonEntry.COLUMN_IMAGE_URL));
        holder.mTextView.setText(p1_name);
        Picasso.with(mContext).load(img_url).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
