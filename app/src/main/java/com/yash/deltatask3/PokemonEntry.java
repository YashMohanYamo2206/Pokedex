package com.yash.deltatask3;

import com.google.gson.annotations.SerializedName;

class PokemonEntry {
    @SerializedName("entry_number")
    private long EntryNumber;

    @SerializedName("pokemon_species")
    private Region PokemonSpecies;

    @SerializedName("pokemon")
    private Region pokemon_names;

    public long getEntryNumber() {
        return EntryNumber;
    }

    public Region getPokemon_names() {
        return pokemon_names;
    }

    public void setPokemon_names(Region pokemon_names) {
        this.pokemon_names = pokemon_names;
    }

    public void setEntryNumber(long entryNumber) {
        EntryNumber = entryNumber;
    }

    public Region getPokemonSpecies() {
        return PokemonSpecies;
    }

    public void setPokemonSpecies(Region pokemonSpecies) {
        PokemonSpecies = pokemonSpecies;
    }
}
