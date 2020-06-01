package com.yash.deltatask3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pokemon_list extends AppCompatActivity implements pokemon_list_adapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private pokemon_list_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Pokemon> pokemons = new ArrayList<>();
    private int currentId = 1;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);
        mAdapter = new pokemon_list_adapter(pokemon_list.this, pokemons);
        mRecyclerView = findViewById(R.id.pokemon_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        currentId = 1;
        mRecyclerView.setAdapter(mAdapter);
        addData(20);
        mRecyclerView.addOnScrollListener(new CustomOnScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (currentId <= 10157) {
                    Snackbar.make(mRecyclerView, "Loading more Pokémons....", Snackbar.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addData(20);
                        }
                    }, 600);
                }
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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
    }

    private void serchaction(String s) {
        List<Pokemon> searchname = new ArrayList<>();
        if (!s.equals("")) {
            for (int i = 0; i < pokemons.size(); i++) {
                if (pokemons.get(i).getShortName().toLowerCase().contains(s.toLowerCase())) {
                    searchname.add(pokemons.get(i));
                }
            }

            mAdapter.notifyDataSetChanged();
            mAdapter = new pokemon_list_adapter(this, searchname);
            mAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mAdapter);
        } else {

            mAdapter.notifyDataSetChanged();
            mAdapter = new pokemon_list_adapter(this, pokemons);
            mAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mAdapter);

        }
    }


    private void addData(int quantity) {
        PokemonApiInterface service = PokemonApi.getApi().create(PokemonApiInterface.class);
        int finalId = detectGap(quantity);
        for (; currentId <= finalId; currentId++) {
            if (containsPokemon(currentId)) {
                continue;
            }
            service.getPokemon(currentId).enqueue(new Callback<Pokemon>() {
                @Override
                public void onResponse(@NonNull Call<Pokemon> call, @NonNull Response<Pokemon> response) {
                    if (response.isSuccessful()) {
                        pokemons.add(response.body());
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setOnItemClickListener(pokemon_list.this);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Pokemon> call, @NonNull Throwable t) {

                }
            });
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(pokemon_list.this, pokemon_details_activity.class);
        //String move = pokemons.get(position).getMove().get(0).getMoveMove().getName();
        //intent.putExtra("moves", move);
        intent.putExtra("pokemon_name", pokemons.get(position).getShortName().toUpperCase());
        intent.putExtra("img_url", pokemons.get(position).getSprites().getFrontShine());
        // Toast.makeText(this, move, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private boolean containsPokemon(int idPokemon) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(idPokemon);
        return pokemons.contains(pokemon);
    }

    private int detectGap(int quantity) {
        int finalId = currentId + quantity;
        if (finalId <= 801 || finalId >= 10001) {
            if (finalId > 10157) {
                finalId = 10157;
            }
            return finalId;
        }
        if (currentId > 801) {
            currentId = 10001;
            finalId = currentId + quantity;
            return finalId;
        }
        return 802;
    }
}
