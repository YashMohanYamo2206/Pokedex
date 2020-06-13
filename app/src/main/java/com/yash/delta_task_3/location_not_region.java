package com.yash.delta_task_3;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class location_not_region {

    @SerializedName("results")
    private List<Region> location_not_region;

    public List<Region> getLocation_not_region() {
        return location_not_region;
    }

    public void setLocation_not_region(List<Region> location_not_region) {
        this.location_not_region = location_not_region;
    }
}
