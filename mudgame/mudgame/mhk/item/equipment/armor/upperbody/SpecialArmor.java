package com.mudgame.mhk.item.equipment.armor.upperbody;

public class SpecialArmor extends UpperBody{

    public SpecialArmor() {
        super.setName("특수 상의");
        super.setExtra_defense_point(10);
        super.setDescription("☞ 사용시 " + super.getDurability() + "턴 동안 방어력+" + super.getExtra_defense_point()  + " 회피율" + super.getLowered_evasion_point());
        super.setPrice(super.getExtra_defense_point() * 15);
    }
}
