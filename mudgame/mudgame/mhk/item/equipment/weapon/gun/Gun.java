package com.mudgame.mhk.item.equipment.weapon.gun;

import com.mudgame.mhk.item.equipment.Equipment;

public class Gun extends Equipment {

    private final int extra_evasion_point = 5; // Due to the size of the weapon, additional attack_point for the player's status.
    private int extra_attack_point; // Additional attack_point for the player's status.
//    private final int attack_range = 3; // The reach of the attack.

    public Gun() {
        super.setType("Weapon: Gun");
    }

    public int getExtra_evasion_point() {
        return extra_evasion_point;
    }

    public int getExtra_attack_point() {
        return extra_attack_point;
    }

    public void setExtra_attack_point(int extra_attack_point) {
        this.extra_attack_point = extra_attack_point;
    }
}
