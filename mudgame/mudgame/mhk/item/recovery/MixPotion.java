package com.mudgame.mhk.item.recovery;

public class MixPotion extends Potion{

    public MixPotion() {
        // Define the name and type of the item.
        super.setName("Small mixed potion");
        super.setType("Recovery");
        super.setPrice(10);
        super.setDescription("☞ Recovers HP & SP by " + super.getRecovery_point() + "%.");
    }
}
