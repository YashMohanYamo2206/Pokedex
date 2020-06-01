package com.yash.deltatask3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class location_not_region_list extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private location_not_region_list_adapter mAdapter;
    location_not_region location_not_region = new location_not_region();
    Handler handler = new Handler();
    int item_id = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_not_region);
        mRecyclerView = findViewById(R.id.location_not_region_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        getLocationName(item_id);
        mRecyclerView.addOnScrollListener(new CustomOnScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(mRecyclerView, "Loading more locations....", Snackbar.LENGTH_SHORT).show();
                        if (item_id <= 761) {
                            item_id = item_id + 20;
                            getLocationName(item_id);
                        }
                    }
                }, 600);
            }

        });
    }

    private void getLocationName(int item_id) {
        final int i = item_id;
        if (item_id <= 20) {
            PokemonApiInterface service = PokemonApi.getApi().create(PokemonApiInterface.class);
            service.getLocation(781).enqueue(new Callback<location_not_region>() {
                @Override
                public void onResponse(Call<location_not_region> call, Response<location_not_region> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(location_not_region_list.this, "kuch to gadbad hai", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    location_not_region = response.body();
                    mAdapter = new location_not_region_list_adapter(location_not_region.getLocation_not_region(), i);
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<location_not_region> call, Throwable t) {

                }
            });
        } else {
            mAdapter.mCount = i;
            mAdapter.notifyDataSetChanged();
        }
    }
}

