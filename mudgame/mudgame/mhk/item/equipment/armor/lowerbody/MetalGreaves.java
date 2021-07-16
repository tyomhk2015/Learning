package com.mudgame.mhk.item.equipment.armor.lowerbody;

public class MetalGreaves extends LowerBody {

    public MetalGreaves() {
        super.setName("철제 하의");
        super.setExtra_defense_point(7);
        super.setDescription("☞ 사용시 " + super.getDurability() + "턴 동안 방어력+" + super.getExtra_defense_point() + " 회피율" + super.getLowered_evasion_point());
        super.setPrice(super.getExtra_defense_point() * 15);
    }
}
