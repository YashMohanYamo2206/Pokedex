package com.yash.deltatask3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.yash.deltatask3.Database.Database;
import com.yash.deltatask3.Database.pokemon_contract;

import java.util.ArrayList;
import java.util.List;

public class favourite_pokemon extends AppCompatActivity implements favourite_pokemon_adapter.OnItemClickListener {

    public RecyclerView mRecyclerView;
    private favourite_pokemon_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SQLiteDatabase mDatabase;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_pokemon);
        mRecyclerView = findViewById(R.id.favourite_pokemon_recycler_view);
        Database dbHelper = new Database(this);
        mDatabase = dbHelper.getWritableDatabase();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new favourite_pokemon_adapter(this, getAllItems());
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
        Intent intent = new Intent(favourite_pokemon.this, pokemon_details_activity.class);
        intent.putExtra("moves", String.valueOf(cursor.getString(3)));
        intent.putExtra("pokemon_name", String.valueOf(cursor.getString(1)));
        intent.putExtra("img_url", String.valueOf(cursor.getString(2)));
        intent.putExtra("can_share",true);
        startActivity(intent);
    }
}
