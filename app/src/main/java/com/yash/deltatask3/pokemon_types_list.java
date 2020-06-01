package com.yash.deltatask3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class pokemon_types_list extends AppCompatActivity implements pokemon_types_adapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private pokemon_types_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> types_of_pokemon = new ArrayList<>();
    private int currentId = 1;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_list);
        mRecyclerView = findViewById(R.id.pokemon_types_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        currentId = 1;
        mRequestQueue = Volley.newRequestQueue(this);
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
                            mAdapter = new pokemon_types_adapter(pokemon_types_list.this, types_of_pokemon);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.setOnItemClickListener(pokemon_types_list.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        if(position==16||position==18){
            Toast.makeText(this, "No information available for this type of pokemon".toUpperCase(), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(pokemon_types_list.this,pokemon_of_types_list.class);
        intent.putExtra("type_of_pokemon", types_of_pokemon.get(position));
        startActivity(intent);
    }
}
