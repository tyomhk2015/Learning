package com.mudgame.mhk.item.recovery;

public class HyperMixPotion extends SuperMixPotion {

    private int mix_recovery_ratio = 2;

    public HyperMixPotion() {
        // Define the name and type of the item.
        super.setName("혼합포션大");
        super.setType("회복");
        super.setPrice(super.getPrice() * 3 / 2);

        // Increase the recovery point.
        super.setMix_recovery_ratio(3);
        super.setRecovery_point(super.getRecovery_point() * super.getMix_recovery_ratio() / 2);
        super.setDescription("☞ 전체 HP와 SP를 " + super.getRecovery_point() + "% 만큼 회복합니다.");
    }
}
