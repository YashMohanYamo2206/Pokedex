package com.yash.deltatask3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {
    @SerializedName("pokemon_entries")
    private List<PokemonEntry> PokemonEntries ;

    @SerializedName("region")
    private Region region ;

    public List<PokemonEntry> getPokemonEntries() {
        return PokemonEntries;
    }

    public void setPokemonEntries(List<PokemonEntry> pokemonEntries) {
        PokemonEntries = pokemonEntries;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
