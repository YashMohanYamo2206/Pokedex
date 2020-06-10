package com.yash.deltatask3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.yash.deltatask3.Database.Database;
import com.yash.deltatask3.Database.pokemon_contract;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class location_pokemon_list extends AppCompatActivity implements pokemon_list_adapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private pokemon_list_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Pokemon> pokemons = new ArrayList<>();
    private int currentId = 1;
    Handler handler = new Handler();
    int i = 0, j = 20;
    ProgressBar progressBar;
    SQLiteDatabase mDatabase;
    public boolean isSearching;
    List<Pokemon> searchname = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);
        Database database = new Database(this);
        mDatabase = database.getWritableDatabase();
        progressBar = findViewById(R.id.loading);
        mAdapter = new pokemon_list_adapter(location_pokemon_list.this, pokemons);
        mRecyclerView = findViewById(R.id.pokemon_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        currentId = 1;
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.INVISIBLE);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        addLocationData(0, 20);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, 6000);
        EditText search = findViewById(R.id.detailsearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                serchaction(s.toString());
            }
        });
        mRecyclerView.addOnScrollListener(new CustomOnScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (currentId <= 10157) {
                    Snackbar.make(mRecyclerView, "Loading more Pokémons....", Snackbar.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i = i + 20;
                            j = j + 20;
                            addLocationData(i, j);
                        }
                    }, 600);
                }
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (!CheckIsDataAlreadyInDBorNot(pokemons.get(viewHolder.getAdapterPosition()).getShortName())) {
                Toast.makeText(location_pokemon_list.this, "ADDED TO FAVOURITES", Toast.LENGTH_SHORT).show();
                List<Move> moves = pokemons.get(viewHolder.getAdapterPosition()).getMove();
                String move = " ";
                move += "Weight:- " + pokemons.get(viewHolder.getAdapterPosition()).getWeight() + "\n\n" + "Height:- " + pokemons.get(viewHolder.getAdapterPosition()).getHeight() + "\n\n";
                move += "Moves:- ";
                for (int i = 0; i < moves.size(); i++) {
                    if (i < moves.size() - 1)
                        move += moves.get(i).getMoveMove().getName() + ", ";
                    else
                        move += moves.get(i).getMoveMove().getName() + ".";
                }
                move += "\n\n";
                List<Stat> stats = pokemons.get(viewHolder.getAdapterPosition()).getStats();
                for (Stat stat : stats) {
                    move += stat.getStatStat().getName() + ":-\t\t" + stat.getBaseStat() + "\n\n";
                }
                move += "Types:- ";
                List<TypeElement> typeElements = pokemons.get(viewHolder.getAdapterPosition()).getTypes();
                for (int i = 0; i < typeElements.size(); i++) {
                    if (i < typeElements.size() - 1)
                        move += typeElements.get(i).getType().getName() + ", ";
                    else
                        move += typeElements.get(i).getType().getName() + ". ";
                }
                ContentValues cv = new ContentValues();
                cv.put(pokemon_contract.PokemonEntry.COLUMN_POKEMON_NAME, pokemons.get(viewHolder.getAdapterPosition()).getShortName());
                cv.put(pokemon_contract.PokemonEntry.COLUMN_IMAGE_URL, pokemons.get(viewHolder.getAdapterPosition()).getSprites().getFrontShine());
                cv.put(pokemon_contract.PokemonEntry.COLUMN_STATS, move);
                mDatabase.insert(pokemon_contract.PokemonEntry.TABLE_NAME, null, cv);
            } else {
                Toast.makeText(location_pokemon_list.this, "ALREADY IN FAVOURITES", Toast.LENGTH_SHORT).show();
            }
            mAdapter.notifyDataSetChanged();
        }

    };

    private void serchaction(String s) {
        List<Pokemon> searchname = new ArrayList<>();
        if (!s.equals("")) {
            isSearching = true;
            for (int i = 0; i < pokemons.size(); i++) {
                if (pokemons.get(i).getShortName().toLowerCase().startsWith(s.toLowerCase())) {
                    searchname.add(pokemons.get(i));
                }
            }

            mAdapter.notifyDataSetChanged();
            mAdapter = new pokemon_list_adapter(this, searchname);
            mAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            isSearching = false;
            mAdapter.notifyDataSetChanged();
            mAdapter = new pokemon_list_adapter(this, pokemons);
            mAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

    @Override
    public void onItemClick(int position) {
        if (!isSearching) {
            Intent intent = new Intent(location_pokemon_list.this, pokemon_details_activity.class);
            List<Move> moves = pokemons.get(position).getMove();
            String move = "";
            move += "Weight:- " + pokemons.get(position).getWeight() + "\n\n" + "Height:- " + pokemons.get(position).getHeight() + "\n\n";
            move += "Moves:- ";
            for (int i = 0; i < moves.size(); i++) {
                if (i < moves.size() - 1)
                    move += moves.get(i).getMoveMove().getName() + ", ";
                else
                    move += moves.get(i).getMoveMove().getName() + ".";
            }
            move += "\n\n";
            List<Stat> stats = pokemons.get(position).getStats();
            for (Stat stat : stats) {
                move += stat.getStatStat().getName() + ":-\t\t" + stat.getBaseStat() + "\n\n";
            }
            move += "Types:- ";
            List<TypeElement> typeElements = pokemons.get(position).getTypes();
            for (int i = 0; i < typeElements.size(); i++) {
                if (i < typeElements.size() - 1)
                    move += typeElements.get(i).getType().getName() + ", ";
                else
                    move += typeElements.get(i).getType().getName() + ". ";
            }
            intent.putExtra("moves", move);
            intent.putExtra("pokemon_name", pokemons.get(position).getShortName().toUpperCase());
            intent.putExtra("img_url", pokemons.get(position).getSprites().getFrontShine());
            startActivity(intent);
        } else {
            //isSearching = false;
            Intent intent = new Intent(location_pokemon_list.this, pokemon_details_activity.class);
            List<Move> moves = searchname.get(position).getMove();
            String move = "";
            move += "Weight:- " + searchname.get(position).getWeight() + "\n\n" + "Height:- " + searchname.get(position).getHeight() + "\n\n";
            move += "Moves:- ";
            for (int i = 0; i < moves.size(); i++) {
                if (i < moves.size() - 1)
                    move += moves.get(i).getMoveMove().getName() + ", ";
                else
                    move += moves.get(i).getMoveMove().getName() + ".";
            }
            move += "\n\n";
            List<Stat> stats = searchname.get(position).getStats();
            for (Stat stat : stats) {
                move += stat.getStatStat().getName() + ":-\t\t" + stat.getBaseStat() + "\n\n";
            }
            move += "Types:- ";
            List<TypeElement> typeElements = searchname.get(position).getTypes();
            for (int i = 0; i < typeElements.size(); i++) {
                if (i < typeElements.size() - 1)
                    move += typeElements.get(i).getType().getName() + ", ";
                else
                    move += typeElements.get(i).getType().getName() + ". ";
            }
            intent.putExtra("moves", move);
            intent.putExtra("pokemon_name", searchname.get(position).getShortName().toUpperCase());
            intent.putExtra("img_url", searchname.get(position).getSprites().getFrontShine());
            startActivity(intent);
        }
    }

    private void addLocationData(int i, int j) {
        PokemonApiInterface service = PokemonApi.getApi().create(PokemonApiInterface.class);
        List<PokemonEntry> pokemonEntries = location_list.pokemon_locations;
        if (i < pokemonEntries.size() - 3 && i < j) {
            for (; i < j; i++) {
                try {
                    service.getPokemon(pokemonEntries.get(i).getPokemonSpecies().getName()).enqueue(new Callback<Pokemon>() {
                        @Override
                        public void onResponse(@NonNull Call<Pokemon> call, @NonNull Response<Pokemon> response) {
                            if (response.isSuccessful()) {
                                pokemons.add(response.body());
                                mAdapter.notifyDataSetChanged();
                                mAdapter.setOnItemClickListener(location_pokemon_list.this);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Pokemon> call, @NonNull Throwable t) {

                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(this, "DONE..!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public boolean CheckIsDataAlreadyInDBorNot(String pokemon_name) {
        String Query = "SELECT EXISTS (SELECT * FROM " + pokemon_contract.PokemonEntry.TABLE_NAME + " WHERE " + pokemon_contract.PokemonEntry.COLUMN_POKEMON_NAME + "='" + pokemon_name + "' LIMIT 1)";
        Cursor cursor = mDatabase.rawQuery(Query, null);
        cursor.moveToFirst();

        if (cursor.getInt(0) == 1) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;

    }
}

