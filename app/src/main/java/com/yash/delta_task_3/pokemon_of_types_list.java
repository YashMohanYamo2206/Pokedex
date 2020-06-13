package com.yash.delta_task_3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.yash.delta_task_3.Database.Database;
import com.yash.delta_task_3.Database.pokemon_contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pokemon_of_types_list extends Fragment implements pokemon_list_adapter.OnItemClickListener {
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
    ProgressBar progressBar;
    SQLiteDatabase mDatabase;
    List<Pokemon> searchname = new ArrayList<>();
    public boolean isSearching;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pokemon_list, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Database db = new Database(getContext());
        mDatabase = db.getWritableDatabase();
        progressBar = getView().findViewById(R.id.loading);
        Bundle bundle = this.getArguments();
        type_of_pokemon = bundle.getString("type_of_pokemon");
        mAdapter = new pokemon_list_adapter(getContext(), pokemons);
        mRecyclerView = getView().findViewById(R.id.pokemon_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        currentId = 1;
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.INVISIBLE);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        addData();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }, 6000);
        EditText search = getView().findViewById(R.id.detailsearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //serchaction(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //serchaction(s.toString());
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
            isSearching = true;
            for (int i = 0; i < pokemons.size(); i++) {
                if (pokemons.get(i).getShortName().toLowerCase().contains(s.toLowerCase())) {
                    searchname.add(pokemons.get(i));
                }
            }

            mAdapter.notifyDataSetChanged();
            mAdapter = new pokemon_list_adapter(getContext(), searchname);
            mAdapter.setOnItemClickListener(pokemon_of_types_list.this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            isSearching = false;
            mAdapter.notifyDataSetChanged();
            mAdapter = new pokemon_list_adapter(getContext(), pokemons);
            mAdapter.setOnItemClickListener(pokemon_of_types_list.this);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

    @Override
    public void onItemClick(int position) {
        if (!isSearching) {
            Bundle bundle = new Bundle();
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
            bundle.putString("moves", move);
            bundle.putString("pokemon_name", pokemons.get(position).getShortName().toUpperCase());
            bundle.putString("img_url", pokemons.get(position).getSprites().getFrontShine());
            pokemon_details_activity fragment2 = new pokemon_details_activity();
            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment2.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, fragment2);
            fragmentTransaction.addToBackStack(null).commit();
        } else {
            //isSearching = false;
            Bundle bundle = new Bundle();
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
            bundle.putString("moves", move);
            bundle.putString("pokemon_name", searchname.get(position).getShortName().toUpperCase());
            bundle.putString("img_url", searchname.get(position).getSprites().getFrontShine());
            pokemon_details_activity fragment2 = new pokemon_details_activity();
            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment2.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, fragment2);
            fragmentTransaction.addToBackStack(null).commit();
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (!CheckIsDataAlreadyInDBorNot(pokemons.get(viewHolder.getAdapterPosition()).getShortName())) {
                Toast.makeText(getContext(), "ADDED TO FAVOURITES", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "ALREADY IN FAVOURITES", Toast.LENGTH_SHORT).show();
            }
            mAdapter.notifyDataSetChanged();
        }

    };

    private void addData() {
        PokemonApiInterface service = PokemonApi.getApi().create(PokemonApiInterface.class);
        service.getTypes(type_of_pokemon).enqueue(new Callback<pokemon_of_types_name>() {
            @Override
            public void onResponse(@NonNull Call<pokemon_of_types_name> call, @NonNull Response<pokemon_of_types_name> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Error..!! TRY AGAIN OR CHECK INTERNET", Toast.LENGTH_SHORT).show();
                }
                pokemon_of_types_names.add(response.body());
                addPokemonData(0, 20);
            }

            @Override
            public void onFailure(@NonNull Call<pokemon_of_types_name> call, @NonNull Throwable t) {
                Log.d( "onFailure: ", Objects.requireNonNull(t.getMessage()));
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addPokemonData(int i, int j) {
        PokemonApiInterface service = PokemonApi.getApi().create(PokemonApiInterface.class);
        List<PokemonEntry> pokemonEntries = pokemon_of_types_names.get(0).getPokemon();
        while (i < pokemonEntries.size() - 1 && i < j) {
            try {
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
            } catch (Exception e) {
                Toast.makeText(getContext(), "DONE..!!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onResume() {
        super.onResume();
        pokemons.clear();
    }
}
