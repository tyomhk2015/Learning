package com.mudgame.mhk.item.recovery;

import com.mudgame.mhk.item.Item;

public class HealthPotion extends Potion {

    public HealthPotion() {
        // Define the name and type of the item.
        super.setName("HP포션小");
        super.setType("회복");
        super.setPrice(5);
        super.setDescription("☞ 전체 HP의 " + super.getRecovery_point() + "%를 회복합니다.");
    }

}
