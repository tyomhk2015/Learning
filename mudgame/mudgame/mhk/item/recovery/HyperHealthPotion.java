package com.mudgame.mhk.item.recovery;

public class HyperHealthPotion extends SuperHealthPotion {

    public HyperHealthPotion() {
        // Define the name and type of the item.
        super.setName("Large HP potion");
        super.setType("Recovery");
        super.setPrice(super.getPrice() * 3 / 2);
        // Increase the recovery point.
        super.setRecovery_ratio(3);
        super.setRecovery_point(super.getRecovery_point() * super.getRecovery_ratio() / 2);
        super.setDescription("☞ Recovers " + super.getRecovery_point() + "%.");
    }
}
