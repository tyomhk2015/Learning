package com.mudgame.mhk.item.equipment.weapon.blade;

public class Katana extends Blade {

    public Katana() {
        super.setName("일본도");
        super.setExtra_attack_point(3);
        super.setDescription("☞ 사용시 " + super.getDurability() + "턴 동안 공격력+" + super.getExtra_attack_point() + " 크리티컬 히트 확률+" + super.getExtra_critical_rate());
        super.setPrice(super.getExtra_attack_point() * 15);
    }
}
