package com.mudgame.mhk.item.equipment.weapon.blade;

public class Katana extends Blade {

    public Katana() {
        super.setName("Katana");
        super.setExtra_attack_point(3);
        super.setDescription("☞ For " + super.getDurability() + " turns, +" + super.getExtra_attack_point() + " attack point , +" + super.getExtra_critical_rate() + " critical rate.");
        super.setPrice(super.getExtra_attack_point() * 15);
    }
}
