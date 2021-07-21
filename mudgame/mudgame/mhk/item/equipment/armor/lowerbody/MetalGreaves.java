package com.mudgame.mhk.item.equipment.armor.lowerbody;

public class MetalGreaves extends LowerBody {

    public MetalGreaves() {
        super.setName("Iron greaves");
        super.setExtra_defense_point(7);
        super.setDescription("☞ For " + super.getDurability() + " turns, +" + super.getExtra_defense_point() + " defense points, -" + super.getLowered_evasion_point() + " flee rate.");
        super.setPrice(super.getExtra_defense_point() * 15);
    }
}
