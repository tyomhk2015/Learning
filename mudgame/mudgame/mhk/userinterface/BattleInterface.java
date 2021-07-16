package com.mudgame.mhk.userinterface;

import com.mudgame.mhk.item.Item;
import com.mudgame.mhk.person.Vendor;
import com.mudgame.mhk.person.brawler.Player;
import com.mudgame.mhk.person.brawler.enemy.Enemy;
import com.mudgame.mhk.person.brawler.enemy.EnemyBoss;
import com.mudgame.mhk.stage.StageMap;
import com.mudgame.mhk.story.Story;

import java.util.concurrent.atomic.AtomicBoolean;

public class BattleInterface extends Thread {

    private AtomicBoolean thread_flag = new AtomicBoolean(true); // TODO
    private static Player player; // TODO
    private static Enemy[] enemies; // TODO
    private static EnemyBoss boss; // TODO
    private static Vendor vendor; // TODO
    private boolean isEnemyNear;
    private boolean isVendorNear;
    private Runnable runnable;
    private Thread thread;

    // Related to commands
    private boolean onMovingInfo;
    private boolean isMoveInputValid = true;
    private boolean isMovable = true;
    private boolean onInventory;
    private boolean isItemInputValid = true;
    private boolean isItemUsed;
    private boolean onAttackingDirection;
    private boolean isAttackDirectionValid;
    private boolean onSkills;
    private boolean isSPEnough = true;
    private boolean isSkillSelected;
    private boolean onSkillDirection;
    private boolean doesEnemyExist = true;
    private String chosenSkill = "";
    private boolean isSkillUsed;
    private boolean isBuffSkillUsed;


    public BattleInterface() {
        this.runnable = this;
        this.thread = new Thread(this);
    }

    /**
     * Show current status of the player.
     */
    public static void showPlayerStatus(Player player) {
        String player_status_print = "\n";
        // Show the icon that the player is in control of.
        player_status_print += "" + player.getIcon() + "  : Your Lv " + player.getLevel() + "　　　　　　　　　　　　　　　　　　　Money : " + player.getMoney() + "￥";

        // Show the remaining HP in blocks
        int health_point_block = (int) ((player.getCurrent_health_point() / (player.getHealth_point() + 0.0)) * 10);
        player_status_print += "\nHP  : ";
        for (int index = 0; index < 10; index++) {
            if (index < health_point_block) {
                player_status_print += UserInterface.ANSI_GREEN_BG + " ■ " + UserInterface.ANSI_RESET;
            } else {
                player_status_print += UserInterface.ANSI_GREEN_BG + "    " + UserInterface.ANSI_RESET;
            }
        }
        player_status_print += " " + player.getCurrent_health_point() + " / " + player.getHealth_point(); // Show the HP in number.
        player_status_print += "\n"; // Put new line.

        if (player.isAwakened()) {
            // Skill is usable only when the player has awakened his/her inner power.
            // Show the remaining stamina point in blocks.
            int skill_point_block = (int) ((player.getCurrent_stamina_point() / (player.getStamina_point() + 0.0)) * 10);
            player_status_print += "SP  : ";
            for (int index = 0; index < 10; index++) {
                if (index < skill_point_block) {
                    player_status_print += UserInterface.ANSI_CYAN_BG + " ■ " + UserInterface.ANSI_RESET;
                } else {
                    player_status_print += UserInterface.ANSI_CYAN_BG + "    " + UserInterface.ANSI_RESET;
                }
            }
            player_status_print += " " + player.getCurrent_stamina_point() + " / " + player.getStamina_point() + UserInterface.ANSI_RESET; // Show the SP in number.
            player_status_print += "\n"; // Put new line.
        }

        if (player.getBuff_point() >= 0) {
            // Show the remaining counter of buff
            player_status_print += "BUFF : ";
            for (int index = 0; index <= player.getBuff_point(); index++) {
                player_status_print += "■ ";
            }
            player_status_print += " 남은 버프 수 " + (player.getBuff_point() + 1);
            player_status_print += "\n"; // Put new line.
        }

        // Show equipment status.
        if(player.getEquipped_armor()[0] != null || player.getEquipped_armor()[1] != null || player.getEquipped_weapon() != null){
            // If upper armor has been equipped.
            if(player.getEquipped_armor()[0] != null){
                player_status_print += player.getEquipped_armor()[0].getName() + " : ";
                for(int index = 0 ; index < player.getEquipped_armor()[0].getDurability() ; index++){
                    player_status_print += "■ ";
                }
                player_status_print += " Durability " + player.getEquipped_armor()[0].getDurability();
                player_status_print += "\n"; // Put new line.
            }

            if(player.getEquipped_armor()[1] != null){
                player_status_print += player.getEquipped_armor()[1].getName() + " : ";
                for(int index = 0 ; index < player.getEquipped_armor()[1].getDurability() ; index++){
                    player_status_print +="■ ";
                }
                player_status_print += " Durability " + player.getEquipped_armor()[1].getDurability();
                player_status_print += "\n"; // Put new line.
            }

            if(player.getEquipped_weapon() != null){
                player_status_print += player.getEquipped_weapon().getName() + " : ";
                for(int index = 0 ; index < player.getEquipped_weapon().getDurability() ; index++){
                    player_status_print += "■ ";
                }
                player_status_print += " Durability " + player.getEquipped_weapon().getDurability();
                player_status_print += "\n"; // Put new line.
            }
        }

        // Show the EXP blocks, only if the player level is less than 99.
        if (player.getLevel() <= 99){
            int experience_point_block = (int)((player.getAccumulated_experience() % player.getRequired_experience() / (player.getRequired_experience() + 0.0)) * 10);
            player_status_print += "EXP : [";
            for (int index = 0; index < 10; index++) {
                if (index < experience_point_block) {
                    player_status_print += "■";
                } else {
                    player_status_print += "  ";
                }
            }
            player_status_print += "]  " + player.getAccumulated_experience() % player.getRequired_experience() + " / " + player.getRequired_experience(); // Show the HP in number.
            player_status_print += "\n"; // Put new line.
        }

        System.out.println(player_status_print);
    }

