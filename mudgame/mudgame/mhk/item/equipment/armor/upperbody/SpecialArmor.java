package com.mudgame.mhk.item.equipment.armor.upperbody;

public class SpecialArmor extends UpperBody{

    public SpecialArmor() {
        super.setName("Special armor");
        super.setExtra_defense_point(10);
        super.setDescription("☞ For " + super.getDurability() + " turns, +" + super.getExtra_defense_point()  + " defense points, -" + super.getLowered_evasion_point() + " flee rate.");
        super.setPrice(super.getExtra_defense_point() * 15);
    }
}
