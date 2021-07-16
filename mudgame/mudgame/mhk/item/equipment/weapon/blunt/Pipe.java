package com.mudgame.mhk.item.equipment.weapon.blunt;

public class Pipe extends Blunt {

    public Pipe() {
        super.setName("쇠 파이프");
        super.setExtra_attack_point(4);
        super.setDescription("☞ 사용시 " + super.getDurability() + "턴 동안 공격력+" + super.getExtra_attack_point() + " 명중율+" + super.getExtra_accuracy());
        super.setPrice(super.getExtra_attack_point() * 15);
    }
}
