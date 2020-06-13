package com.yash.delta_task_3;

import com.google.gson.annotations.SerializedName;

class Region {

    @SerializedName("name")
    private String Name ;

    @SerializedName("url")
    private String Url ;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
