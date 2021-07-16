package com.mudgame.mhk.item.recovery;

import com.mudgame.mhk.item.Item;

public class SuperHealthPotion extends HealthPotion {

    private int recovery_ratio = 2;

    public SuperHealthPotion() {
        // Define the name and type of the item.
        super.setName("HP포션中");
        super.setType("회복");
        super.setPrice(super.getPrice() * 3);
        // Increase the recovery point.
        super.setRecovery_point(super.getRecovery_point() * this.recovery_ratio);
        super.setDescription("☞ 전체 HP의 " + super.getRecovery_point() + "%를 회복합니다.");
    }

    public int getRecovery_ratio() {
        return recovery_ratio;
    }

    public void setRecovery_ratio(int recovery_ratio) {
        this.recovery_ratio = recovery_ratio;
    }
}
