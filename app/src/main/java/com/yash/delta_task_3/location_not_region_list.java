package com.yash.delta_task_3;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class location_not_region_list extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private location_not_region_list_adapter mAdapter;
    location_not_region location_not_region = new location_not_region();
    location_not_region location_not_region_search_action = new location_not_region();
    Handler handler = new Handler();
    int item_id = 20;
    ProgressBar progressBar;
    EditText search;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_location_not_region, container, false);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        search = getView().findViewById(R.id.detailsearch);
        progressBar = getView().findViewById(R.id.loading);
        mRecyclerView = getView().findViewById(R.id.location_not_region_recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.INVISIBLE);
        getLocationName(item_id);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }, 2000);
        mRecyclerView.addOnScrollListener(new CustomOnScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Snackbar.make(mRecyclerView, "Loading more locations....", Snackbar.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (item_id <= 761) {
                            item_id = item_id + 20;
                            getLocationName(item_id);
                        }
                    }
                }, 600);
            }

        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search_action(s.toString());
            }
        });
    }

    private void search_action(String s) {
        List<Region> regions = new ArrayList<>();
        if (!s.trim().equals("")) {
            for (int i = 0; i < location_not_region.getLocation_not_region().size(); i++) {
                if (s.equals(location_not_region.getLocation_not_region().get(i).getName())) {
                    regions.add(location_not_region.getLocation_not_region().get(i));
                }
            }
            mAdapter.notifyDataSetChanged();
            mAdapter = new location_not_region_list_adapter(regions, 20);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter = new location_not_region_list_adapter(location_not_region.getLocation_not_region(), 20);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void getLocationName(int item_id) {
        final int i = item_id;
        if (item_id <= 20) {
            PokemonApiInterface service = PokemonApi.getApi().create(PokemonApiInterface.class);
            service.getLocation(781).enqueue(new Callback<location_not_region>() {
                @Override
                public void onResponse(Call<location_not_region> call, Response<location_not_region> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), "ERROR..!! TRY AGAIN or CHECK INTERNET", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    location_not_region = response.body();
                    mAdapter = new location_not_region_list_adapter(location_not_region.getLocation_not_region(), i);
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<location_not_region> call, Throwable t) {
                    Toast.makeText(getContext(), "ERROR..!! TRY AGAIN or CHECK INTERNET", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mAdapter.mCount = i;
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(location_not_region.getLocation_not_region()!=null)
            location_not_region.getLocation_not_region().clear();
    }
}

