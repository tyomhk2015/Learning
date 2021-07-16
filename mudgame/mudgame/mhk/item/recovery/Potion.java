package com.mudgame.mhk.item.recovery;

import com.mudgame.mhk.item.Item;

public class Potion extends Item {

    private int recovery_point = 25; // Recover a quarter of max health_point of the player.

    public Potion() {
    }

    public void setRecovery_point(int recovery_point) {
        this.recovery_point = recovery_point;
    }

    public int getRecovery_point() {
        return recovery_point;
    }
}
