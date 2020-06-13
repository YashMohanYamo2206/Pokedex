package com.yash.delta_task_3;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class pokemon_details_activity extends Fragment {
    ImageView pokemon_image;
    TextView pokemon_details, pokemon_name;
    boolean can_share;
    Button share_button;
    String image_url;
    private RequestQueue mRequestQueue;
    String evolution_chain_url;
    String pokemon_name_str, move;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pokemon_details_activity, container, false);
        Button share_button = (Button) view.findViewById(R.id.share_button);
        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                String details = pokemon_name.getText().toString() + "\n\n" + pokemon_details.getText().toString() + "\nIMAGE URL :- " + image_url;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, details);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "share details using..."));
            }
        });
        return view;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        mRequestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        pokemon_image = Objects.requireNonNull(getView()).findViewById(R.id.pokemon_detail_image);
        pokemon_name = getView().findViewById(R.id.pokemon_detail_name);
        pokemon_details = getView().findViewById(R.id.pokemon_detail_moves);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        pokemon_name_str = bundle.getString("pokemon_name");
        pokemon_name.setText("NAME:- " + pokemon_name_str);
        share_button = getView().findViewById(R.id.share_button);
        move = bundle.getString("moves");
        image_url = bundle.getString("img_url");
        can_share = bundle.getBoolean("can_share", false);
        if (can_share) {
            share_button.setVisibility(View.VISIBLE);
        } else {
            share_button.setVisibility(View.GONE);
        }
        if(image_url!=null)
            Picasso.with(getContext()).load(image_url).fit().centerInside().into(pokemon_image);
        else
            pokemon_image.setImageResource(R.mipmap.pokeball);
        pokemon_details.setText(move);
        //Toast.makeText(this, (CharSequence) pokemon_image.getDrawable(), Toast.LENGTH_SHORT).show();
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
