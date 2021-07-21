package com.mudgame.mhk.item.recovery;

public class SuperStaminaPotion extends StaminaPotion {

    private int stamina_recovery_ratio = 2;

    public SuperStaminaPotion() {
        // Define the name and type of the item.
        super.setName("Medium SP potion");
        super.setType("Recovery");
        super.setPrice(super.getPrice() * 3);
        // Increase the recovery point.
        super.setRecovery_point(super.getRecovery_point() * this.stamina_recovery_ratio);
        super.setDescription("☞ Recovers SP by " + super.getRecovery_point() + "%.");
    }

    public int getStamina_recovery_ratio() {
        return stamina_recovery_ratio;
    }

    public void setStamina_recovery_ratio(int stamina_recovery_ratio) {
        this.stamina_recovery_ratio = stamina_recovery_ratio;
    }
}
