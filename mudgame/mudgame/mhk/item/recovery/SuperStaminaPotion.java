package com.mudgame.mhk.item.recovery;

public class SuperStaminaPotion extends StaminaPotion {

    private int stamina_recovery_ratio = 2;

    public SuperStaminaPotion() {
        // Define the name and type of the item.
        super.setName("SP포션中");
        super.setType("회복");
        super.setPrice(super.getPrice() * 3);
        // Increase the recovery point.
        super.setRecovery_point(super.getRecovery_point() * this.stamina_recovery_ratio);
        super.setDescription("☞ 전체 SP의 " + super.getRecovery_point() + "%를 회복합니다.");
    }

    public int getStamina_recovery_ratio() {
        return stamina_recovery_ratio;
    }

    public void setStamina_recovery_ratio(int stamina_recovery_ratio) {
        this.stamina_recovery_ratio = stamina_recovery_ratio;
    }
}
