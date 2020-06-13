package com.yash.delta_task_3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.delta_task_3.Database.Database;
import com.yash.delta_task_3.Database.pokemon_contract;

public class favourite_pokemon extends Fragment implements favourite_pokemon_adapter.OnItemClickListener {

    public RecyclerView mRecyclerView;
    private favourite_pokemon_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SQLiteDatabase mDatabase;
    Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favourite_pokemon, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView = getView().findViewById(R.id.favourite_pokemon_recycler_view);
        Database dbHelper = new Database(getContext());
        mDatabase = dbHelper.getWritableDatabase();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new favourite_pokemon_adapter(getContext(), getAllItems());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.swapCursor(getAllItems());
        mAdapter.setOnItemClickListener(this);
        cursor = getAllItems();
    }

    public Cursor getAllItems() {
        return mDatabase.query(
                pokemon_contract.PokemonEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                pokemon_contract.PokemonEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    public Cursor getPokemon(int rowId) {
        Cursor mCursor =
                mDatabase.rawQuery("select * from " + pokemon_contract.PokemonEntry.TABLE_NAME + " WHERE " + pokemon_contract.PokemonEntry._ID + "=" + rowId , null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    @Override
    public void onItemClick(int position) {
        Cursor cursor = getPokemon(mAdapter.getItemCount()-(position));
        Bundle bundle = new Bundle();
        bundle.putString("moves", String.valueOf(cursor.getString(3)));
        bundle.putString("pokemon_name", String.valueOf(cursor.getString(1)));
        bundle.putString("img_url", String.valueOf(cursor.getString(2)));
        bundle.putBoolean("can_share",true);
        pokemon_details_activity fragment2 = new pokemon_details_activity();
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment2.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container, fragment2);
        fragmentTransaction.addToBackStack(null).commit();
    }
}
