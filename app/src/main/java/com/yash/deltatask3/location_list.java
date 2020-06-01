package com.yash.deltatask3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class location_list extends AppCompatActivity implements location_list_adapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private location_list_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Location> locations = new ArrayList<>();
    public static List<PokemonEntry> pokemon_locations ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        mAdapter = new location_list_adapter(locations);
        mRecyclerView = findViewById(R.id.location_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        getLocationName();
    }

    private void getLocationName() {
        PokemonApiInterface service = PokemonApi.getApi().create(PokemonApiInterface.class);
        final int[] id = new int[]{2, 6, 7, 9, 12, 15};
        for (int i = 0; i < 6; i++) {
            final int finalI = i;
            service.get_location(id[i]).enqueue(new Callback<Location>() {
                @Override
                public void onResponse(Call<Location> call, Response<Location> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(location_list.this, "hua kuch nhi", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    locations.add(response.body());
                    mAdapter.notifyDataSetChanged();
                    mAdapter.setOnItemClickListener(location_list.this);
                }

                @Override
                public void onFailure(Call<Location> call, Throwable t) {
                    int k = id[finalI];
                    Log.d("paise barbaad bc", t.getCause().toString());
                    Toast.makeText(location_list.this, "paise barbaad bc", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(location_list.this,location_pokemon_list.class);
        pokemon_locations = locations.get(position).getPokemonEntries();
        startActivity(intent);
    }
}
