package com.mudgame.mhk.person.brawler;

import com.mudgame.mhk.item.equipment.Equipment;
import com.mudgame.mhk.person.Person;

import java.util.Arrays;
import java.util.HashMap;

public class Brawler extends Person {

    /**
     * (int)
     * level : The level of the player. Used for modifying player's status and deciding the battle phase.
     * health_point, current_health_point : When the point reaches 0 or below, the game is over.
     * stamina_point, current_stamina_point : Used for using skills. Does not go below 0.
     * attack_point : The amount of damage the player can inflict on enemies.
     * defense_point : Reduces the receiving damage point.
     * accuracy : The success rate of hitting an enemy.
     * evasion : The success rate of evading an enemy's attack.
     * critical_rate : The rate of inflicting extra damage on an enemy.
     * critical_ratio : The amount of inflicting damage increment when critical rate is a success.
     * buff_point : The number of turns that buff can persist.
     * attack_range : The reach of attacking range.
     * move_distance : The number of tiles the character can move.
     *
     * (int [])
     * current_pos : Store current position of the fighters. [0] = row, [1] = column.
     * checking_pos : Check for any obstacles at the new position before moving. [0] = row, [1] = column.
     * latest_pos : The information of the latest location of the character. [0] = row, [1] = column.
     *
     * (String)
     * equipped_weapon : Store the name of the weapon the character is holding. Used for updating the 'attack_point'.
     *
     * (HashMap<Integer, Character>)
     * @key : Threshold depending on the remaining health_point.
     * @value: Visual effect to be displayed.
     * Stores visual effect for displaying condition of the entity, depending on the remaining health_point.
     */
    private int level, health_point, current_health_point, stamina_point, current_stamina_point, attack_point, defense_point, accuracy, evasion, critical_rate, critical_ratio, buff_point, attack_range, move_distance;
    private int [] current_pos = new int[2];
    private int [] checking_pos = new int[2];
    private int [] latest_pos = new int[2];
    private Equipment equipped_weapon;
    private HashMap<Integer, String> status_icon = new HashMap<Integer, String>(); // Store icon that represents the condition of the entity.

    public Brawler() {
        // Set range for attack_range.
        this.attack_range = 1;
    }

    public Brawler(String name) {
        // Parameterized constructor
        super(name);
        // Set range for attack_range.
        this.attack_range = 1;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth_point() {
        return health_point;
    }

    public void setHealth_point(int health_point) {
        this.health_point = health_point;
    }

    public int getCurrent_health_point() {
        synchronized (this){
            return current_health_point;
        }
    }

    public void setCurrent_health_point(int current_health_point) {
        synchronized (this) {
            this.current_health_point = current_health_point;
            if (this.current_health_point < 0) {
                this.current_health_point = 0;
            } else if (this.current_health_point > this.health_point) {
                this.current_health_point = this.health_point;
            }
        }
    }

    public int getStamina_point() {
        return stamina_point;
    }

    public void setStamina_point(int stamina_point) {
        this.stamina_point = stamina_point;
        if(this.stamina_point < 0) {
            this.stamina_point = 0;
        } else if (this.stamina_point > 20) {
            this.stamina_point = 20;
        }
    }

    public int getCurrent_stamina_point() {
        return current_stamina_point;
    }

    public void setCurrent_stamina_point(int current_stamina_point) {
        this.current_stamina_point = current_stamina_point;
        if(this.current_stamina_point < 0) {
            this.current_stamina_point = 0;
        } else if (this.current_stamina_point > 99) {
            this.current_stamina_point = 99;
        }
    }

    public int getAttack_point() {
        return attack_point;
    }

    public void setAttack_point(int attack_point) {
        this.attack_point = attack_point;
    }

    public int getDefense_point() {
        return defense_point;
    }

    public void setDefense_point(int defense_point) {
        this.defense_point = defense_point;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getEvasion() {
        return evasion;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    public int getCritical_rate() {
        return critical_rate;
    }

    public void setCritical_rate(int critical_rate) {
        this.critical_rate = critical_rate;
    }

    public int getCritical_ratio() {
        return critical_ratio;
    }

    public void setCritical_ratio(int critical_ratio) {
        this.critical_ratio = critical_ratio;
    }

    public int getBuff_point() {
        return buff_point;
    }

    public void setBuff_point(int buff_point) {
        this.buff_point = buff_point;
        if(this.buff_point < 0) {
            this.buff_point = -1;
        }
    }

    public int getMove_distance() {
        return move_distance;
    }

    public void setMove_distance(int move_distance) {
        this.move_distance = move_distance;
    }

    public int[] getCurrent_pos() {
        return this.current_pos;
    }

    public int[] getChecking_pos() {
        return checking_pos;
    }

    public void setChecking_pos(int[] checking_pos) {
        this.checking_pos = checking_pos;
    }

    public int[] getLatest_pos() {
        return latest_pos;
    }

    public void setLatest_pos(int[] latest_pos) {
        this.latest_pos = latest_pos;
    }

    public void setCurrent_pos(int[] current_pos) {
        this.current_pos = current_pos;
    }

    public int getAttack_range() {
        return attack_range;
    }

    public void setAttack_range(int attack_range) {
        this.attack_range = attack_range;
    }

    public Equipment getEquipped_weapon() {
        return equipped_weapon;
    }

    public void setEquipped_weapon(Equipment equipped_weapon) {
        this.equipped_weapon = equipped_weapon;
    }

    public HashMap<Integer, String> getStatus_icon() {
        return status_icon;
    }

    /**
     * Get the icon that corresponds to the remaining health_point.
     * @return String that represents the character's condition.
     */
    public String getIcon() {
        int remaining_health = (int)((this.getCurrent_health_point() / (this.getHealth_point() + 0.0)) * 100);
        String icon = "";
        if (this.getCurrent_health_point() <= 0) {
            icon =  this.status_icon.get(remaining_health);
        } else if (remaining_health >= 0 && remaining_health <= 50) {
            icon =  this.status_icon.get(50);
        } else {
            icon =  this.status_icon.get(51);
        }
        return icon;
    }
}