    /**
     * Show battle status
     */
    public static void showBattleStatus(Player player){
        String player_status_print = "\n";
        // Show the icon that the player is in control of.
        player_status_print += "" + player.getIcon() + "  : Your Lv " + player.getLevel();

        // Show the remaining HP in blocks
        int health_point_block = (int) ((player.getCurrent_health_point() / (player.getHealth_point() + 0.0)) * 10);
        player_status_print += "\nHP : ";
        for (int index = 0; index < 10; index++) {
            if (index < health_point_block) {
                player_status_print += UserInterface.ANSI_GREEN_BG + " ■ " + UserInterface.ANSI_RESET;
            } else {
                player_status_print += UserInterface.ANSI_GREEN_BG + "    " + UserInterface.ANSI_RESET;
            }
        }
        player_status_print += " " + player.getCurrent_health_point() + " / " + player.getHealth_point(); // Show the HP in number.
        player_status_print += "\n"; // Put new line.
        System.out.println(player_status_print);
    }


    /**
     * Show options for basic commands before the battle.
     */
    public void showCommands(boolean isEnemyNear, boolean player_awakened, boolean isVendorNear) {
        String command_print = "\n";
        for (int index = 0; index < StageMap.cols; index++) {
            command_print += "◇";
        }
        command_print += UserInterface.ANSI_CYAN + "\n\n　　　1. Move";
        command_print += "\n　　　2. Item" + UserInterface.ANSI_RESET;
        if (isEnemyNear) {
            // If an enemy is near the player, show 'Attack' option.
            command_print += UserInterface.ANSI_CYAN + "\n　　　3. Attack" + UserInterface.ANSI_RESET;
            if (player_awakened) {
                // Skill option will be available when the player has been awakened.
                command_print += UserInterface.ANSI_CYAN + "\n　　　4. Skill" + UserInterface.ANSI_RESET;
            }
        }

        if(isVendorNear) {
            command_print += UserInterface.ANSI_CYAN + "\n　　　6. Shop" + UserInterface.ANSI_RESET;
        }

        // Show an option for a manual.
        command_print += UserInterface.ANSI_CYAN + "\n\n　　　8. Game manual\n" + UserInterface.ANSI_RESET;
        // Show an option for playing/stopping the background music.
        if(MusicInterface.isMusicOn()){
            command_print += UserInterface.ANSI_CYAN + "　　　9. Turn BGM off\n　　　　 [Increase volume : +  / Decrease volume : - ]\n\n" + UserInterface.ANSI_RESET;
        } else {
            command_print += UserInterface.ANSI_CYAN + "　　　9. Turn BGM on\n　　　　 [Increase volume : +  / Decrease volume : - ]\n\n" + UserInterface.ANSI_RESET;
        }

        for (int index = 0; index < StageMap.cols; index++) {
            command_print += "◇";
        }
        command_print += "\n>>> ";

        System.out.print(command_print);
    }

