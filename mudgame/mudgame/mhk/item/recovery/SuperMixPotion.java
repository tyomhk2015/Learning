package com.mudgame.mhk.item.recovery;

public class SuperMixPotion extends MixPotion {

    private int mix_recovery_ratio = 2;

    public SuperMixPotion() {
        // Define the name and type of the item.
        super.setName("Medium mixed potion");
        super.setType("Recovery");
        super.setPrice(super.getPrice() * 3);

        // Increase the recovery point.
        super.setRecovery_point(super.getRecovery_point() * this.mix_recovery_ratio);
        super.setDescription("☞ Recovers HP & SP by " + super.getRecovery_point() + "%.");
    }

    public int getMix_recovery_ratio() {
        return mix_recovery_ratio;
    }

    public void setMix_recovery_ratio(int mix_recovery_ratio) {
        this.mix_recovery_ratio = mix_recovery_ratio;
    }
}
