package com.mudgame.mhk.item.equipment.weapon.gun;

public class Shotgun extends Gun {

    public Shotgun() {
        super.setName("샷건");
        super.setExtra_attack_point(10);
        super.setDescription("☞ 사용시 " + super.getDurability() + "턴 동안 공격력+" + super.getExtra_attack_point() + " 회피율+" + super.getExtra_evasion_point());
        super.setPrice(super.getExtra_attack_point() * 30);
    }
}
