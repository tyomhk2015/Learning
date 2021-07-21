package com.mudgame.mhk.item.equipment.weapon.blunt;

import com.mudgame.mhk.item.equipment.Equipment;

public class Blunt extends Equipment {

    private final int extra_accuracy = 3; // Due to the weight of the blunt weapons, reduce the accuracy.
    private int extra_attack_point; // Additional attack_point for the player's status.
//    private final int attack_range = 1; // The reach of the attack.

    public Blunt() {
        super.setType("Weapon: Blunt");
    }

    public int getExtra_accuracy() {
        return extra_accuracy;
    }
    public int getExtra_attack_point() {
        return extra_attack_point;
    }

    public void setExtra_attack_point(int extra_attack_point) {
        this.extra_attack_point = extra_attack_point;
    }
}
