package com.yash.delta_task_3;

import com.google.gson.annotations.SerializedName;

public class Move {
    @SerializedName("move")
    public Species MoveMove;

    public Species getMoveMove() {
        return MoveMove;
    }

    public void setMoveMove(Species moveMove) {
        MoveMove = moveMove;
    }
}
