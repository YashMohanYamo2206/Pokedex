package com.yash.deltatask3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class pokemon_details_activity extends AppCompatActivity {
    ImageView pokemon_image;
    TextView pokemon_details,pokemon_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details_activity);
        pokemon_image=findViewById(R.id.pokemon_detail_image);
        pokemon_name=findViewById(R.id.pokemon_detail_name);
        Intent intent = getIntent();
        String image_url=intent.getStringExtra("img_url");
        Picasso.with(this).load(image_url).fit().centerInside().into(pokemon_image);
        //pokemon_details.setText(intent.getStringExtra("moves"));
        pokemon_name.setText("NAME:- "+intent.getStringExtra("pokemon_name"));
    }
}
