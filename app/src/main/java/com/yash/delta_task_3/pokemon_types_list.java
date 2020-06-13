package com.yash.delta_task_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class pokemon_types_list extends Fragment implements pokemon_types_adapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private pokemon_types_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> types_of_pokemon = new ArrayList<>();
    private int currentId = 1;
    private RequestQueue mRequestQueue;
    TextView error;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_types_list, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        error=getView().findViewById(R.id.error_tv_types);
        mRecyclerView = getView().findViewById(R.id.pokemon_types_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        currentId = 1;
        mRequestQueue = Volley.newRequestQueue(getContext());
        addData();
    }

    private void addData() {
        String url = "https://pokeapi.co/api/v2/type";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String creatorName = hit.getString("name");
                                types_of_pokemon.add(creatorName);
                            }
                            Collections.sort(types_of_pokemon);
                            mAdapter = new pokemon_types_adapter(getContext(), types_of_pokemon);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.setOnItemClickListener(pokemon_types_list.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError er) {
                error.setVisibility(View.VISIBLE);
            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        if(position==16||position==18){
            Toast.makeText(getContext(), "No information available for this type of pokemon".toUpperCase(), Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("type_of_pokemon", types_of_pokemon.get(position).toLowerCase());
        pokemon_of_types_list fragment2 = new pokemon_of_types_list();
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment2.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container, fragment2);
        fragmentTransaction.addToBackStack(null).commit();
    }


    @Override
    public void onResume() {
        super.onResume();
        types_of_pokemon.clear();
    }
}
