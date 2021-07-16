package com.mudgame.mhk.item.recovery;

public class HyperStaminaPotion extends SuperStaminaPotion {

    public HyperStaminaPotion() {
        // Define the name and type of the item.
        super.setName("SP포션大");
        super.setType("회복");
        super.setPrice(super.getPrice() * 3 / 2);

        // Increase the recovery point.
        super.setStamina_recovery_ratio(3);
        super.setRecovery_point(super.getRecovery_point() * super.getStamina_recovery_ratio() / 2);
        super.setDescription("☞ 전체 SP의 " + super.getRecovery_point() + "%를 회복합니다.");
    }
}
