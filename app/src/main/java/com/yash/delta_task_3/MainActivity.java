package com.yash.delta_task_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView.bringToFront();

        ActionBarDrawerToggle toggle
                = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home_nav);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new pokemon_home()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new pokemon_home()).addToBackStack(null).commit();
                break;
            case R.id.pokedex_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new pokemon_list()).addToBackStack(null).commit();
                break;
            case R.id.types_of_pokemon_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new pokemon_types_list()).addToBackStack(null).commit();
                break;
            case R.id.region_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new location_list()).addToBackStack(null).commit();
                break;
            case R.id.items_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new items_list()).addToBackStack(null).commit();
                break;
            case R.id.locations_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new location_not_region_list()).addToBackStack(null).commit();
                break;
            case R.id.favourite_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new favourite_pokemon()).addToBackStack(null).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
