package com.mudgame.mhk.item.recovery;

import com.mudgame.mhk.item.Item;

public class HealthPotion extends Potion {

    public HealthPotion() {
        // Define the name and type of the item.
        super.setName("Small HP potion");
        super.setType("Recovery");
        super.setPrice(5);
        super.setDescription("☞ Recovers " + super.getRecovery_point() + "%.");
    }

}
