package com.mudgame.mhk.item.equipment.armor.lowerbody;

public class SpecialGreaves extends LowerBody {

    public SpecialGreaves() {
        super.setName("Special greaves");
        super.setExtra_defense_point(9);
        super.setDescription("☞ For " + super.getDurability() + " turns, +" + super.getExtra_defense_point() + " defense points, -" + super.getLowered_evasion_point() + "flee rate.");
        super.setPrice(super.getExtra_defense_point() * 15);
    }
}
