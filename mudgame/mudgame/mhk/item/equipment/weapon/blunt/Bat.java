package com.mudgame.mhk.item.equipment.weapon.blunt;

public class Bat extends Blunt {

    public Bat() {
        super.setName("야구 배트");
        super.setExtra_attack_point(5);
        super.setDescription("☞ 사용시 " + super.getDurability() + "턴 동안 공격력+" + super.getExtra_attack_point() + " 명중율+" + super.getExtra_accuracy());
        super.setPrice(super.getExtra_attack_point() * 15);
    }
}
