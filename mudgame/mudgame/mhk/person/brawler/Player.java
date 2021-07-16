package com.mudgame.mhk.person.brawler;

import com.mudgame.mhk.item.Item;
import com.mudgame.mhk.item.equipment.Equipment;
import com.mudgame.mhk.item.equipment.armor.lowerbody.LowerBody;
import com.mudgame.mhk.item.equipment.armor.upperbody.UpperBody;
import com.mudgame.mhk.item.equipment.weapon.blade.Blade;
import com.mudgame.mhk.item.equipment.weapon.blade.Katana;
import com.mudgame.mhk.item.equipment.weapon.blade.Rapier;
import com.mudgame.mhk.item.equipment.weapon.blunt.Bat;
import com.mudgame.mhk.item.equipment.weapon.blunt.Blunt;
import com.mudgame.mhk.item.equipment.weapon.blunt.Pipe;
import com.mudgame.mhk.item.equipment.weapon.gun.Gun;
import com.mudgame.mhk.item.recovery.*;
import com.mudgame.mhk.person.brawler.enemy.Enemy;
import com.mudgame.mhk.person.brawler.enemy.EnemyBoss;
import com.mudgame.mhk.skilldata.SkillData;
import com.mudgame.mhk.stage.StageMap;
import com.mudgame.mhk.story.Story;
import com.mudgame.mhk.userinterface.BattleInterface;
import com.mudgame.mhk.userinterface.UserInterface;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Player extends Brawler implements GodHand, Runnable {

    private Item [] inventory = new Item [10]; // A number of item that can be possessed.
    private Equipment [] equipped_armor = new Equipment[2]; // Store the names of the protective gears the character is wearing. Used for updating defense_point. [0] Upper Armor, [1] Lower Armor
    private int money = 0; // Currency used for buying items. Japan Yen will be displayed.
    private int required_experience = 250; // Required experience for leveling up.
    private int accumulated_experience = 250; // The experience point earned so far.
    private boolean awakened = false; // Flag for whether the player has awaken his/her inner power.
    private HashMap<String, Float> skills; // All the available skills is stored.
    private int before_buff_attack_point = 0; // Store the attack power before the buff is applied.
    private int weapon_extra_attack = 0; // TODO
    private int weapon_critical_buff = 0;
    private int weapon_accuracy_buff = 0;
    private int weapon_evasion_buff = 0;
    private int armor_debuff_stat = 0;
    private int armor_extra_defense = 0;
    private static AtomicBoolean thread_flag = new AtomicBoolean(true); // TODO
    private Thread thread;
    private Runnable runnable;
    private static EnemyBoss boss;
    private static Enemy [] enemies;

    // Related to showing msg
    private String usedItemEffect = "";
    private String attackStatus = "";
    private String levelUpStatus = "";
    private String usedSkillStatus = "";
    private String obtainedItemStatus = "";

    public Player(boolean debugMode) {
        super.setName("DEBUG_MODE");

        // Store icon that reflects the condition of the entity.
        super.getStatus_icon().put(0, UserInterface.ANSI_YELLOW + "\uD83D\uDC80" + UserInterface.ANSI_RESET);
        if(this.isAwakened()) {
            super.getStatus_icon().put(50, UserInterface.ANSI_YELLOW_BG + "♡" + UserInterface.ANSI_RESET);
            super.getStatus_icon().put(51, UserInterface.ANSI_YELLOW_BG + "♥" + UserInterface.ANSI_RESET);
        } else {
            super.getStatus_icon().put(50, UserInterface.ANSI_YELLOW + "♡" + UserInterface.ANSI_RESET);
            super.getStatus_icon().put(51, UserInterface.ANSI_YELLOW + "♥" + UserInterface.ANSI_RESET);
        }

        // Set a position for the player.
        int [] current_pos = {StageMap.rows / 2, 2};
        super.setCurrent_pos(current_pos);

        // Set status of the player.
        this.setStatus(debugMode);

        // Give items.
        this.inventory[0] = new HealthPotion();
        this.inventory[1] = new HealthPotion();
        this.inventory[2] = new Pipe();

        this.runnable = this;
        this.thread = new Thread(this.runnable);
        this.thread.setPriority(9);
    }

    public Player(String name, boolean debugMode) {
        super(name);

        // Store icon that reflects the condition of the entity.
        super.getStatus_icon().put(0, UserInterface.ANSI_YELLOW + "\uD83D\uDC80" + UserInterface.ANSI_RESET);
        if(this.isAwakened()) {
            super.getStatus_icon().put(50, UserInterface.ANSI_YELLOW_BG + "♡" + UserInterface.ANSI_RESET);
            super.getStatus_icon().put(51, UserInterface.ANSI_YELLOW_BG + "♥" + UserInterface.ANSI_RESET);
        } else {
            super.getStatus_icon().put(50, UserInterface.ANSI_YELLOW + "♡" + UserInterface.ANSI_RESET);
            super.getStatus_icon().put(51, UserInterface.ANSI_YELLOW + "♥" + UserInterface.ANSI_RESET);
        }


        // Set a position for the player.
        int [] current_pos = {StageMap.rows / 2, 2};
        super.setCurrent_pos(current_pos);

        // Set status of the player.
        this.setStatus(debugMode);

        // Set money
        this.setMoney(10000);

        // Give a couple of potions.
        this.inventory[0] = new HyperHealthPotion();
        this.inventory[1] = new HyperStaminaPotion();
        this.inventory[2] = new HyperMixPotion();

        this.runnable = this;
        this.thread = new Thread(this.runnable);
        this.thread.setPriority(9);
    }

    /**
     * Set all the status for the player.
     */
    public void setStatus(boolean debugMode) {
        if(debugMode) {
            // If the game is in debug mode.
            super.setLevel(200);
            this.setMoney(10000);
        } else {
            // If the game is normal play.
            super.setLevel(1);
        }
        super.setHealth_point(super.getLevel() * 10);
        super.setCurrent_health_point(super.getLevel() * 10);
        super.setStamina_point(super.getLevel() * 2);
        super.setCurrent_stamina_point(super.getStamina_point());
        super.setAttack_point(super.getLevel() * 5);
        super.setDefense_point(super.getLevel() * 2);
        super.setAccuracy(80);
        super.setEvasion(20);
        super.setCritical_rate(15);
        super.setCritical_ratio(2);
        super.setBuff_point(-1);
        super.setAttack_range(1);
        super.setMove_distance((super.getLevel() / 20) + 7);
        super.setEquipped_weapon(null);
        this.setBefore_buff_attack_point(super.getAttack_point());

        if(super.getStamina_point() > 20) {
            // For the game balance, limit the stamina point to 20.
            super.setStamina_point(20);
            super.setCurrent_stamina_point(20);
        }

        if(this.isAwakened()){
            // If the player has cleared the game once, unlock the skills.
            this.getSkill();
        }
    }

    /**
     * Raise all status and recover all the health point and stamina point.
     */
    public void updateStatus() {
        super.setHealth_point(super.getLevel() * 10);
        super.setCurrent_health_point(super.getHealth_point());
        super.setStamina_point(super.getLevel() * 2);
        super.setCurrent_stamina_point(super.getStamina_point());
        super.setAttack_point(super.getLevel() * 5);
        super.setDefense_point(super.getLevel() * 2);
        super.setMove_distance((super.getLevel() / 10) + 7);

        if(super.getStamina_point() > 20) {
            // For the game balance, limit the stamina point to 20.
            super.setStamina_point(20);
            super.setCurrent_stamina_point(20);
        }
    }

    /**
     * Make the player move to the given direction.
     * @param direction : A sequence of chars', giving the guidance.
     */
    public void checkMovedPos(String direction) {

        // Receive input within the moving distance range.
        if(direction.length() < super.getMove_distance()) {
            direction = direction.substring(0, direction.length());
        } else {
            direction = direction.substring(0, super.getMove_distance());
        }

        int [] current_pos = super.getCurrent_pos().clone();
        for(int index = 0; index < direction.length(); index++) {
            if(direction.charAt(index) == 'a') {
                // Move left.
                current_pos[1] -= 1;
                if(current_pos[1] < StageMap.movable_cols[0]) {
                    // If the new position went beyond the left-end of the map,
                    // overwrite with the column value with the minimum movable cols.
                    current_pos[1] = StageMap.movable_cols[0];
                }
            } else if (direction.charAt(index) == 's') {
                // Move down.
                current_pos[0] += 1;
                if(current_pos[0] > StageMap.movable_rows[1]) {
                    // If the new position went beyond the lowest row of the map,
                    // overwrite with the row value with the maximum movable rows.
                    current_pos[0] = StageMap.movable_rows[1];
                }
            } else if (direction.charAt(index) == 'd') {
                // Move right.
                current_pos[1] += 1;
                if(current_pos[1] > StageMap.movable_cols[1]) {
                    // If the new position went beyond the right-end of the map,
                    // overwrite with the column value with the maximum movable cols.
                    current_pos[1] = StageMap.movable_cols[1];
                }
            } else if (direction.charAt(index) == 'w'){
                // Move up.
                current_pos[0] -= 1;
                if(current_pos[0] < StageMap.movable_rows[0]) {
                    // If the new position went beyond the top-limit of the map,
                    // overwrite with the row value with the minimum movable rows.
                    current_pos[0] = StageMap.movable_rows[0];
                }
            }
        }
        super.setChecking_pos(current_pos);
    }

    /**
     * Attack the enemy.
     * @param target_pos : The position where the targeted enemy is.
     */
    public void attackEnemy(int [] target_pos) {
        Random random = new Random(); // For critical and evasion.
        int damage; // The amount of damage inflicted to enemy.
        int critical_hit; //  If the random number is in the range of player's critical hit value, return the player's critical damage ratio.
        boolean missed; // True, if the attack is missed.
        this.attackStatus = "";

        this.applyEquipmentStat(); // Apply the equipment's bonus stats.

        // Attack boss if the target position is correct.
        if(this.boss.getCurrent_pos()[0] == target_pos[0] && this.boss.getCurrent_pos()[1] == target_pos[1]) {
            this.obtainedItemStatus = "";
            missed = random.nextInt(100) > (super.getAccuracy() + this.weapon_accuracy_buff - this.boss.getEvasion()) ? true : false; // True, when the hit is missed.
            critical_hit = random.nextInt(100) < super.getCritical_rate() + this.weapon_critical_buff ? super.getCritical_ratio() : 1;  // Store player's critical damage ratio if true.
            damage = missed ? 0 : ((super.getAttack_point() + this.weapon_extra_attack) * critical_hit) - this.boss.getDefense_point(); // If the hit is not missed, inflict the damage to the enemy.

            if(damage < 0) {
                // When damage is lesser than 0, set it to 0.
                damage = 0;
            }

            if (missed) {
                this.attackStatus += "\nSYSTEM : " + UserInterface.ANSI_YELLOW + super.getName() + UserInterface.ANSI_RESET + "의 공격이 빗나갔다."; // The attack missed.
            } else {
                if(critical_hit == super.getCritical_ratio()) {
                    this.attackStatus += "\nSYSTEM : " + UserInterface.ANSI_YELLOW  + "크리티컬 히트!! " + UserInterface.ANSI_RESET; // Show that critical hit has been inflicted.
                }
                this.attackStatus += "\nSYSTEM : " + UserInterface.ANSI_RED + this.boss.getName() + UserInterface.ANSI_RESET + "에게 " + UserInterface.ANSI_YELLOW + damage + UserInterface.ANSI_RESET + "데미지를 입혔다."; // The attack hit.
                this.boss.setCurrent_health_point(this.boss.getCurrent_health_point() - damage); // Update the HP of the boss
                StageMap.updateStageMap(boss);

                if(this.boss.getCurrent_health_point() == 0) {
                    this.obtainedItemStatus = ""; // Reset the dropped item message.
                    //IF THE ENEMY"S BEEN DOWN, GIVE EXP TO THE PLAYER
                    this.attackStatus += "\nSYSTEM : " + UserInterface.ANSI_RED + this.boss.getName() + UserInterface.ANSI_RESET + "를 쓰러트렸습니다."; // The attack missed.
                    this.attackStatus += "\nSYSTEM : " + this.boss.getExperience_point() + " 경험치를 얻었습니다."; // The attack missed.
                    this.setAccumulated_experience(this.getAccumulated_experience() + this.boss.getExperience_point()); // Accumulated the experience point.
                    this.attackStatus += "\nSYSTEM : " + this.boss.getPossessing_money() + "￥을 얻었습니다."; // The attack missed.
                    this.setMoney(this.getMoney() + this.boss.getPossessing_money());

                    // Check if the level can be increased.
                    if(this.getLevel() < 100)   {
                        this.checkLevelUp();
                    }

                    if(boss.getPossessing_item() != null) {
                    // Get dropped item from the boss.
                    Item dropped_item = this.boss.getPossessing_item();

                    // If the player is not awakened yet, change the stamina potion to a health potion.
                    if(!this.isAwakened()){
                        if(dropped_item instanceof StaminaPotion || dropped_item instanceof MixPotion){
                            dropped_item = new HealthPotion();
                        } else if (dropped_item instanceof SuperStaminaPotion || dropped_item instanceof SuperMixPotion) {
                            dropped_item = new SuperHealthPotion();
                        } else if (dropped_item instanceof HyperStaminaPotion || dropped_item instanceof HyperMixPotion) {
                            dropped_item = new HyperHealthPotion();
                        }
                    }


                        this.getItemFromEnemy(dropped_item);
                        boss.nullifyPossessingItem();
                    }
                }
            }
        } else {
            // Attack enemy if the target position is correct.
            for (int index = 0; index < this.enemies.length; index++){
                this.obtainedItemStatus = "";
                // Find the targeted enemy and attack.
                if(this.enemies[index].getCurrent_pos()[0] == target_pos[0] && this.enemies[index].getCurrent_pos()[1] == target_pos[1]){
                    if(this.enemies[index].getCurrent_health_point() == 0) {
                        // Stop this process when the enemy don't have any leftover HP.
                        this.attackStatus += "\nSYSTEM : " + UserInterface.ANSI_RED + this.enemies[index].getName() + UserInterface.ANSI_RESET + "는 싸늘한 주검으로 되어있습니다."; // The attack missed.
                        break;
                    }

                    missed = random.nextInt(100) > (super.getAccuracy() + this.weapon_accuracy_buff - this.enemies[index].getEvasion()) ? true : false; // True, when the hit is missed.
                    critical_hit = random.nextInt(100) < super.getCritical_rate() + this.weapon_critical_buff ? super.getCritical_ratio() : 1;  // Store player's critical damage ratio if true.
                    damage = missed ? 0 : ((super.getAttack_point() + this.weapon_extra_attack) * critical_hit) - this.enemies[index].getDefense_point(); // If the hit is not missed, inflict the damage to the enemy.

                    if(damage < 0) {
                        // When damage is lesser than 0, set it to 0.
                        damage = 0;
                    }

                    if (missed) {
                        this.attackStatus += "\nSYSTEM : " + UserInterface.ANSI_YELLOW + super.getName() + UserInterface.ANSI_RESET + "의 공격이 빗나갔다."; // The attack missed.
                    } else {
                        if(critical_hit == super.getCritical_ratio()) {
                            this.attackStatus += "\nSYSTEM : " + UserInterface.ANSI_YELLOW  + "크리티컬 히트!! " + UserInterface.ANSI_RESET; // Show that critical hit has been inflicted.
                        }
                        this.attackStatus += "\nSYSTEM : " + UserInterface.ANSI_RED + this.enemies[index].getName() + UserInterface.ANSI_RESET + "에게 " + UserInterface.ANSI_YELLOW + damage + UserInterface.ANSI_RESET + "데미지를 입혔다."; // The attack hit.
                        this.enemies[index].setCurrent_health_point(this.enemies[index].getCurrent_health_point() - damage); // Update the HP of the enemy.
                        StageMap.updateStageMap(enemies[index]);
                    }

                    if(enemies[index].getCurrent_health_point() == 0) {
                        //IF THE ENEMY"S BEEN DOWN, GIVE EXP TO THE PLAYER
                        this.attackStatus += "\nSYSTEM : " + UserInterface.ANSI_RED + this.enemies[index].getName() + UserInterface.ANSI_RESET + "를 쓰러트렸습니다."; // The attack missed.
                        this.attackStatus += "\nSYSTEM : " + this.enemies[index].getExperience_point() + "경험치를 얻었습니다."; // The attack missed.
                        this.setAccumulated_experience(this.getAccumulated_experience() + this.enemies[index].getExperience_point()); // Accumulated the experience point.
                        this.attackStatus += "\nSYSTEM : " + this.enemies[index].getPossessing_money() + "￥을 얻었습니다."; // The attack missed.
                        this.setMoney(this.getMoney() + this.enemies[index].getPossessing_money());

                        // Check if the level can be increased.
                        if(super.getLevel() < 100) {
                            this.checkLevelUp();
                        }

                        if(enemies[index].getPossessing_item() != null) {
                            // Get dropped item from the Enemy.
                            Item dropped_item = this.enemies[index].getPossessing_item();

                            // If the player is not awakened yet, change the stamina potion to a health potion.
                            if(!this.isAwakened()){
                                if(dropped_item instanceof StaminaPotion || dropped_item instanceof MixPotion){
                                    dropped_item = new HealthPotion();
                                } else if (dropped_item instanceof SuperStaminaPotion || dropped_item instanceof SuperMixPotion) {
                                    dropped_item = new SuperHealthPotion();
                                } else if (dropped_item instanceof HyperStaminaPotion || dropped_item instanceof HyperMixPotion) {
                                    dropped_item = new HyperHealthPotion();
                                }
                            }

                            this.getItemFromEnemy(dropped_item);
                            enemies[index].nullifyPossessingItem();
                        }
                    }
                    break; // Once the targeted enemy is found, terminate the loop.
                }
            }
        }
    }

    /**
     * Attack the enemy with the player's skill.
     * @param target_pos : The position where the targeted enemy is.
     */
    public void useSkillOnEnemy(int [] target_pos, String skill_name) {
        this.usedSkillStatus = "";
        String skillUse = "";

        skillUse += "\nSYSTEM :  " + skill_name + " 스킬 사용!";

        int damage = (int) (this.useSkill(skill_name) * this.getAttack_point()); // Calculate skill damage.

        // Attack boss if the target position is correct.
        if(this.boss.getCurrent_pos()[0] == target_pos[0] && this.boss.getCurrent_pos()[1] == target_pos[1]) {
            this.obtainedItemStatus = "";
            skillUse += "\nSYSTEM : " + UserInterface.ANSI_RED + this.boss.getName() + UserInterface.ANSI_RESET + "에게 " + UserInterface.ANSI_YELLOW + damage + UserInterface.ANSI_RESET + "데미지를 입혔다."; // The attack hit.
            this.boss.setCurrent_health_point(this.boss.getCurrent_health_point() - damage); // Update the HP of the boss
            StageMap.updateStageMap(this.boss);

            if(boss.getCurrent_health_point() == 0) {
                //IF THE ENEMY"S BEEN DOWN, GIVE EXP TO THE PLAYER
                skillUse += "\nSYSTEM : " + UserInterface.ANSI_RED + this.boss.getName() + UserInterface.ANSI_RESET + "를 쓰러트렸습니다."; // The attack missed.
                skillUse += "\nSYSTEM : " + this.boss.getExperience_point() + "경험치를 얻었습니다."; // The attack missed.
                this.setAccumulated_experience(this.getAccumulated_experience() + this.boss.getExperience_point()); // Accumulated the experience point.

                // Check if the level can be increased.
                this.checkLevelUp();

                if(boss.getPossessing_item() != null) {
                    // Get dropped item from the boss.
                    Item dropped_item = this.boss.getPossessing_item();
                    this.getItemFromEnemy(dropped_item);
                    boss.nullifyPossessingItem();
                }
            }
        } else {
            // Attack enemy if the target position is correct.
            for (int index = 0; index < this.enemies.length; index++){
                // Find the targeted enemy and attack.
                if(this.enemies[index].getCurrent_pos()[0] == target_pos[0] && this.enemies[index].getCurrent_pos()[1] == target_pos[1]){
                    this.obtainedItemStatus = "";
                    if(this.enemies[index].getCurrent_health_point() == 0) {
                        // Stop this process when the enemy don't have any leftover HP.
                        skillUse += "\nSYSTEM : " + UserInterface.ANSI_RED + this.enemies[index].getName() + UserInterface.ANSI_RESET + "는 싸늘한 주검으로 되어있습니다."; // The attack missed.
                        break;
                    }
                    skillUse += "\nSYSTEM : " + UserInterface.ANSI_RED + this.enemies[index].getName() + UserInterface.ANSI_RESET + "에게 " + UserInterface.ANSI_YELLOW + damage + UserInterface.ANSI_RESET + "데미지를 입혔다."; // The attack hit.
                    this.enemies[index].setCurrent_health_point(this.enemies[index].getCurrent_health_point() - damage); // Update the HP of the enemy.
                    StageMap.updateStageMap(this.enemies[index]);

                    if(enemies[index].getCurrent_health_point() == 0) {
                        //IF THE ENEMY"S BEEN DOWN, GIVE EXP TO THE PLAYER
                        skillUse += "\nSYSTEM : " + UserInterface.ANSI_RED + this.enemies[index].getName() + UserInterface.ANSI_RESET + "를 쓰러트렸습니다."; // The attack missed.
                        skillUse += "\nSYSTEM : " + this.enemies[index].getExperience_point() + "경험치를 얻었습니다."; // The attack missed.
                        this.setAccumulated_experience(this.getAccumulated_experience() + this.enemies[index].getExperience_point()); // Accumulated the experience point.

                        // Check if the level can be increased.
                        this.checkLevelUp();

                        if(enemies[index].getPossessing_item() != null) {
                            // Get dropped item from the Enemy.
                            Item dropped_item = this.enemies[index].getPossessing_item();
                            this.getItemFromEnemy(dropped_item);
                            enemies[index].nullifyPossessingItem();
                        }
                    }
                    break; // Once the targeted enemy is found, terminate the loop.
                }
            }
        }
        this.usedSkillStatus = skillUse;
    }

    /**
     * Use buff skill, Roar (SP : 6)
     */
    public void useBuffSkill(boolean available) {
        if (available) {
            this.usedSkillStatus = "\nSYSTEM : 3턴 동안 공격력이 25% 증가합니다.";
        } else {
            this.usedSkillStatus = "\nSYSTEM : 이미 버프가 적용되고 있습니다.";
        }
    }

    /**
     * Check if the level can be changed.
     * @return True, if the level can be raised.
     */
    private void checkLevelUp(){
        this.levelUpStatus = "";
        if(this.getLevel() < (this.getAccumulated_experience() / this.getRequired_experience())){
            int level = this.getAccumulated_experience() / this.getRequired_experience();
            this.levelUpStatus += "SYSTEM : " + UserInterface.ANSI_YELLOW + "LEVEL UP!" + UserInterface.ANSI_RESET; // Show leveled up message.
            this.levelUpStatus += "\nSYSTEM : " + level + " 레벨이 되었습니다."; // Show leveled up message.
            this.setLevel(level); // Update the level.
            this.updateStatus(); // Update the status.
        };
    }

    /**
     * Check if the player is within the range.
     * @param vendor_pos : The position of the vendor.
     * @return True, if the player is near the vendor.
     */
    public boolean isVendorNear(int [] vendor_pos) {
        boolean result = false;

        if((this.getCurrent_pos()[0] - 1) == vendor_pos[0] && this.getCurrent_pos()[1] == vendor_pos[1]){
            // Check if the player is at the upper area.
            result = true;
        } else if ((this.getCurrent_pos()[0] + 1) == vendor_pos[0] && this.getCurrent_pos()[1] == vendor_pos[1]) {
            // Check if the player is at the lower area.
            result = true;
        } else if (this.getCurrent_pos()[0] == vendor_pos[0] && (this.getCurrent_pos()[1] - 1) == vendor_pos[1]) {
            // Check if the player is at the left area.
            result = true;
        } else if (this.getCurrent_pos()[0] == vendor_pos[0] && (this.getCurrent_pos()[1] + 1) == vendor_pos[1]) {
            // Check if the player is at the right area.
            result = true;
        }
        return result;
    }


    /**
     * Check if the item is dropped.
     * If the Item is dropped, put it in the the player's inventory.
     */
    private void checkItemDrop(){
        // TODO:
    }

    /**
     * Get skills and apply it to the player.
     */
    @Override
    public void getSkill() {
        // Set skills to the player.
        this.skills = new SkillData().getSkills();
    }

    /**
     * Use the acquired skills.
     */
    @Override
    public float useSkill(String skill_name) {
        // Return the calculated skill damage.
        return this.skills.get(skill_name);
    }

    /**
     * Upgrade the icons when the player has been awakened.
     */
    public void upgradeIcons(){
        if(this.isAwakened()){
            // Upgrade the icons when the player has been awakened.
            super.getStatus_icon().put(50, UserInterface.ANSI_YELLOW_BG + "♡" + UserInterface.ANSI_RESET);
            super.getStatus_icon().put(51, UserInterface.ANSI_YELLOW_BG + "♥" + UserInterface.ANSI_RESET);
        }
    }

    /**
     * Recover HP and SP, after clearing the stage.
     */
    public void recover(){
        super.setCurrent_health_point(super.getHealth_point());
        super.setCurrent_stamina_point(super.getStamina_point());
    }

    /**
     * Get item from the enemy and store it in the inventory.
     */
    public void getItemFromEnemy(Item dropped_item){
        this.obtainedItemStatus = "";
        if(dropped_item != null){
            // If dropping item is assured, put the item in to the empty slot.
            for (int index = 0; index < this.inventory.length; index++){
                if(this.inventory[index] == null){
                    this.inventory[index] = dropped_item;
                    this.obtainedItemStatus += "SYSTEM : " + UserInterface.ANSI_YELLOW + dropped_item.getName() + UserInterface.ANSI_RESET + "를 획득했습니다.";
                    break; // To prevent the inventory storing the same item multiple times, stop the loop.
                }
                if(index == this.inventory.length -1) {
                    // If there is no empty slot in the inventory, tell the player that no more items can be kept.
                    this.obtainedItemStatus += "SYSTEM : " + "인벤토리가 가득 찼습니다." + UserInterface.ANSI_YELLOW + dropped_item.getName() + UserInterface.ANSI_RESET + "를 버렸습니다.";
                }
            }
        }
    }

    /**
     * Use the selected potion.
     * @param item_index : The item that player have chosen to use.
     * @return result : True, if the item has been used with no problem.
     */
    public void useSelectedItem(int item_index) {
        item_index--; // Reduce one index, because the first item index is shown as 1, not 0.

        Item item = this.inventory[item_index]; // Get the name of the item.

        // Find out what kind of item is being used.
        if(item instanceof  Potion) {

            int potion_effect = 0; // recovery points from the potion.

            if(item instanceof HealthPotion) {
                // Get HP recovery point from the HealthPotion.
                potion_effect = ((HealthPotion)item).getRecovery_point();

//                if(item instanceof SuperHealthPotion){
//                    potion_effect = ((SuperHealthPotion) item).getRecovery_point();
//                } else if (item instanceof HyperHealthPotion){
//                    potion_effect = ((HyperHealthPotion) item).getRecovery_point();
//                }

                super.setCurrent_health_point(super.getCurrent_health_point() + (int) (super.getHealth_point() * (potion_effect / 100.0)));
                this.usedItemEffect = "\nSYSTEM : " + "HP를 " + (int) (super.getHealth_point() * (potion_effect / 100.0)) + "만큼 회복했습니다.";
            } else if (item instanceof StaminaPotion) {
                // Get the stamina recovery point from the StaminaPotion
                potion_effect = ((StaminaPotion) item).getRecovery_point();

//                if(item instanceof SuperStaminaPotion){
//                    potion_effect = ((SuperStaminaPotion) item).getRecovery_point();
//                } else if (item instanceof HyperStaminaPotion){
//                    potion_effect = ((HyperStaminaPotion) item).getRecovery_point();
//                }

                super.setCurrent_stamina_point(super.getCurrent_stamina_point() + (int) (super.getStamina_point() * (potion_effect / 100.0)));
                this.usedItemEffect = "\nSYSTEM : " + "SP를 " + (int) (super.getStamina_point() * (potion_effect / 100.0)) + "만큼 회복했습니다.";
            } else if (item instanceof MixPotion) {
                // Get the HP & stamina recovery point from the MixPotion.
                potion_effect = ((MixPotion) item).getRecovery_point();

//                if(item instanceof SuperMixPotion){
//                    potion_effect = ((SuperMixPotion) item).getRecovery_point();
//                } else if (item instanceof HyperMixPotion){
//                    potion_effect = ((HyperMixPotion) item).getRecovery_point();
//                }

                super.setCurrent_health_point(super.getCurrent_health_point() + (int) (super.getHealth_point() * (potion_effect / 100.0)));
                super.setCurrent_stamina_point(super.getCurrent_stamina_point() + (int) (super.getStamina_point() * (potion_effect / 100.0)));
                this.usedItemEffect =  "\nSYSTEM : " + "HP와 SP가 각각 " + (int) (super.getHealth_point() * (potion_effect / 100.0)) + ", " + (int) (super.getStamina_point() * (potion_effect / 100.0)) + "만큼 회복되었습니다.";
            }

        } else if (item instanceof Equipment) {
                // If the chosen item is equipment.
                if(item instanceof UpperBody && this.getEquipped_armor()[0] == null) {
                    // Equip the upper body armor
                    this.getEquipped_armor()[0] = (UpperBody)item;
                    this.usedItemEffect = "SYSTEM : " + item.getName() + "을 착용합니다.";
                } else if (item instanceof LowerBody && this.getEquipped_armor()[1] == null) {
                    // Equip the lower body armor
                    this.getEquipped_armor()[1] = (LowerBody)item;
                    this.usedItemEffect = "SYSTEM : " + item.getName() + "을 착용합니다.";
                } else if ((item instanceof Blade || item instanceof Blunt || item instanceof Gun) && this.getEquipped_weapon() == null) {
                    // Equip the weapon
                    super.setEquipped_weapon((Equipment) item);
                    this.usedItemEffect = "SYSTEM : " + item.getName() + "을 착용합니다.";
                } else {
                    // Show a message that you cannot use the other equipment, yet.
                    this.usedItemEffect = "SYSTEM : 이미 같은 부위에 착용 중인 장비가 있습니다.";
                    return; // Stop further the process.
                }
        } else {
            this.usedItemEffect = "SYSTEM : 아이템이 없습니다.";
            return; // Stop further the process.
        }
        this.inventory[item_index] = null; // Make the inventory slot empty, because the potion has been used.
    }

    /**
     * Apply bonus stats to player if equipments are equipped.
     */
    public void applyEquipmentStat(){
    // Apply stats from equipments to the player.
        if(this.equipped_armor[0] != null) {
            // If the upper body armor is equipped
            this.armor_debuff_stat = ((UpperBody)this.equipped_armor[0]).getLowered_evasion_point();
            this.armor_extra_defense = ((UpperBody)this.equipped_armor[0]).getExtra_defense_point();

            // Reduce the durability
            if(this.equipped_armor[0].getDurability() > 0) {
                this.equipped_armor[0].setDurability((this.equipped_armor[0]).getDurability() - 1);
            }

            // If the armor runs out the durability, make it disappear from the inventory.
            if(this.equipped_armor[0].getDurability() == 0) {
                // Take away the bonus stat.
                this.armor_debuff_stat -= ((UpperBody)this.equipped_armor[0]).getLowered_evasion_point();
                this.armor_extra_defense -= ((UpperBody)this.equipped_armor[0]).getExtra_defense_point();

                if(this.armor_debuff_stat > 0 || this.armor_extra_defense > 0){
                    // Take away the bonus stat.
                    this.armor_debuff_stat = 0;
                    this.armor_extra_defense = 0;
                }

                this.equipped_armor[0] = null;
            }
        }

        if(this.equipped_armor[1] != null) {
            // If the lower body armor is equipped
            this.armor_debuff_stat += ((LowerBody)this.equipped_armor[1]).getLowered_evasion_point();
            this.armor_extra_defense += ((LowerBody)this.equipped_armor[1]).getExtra_defense_point();

            // Reduce the durability
            if(this.equipped_armor[1].getDurability() > 0) {
                this.equipped_armor[1].setDurability((this.equipped_armor[1]).getDurability() - 1);
            }

            // If the armor runs out the durability, make it disappear from the inventory.
            if(this.equipped_armor[1].getDurability() == 0) {
                // Take away the bonus stat.
                this.armor_debuff_stat = 0;
                this.armor_extra_defense = 0;
                this.equipped_armor[1] = null;
            }
        }

        if(super.getEquipped_weapon() != null) {
            // If the weapon is equipped
            if (super.getEquipped_weapon() instanceof Blade) {
                // Get extra stats from the blade weapon
                this.weapon_extra_attack = ((Blade) super.getEquipped_weapon()).getExtra_attack_point();
                this.weapon_critical_buff = ((Blade) super.getEquipped_weapon()).getExtra_critical_rate();
            } else if (super.getEquipped_weapon() instanceof Blunt) {
                // Get extra stats from the blunt weapon
                this.weapon_extra_attack = ((Blunt) super.getEquipped_weapon()).getExtra_attack_point();
                this.weapon_accuracy_buff = ((Blunt) super.getEquipped_weapon()).getExtra_accuracy();
            } else if (super.getEquipped_weapon() instanceof Gun) {
                // Get extra stats from the guns
                this.weapon_extra_attack = ((Gun) super.getEquipped_weapon()).getExtra_attack_point();
                this.weapon_evasion_buff = ((Gun) super.getEquipped_weapon()).getExtra_evasion_point();
            }

            // Reduce the durability
            if(super.getEquipped_weapon().getDurability() > 0) {
                super.getEquipped_weapon().setDurability(super.getEquipped_weapon().getDurability() - 1);
            }

            // If the weapon runs out the durability, make it disappear from the inventory.
            if(super.getEquipped_weapon().getDurability() == 0) {
                // If the weapon runs out the durability, make it disappear from the inventory.
                this.weapon_extra_attack = 0;
                this.weapon_critical_buff = 0;
                this.weapon_accuracy_buff = 0;
                this.weapon_evasion_buff = 0;
                super.setEquipped_weapon(null);
            }
        }
    }

    /**
     * Check if the inventory is full
     * @return True, when there is no empty slot in the inventory.
     */
    public boolean checkInventoryFull(){
        boolean isFull = true;
        for(int index = 0; index < this.inventory.length ; index++){
            if(this.inventory[index] == null){
                isFull = false;
            }
        }
        return isFull;
    }

    /**
     * Check if the inventory is full
     * @return True, when there is no empty slot in the inventory.
     */
    public int countEmptyInventorySlot(){
        int empty_slot = 0;
        for(int index = 0; index < this.inventory.length ; index++){
            if(this.inventory[index] == null){
                empty_slot++;
            }
        }
        return empty_slot;
    }

    /**
     * Buy an item and put store it in the inventory.
     */
    public void buyItem(Item boughtItem){
        for(int index = 0; index < this.inventory.length ; index++){
            if(this.inventory[index] == null){
                this.inventory[index] = boughtItem; // Store the item in the inventory.
                this.setMoney(this.getMoney() - boughtItem.getPrice()); // Update the possessing money.
                System.out.println("\nSYSTEM : " +  UserInterface.ANSI_CYAN + boughtItem.getName() + UserInterface.ANSI_RESET + " 1개를 구입했습니다."); // Show the shopping result.
                System.out.println("SYSTEM : " + "(엔터 키를 눌러서 계속 진행합니다.)");
                UserInterface.getInput();
                break;
            }
        }
    }

    /**
     * Sell the item that player possesses.
     * @param index : An index of the inventory slot.
     */
    public void sellItem(int index){
        if(this.inventory[index] == null){
            UserInterface.invalidateInput();
        } else {
            this.setMoney(this.getMoney() + (this.inventory[index].getPrice() / 2 ));
            System.out.println("\nSYSTEM : " +  UserInterface.ANSI_CYAN + this.inventory[index].getName() + UserInterface.ANSI_RESET + " 1개를 판매했습니다."); // Show the shopping result.
            this.inventory[index] = null;
            System.out.println("SYSTEM : " + "(엔터 키를 눌러서 계속 진행합니다.)");
            UserInterface.getInput();
        }
    }

    public void setBefore_buff_attack_point(int before_buff_attack_point) {
        this.before_buff_attack_point = before_buff_attack_point;
    }

    public Item[] getInventory() {
        return inventory;
    }

    public Equipment[] getEquipped_armor() {
        return equipped_armor;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        if(this.money < 0) {
            this.money = 0;
        }
    }

    public int getRequired_experience() {
        return required_experience;
    }

    public int getAccumulated_experience() {
        return accumulated_experience;
    }

    public void setAccumulated_experience(int accumulated_experience) {
        this.accumulated_experience = accumulated_experience;
    }

    public boolean isAwakened() {
        return awakened;
    }

    public void setAwakened(boolean awakened) {
        this.awakened = awakened;
    }

    public int getWeapon_evasion_buff() {
        return weapon_evasion_buff;
    }

    public int getArmor_debuff_stat() {
        return armor_debuff_stat;
    }

    public int getArmor_extra_defense() {
        return armor_extra_defense;
    }

    public boolean isThread_flag() {
        return thread_flag.get();
    }

    public void targetEnemies(EnemyBoss boss, Enemy [] enemies){
        this.boss = boss;
        this.enemies = enemies;
    }

    public String getUsedItemEffect() {
        return usedItemEffect;
    }

    public String getAttackStatus() {
        return this.attackStatus;
    }

    public String getLevelUpStatus() {
        return this.levelUpStatus;
    }

    public void setLevelUpStatus(String levelUpStatus) {
        this.levelUpStatus = levelUpStatus;
    }

    public String getUsedSkillStatus() {
        return usedSkillStatus;
    }

    public void setUsedSkillStatus(String usedSkillStatus) {
        this.usedSkillStatus = usedSkillStatus;
    }

    public void setUsedItemEffect(String usedItemEffect) {
        this.usedItemEffect = usedItemEffect;
    }

    public String getObtainedItemStatus() {
        return obtainedItemStatus;
    }

    public void setObtainedItemStatus(String obtainedItemStatus) {
        this.obtainedItemStatus = obtainedItemStatus;
    }

    public void activatePlayerStatus(){
        this.thread.start();
    }

    public void pausePlayerStatus(){
        this.thread_flag.set(false);
        this.thread.interrupt();
        this.thread = null;
    }

    public void resumePlayerStatus(){
        this.thread_flag.set(true);
        this.thread = new Thread(this.runnable);
        this.thread.start();
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        if(this.getCurrent_health_point() == 0){
            // When the player's HP is 0.
            UserInterface.clearConsole();
            StageMap.displayStageMap(); // Show the lasted map state.
            BattleInterface.showPlayerStatus(this); // Show player's HP, SP, weapon, and the weapon's durability.
            Story.showGameOverMsg(); // Show Game Over
            System.exit(0); // Turn the game off.
            return;
        }

        StageMap.updateStageMap(this); // Reflect character's status via its icon.
    }
}