    /**
     * Show cool time message.
     */
    public static void showCoolTimeMsg() {
        String command_print = "";
        for (int index = 0; index < StageMap.cols; index++) {
            command_print += "◇";
        }
        command_print += "\n\n\n　　　The cool down isn't over yet.\n\n\n";

        for (int index = 0; index < StageMap.cols; index++) {
            command_print += "◇";
        }

        System.out.print(command_print + "\n");
    }

    /**
     * Show guidance for moving.
     */
    public static void showMovingInfo(int player_movement) {
        String moving_print = "\n";
        for (int index = 0; index < StageMap.cols; index++) {
            moving_print += "◇";
        }
        moving_print += UserInterface.ANSI_CYAN + "\n\n　　　Control";
        moving_print += UserInterface.ANSI_YELLOW + "\n　　　A : Move one block To the left.　　　S : Move down by one block.\n　　　D : Move one block to the right.　　　W : Move up by one block." + UserInterface.ANSI_RESET;
        moving_print += "\n　　　0번 : Cancel 'Move'.\n";
        moving_print += UserInterface.ANSI_CYAN + "\nInput directions as many as you want.  \nYou can move a maximum of " + UserInterface.ANSI_RESET + player_movement + UserInterface.ANSI_CYAN + "blocks.\n" + UserInterface.ANSI_RESET;
        for (int index = 0; index < StageMap.cols; index++) {
            moving_print += "◇";
        }
        moving_print += "\n>>> ";
        System.out.print(moving_print);
    }

    /**
     * Show check if the movement input is correct.
     */
    public void validateMoveInput(String move_input, int player_movement) {
        this.isMoveInputValid = true;
        int input_length = move_input.length(); // Get the length of the input, to prevent indexOutOfBoundsException.

        if (input_length > player_movement) {
            input_length = player_movement; // Make sure that the number of readable characters do not exceed player's movement.
        }

        char[] input_chars = move_input.substring(0, input_length).toLowerCase().toCharArray(); // Change the String to an char array.

        for (char element : input_chars) {
            if (!(element == 'a' || element == 's' || element == 'd' || element == 'w')) {
                this.isMoveInputValid = false;
                break;
            }
        }
    }

    /**
     * Show the direction options for attack.
     */
    public static void showAttackingDirection() {
        // Show information about attacking.
        String attackDirection = "\n";
        for (int index = 0; index < StageMap.cols; index++) {
            attackDirection += "◇";
        }
        attackDirection += UserInterface.ANSI_CYAN + "\n\n　　　Select direction for attack.\n\n";
        attackDirection += UserInterface.ANSI_YELLOW + "　　　A : Left\n　　　S : Down\n　　　D : Right\n　　　W : Up\n" + UserInterface.ANSI_RESET;
        attackDirection += "\n　　　0번 : Cancel 'Attack'.\n\n";

        for (int index = 0; index < StageMap.cols; index++) {
            attackDirection += "◇";
        }
        attackDirection += "\n>>> ";
        System.out.print(attackDirection);
    }

