package com.mudgame.mhk.item.recovery;

public class HyperHealthPotion extends SuperHealthPotion {

    public HyperHealthPotion() {
        // Define the name and type of the item.
        super.setName("HP포션大");
        super.setType("회복");
        super.setPrice(super.getPrice() * 3 / 2);
        // Increase the recovery point.
        super.setRecovery_ratio(3);
        super.setRecovery_point(super.getRecovery_point() * super.getRecovery_ratio() / 2);
        super.setDescription("☞ 전체 HP의 " + super.getRecovery_point() + "%를 회복합니다.");
    }
}
