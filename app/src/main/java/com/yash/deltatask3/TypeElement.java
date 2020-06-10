package com.yash.deltatask3;

import com.google.gson.annotations.SerializedName;

public class TypeElement {
    @SerializedName("slot")
    public long Slot;

    @SerializedName("type")
    public Species Type;

    public long getSlot() {
        return Slot;
    }

    public void setSlot(long slot) {
        Slot = slot;
    }

    public Species getType() {
        return Type;
    }

    public void setType(Species type) {
        Type = type;
    }
}
