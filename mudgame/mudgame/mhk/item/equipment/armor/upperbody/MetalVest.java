package com.mudgame.mhk.item.equipment.armor.upperbody;

public class MetalVest extends UpperBody {

    public MetalVest() {
        super.setName("철제 갑옷");
        super.setExtra_defense_point(8);
        super.setDescription("☞ 사용시 " + super.getDurability() + "턴 동안 방어력+" + super.getExtra_defense_point() + " 회피율" + super.getLowered_evasion_point());
        super.setPrice(super.getExtra_defense_point() * 15);
    }
}
