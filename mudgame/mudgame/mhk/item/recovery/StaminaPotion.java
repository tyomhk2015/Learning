package com.mudgame.mhk.item.recovery;

import com.mudgame.mhk.item.Item;

public class StaminaPotion extends Potion {

    public StaminaPotion() {
        // Define the name and type of the item.
        super.setName("SP포션小");
        super.setType("회복");
        super.setPrice(5);
        super.setDescription("☞ 전체 SP의 " + super.getRecovery_point() + "%를 회복합니다.");
    }
}
