package com.yash.delta_task_3;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class item {
    @SerializedName("results")
    private List<Region> results;

    public List<Region> getResults() {
        return results;
    }

    public void setResults(List<Region> results) {
        this.results = results;
    }
}