    /**
     * Show the directions options for skill.
     */
    public static void showSkillDirection(String skillName) {
        String skillDirection = "\n";

        for (int index = 0; index < StageMap.cols; index++) {
            skillDirection += "◇";
        }

        // Show information about skill.
        skillDirection += UserInterface.ANSI_CYAN + "\n\n　　　Decide the direction for using skill, " + skillName + ".\n\n";
        skillDirection += UserInterface.ANSI_YELLOW + "　　　A : Left\n　　　S : Down\n　　　D : Right\n　　　W : Up\n" + UserInterface.ANSI_RESET;
        skillDirection += "\n　　　0번 : Cancel 'Skill'.\n\n";

        for (int index = 0; index < StageMap.cols; index++) {
            skillDirection += "◇";
        }
        skillDirection += "\n>>> ";
        System.out.print(skillDirection);
    }

    /**
     * Check the input for the attacking direction
     *
     * @return result : True, if the input is correct.
     */
    public static boolean checkAttackingDirection(String direction) {
        boolean result = false;
        if (direction.equals("a") || direction.equals("s") || direction.equals("d") || direction.equals("w") || direction.equals("0")) {
            // Return true if the input has either directions or a cancel option.
            result = true;
        }
        return result;
    }

    /**
     * Show player's turn
     */
    public static void showPlayerPhase() {
        System.out.println(UserInterface.ANSI_GREEN_BG + " ▼ Your turn. " + UserInterface.ANSI_RESET);
    }

    /**
     * Show enemies' turn
     */
    public static void showEnemyPhase() {
        System.out.println(UserInterface.ANSI_RED_BG + " ▼ Enemies' turn. " + UserInterface.ANSI_RESET);
    }

    /**
     * Show the options for skills
     */
    public void showSkills() {
        String skill_print = "\n";

        for (int index = 0; index < StageMap.cols; index++) {
            skill_print += "◇";
        }

        skill_print += UserInterface.ANSI_CYAN + "\n\n　　　1. Mega punch (Attacking skill : Consumes 3 SP)" + UserInterface.ANSI_RESET;
        skill_print += UserInterface.ANSI_CYAN + "\n　　　2. 점프 킥 (Attacking skill : Consumes 4 SP)" + UserInterface.ANSI_RESET;
        skill_print += UserInterface.ANSI_CYAN + "\n　　　3. 그라운드 파운드 (Attacking skill : Consumes 5 SP)" + UserInterface.ANSI_RESET;
        skill_print += UserInterface.ANSI_CYAN + "\n　　　4. 포효 (Buff skill : Consumes 6 SP)" + UserInterface.ANSI_RESET;
        skill_print += "\n\n　　　0. Cancel 'Skill'.\n\n";

        for (int index = 0; index < StageMap.cols; index++) {
            skill_print += "◇";
        }

        skill_print += "\n>>> ";
        System.out.print(skill_print);
    }

    /**
     * Show the warning for insufficient SP.
     */
    public void showNotEnoughSP() {
        System.out.println(UserInterface.ANSI_RED + "SYSTEM :  Not enough SP." + UserInterface.ANSI_RESET);
    }

    /**
     * Show the there is no enemy.
     */
    public void showNoEnemyMsg() {
        System.out.println(UserInterface.ANSI_RED + "SYSTEM :  There is no enemy at the direction." + UserInterface.ANSI_RESET);
    }

