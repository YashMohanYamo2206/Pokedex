package com.yash.deltatask3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pokemon_of_types_list extends AppCompatActivity implements pokemon_list_adapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private pokemon_list_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Pokemon> pokemons = new ArrayList<>();
    private int currentId = 1;
    public static boolean isRegion = false;
    String type_of_pokemon = "";
    List<pokemon_of_types_name> pokemon_of_types_names = new ArrayList<>();
    Handler handler = new Handler();
    int i = 0, j = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);
        Intent intent = getIntent();
        type_of_pokemon = intent.getStringExtra("type_of_pokemon");
        mAdapter = new pokemon_list_adapter(pokemon_of_types_list.this, pokemons);
        mRecyclerView = findViewById(R.id.pokemon_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        currentId = 1;
        mRecyclerView.setAdapter(mAdapter);
        addData();
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
                Snackbar.make(mRecyclerView, "Loading more Pokémons....", Snackbar.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        i = i + 20;
                        j = j + 20;
                        addPokemonData(i, j);
                    }
                }, 600);
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

    private void addData() {
        PokemonApiInterface service = PokemonApi.getApi().create(PokemonApiInterface.class);
        service.getTypes(type_of_pokemon).enqueue(new Callback<pokemon_of_types_name>() {
            @Override
            public void onResponse(Call<pokemon_of_types_name> call, Response<pokemon_of_types_name> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(pokemon_of_types_list.this, "kuch to gadbad hai", Toast.LENGTH_SHORT).show();
                }
                pokemon_of_types_names.add(response.body());
                addPokemonData(0, 20);
            }

            @Override
            public void onFailure(Call<pokemon_of_types_name> call, Throwable t) {
                Toast.makeText(pokemon_of_types_list.this, "kuch to gadbad hai", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addPokemonData(int i, int j) {
        PokemonApiInterface service = PokemonApi.getApi().create(PokemonApiInterface.class);
        List<PokemonEntry> pokemonEntries = pokemon_of_types_names.get(0).getPokemon();
        while (i < pokemonEntries.size() - 1) {

            service.getPokemon(pokemonEntries.get(i).getPokemon_names().getName()).enqueue(new Callback<Pokemon>() {
                @Override
                public void onResponse(@NonNull Call<Pokemon> call, @NonNull Response<Pokemon> response) {
                    if (response.isSuccessful()) {
                        pokemons.add(response.body());
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setOnItemClickListener(pokemon_of_types_list.this);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Pokemon> call, @NonNull Throwable t) {

                }
            });
            i++;
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(pokemon_of_types_list.this, pokemon_details_activity.class);
        //String move = pokemons.get(position).getMove().get(0).getMoveMove().getName();
        //intent.putExtra("moves", move);
        intent.putExtra("pokemon_name", pokemons.get(position).getShortName().toUpperCase());
        intent.putExtra("img_url", pokemons.get(position).getSprites().getFrontShine());
        // Toast.makeText(this, move, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
