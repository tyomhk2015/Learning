package com.mudgame.mhk.item.equipment.weapon.gun;

public class Shotgun extends Gun {

    public Shotgun() {
        super.setName("Shotgun");
        super.setExtra_attack_point(10);
        super.setDescription("☞ For  " + super.getDurability() + " turns, +" + super.getExtra_attack_point() + " attack point, -" + super.getExtra_evasion_point() + " flee rate.");
        super.setPrice(super.getExtra_attack_point() * 30);
    }
}
