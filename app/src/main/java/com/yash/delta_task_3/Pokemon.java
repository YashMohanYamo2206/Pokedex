package com.yash.delta_task_3;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pokemon {

    @SerializedName("height")
    public long Height ;

    @SerializedName("moves")
    public List<Move> move;

    @SerializedName("types")
    public List<TypeElement> Types;

    @SerializedName("id")
    private int id;

    @SerializedName("forms")
    private List<PokemonForm> forms;

    @SerializedName("sprites")
    private PokemonSprite sprites;

    @SerializedName("stats")
    public List<Stat> Stats ;

    @SerializedName("weight")
    public long Weight ;

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

    public List<Stat> getStats() {
        return Stats;
    }

    public void setStats(List<Stat> stats) {
        Stats = stats;
    }

    public long getWeight() {
        return Weight;
    }

    public void setWeight(long weight) {
        Weight = weight;
    }

    public long getHeight() {
        return Height;
    }

    public void setHeight(long height) {
        Height = height;
    }
}
