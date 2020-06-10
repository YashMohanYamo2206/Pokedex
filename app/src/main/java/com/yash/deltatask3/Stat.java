package com.yash.deltatask3;

import com.google.gson.annotations.SerializedName;

public class Stat {

    @SerializedName("base_stat")
    public long BaseStat;

    @SerializedName("effort")
    public long Effort;

    @SerializedName("stat")
    public Species StatStat;

    public long getBaseStat() {
        return BaseStat;
    }

    public void setBaseStat(long baseStat) {
        BaseStat = baseStat;
    }

    public long getEffort() {
        return Effort;
    }

    public void setEffort(long effort) {
        Effort = effort;
    }

    public Species getStatStat() {
        return StatStat;
    }

    public void setStatStat(Species statStat) {
        StatStat = statStat;
    }
}

