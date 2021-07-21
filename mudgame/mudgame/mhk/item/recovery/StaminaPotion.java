package com.mudgame.mhk.item.recovery;

import com.mudgame.mhk.item.Item;

public class StaminaPotion extends Potion {

    public StaminaPotion() {
        // Define the name and type of the item.
        super.setName("Small SP potion");
        super.setType("Recovery");
        super.setPrice(5);
        super.setDescription("☞ Recovers SP by " + super.getRecovery_point() + "%.");
    }
}
