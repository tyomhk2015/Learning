package com.mudgame.mhk.item.equipment;

import com.mudgame.mhk.item.Item;

public class Equipment extends Item {

    private int durability = 10; // A number of hits the equipment can endure. If the value is zero, the equipment will be gone.

    public Equipment() {
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
