package com.mudgame.mhk.item.equipment.armor.lowerbody;

import com.mudgame.mhk.item.equipment.Equipment;

public class LowerBody extends Equipment {

    private final int lowered_evasion_point = -10; // Due to the weight of the armors, evasion point get decreased.
    private int extra_defense_point; // Extra defense_point while wearing MetalGreaves.

    public LowerBody() {
        super.setType("Armor: Lower-body");
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
