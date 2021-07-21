package com.mudgame.mhk.item.equipment.armor.upperbody;

public class MetalVest extends UpperBody {

    public MetalVest() {
        super.setName("Iron armor");
        super.setExtra_defense_point(8);
        super.setDescription("☞ For  " + super.getDurability() + " turns, +" + super.getExtra_defense_point() + " defense points, -" + super.getLowered_evasion_point() + " flee rate.");
        super.setPrice(super.getExtra_defense_point() * 15);
    }
}
