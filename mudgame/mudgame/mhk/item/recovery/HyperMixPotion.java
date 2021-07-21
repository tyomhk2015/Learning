package com.mudgame.mhk.item.recovery;

public class HyperMixPotion extends SuperMixPotion {

    private int mix_recovery_ratio = 2;

    public HyperMixPotion() {
        // Define the name and type of the item.
        super.setName("Large mixed potion");
        super.setType("Recovery");
        super.setPrice(super.getPrice() * 3 / 2);

        // Increase the recovery point.
        super.setMix_recovery_ratio(3);
        super.setRecovery_point(super.getRecovery_point() * super.getMix_recovery_ratio() / 2);
        super.setDescription("☞ Recovers HP and SP by " + super.getRecovery_point() + "%.");
    }
}
