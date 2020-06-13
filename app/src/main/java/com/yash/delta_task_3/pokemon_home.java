package com.yash.delta_task_3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class pokemon_home extends Fragment {
    private Button pokedex, type_of_pokemon, regions, items, locations, favorite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pokemon_home, container, false);
        pokedex = view.findViewById(R.id.pokedex_btn);
        items=view.findViewById(R.id.items_btn);
        locations=view.findViewById(R.id.locations_btn);
        regions=view.findViewById(R.id.region_btn);
        type_of_pokemon=view.findViewById(R.id.types_of_pokemon_btn);
        favorite=view.findViewById(R.id.favourite_pokemon_btn);
        pokedex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokemon_list fragment2 = new pokemon_list();
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment2);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });
        items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items_list fragment2 = new items_list();
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment2);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });
        locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_not_region_list fragment2 = new location_not_region_list();
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment2);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });
        regions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_list fragment2 = new location_list();
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment2);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });
        type_of_pokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokemon_types_list fragment2 = new pokemon_types_list();
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment2);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favourite_pokemon fragment2 = new favourite_pokemon();
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment2);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });
        return view;
    }
}
