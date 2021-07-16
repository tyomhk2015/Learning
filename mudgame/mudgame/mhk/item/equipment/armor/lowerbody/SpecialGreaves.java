package com.mudgame.mhk.item.equipment.armor.lowerbody;

public class SpecialGreaves extends LowerBody {

    public SpecialGreaves() {
        super.setName("특수 하의");
        super.setExtra_defense_point(9);
        super.setDescription("☞ 사용시 " + super.getDurability() + "턴 동안방어력+" + super.getExtra_defense_point() + " 회피율" + super.getLowered_evasion_point());
        super.setPrice(super.getExtra_defense_point() * 15);
    }
}
