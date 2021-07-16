package com.mudgame.mhk.item.recovery;

public class MixPotion extends Potion{

    public MixPotion() {
        // Define the name and type of the item.
        super.setName("혼합포션小");
        super.setType("회복");
        super.setPrice(10);
        super.setDescription("☞ 전체 HP와 SP를 " + super.getRecovery_point() + "% 만큼 회복합니다.");
    }
}
