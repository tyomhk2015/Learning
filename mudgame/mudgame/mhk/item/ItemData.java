package com.mudgame.mhk.item;

import com.mudgame.mhk.item.equipment.armor.lowerbody.MetalGreaves;
import com.mudgame.mhk.item.equipment.armor.lowerbody.SpecialGreaves;
import com.mudgame.mhk.item.equipment.armor.upperbody.MetalVest;
import com.mudgame.mhk.item.equipment.armor.upperbody.SpecialArmor;
import com.mudgame.mhk.item.equipment.weapon.blade.Katana;
import com.mudgame.mhk.item.equipment.weapon.blade.Rapier;
import com.mudgame.mhk.item.equipment.weapon.blunt.Bat;
import com.mudgame.mhk.item.equipment.weapon.blunt.Pipe;
import com.mudgame.mhk.item.equipment.weapon.gun.Magnum;
import com.mudgame.mhk.item.equipment.weapon.gun.Shotgun;
import com.mudgame.mhk.item.recovery.*;

import java.util.LinkedHashMap;

public class ItemData {

    private final LinkedHashMap<String, Item> item_data = new LinkedHashMap<>();

    public ItemData() {
        // Store all the potions in the item database.
        item_data.put(new HealthPotion().getName(), new HealthPotion());
        item_data.put(new SuperHealthPotion().getName(), new SuperHealthPotion());
        item_data.put(new HyperHealthPotion().getName(), new HyperHealthPotion());
        item_data.put(new StaminaPotion().getName(), new StaminaPotion());
        item_data.put(new SuperStaminaPotion().getName(), new SuperStaminaPotion());
        item_data.put(new HyperStaminaPotion().getName(), new HyperStaminaPotion());
        item_data.put(new MixPotion().getName(), new MixPotion());
        item_data.put(new SuperMixPotion().getName(), new SuperMixPotion());
        item_data.put(new HyperMixPotion().getName(), new HyperMixPotion());

        // Store armors the equipments in the item database.
        item_data.put(new MetalVest().getName(), new MetalVest());
        item_data.put(new SpecialArmor().getName(), new SpecialArmor());
        item_data.put(new MetalGreaves().getName(), new MetalGreaves());
        item_data.put(new SpecialGreaves().getName(), new SpecialGreaves());

        // Store armors the equipments in the item database.
        item_data.put(new Katana().getName(), new Katana());
        item_data.put(new Rapier().getName(), new Rapier());
        item_data.put(new Pipe().getName(), new Pipe());
        item_data.put(new Bat().getName(), new Bat());
        item_data.put(new Magnum().getName(), new Magnum());
        item_data.put(new Shotgun().getName(), new Shotgun());
    }

    public LinkedHashMap<String, Item> getItem_data() {
        return item_data;
    }
}
