package com.mudgame.mhk.item.equipment.weapon.blade;

import com.mudgame.mhk.item.equipment.Equipment;

public class Blade extends Equipment {

    private final int extra_critical_rate = 3; // Extra critical rate when blade weapons are equipped.
    private int extra_attack_point; // Additional attack_point for the player's status.
//    private final int attack_range = 1; // The reach of the attack.

    public Blade() {
        super.setType("Weapon: Blade");
    }

    public int getExtra_critical_rate() {
        return extra_critical_rate;
    }

    public int getExtra_attack_point() {
        return extra_attack_point;
    }

    public void setExtra_attack_point(int extra_attack_point) {
        this.extra_attack_point = extra_attack_point;
    }
}
