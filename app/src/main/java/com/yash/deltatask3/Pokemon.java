package com.yash.deltatask3;


import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    @SerializedName("move")
    public List<Move> move;

    public List<TypeElement> getTypes() {
        return Types;
    }

    public void setTypes(List<TypeElement> types) {
        Types = types;
    }

    public List<Move> getMove() {
        return move;
    }

    public void setMove(List<Move> move) {
        this.move = move;
    }

    @SerializedName("types")
    public List<TypeElement> Types;
    @SerializedName("id")
    private int id;
    @SerializedName("forms")
    private List<PokemonForm> forms;
    @SerializedName("sprites")
    private PokemonSprite sprites;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PokemonForm> getForms() {
        return forms;
    }

    public void setForms(List<PokemonForm> forms) {
        this.forms = forms;
    }

    public PokemonSprite getSprites() {
        return sprites;
    }

    public void setSprites(PokemonSprite sprites) {
        this.sprites = sprites;
    }


    public String getShortName() {
        String pokemonName = "";
        if (!this.forms.isEmpty()) {
            pokemonName = this.forms.get(0).getName();
        }
        return pokemonName;

    }

    public String getFullName() {
        String pokemonName = "";
        if (!this.forms.isEmpty()) {
            for (PokemonForm form : forms) {
                pokemonName += form.getName() + " / ";
            }
            pokemonName = pokemonName.substring(0, pokemonName.length() - 3);
        }
        return pokemonName;

    }

}
