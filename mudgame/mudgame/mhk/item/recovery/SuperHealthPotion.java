package com.mudgame.mhk.item.recovery;

import com.mudgame.mhk.item.Item;

public class SuperHealthPotion extends HealthPotion {

    private int recovery_ratio = 2;

    public SuperHealthPotion() {
        // Define the name and type of the item.
        super.setName("Medium HP potion");
        super.setType("Recovery");
        super.setPrice(super.getPrice() * 3);
        // Increase the recovery point.
        super.setRecovery_point(super.getRecovery_point() * this.recovery_ratio);
        super.setDescription("☞ Recovers HP by " + super.getRecovery_point() + "%.");
    }

    public int getRecovery_ratio() {
        return recovery_ratio;
    }

    public void setRecovery_ratio(int recovery_ratio) {
        this.recovery_ratio = recovery_ratio;
    }
}
