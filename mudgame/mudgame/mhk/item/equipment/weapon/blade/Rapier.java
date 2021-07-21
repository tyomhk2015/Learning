package com.mudgame.mhk.item.equipment.weapon.blade;

public class Rapier extends Blade {

    public Rapier() {
        super.setName("Rapier");
        super.setExtra_attack_point(2);
        super.setDescription("☞ For " + super.getDurability() + " turns, +" + super.getExtra_attack_point() + " attack point , +" + super.getExtra_critical_rate() + " critical rate.");
        super.setPrice(super.getExtra_attack_point() * 15);
    }
}
