package com.mudgame.mhk.item.equipment.weapon.blunt;

public class Pipe extends Blunt {

    public Pipe() {
        super.setName("Iron pipe");
        super.setExtra_attack_point(4);
        super.setDescription("☞ For " + super.getDurability() + " turns, +" + super.getExtra_attack_point() + " attack points, +" + super.getExtra_accuracy() + " accuracy.");
        super.setPrice(super.getExtra_attack_point() * 15);
    }
}
