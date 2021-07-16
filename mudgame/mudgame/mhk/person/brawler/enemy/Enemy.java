package com.mudgame.mhk.person.brawler.enemy;

import com.mudgame.mhk.item.Item;
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
import com.mudgame.mhk.person.brawler.Brawler;
import com.mudgame.mhk.person.brawler.Player;
import com.mudgame.mhk.stage.StageMap;
import com.mudgame.mhk.userinterface.UserInterface;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Enemy extends Brawler implements Runnable {

    private Item possessing_item; // Stores an item for the battle reward.
    private int possessing_money = 4; // Stores some amount of money for the battle reward.
    private int item_drop_rate = 50; // Stores some amount of money for the battle reward.
    private int view_range = 5; // The distance of the enemy's sight.
    private int experience_point = 85;
    private String attackResult;
    private static Player player; // TODO
    private AtomicBoolean thread_flag = new AtomicBoolean(true); // TODO
    private Thread thread;
    private Runnable runnable;


    public Enemy(){}

    public Enemy(int stage_num, Player player) {
        // Generate the enemy's name and EXP points automatically, depending on the stage number.
        if(stage_num == 1){
            super.setName("불량배");
        } else if (stage_num == 2 ){
            super.setName("깡패");
        } else if (stage_num == 3 ){
            super.setName("야쿠자");
        } else if (stage_num == 4 ){
            super.setName("경호원");
            this.setExperience_point(this.experience_point * 2);
        }

        // Store icon that reflects the condition of the entity.
        super.getStatus_icon().put(0, UserInterface.ANSI_RED + "\uD83D\uDC80" + UserInterface.ANSI_RESET);
        super.getStatus_icon().put(50, UserInterface.ANSI_RED + "□" + UserInterface.ANSI_RESET);
        super.getStatus_icon().put(51, UserInterface.ANSI_RED + "▣" + UserInterface.ANSI_RESET);
        if(stage_num == 4){
            // Put special icon for the last stage.
            super.getStatus_icon().put(50, UserInterface.ANSI_RED + "◇" + UserInterface.ANSI_RESET);
            super.getStatus_icon().put(51, UserInterface.ANSI_RED + "◈" + UserInterface.ANSI_RESET);
        }
        super.setCurrent_pos(this.generatePos());

        //Set Status of enemy
        this.setStatus(player.getLevel());

        //Set drop-item
        this.setPossessing_item();

        // TODO
        this.player = player;

        this.runnable = this;
        this.thread = new Thread(this);
    }

    /**
     * Set all status for the enemy boss.
     */
    public void setStatus(int player_level) {
        if(player_level > 99) {
            // For debugging mode.
            player_level = 99;
        }
        super.setLevel(player_level);
        super.setHealth_point(super.getLevel() * 8);
        super.setCurrent_health_point(super.getLevel() * 8);
        super.setStamina_point(super.getLevel() * 1);
        super.setCurrent_stamina_point(super.getLevel() * 1);
        super.setAttack_point(super.getLevel() * 3);
        super.setDefense_point(super.getLevel() * 2);
        super.setAccuracy(55);
        super.setEvasion(15);
        super.setCritical_rate(super.getLevel() + 10);
        super.setCritical_ratio(1);
        super.setBuff_point(0);
        super.setAttack_range(1);
        super.setMove_distance((super.getLevel() / 20) + 4);
        super.setEquipped_weapon(null);
    }

    /**
     * Set a position for the enemy.
     * @return pos : An array of the position on the map.
     */
    public int [] generatePos() {
        // Generate random co-ordinate of the enemy.
        // The range is limited by 'movable' values from the 'Stage Map'.
        // [0] has minimum movable value, [1] has the maximum movable value on the map.
        Random random = new Random();
        int limit_cols = 10; // The number of area to limit where the enemy can be created.
        int [] pos = {
                (random.nextInt(StageMap.movable_rows[1] - StageMap.movable_rows[0])),
                (random.nextInt(StageMap.movable_cols[1] - limit_cols) + limit_cols)
            };
        return pos;
    }

    /**
     * Make enemies approach to the enemy.
     */
    public void approachPlayer(){
        if(this.getCurrent_health_point() == 0) {
            // If the enemy is dead, do not make any move.
            return;
        }
        int [] player_pos = this.player.getCurrent_pos();
        boolean move = new Random().nextInt(2) % 2 == 0 ? true : false; // Decide whether to move the enemies or not.
        boolean is_pos_changed = false; // A flag for detecting the change of the enemy's position.

        if(!move){
            // If the move flag is false, do not make this enemy move.
            return;
        }

        // Store enemy's recent position.
        if(this.getCurrent_pos()[1] < player_pos[1]){
            // Try to match columns : When player is further on the right side.
            int [] temp_pos = new int[2];
            temp_pos[0] = this.getCurrent_pos()[0]; // Keep the rows
            temp_pos[1] = this.getCurrent_pos()[1] + 1; // Move to right, one step.
            this.setChecking_pos(temp_pos);
            is_pos_changed = true;
        } else if (this.getCurrent_pos()[1] > player_pos[1]){
            // Try to match columns : When player is further on the left side.
            int [] temp_pos = new int[2];
            temp_pos[0] = this.getCurrent_pos()[0]; // Keep the rows
            temp_pos[1] = this.getCurrent_pos()[1] - 1; // Move to left, one step.
            this.setChecking_pos(temp_pos);
            is_pos_changed = true;
        } else if (this.getCurrent_pos()[0] > player_pos[0]){
            // Try to match rows : When player is further on the upper side.
            int [] temp_pos = new int[2];
            temp_pos[0] = this.getCurrent_pos()[0] - 1; // Move up, one step.
            temp_pos[1] = this.getCurrent_pos()[1]; // Keep the column.
            this.setChecking_pos(temp_pos);
            is_pos_changed = true;
        } else if (this.getCurrent_pos()[0] < player_pos[0]){
            // Try to match rows : When player is further on the lower side.
            int [] temp_pos = new int[2];
            temp_pos[0] = this.getCurrent_pos()[0] + 1; // Move down, one step.
            temp_pos[1] = this.getCurrent_pos()[1]; // Keep the column.
            this.setChecking_pos(temp_pos);
            is_pos_changed = true;
        }

        // Check if the enemies' new position overlaps with the player's position.
        boolean collideWithPlayer = player_pos[0] == this.getChecking_pos()[0] && player_pos[1] == this.getChecking_pos()[1];

        // Update the enemies' icon on the current map.
        if(!collideWithPlayer && is_pos_changed){
            if(StageMap.getStageMap() == null){
                if(this instanceof EnemyBoss && StageMap.getCurrent_section() != 2){
                    // Boss is movable only in the last section.
                    return;
                }
                // Update the 2nd, 3rd stage map.
                boolean collided = StageMap.collideWithObstacle(StageMap.getStageMaps()[StageMap.getCurrent_section()][this.getChecking_pos()[0]][this.getChecking_pos()[1]]); // See if the new position collides with any obstacles.

                // Update the map only when there is no collision.
                if(!collided){
                    StageMap.getStageMaps()[StageMap.getCurrent_section()][this.getCurrent_pos()[0]][this.getCurrent_pos()[1]] = "　"; // Make old position empty.
                    this.setCurrent_pos(this.getChecking_pos()); // Update the current position.
                    StageMap.getStageMaps()[StageMap.getCurrent_section()][this.getCurrent_pos()[0]][this.getCurrent_pos()[1]] = this.getIcon(); // Update the current position on the map
                }
            } else {
                // Update the 1st, last stage map.
                boolean collided = StageMap.collideWithObstacle(StageMap.getStageMap()[this.getChecking_pos()[0]][this.getChecking_pos()[1]]);  // See if the new position collides with any obstacles.

                // Update the map only when there is no collision.
                if(!collided) {
                    StageMap.getStageMap()[this.getCurrent_pos()[0]][this.getCurrent_pos()[1]] = "　"; // Make old position empty.
                    this.setCurrent_pos(this.getChecking_pos()); // Update the current position
                    StageMap.getStageMap()[this.getCurrent_pos()[0]][this.getCurrent_pos()[1]] = this.getIcon(); // Update the current position on the map
                }
            }
        }
    }

    /**
     * Attack the player.
     */
    public String attackPlayer(){
        if (this.getCurrent_health_point() == 0 || !this.isPlayerNear(player.getCurrent_pos())) {
            // If the enemy is dead, do not make any move.

            // Check if the player is near this enemy entity.
            // True, engage the attack. False, do not attack the player.
            return null;
        }

        // Attack player
        Random random = new Random(); // For critical hit and evasion.
        boolean missed = random.nextInt(100) > (this.getAccuracy() - (player.getEvasion() + player.getWeapon_evasion_buff() + player.getArmor_debuff_stat())) ? true : false; // True, when the hit is missed.
        int critical_hit = random.nextInt(100) < this.getCritical_rate() ? this.getCritical_ratio() : 1;  // Store player's critical damage ratio if true.
        int damage = missed ? 0 : (this.getAttack_point() * critical_hit) - (player.getDefense_point() + player.getArmor_extra_defense()); // If the hit is not missed, inflict the damage to the enemy.

        if(damage < 0) {
            // When damage is lesser than 0, set it to 0.
            damage = 0;
        }

        if (missed) {
            return "SYSTEM : " + UserInterface.ANSI_RED + super.getName() + UserInterface.ANSI_RESET + "의 공격이 빗나갔다."; // The attack missed.
        } else {
            if(critical_hit == this.getCritical_ratio()) {
//                System.out.println("SYSTEM : " + UserInterface.ANSI_RED  + "크리티컬 히트!! " + UserInterface.ANSI_RESET); // Show that critical hit has been inflicted.
            }
            player.setCurrent_health_point(player.getCurrent_health_point() - damage); // Update the HP of the boss
            return "SYSTEM : " + UserInterface.ANSI_RED + super.getName() + UserInterface.ANSI_RESET + "가 " + UserInterface.ANSI_YELLOW + player.getName() + UserInterface.ANSI_RESET + "에게 " + UserInterface.ANSI_RED + damage + UserInterface.ANSI_RESET + "데미지를 입혔다."; // The attack hit.
        }
    }

    /**
     * Check if the player is within the range.
     * @param player_pos : The position of the player.
     * @return True, if the player is near the enemy.
     */
    public boolean isPlayerNear(int [] player_pos) {
        boolean result = false;

        if((this.getCurrent_pos()[0] - 1) == player_pos[0] && this.getCurrent_pos()[1] == player_pos[1]){
            // Check if the player is at the upper area.
            result = true;
        } else if ((this.getCurrent_pos()[0] + 1) == player_pos[0] && this.getCurrent_pos()[1] == player_pos[1]) {
            // Check if the player is at the lower area.
            result = true;
        } else if (this.getCurrent_pos()[0] == player_pos[0] && (this.getCurrent_pos()[1] - 1) == player_pos[1]) {
            // Check if the player is at the left area.
            result = true;
        } else if (this.getCurrent_pos()[0] == player_pos[0] && (this.getCurrent_pos()[1] + 1) == player_pos[1]) {
            // Check if the player is at the right area.
            result = true;
        }
        return result;
    }

    /**
     * Allocate an item to enemy randomly.
     */
    public void setPossessing_item() {
        int random_number = new Random().nextInt(100); // For allocating items randomly to enemy.

        // Set a potion as a drop item.
        if(random_number > 89 && random_number <= 99) {
            this.possessing_item = new HealthPotion();
        } else if (random_number > 79 && random_number <= 89) {
            this.possessing_item = new SuperHealthPotion();
        } else if (random_number > 69 && random_number <= 79) {
            this.possessing_item = new HyperHealthPotion();
        } else if (random_number > 69 && random_number <= 79) {
            this.possessing_item = new StaminaPotion();
        } else if (random_number > 59 && random_number <= 69) {
            this.possessing_item = new SuperStaminaPotion();
        } else if (random_number > 49 && random_number <= 59) {
            this.possessing_item = new HyperStaminaPotion();
        } else if (random_number > 44 && random_number <= 49) {
            this.possessing_item = new MixPotion();
        } else if (random_number > 39 && random_number <= 44) {
            this.possessing_item = new SuperMixPotion();
        } else if (random_number > 34 && random_number <= 39) {
            this.possessing_item = new HyperMixPotion();
        } else if (random_number == 0) { // Set an equipment as a drop item.
            this.possessing_item = new MetalGreaves();
        } else if (random_number == 1) {
            this.possessing_item = new SpecialGreaves();
        } else if (random_number == 2) {
            this.possessing_item = new MetalVest();
        } else if (random_number == 3) {
            this.possessing_item = new SpecialArmor();
        } else if (random_number == 4) {
            this.possessing_item = new Katana();
        } else if (random_number == 5) {
            this.possessing_item = new Rapier();
        } else if (random_number == 6) {
            this.possessing_item = new Bat();
        } else if (random_number == 7) {
            this.possessing_item = new Pipe();
        } else if (random_number == 8) {
            this.possessing_item = new Magnum();
        } else if (random_number == 9) {
            this.possessing_item = new Shotgun();
        }
    }

    /**
     * Erase the possessing item.
     */
    public void nullifyPossessingItem() {
        this.possessing_item = null;
    }

    /**
     * Allocate an item to enemy randomly.
     */
    public Item getPossessing_item() {
        Item dropped_item = null;
        // If the random number is within the drop rate, drop the item when the enemy gets killed.
        if(this.item_drop_rate >= new Random().nextInt(100)){
            dropped_item = this.possessing_item;
        }
        return dropped_item;
    }

    /**
     * Show the message of the enemy attacking the player.
     */
    public String getAttackResult() {
        return attackResult;
    }

    /**
     * Set the message of the enemy attacking the player.
     * @param attackResult : The status of the enemy's attack will be stored.
     */
    public void setAttackResult(String attackResult) {
        this.attackResult = attackResult;
    }

    public int getExperience_point() {
        return experience_point;
    }

    public void setExperience_point(int experience_point) {
        this.experience_point = experience_point;
    }

    public int getView_range() {
        return view_range;
    }

    public void setView_range(int view_range) {
        this.view_range = view_range;
    }

    private int [] getPos() {
        return super.getCurrent_pos();
    }

    public int getPossessing_money() {
        return possessing_money;
    }

    public void setPossessing_money(int possessing_money) {
        this.possessing_money = possessing_money;
    }

    public int getItem_drop_rate() {
        return item_drop_rate;
    }

    public void setItem_drop_rate(int item_drop_rate) {
        this.item_drop_rate = item_drop_rate;
    }

    public static Player getPlayer() {
        return player;
    }

    public void activateEnemy(){
        this.thread.setPriority(Thread.MAX_PRIORITY);
        this.thread.start();
    }

    public void pauseEnemy(){
        this.thread_flag.set(false);
        this.thread.interrupt();
        this.thread = null;
    }

    public void resumeEnemy(){
        this.thread_flag.set(true);
        this.thread = new Thread(this);
        this.thread.setPriority(Thread.MAX_PRIORITY);
        this.thread.start();
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public Thread getThread() {
        return this.thread;
    }

    @Override
    public void run() {
        if(player.getCurrent_health_point() == 0 || this.getCurrent_health_point() == 0) {
            return;
        }

        StageMap.updateStageMap(this);

        this.approachPlayer();
        StageMap.updateStageMap(this);

        boolean isPlayerNear = this.isPlayerNear(player.getCurrent_pos());
        if (isPlayerNear && player.getCurrent_health_point() > 0) {
            this.setAttackResult(this.attackPlayer());
            StageMap.updateStageMap(player);
        }
    }
}
