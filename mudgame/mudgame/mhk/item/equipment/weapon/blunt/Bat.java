package com.mudgame.mhk.item.equipment.weapon.blunt;

public class Bat extends Blunt {

    public Bat() {
        super.setName("Bat");
        super.setExtra_attack_point(5);
        super.setDescription("☞ For " + super.getDurability() + " turns, +" + super.getExtra_attack_point() + " attack point, +" + super.getExtra_accuracy() + " accuracy.");
        super.setPrice(super.getExtra_attack_point() * 15);
    }
}
