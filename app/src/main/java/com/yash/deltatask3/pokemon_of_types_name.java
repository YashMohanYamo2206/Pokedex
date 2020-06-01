package com.yash.deltatask3;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class pokemon_of_types_name {
    @SerializedName("pokemon")
    public List<PokemonEntry> Pokemon ;

    public List<PokemonEntry> getPokemon() {
        return Pokemon;
    }

    public void setPokemon(List<PokemonEntry> pokemon) {
        Pokemon = pokemon;
    }
}
