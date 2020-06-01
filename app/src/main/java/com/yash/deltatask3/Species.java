package com.yash.deltatask3;

import com.google.gson.annotations.SerializedName;

class Species {
    @SerializedName("name")
    public String Name ;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
