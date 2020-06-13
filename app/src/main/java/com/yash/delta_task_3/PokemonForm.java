package com.yash.delta_task_3;

import com.google.gson.annotations.SerializedName;

public class PokemonForm {

    @SerializedName("name")
    private String name;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
