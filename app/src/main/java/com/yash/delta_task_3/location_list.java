package com.yash.delta_task_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class location_list extends Fragment implements location_list_adapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private location_list_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Location> locations = new ArrayList<>();
    public static List<PokemonEntry> pokemon_locations;
    TextView error;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_location_list, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        error = getView().findViewById(R.id.error_tv);
        mAdapter = new location_list_adapter(locations);
        mRecyclerView = getView().findViewById(R.id.location_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
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
                        error.setVisibility(View.VISIBLE);
                        return;
                    }
                    locations.add(response.body());
                    mAdapter.notifyDataSetChanged();
                    mAdapter.setOnItemClickListener(location_list.this);
                }

                @Override
                public void onFailure(Call<Location> call, Throwable t) {
                    error.setVisibility(View.VISIBLE);
                }
            });
        }

    }

    @Override
    public void onItemClick(int position) {
        pokemon_locations = locations.get(position).getPokemonEntries();
        location_pokemon_list fragment2 = new location_pokemon_list();
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment2);
        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        locations.clear();
    }
}
