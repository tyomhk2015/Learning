package com.mudgame.mhk.item.equipment.armor.upperbody;

import com.mudgame.mhk.item.equipment.Equipment;

public class UpperBody extends Equipment {

    private final int lowered_evasion_point = -5; // Due to the weight of the armors, evasion point get decreased.
    private int extra_defense_point; // Extra defense_point while wearing MetalVest.

    public UpperBody() {
        super.setType("Armor: Upper-body");

    }

    public int getLowered_evasion_point() {
        return lowered_evasion_point;
    }
    public int getExtra_defense_point() {
        return extra_defense_point;
    }

    public void setExtra_defense_point(int extra_defense_point) {
        this.extra_defense_point = extra_defense_point;
    }
}
