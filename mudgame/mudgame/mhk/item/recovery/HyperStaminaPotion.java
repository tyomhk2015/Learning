package com.mudgame.mhk.item.recovery;

public class HyperStaminaPotion extends SuperStaminaPotion {

    public HyperStaminaPotion() {
        // Define the name and type of the item.
        super.setName("Large SP potion.");
        super.setType("Recovery");
        super.setPrice(super.getPrice() * 3 / 2);

        // Increase the recovery point.
        super.setStamina_recovery_ratio(3);
        super.setRecovery_point(super.getRecovery_point() * super.getStamina_recovery_ratio() / 2);
        super.setDescription("☞ Recovers SP by " + super.getRecovery_point() + "%.");
    }
}
