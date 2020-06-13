package com.yash.delta_task_3;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Chain {

    @SerializedName("evolves_to")
    private List<Chain> EvolvesTo;
    @SerializedName("species")
    private Species Species;

    public List<Chain> getEvolvesTo() {
        return EvolvesTo;
    }

    public void setEvolvesTo(List<Chain> evolvesTo) {
        EvolvesTo = evolvesTo;
    }

    public com.yash.delta_task_3.Species getSpecies() {
        return Species;
    }

    public void setSpecies(com.yash.delta_task_3.Species species) {
        Species = species;
    }
}
