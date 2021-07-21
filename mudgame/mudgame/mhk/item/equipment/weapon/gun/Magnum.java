package com.mudgame.mhk.item.equipment.weapon.gun;

public class Magnum extends Gun {

    public Magnum() {
        super.setName("Magnum revolver");
        super.setExtra_attack_point(8);
        super.setDescription("☞ For " + super.getDurability() + " turns, +" + super.getExtra_attack_point() + " attack points, -" + super.getExtra_evasion_point() + "flee rate,");
        super.setPrice(super.getExtra_attack_point() * 30);
    }
}