    /**
     * Show all the items the player has got.
     */
    public static void showInventory(Player player) {
        String item_print = "\n";
        Item[] inventory = player.getInventory(); // For readability, make a temporary inventory variable.

        for (int index = 0; index < StageMap.cols; index++) {
            item_print += "◇";
        }

        item_print += "\n\n　　　A list of your items : \n";
        for (int index = 0; index < inventory.length; index++) {
            if (inventory[index] != null) { // As long as there is an item in the inventory,
                item_print += UserInterface.ANSI_CYAN + "\n　　　" + (index + 1) + ". " + inventory[index].getName() + "　　　 " + inventory[index].getDescription() + UserInterface.ANSI_RESET; // Show the name of the item in the inventory.
            } else {
                item_print += UserInterface.ANSI_CYAN + "\n　　　" + (index + 1) + ". " + "(N/A)" + UserInterface.ANSI_RESET; // Show that the current slot is empty.
            }
        }
        item_print += "\n\n　　　0. Cancel 'Item'.\n\n";

        for (int index = 0; index < StageMap.cols; index++) {
            item_print += "◇";
        }

        item_print += "\nWhich item do you want to use?\n>>> ";
        System.out.print(item_print);
    }

    /**
     * Check the input of the item index from 0 to 9 only.
     */
    public boolean checkItemInput(String item_selection) {
        boolean result = false;
        result = item_selection.matches("[1-9]|[1-9][0-9]{1,10}"); // Get the matching result.
        this.isItemInputValid = result;
        return result;
    }

    public void setOnAttackingDirection(boolean onAttackingDirection) {
        this.onAttackingDirection = onAttackingDirection;
    }

    public void setMovable(boolean movable) {
        this.isMovable = movable;
    }

    public void setOnMovingInfo(boolean onMovingInfo) {
        this.onMovingInfo = onMovingInfo;
    }

    public boolean isMoveInputValid() {
        return isMoveInputValid;
    }

    public void setOnInventory(boolean onInventory) {
        this.onInventory = onInventory;
    }

    public void setItemInputValid(boolean itemInputValid) {
        this.isItemInputValid = itemInputValid;
    }

    public void setItemUsed(boolean itemUsed) {
        this.isItemUsed = itemUsed;
    }

    public void setAttackDirectionValid(boolean attackDirectionValid) {
        isAttackDirectionValid = attackDirectionValid;
    }

    public void setOnSkills(boolean onSkills) {
        this.onSkills = onSkills;
    }

    public void setSPEnough(boolean SPEnough) {
        isSPEnough = SPEnough;
    }

    public Runnable getRunnable() {
        return this.runnable;
    }

    public boolean isEnemyNear() {
        return this.isEnemyNear;
    }

    public void setDoesEnemyExist(boolean doesEnemyExist) {
        this.doesEnemyExist = doesEnemyExist;
    }

    private void showAttackingPlayerResult(){

        if(boss.getAttackResult() != null){
            System.out.println(boss.getAttackResult());
        }

        for(int index = 0; index < enemies.length ; index++){
            if(enemies[index].getAttackResult() != null) {
                System.out.println(enemies[index].getAttackResult());
            }
        }
    }

    public void resetAttackingPlayerResult(){
        boss.setAttackResult(null);
        for(int index = 0; index < enemies.length ; index++){
            enemies[index].setAttackResult(null);
        }
    }
    public void targetCharacters(Player player, EnemyBoss boss, Enemy[] enemies, Vendor vendor){
        this.player = player;
        this.boss = boss;
        this.enemies = enemies;
        this.vendor = vendor;
    }

    public void setSkillUsed(boolean skillUsed) {
        isSkillUsed = skillUsed;
    }

    public void setOnSkillDirection(boolean onSkillDirection) {
        this.onSkillDirection = onSkillDirection;
    }

    public void setChosenSkill(String chosenSkill) {
        this.chosenSkill = chosenSkill;
    }

    public void setBuffSkillUsed(boolean buffSkillUsed) {
        isBuffSkillUsed = buffSkillUsed;
    }

    public void activateBattleUI(){
        this.setPriority(10);
        this.thread.start();
    }

    public void pauseBattleUI(){
        this.thread_flag.set(false);
        this.thread.interrupt();
        this.thread = null;

    }

