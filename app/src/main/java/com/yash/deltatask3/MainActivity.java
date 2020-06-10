package com.yash.deltatask3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pokedex(View view) {
        Intent intent = new Intent(MainActivity.this, pokemon_list.class);
        startActivity(intent);
    }

    public void types_of_pokemon(View view) {
        Intent intent = new Intent(MainActivity.this, pokemon_types_list.class);
        startActivity(intent);
    }

    public void location(View view) {
        Intent intent = new Intent(MainActivity.this, location_list.class);
        startActivity(intent);
    }

    public void locations_not_regions(View view) {
        Intent intent = new Intent(MainActivity.this,location_not_region_list.class);
        startActivity(intent);
    }

    public void items(View view) {
        Intent intent = new Intent(MainActivity.this,items_list.class);
        startActivity(intent);
    }

    public void favourite_pokemon(View view) {
        Intent intent = new Intent(MainActivity.this,favourite_pokemon.class);
        startActivity(intent);
    }
}
