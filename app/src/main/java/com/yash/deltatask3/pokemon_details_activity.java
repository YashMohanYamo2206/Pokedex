package com.yash.deltatask3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class pokemon_details_activity extends AppCompatActivity {
    ImageView pokemon_image;
    TextView pokemon_details, pokemon_name;
    boolean can_share;
    Button share_button;
    String image_url;
    private RequestQueue mRequestQueue;
    String evolution_chain_url;
    String pokemon_name_str, move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details_activity);
        mRequestQueue = Volley.newRequestQueue(this);
        pokemon_image = findViewById(R.id.pokemon_detail_image);
        pokemon_name = findViewById(R.id.pokemon_detail_name);
        pokemon_details = findViewById(R.id.pokemon_detail_moves);
        Intent intent = getIntent();
        pokemon_name_str = intent.getStringExtra("pokemon_name");
        pokemon_name.setText("NAME:- " + pokemon_name_str);
        //setEvolution_chain_url();
        share_button = findViewById(R.id.share_button);
        move = intent.getStringExtra("moves");
        image_url = intent.getStringExtra("img_url");
        can_share = intent.getBooleanExtra("can_share", false);
        if (can_share) {
            share_button.setVisibility(View.VISIBLE);
        } else {
            share_button.setVisibility(View.GONE);
        }
        Picasso.with(this).load(image_url).fit().centerInside().into(pokemon_image);
        pokemon_details.setText(move);
        //Toast.makeText(this, (CharSequence) pokemon_image.getDrawable(), Toast.LENGTH_SHORT).show();
    }

    public void share_details(View view) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        String details = pokemon_name.getText().toString() + "\n\n" + pokemon_details.getText().toString() + "\nIMAGE URL :- " + image_url;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, details);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "share details using..."));
    }
/**
    private void setEvolution_chain_url() {
        String url = "https://pokeapi.co/api/v2/pokemon-species/" + pokemon_name_str.toLowerCase();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("evolution_chain");
                            evolution_chain_url = jsonObject.getString("url");
                            // Toast.makeText(pokemon_details_activity.this, evolution_chain_url, Toast.LENGTH_SHORT).show();
                            //getEvolution_details(evolution_chain_url);
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

    private void getEvolution_details(String evolution_chain_url) {
        //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        PokemonApiInterface service = PokemonApi.custom_url(evolution_chain_url).create(PokemonApiInterface.class);
        service.get_pokemon_species().enqueue(new Callback<evolution_chain>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<evolution_chain> call, retrofit2.Response<evolution_chain> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(pokemon_details_activity.this, "Error..!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                assert response.body().chain != null;
                pokemon_details.setText(move + "\n\nEvolution chain:- " + response.body().chain.getSpecies().getName() + ", " + response.body().chain.getEvolvesTo().get(0).getSpecies().getName() + ", " + response.body().chain.getEvolvesTo().get(0).getEvolvesTo().get(0).getSpecies().getName() + ".");
            }

            @Override
            public void onFailure(Call<evolution_chain> call, Throwable t) {
                Toast.makeText(pokemon_details_activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
 */
 }