    public void resumeBattleUI(){
        this.thread_flag.set(true);
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Show all the interface related to the battle.
     */
    public void showAllBattleUI() {
        UserInterface.clearConsole();
        StageMap.displayStageMap(); // Show the latest state of the stage.
        this.showPlayerStatus(this.player); // Show the current condition of the player.
        this.isEnemyNear = StageMap.checkCloseEnemy(this.player.getCurrent_pos()); // Check if the player is close to the enemy.
        this.isVendorNear = this.player.isVendorNear(vendor.getCurrent_pos()); // Check if the vendor is near by.

        if(this.isEnemyNear){
            // If the player is close to the enemy, show the battle scene.
            this.showAttackingPlayerResult();
        } else {
            // If the player is not close to the enemy, erase the previous battle scene.
            this.resetAttackingPlayerResult();
        }

        //Show general commands.
        if(this.onMovingInfo){
            // Show information about making move.
            this.showMovingInfo(this.player.getMove_distance());
            if(!(this.isMoveInputValid)){
                // When there is problem with the move input.
                System.out.println("\nSYSTEM : " + UserInterface.ANSI_RED + "Input direction keys only. (A,S,W,D)" + UserInterface.ANSI_RESET);
            } else if (!this.isMovable){
                // If there is an obstacle on the map, show a warning that proceeding is not possible.
                System.out.println("\nSYSTEM : " + UserInterface.ANSI_RED + "Couldn't move. There is something blocking you." + UserInterface.ANSI_RESET);
            }
        } else if (this.onInventory) {
            // Show the player's inventory.
            this.showInventory(this.player); // Show the player's inventory.
            if(isItemUsed) {
                // Show the effect of the item that player has used.
                System.out.println(this.player.getUsedItemEffect());
            }
        } else if (this.onAttackingDirection) {
            // When the player is near the Enemy and able to engage an attack.
            this.showAttackingDirection();
            if(this.isAttackDirectionValid){
                // Display the result of player's attack.
                System.out.println(this.player.getAttackStatus());
                // Show the message of the level-up.
                if(this.player.getLevelUpStatus().length() > 0) {
                    System.out.println(this.player.getLevelUpStatus());
                }
                // Show the obtained item.
                if(this.player.getObtainedItemStatus().length() > 0) {
                    System.out.print(this.player.getObtainedItemStatus());
                }
            }
        } else if (this.onSkills) {
            // Display available skills.
            if(!this.onSkillDirection){
                this.showSkills();
            }
            if(!this.isSPEnough) {
                // When there is not enough SP to use the skill.
                this.showNotEnoughSP();
            } else if(this.onSkillDirection) {
                // Display the direction to use the skill.
                this.showSkillDirection(this.chosenSkill); // Show the skill direction
                if(!this.doesEnemyExist){
                    // When there is no enemy near by the player.
                    this.showNoEnemyMsg();
                } else if (this.isSkillUsed) {
                    // Display the message that player used skill successfully.
                    System.out.println(this.player.getUsedSkillStatus());
                    // Show the message of the level-up.
                    if (this.player.getLevelUpStatus().length() > 0) {
                        System.out.println(this.player.getLevelUpStatus());
                    }
                    // Show the obtained item.
                    if (this.player.getObtainedItemStatus().length() > 0) {
                        System.out.print(this.player.getObtainedItemStatus());
                    }
                }
            } else if (this.isBuffSkillUsed) {
                // Show buff message.
                System.out.println(this.player.getUsedSkillStatus());
            }
        } else {
            this.showCommands(this.isEnemyNear, this.player.isAwakened(), this.isVendorNear);
        }
    }

    @Override
    public  void run() {
        if(this.player.getCurrent_health_point() == 0) {
            UserInterface.clearConsole();
            StageMap.displayStageMap();
            BattleInterface.showPlayerStatus(this.player); // Show player's HP, SP, weapon, and the weapon's durability.
            Story.showGameOverMsg();
            System.exit(0);
            return;
        }
        this.showAllBattleUI();
    }
}
