package com.mudgame.mhk;

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
import com.mudgame.mhk.person.Vendor;
import com.mudgame.mhk.person.brawler.Player;
import com.mudgame.mhk.person.brawler.enemy.Enemy;
import com.mudgame.mhk.person.brawler.enemy.EnemyBoss;
import com.mudgame.mhk.playdata.PlayData;
import com.mudgame.mhk.stage.Stage;
import com.mudgame.mhk.stage.StageMap;
import com.mudgame.mhk.story.Story;
import com.mudgame.mhk.userinterface.BattleInterface;
import com.mudgame.mhk.userinterface.MusicInterface;
import com.mudgame.mhk.userinterface.ShopInterface;
import com.mudgame.mhk.userinterface.UserInterface;

import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main extends Thread{

    private static PlayData savedData = new PlayData(); // Object for storing and getting play data.
    private static Player player; // The game player.
    private static EnemyBoss boss;
    private static Enemy[] enemies;
    private static Vendor vendor;
    private static StageMap stageMap;
    private static BattleInterface BattleUI = new BattleInterface();
    private static Scanner scanner = new Scanner(System.in);
    private static MusicInterface music = new MusicInterface();
    private static ScheduledExecutorService scheduler;



    public static void main(String[] args) {

        boolean game_flag = true; // A flag for repeating the game. When the value is false, the game is terminated.
        boolean save_data_loaded = false; // A flag for checking if the saved data has been loaded.

        while(game_flag) {

            if(player == null){
                // Create a new player when there is no player object.
                player = new Player(false); // Apply saved data into the player
            }

            /**
             * Show the title screen.
             * Create a player with the data in the PlayData.
             */
            showTitle(save_data_loaded);

            /**
             * Show the prologue of the game only when no saved file has been loaded.
             * Create a player only when no saved file has been loaded.
             */
            showIntro(save_data_loaded);

            /**
             * Begin the stage that corresponds to the number of the stage.
             */
            if(!music.isMusicLoaded()){
                music.activateMusic(args); // Play BGM.
            } else {
                music.resumeMusic();
            }

            initiateStage(1, save_data_loaded);
            showLoading();

            initiateStage(2, save_data_loaded);
            showLoading();

            initiateStage(3, save_data_loaded);
            showLoading();

            initiateStage(4, save_data_loaded);
//            showLoading();

            /**
             * Show the epilogue of the game.
             */
            showEpilogue();
            UserInterface.clearConsole();
        }
    }

    /**
     * Start an introduction of the game.
     * @param save_data_loaded : A flag for checking whether saved data has been loaded or not.
     */
    private static void showTitle(boolean save_data_loaded) {
        boolean title_flag = false;
        String title_choice;
        while(!title_flag){
            UserInterface.showTitle(); // Show the title of the game and selectable options.
            title_choice = UserInterface.getInput(); // Receive player's input.

            // Available choices at the title screen.
            if(title_choice.equals("1")){
                /**
                 * Begin a new game smoothly, when no saved data has been found.
                 */
                System.out.println("SYSTEM : " + "\n게임을 시작합니다.");
                // Turn the 'title' screen loop off for good.
                title_flag = true;
            } else if (title_choice.equals("2")){
                // When "continue" is selected.
                if(!UserInterface.checkSaveFile()){
                    // If the save file do not exist, inform player about it.
                    UserInterface.informNoSavedData();
                } else {
                    // Load player's file and continue from the last game.
                    // TODO : Load player's file and continue from the last game.
                    if(player.isAwakened()) { // If the player cleared game at least once, do not reset the player data.
                        player.setAwakened(true); // Keep the awakened if the game has been cleared at least once.
                        player.upgradeIcons(); // Upgrade the player's icon.
                    }
                    save_data_loaded = true; // Terminate the closest while loop.
                    title_flag = true;
                }
            } else if (title_choice.equals("0")){
                // When "exit game" is selected.
                UserInterface.exitGame();
            } else {
                // When given options are not selected.
                UserInterface.invalidateInput();
                UserInterface.clearConsole();
            }
        }
    }

    /**
     * Start an introduction of the game.
     * @param save_data_loaded : A flag for checking whether saved data has been loaded or not.
     */
    private static void showIntro(boolean save_data_loaded) {
        if (!save_data_loaded) {
            boolean name_flag = false; // Repeat input process until the name is decided.
            while (!name_flag) {
                System.out.println("SYSTEM : " + "Enter your name.");
                String input_name = UserInterface.getInput();
                if(input_name.trim().length() == 0) {
                    // If the name is written with blank spaces, repeat the process.
                    System.out.println("SYSTEM : " + "Retry entering your name.");
                    continue;
                }

                if(!input_name.toLowerCase().equals("ryugagotoku")) {
                    // When the name is not for debug.
                    System.out.println("SYSTEM : " + "You've entered " + input_name + ". Is the name correct?\n　　　1. Yes.\n　　　2. Oops, wrong.\n");
                    String option_checker = UserInterface.getInput();
                    if(option_checker.equals("1")) {
                        if(player.isAwakened()) {
                            player.setAwakened(true); // Keep the awakened if the game has been cleared at least once.
                            player.upgradeIcons(); // Upgrade the player's icon.
                        } else {
                            player.setName(input_name); // Create the name of the player.
                            System.out.println("SYSTEM : " + input_name + ", let's begin the game."); // Show a welcome message.
                        }
                        System.out.println("\nSYSTEM : " + "Press 'Enter' key to continue.");
                        UserInterface.getInput(); // Wait for the 'Enter' key for the next dialogue.
                        name_flag = true; // Terminate the closest while loop.
                    } else if(option_checker.equals("2")){
                        // Repeat this process if the input name is incorrect.
                        System.out.println("SYSTEM : " + "Retry entering your name.");
                    } else {
                        // When none of the given options are selected.
                        UserInterface.invalidateInput();
                    }
                } else {
                    // FOR TESTING & DEBUGGING
                    // Change the player status into a debug mode, "ryugagotoku"
                    if(player.isAwakened()) {
                        player.setAwakened(true); // Keep the awakened if the game has been cleared at least once.
                        player.upgradeIcons(); // Upgrade the player's icon.
                    } else {
                        player = new Player(true); // Create a new player with debug mode on..
                        player.setAwakened(true); // For debugging
                        player.upgradeIcons(); // For debugging
                        player.getSkill(); // For debugging
                    }
                    name_flag = true; // Terminate the closest while loop.
                }
            }
            showLoading(); // Show the loading animation.
            UserInterface.clearConsole(); // Clear the logs.
            Story.showIntro(); // Show the introduction dialogue of the game.
            System.out.println("\nSYSTEM : " + "Press 'Enter' key to continue.");
            UserInterface.getInput(); // For the player's reading sake, pause the logic until Enter is pressed.
            UserInterface.clearConsole(); // Clear the logs.
        }
    }

    /**
     * Starts a stage that corresponds to the given stage number.
     * @param stage_num : The number of the stage to load.
     * @param save_data_loaded : A flag for checking whether saved data has been loaded or not.
     */
    private static void initiateStage(int stage_num, boolean save_data_loaded) {
        if(savedData.getCleared_stages()[stage_num - 1] || player.getCurrent_health_point() == 0){
            // If the current stage is cleared before, terminate the current stage method.
            // // If the player gets killed in the previous stage, terminate the current stage method.
            return;
        }

        UserInterface.clearConsole(); // Clean the console.

        if(stageMap == null){
            stageMap = new StageMap(stage_num); // Create the stage map.
        }

        if(BattleUI == null){
            BattleUI = new BattleInterface();
        }

        int [] current_pos = {stageMap.rows / 2, 2};
        player.setCurrent_pos(current_pos); // Set the position to default at the very beginning of the stage.

        Stage current_stage = new Stage(stage_num); // Create a stage.
        boss = new EnemyBoss(stage_num, player); // Create a boss of the stage.
        enemies = new Enemy[current_stage.getEnemies()]; // Create slot for enemies.

        // Put enemy objects in the 'enemies' array.
        for(int index = 0; index < enemies.length; index++){
            enemies[index] = new Enemy(stage_num, player); // Set level and status of the enemies.
            enemies[index].setName(enemies[index].getName() + Character.toString(65 + index)); // Set different enemy name.
        }


        player.targetEnemies(boss, enemies); // TODO
        vendor = new Vendor(player); // TODO

        if(stage_num == 2 || stage_num == 3){
            stageMap.setCurrent_section(0); // The number of the section of the map to be displayed.
            boss.setCurrent_section(stageMap.getCurrent_section());
        } else {
            stageMap.setCurrent_section(-1); // The number of the section of the map to be displayed.
            boss.setCurrent_section(stageMap.getCurrent_section());
        }

        current_stage.showStageImg(); // Show ASCII art of the current stage.
        System.out.println("\nSYSTEM : " + "Press 'Enter' key to continue.");
        UserInterface.getInput(); // For the player's reading sake, pause the logic until Enter is pressed.
        UserInterface.clearConsole(); // Clear the logs.

        // TODO : Activate thread
        // Update the position of every characters.
        StageMap.updateStageMap(player);
        for(int index = 0; index < enemies.length; index++){
            StageMap.updateStageMap(enemies[index]);
        }
        if(StageMap.getCurrent_section() == -1 || StageMap.getCurrent_section() == 2) {
            StageMap.updateStageMap(boss);
        }
        if(StageMap.getCurrent_section() == 1 && stage_num == 2){
            StageMap.updateStageMap(vendor);
        }
        activateTasks(); // Activate the threadPool.

        while(current_stage.getBoss() == 1) { // If the boss is not defeated, repeat the current stage process.

            // Update the position of every characters.
            StageMap.updateStageMap(player);
            for(int index = 0; index < enemies.length; index++){
                StageMap.updateStageMap(enemies[index]);
            }
            if(StageMap.getCurrent_section() == -1 || StageMap.getCurrent_section() == 2) {
                StageMap.updateStageMap(boss);
            }
            if(StageMap.getCurrent_section() == 1 && stage_num == 2){
                StageMap.updateStageMap(vendor);
            }
            if(checkScheduler()){
                // Activate the scheduler if it is shutdown.
                activateTasks();
            }

            BattleUI.showAllBattleUI();

            boolean sectionChange = false; // A flag for detecting the change of the section.

            String action_choice = null;
            while (action_choice == null) {
                action_choice = UserInterface.getInput();
            }

                if (action_choice.equals("1")) {
                    // 'Move' is selected.
                    boolean move_flag = false;  // The flag to loop the 'move command' until the 'move input' is valid.
                    while (!move_flag) {

                        BattleUI.setOnMovingInfo(true); // Notify BattleInterface that the player is currently in the 'move' section.
                        BattleUI.showAllBattleUI();

                        String move_input = null;
                        while (move_input == null) {
                            move_input = UserInterface.getInput();
                        }

                        if (move_input.equals("0")) {
                            break; // 'Move' has been cancelled. Quit the while loop that takes move_flag as a parameter.
                        }

                        // Check if the input for moving only has the following characters, a / s / d / w.
                        BattleUI.validateMoveInput(move_input, player.getMove_distance());

                        if (!(BattleUI.isMoveInputValid())) {
                            // When there is problem with the move input.
                            BattleUI.showAllBattleUI(); // Show the UI immediately.
                            continue; // Ignore the rest of the code under this line.
                        }

                        // Save the new position in checking_pos to check if it collides with any obstacles on the map.
                        player.checkMovedPos(move_input);
                        boolean movable = true;
                        BattleUI.setMovable(movable); // Sync the movable flag.

                        if (stage_num == 1 || stage_num == 4) {
                            // Find out if the checking_pos collides with any obstacles on the map.
                            // If true, convert it to false, meaning there's is an obstacle on the new position.
                            movable = !stageMap.checkObstacles(player.getChecking_pos());
                        } else {
                            // Find out if the checking_pos collides with any obstacles on the map.
                            // If true, convert it to false, meaning there's is an obstacle on the new position.
                            movable = !stageMap.checkObstacles(player.getChecking_pos(), StageMap.getCurrent_section());
                        }

                        BattleUI.setMovable(movable); // Sync the movable flag.

                        if (!movable) {
                            // If there is an obstacle on the map, show a warning that proceeding is not possible.
                            BattleUI.showAllBattleUI(); // Show the UI immediately.
                            player.setChecking_pos(player.getCurrent_pos());
                            continue; // Ignore the rest of the code under this line.
                        }

                        // For clearing the player's character on the map, save the current position.
                        player.setLatest_pos(player.getCurrent_pos());

                        // Clear previous position of the character on the map.
                        if (stage_num == 1 || stage_num == 4) {
                            // Clear the player's previous mark for the first stage and the last stage.
                            stageMap.clearCharacterIcon(player.getLatest_pos());
                        } else {
                            // Clear the player's previous mark for the second and third stage.
                            stageMap.clearCharacterIcon(player.getLatest_pos(), StageMap.getCurrent_section());
                        }

                        // Update the player's position.
                        player.setCurrent_pos(player.getChecking_pos());

                        if (stage_num == 2 || stage_num == 3) {
                            // Check for a dot on the player's temporary position, which shifts to a new section of the stage map.
                            sectionChange = stageMap.sectionChangeable(player.getChecking_pos(), StageMap.getCurrent_section()); // Detect if the section changes.

                            if (sectionChange) {
                                shutDownTasks();
                                // If the player's new position has a dot, change the section of the map.
                                int[] section_updated_pos = new int[2]; // Temporary storage for saving the player's new position.
                                // Keep the row the same, because the map section only changes when moved either to left or right.
                                section_updated_pos[0] = player.getCurrent_pos()[0];
                                if (player.getChecking_pos()[1] < (stageMap.movable_cols[1] / 2)) {
                                    // When the player has landed on the left side dots.
                                    section_updated_pos[1] = stageMap.movable_cols[1] - 1; // Keep the row position, change the column position only.
                                    player.setCurrent_pos(section_updated_pos); // Update the player's position state
                                    if (StageMap.getCurrent_section() > 0) {
                                        // Erase enemies in the current section
                                        for (int index = 0; index < enemies.length; index++) {
                                            stageMap.clearCharacterIcon(enemies[index].getCurrent_pos(), StageMap.getCurrent_section());
                                        }
                                        // Change the section of the map only within the validated range.
                                        StageMap.setCurrent_section(StageMap.getCurrent_section() - 1); // Go to the previous section of the map.
                                        boss.setCurrent_section(StageMap.getCurrent_section());
                                    }
                                } else {
                                    // When the player has landed on the right side dots.
                                    section_updated_pos[1] = stageMap.movable_cols[0]; // Keep the row position, change the column position only.
                                    player.setCurrent_pos(section_updated_pos);  // Update the player's position state
                                    if (StageMap.getCurrent_section() < 2) {
                                        // Erase enemies in the current section
                                        for (int index = 0; index < enemies.length; index++) {
                                            stageMap.clearCharacterIcon(enemies[index].getCurrent_pos(), StageMap.getCurrent_section());
                                        }
                                        // Change the section of the map only within the validated range.
                                        StageMap.setCurrent_section(StageMap.getCurrent_section() + 1); // Go to the next section of the map.
                                        boss.setCurrent_section(StageMap.getCurrent_section());
                                    }
                                }
                                // Regenerate enemy when the section has been changed.
                                for (int index = 0; index < enemies.length; index++) {
                                    enemies[index] = new Enemy(stage_num, player);
                                    enemies[index].setName(enemies[index].getName() + Character.toString(65 + index)); // Set different enemy name.
                                }
                            }
                        }
                        move_flag = true; // Quit the while loop that takes move_flag as a parameter.
                    }
                    BattleUI.setOnMovingInfo(false); // Notify BattleInterface that the player is currently in the 'move' section.
                    StageMap.updateStageMap(player);
                    for (int index = 0; index < enemies.length; index++) {
                        StageMap.updateStageMap(enemies[index]);
                    }
                    if (StageMap.getCurrent_section() == -1 || StageMap.getCurrent_section() == 2) {
                        StageMap.updateStageMap(boss);
                    }
                } else if (action_choice.equals("2")) {
                    // 'Use item' is selected.
                    boolean item_flag = false; // If there is problem with using the item, repeat the loop.
                    while (!item_flag) {

                        BattleUI.setOnInventory(true); // Set the inventory flag to true.
                        BattleUI.showAllBattleUI();

                        String item_selection = null;
                        while (item_selection == null) {
                            item_selection = UserInterface.getInput();
                        }

                        if (item_selection.equals("0")) {
                            // When using item has been cancelled.
                            BattleUI.setOnInventory(false); // Set the inventory flag to false.
                            BattleUI.setItemInputValid(true); // Reset the item input flag.
                            break;
                        }

                        if (!BattleUI.checkItemInput(item_selection)) {
                            // If the input had other than 0 ~ 9, show the error and repeat the process again.
                            continue;
                        }

                        int item_index = -1; // An item index of the player's inventory.

                        try {
                            item_index = Integer.parseInt(item_selection); // Convert the String number to Integer number for checking the player inventory.
                        } catch (Exception e) {
                            // If the conversion goes wrong, ignore the rest of the code.
                            continue;
                        }

                        if (item_index > player.getInventory().length) {
                            // If the input is higher than the inventory length, ignore the rest of the code
                            continue;
                        }


                        BattleUI.setItemUsed(true); // Set the flag that tells the item has been consumed successfully.
                        player.useSelectedItem(item_index); // Get the information about the potions.
                        BattleUI.showAllBattleUI(); // Redraw the UI

                        UserInterface.getInput(); // Wait for the player to press enter key. For displaying the item effect on the screen.
                        player.setUsedItemEffect(null); // Since the player read the item effect, reset the string.
                        BattleUI.setItemUsed(false); // The item has been used. Return to the main UI.
                        BattleUI.setOnInventory(false); // Set the inventory flag to false.
                        item_flag = true; // If the item has been used successfully, exit the loop.
                    }
                } else if (BattleUI.isEnemyNear() && action_choice.equals("3")) {
                    // 'Attack' is selected.
                    boolean attacking_flag = false; // A flag for looping the attacking phase.
                    while (!attacking_flag) {

                        // Show the direction options for attack.
                        BattleUI.setOnAttackingDirection(true);
                        BattleUI.showAllBattleUI();

                        String attack_direction = null; // For the convenience, lower the case.
                        while (attack_direction == null) {
                            attack_direction = UserInterface.getInput().toLowerCase();
                        }

                        if (!BattleUI.checkAttackingDirection(attack_direction)) {
                            // If the player input incorrect options, loop the process.
                            continue;
                        }

                        if (attack_direction.equals("0")) {
                            BattleUI.setOnAttackingDirection(false);
                            break; // Cancel the attack phase.
                        }

                        int[] target_pos = null;
                        if (stage_num == 1 || stage_num == 4) {
                            target_pos = stageMap.getEnemyPos(attack_direction, player.getCurrent_pos()); // Get the position of the enemy.
                        } else {
                            target_pos = stageMap.getEnemyPos(attack_direction, player.getCurrent_pos(), StageMap.getCurrent_section()); // Get the position of the enemy.
                        }

                        if (target_pos == null) {
                            continue;  // If there is no enemy at the desired direction, loop the process.
                        }

                        player.attackEnemy(target_pos); // Attack the enemy.
                        BattleUI.setAttackDirectionValid(true); // Set the flag for showing player's attack to true.
                        player.setBuff_point(player.getBuff_point() - 1); // Decrease the buff counter.
                        BattleUI.showAllBattleUI(); // Redraw the UI.

                        UserInterface.getInput(); // Halt the scroll and show how much damage the player have inflicted.
                        BattleUI.setOnAttackingDirection(false);
                        BattleUI.setAttackDirectionValid(false);
                        player.setLevelUpStatus("");
                        attacking_flag = true; // Terminate the attacking_flag loop.
                    }
                } else if (BattleUI.isEnemyNear() && player.isAwakened() && action_choice.equals("4")) {

                    BattleUI.setOnSkills(true);

                    boolean skill_flag = false; // For controlling the while loop of 'skill'.
                    while (!skill_flag) {

                        BattleUI.showAllBattleUI();

                        String skill_select = null; // Halt the scroll and show how much damage the player have inflicted.
                        while (skill_select == null) {
                            skill_select = UserInterface.getInput();
                        }

                        BattleUI.setSPEnough(true); // Reset warning sign of insufficient SP.

                        if (skill_select.equals("1")) {
                            // When 'Mega Punch' is used, inflict 150% damage of the given attack_point.

                            if (player.getCurrent_stamina_point() < 3) {
                                // If there is insufficient stamina point to use the skill, skip this loop.
                                BattleUI.setSPEnough(false); // Set warning sign of insufficient SP
                                BattleUI.showAllBattleUI();
                                continue;
                            }

                            boolean skill_use_flag = false; // Check if the skill was used in the selected direction.
                            while (!skill_use_flag) {

                                BattleUI.setOnSkillDirection(true);
                                BattleUI.setChosenSkill("Mega punch");
                                BattleUI.showAllBattleUI();

                                String skill_direction = null; // Decide where to use the skill.
                                while (skill_direction == null) {
                                    skill_direction = UserInterface.getInput().toLowerCase();
                                }

                                BattleUI.setDoesEnemyExist(true); // Reset the message.

                                if (!BattleUI.checkAttackingDirection(skill_direction)) {
                                    // If the player input incorrect options, loop the process.
                                    continue;
                                }

                                if (skill_direction.equals("0")) {
                                    BattleUI.setOnSkillDirection(false);
                                    BattleUI.showAllBattleUI();
                                    break; // Cancel the attack phase.
                                }

                                int[] target_pos = null;
                                if (stage_num == 1 || stage_num == 4) {
                                    target_pos = stageMap.getEnemyPos(skill_direction, player.getCurrent_pos()); // Get the position of the enemy.
                                } else {
                                    target_pos = stageMap.getEnemyPos(skill_direction, player.getCurrent_pos(), StageMap.getCurrent_section()); // Get the position of the enemy.
                                }

                                if (target_pos == null) {
                                    BattleUI.setDoesEnemyExist(false); // Tell the flag that there is no enemy at the selected location.
                                    BattleUI.showAllBattleUI();
                                    continue;  // If there is no enemy at the desired direction, loop the process.
                                }

                                // Use 'Mega Punch'
                                BattleUI.setSkillUsed(true); // Show that the skill has been used.
                                player.setCurrent_stamina_point(player.getCurrent_stamina_point() - 3); // For using the skill, consume the stamina point.
                                player.useSkillOnEnemy(target_pos, "Mega punch"); // Attack the enemy.
                                player.setBuff_point(player.getBuff_point() - 1); // Decrease the buff counter
                                BattleUI.showAllBattleUI();

                                UserInterface.getInput(); // Halt the scroll and show how much damage the player have inflicted.
                                BattleUI.setSkillUsed(false);
                                BattleUI.setOnSkillDirection(false);
                                BattleUI.setOnSkills(false);
                                player.setLevelUpStatus("");
                                skill_use_flag = true; // Stop the while loop of the 'skill'.
                                skill_flag = true; // Stop the while loop of the 'skill'.
                            }
                        } else if (skill_select.equals("2")) {
                            // When 'Jump Kick' is used, inflict 175% damage of the given attack_point.


                            if (player.getCurrent_stamina_point() < 4) {
                                // If there is insufficient stamina point to use the skill, skip this loop.
                                BattleUI.setSPEnough(false); // Set warning sign of insufficient SP
                                BattleUI.showAllBattleUI();
                                continue;
                            }

                            boolean skill_use_flag = false; // Check if the skill was used in the selected direction.
                            while (!skill_use_flag) {

                                BattleUI.setOnSkillDirection(true);
                                BattleUI.setChosenSkill("Jump kick");
                                BattleUI.showAllBattleUI();

                                String skill_direction = null; // Decide where to use the skill.
                                while (skill_direction == null) {
                                    skill_direction = UserInterface.getInput().toLowerCase();
                                }

                                BattleUI.setDoesEnemyExist(true); // Reset the message.

                                if (!BattleUI.checkAttackingDirection(skill_direction)) {
                                    // If the player input incorrect options, loop the process.
                                    continue;
                                }

                                if (skill_direction.equals("0")) {
                                    BattleUI.setOnSkillDirection(false);
                                    BattleUI.showAllBattleUI();
                                    break; // Cancel the attack phase.
                                }

                                int[] target_pos = null;
                                if (stage_num == 1 || stage_num == 4) {
                                    target_pos = stageMap.getEnemyPos(skill_direction, player.getCurrent_pos()); // Get the position of the enemy.
                                } else {
                                    target_pos = stageMap.getEnemyPos(skill_direction, player.getCurrent_pos(), StageMap.getCurrent_section()); // Get the position of the enemy.
                                }

                                if (target_pos == null) {
                                    BattleUI.setDoesEnemyExist(false); // Tell the flag that there is no enemy at the selected location.
                                    BattleUI.showAllBattleUI();
                                    continue;  // If there is no enemy at the desired direction, loop the process.
                                }

                                // Use 'Jump Kick'
                                BattleUI.setSkillUsed(true); // Show that the skill has been used.
                                player.setCurrent_stamina_point(player.getCurrent_stamina_point() - 4); // For using the skill, consume the stamina point.
                                player.useSkillOnEnemy(target_pos, "Jump kick"); // Attack the enemy.
                                player.setBuff_point(player.getBuff_point() - 1); // Decrease the buff counter
                                BattleUI.showAllBattleUI();

                                UserInterface.getInput(); // Halt the scroll and show how much damage the player have inflicted.
                                BattleUI.setSkillUsed(false);
                                BattleUI.setOnSkillDirection(false);
                                BattleUI.setOnSkills(false);
                                player.setLevelUpStatus("");
                                skill_use_flag = true; // Stop the while loop of the 'skill'.
                                skill_flag = true; // Stop the while loop of the 'skill'.
                            }
                        } else if (skill_select.equals("3")) {
                            // When 'Ground Pound' is used, inflict 175% damage of the given attack_point.

                            if (player.getCurrent_stamina_point() < 5) {
                                // If there is insufficient stamina point to use the skill, skip this loop.
                                BattleUI.setSPEnough(false); // Set warning sign of insufficient SP
                                BattleUI.showAllBattleUI();
                                continue;
                            }

                            boolean skill_use_flag = false; // Check if the skill was used in the selected direction.
                            while (!skill_use_flag) {

                                BattleUI.setOnSkillDirection(true);
                                BattleUI.setChosenSkill("Jump kick");
                                BattleUI.showAllBattleUI();

                                String skill_direction = null; // Decide where to use the skill.
                                while (skill_direction == null) {
                                    skill_direction = UserInterface.getInput().toLowerCase();
                                }

                                BattleUI.setDoesEnemyExist(true); // Reset the message.

                                if (!BattleUI.checkAttackingDirection(skill_direction)) {
                                    // If the player input incorrect options, loop the process.
                                    continue;
                                }

                                if (skill_direction.equals("0")) {
                                    BattleUI.setOnSkillDirection(false);
                                    BattleUI.showAllBattleUI();
                                    break; // Cancel the attack phase.
                                }

                                int[] target_pos = null;
                                if (stage_num == 1 || stage_num == 4) {
                                    target_pos = stageMap.getEnemyPos(skill_direction, player.getCurrent_pos()); // Get the position of the enemy.
                                } else {
                                    target_pos = stageMap.getEnemyPos(skill_direction, player.getCurrent_pos(), StageMap.getCurrent_section()); // Get the position of the enemy.
                                }

                                if (target_pos == null) {
                                    BattleUI.setDoesEnemyExist(false); // Tell the flag that there is no enemy at the selected location.
                                    BattleUI.showAllBattleUI();
                                    continue;  // If there is no enemy at the desired direction, loop the process.
                                }

                                // 'Mega Punch' inflicts 150% damage of the given attack_point.
                                BattleUI.setSkillUsed(true); // Show that the skill has been used.
                                player.setCurrent_stamina_point(player.getCurrent_stamina_point() - 5); // For using the skill, consume the stamina point.
                                player.useSkillOnEnemy(target_pos, "Ground pound"); // Attack the enemy.
                                player.setBuff_point(player.getBuff_point() - 1); // Decrease the buff counter
                                BattleUI.showAllBattleUI();

                                UserInterface.getInput(); // Halt the scroll and show how much damage the player have inflicted.
                                BattleUI.setSkillUsed(false);
                                BattleUI.setOnSkillDirection(false);
                                BattleUI.setOnSkills(false);
                                player.setLevelUpStatus("");
                                skill_use_flag = true; // Stop the while loop of the 'skill'.
                                skill_flag = true; // Stop the while loop of the 'skill'
                            }
                        } else if (skill_select.equals("4")) {
                            // When 'Roar' is used, increase the attack_point by 150% for three turns.

                            if (player.getCurrent_stamina_point() < 6) {
                                // If there is insufficient stamina point to use the skill, skip this loop.
                                BattleUI.setSPEnough(false); // Set warning sign of insufficient SP
                                BattleUI.showAllBattleUI();
                                continue;
                            }

                            BattleUI.setSkillUsed(true); // Show that the skill has been used.
                            BattleUI.setBuffSkillUsed(true);

                            if (player.getBuff_point() < 0) {
                                // Roar can be applied only when the buff is not active.
                                player.setBefore_buff_attack_point(player.getAttack_point()); // Store the original attack power before the buff.
                                player.setCurrent_stamina_point(player.getCurrent_stamina_point() - 6); // For using the skill, consume the stamina point.
                                player.setAttack_point((int) (player.getAttack_point() * player.useSkill("Roar"))); // Increase the attack point.
                                player.setBuff_point(2);  // Set the buff counter.
                                player.useBuffSkill(true);
                            } else {
                                player.useBuffSkill(false);
                            }
                            BattleUI.showAllBattleUI();

                            UserInterface.getInput(); // Halt the scroll and show how much damage the player have inflicted.

                            BattleUI.setSkillUsed(false);
                            BattleUI.setBuffSkillUsed(false);
                            BattleUI.setOnSkills(false);
                            skill_flag = true; // Stop the while loop of the 'skill'
                        } else if (skill_select.equals("0")) {
                            // Cancel the skill.
                            BattleUI.setOnSkills(true);
                            BattleUI.showAllBattleUI();
                            break;
                        } else {
                        }
                    }
                    BattleUI.setOnSkills(false);
                } else if (action_choice.equals("6") && player.isVendorNear(vendor.getCurrent_pos())) {
                    // Use vendor's service.

                    shutDownTasks();

                    boolean isShopping = true;
                    while (isShopping) {

                        UserInterface.clearConsole();
                        ShopInterface.showShopMenu();

                        String trade_select = UserInterface.getInput();// The choice of what to do with the vendor.

                        if (trade_select.equals("1")) {
                            // Buy items
                            boolean boughtItem = false;
                            while(!boughtItem){
                                UserInterface.clearConsole();
                                ShopInterface.showBuyableItems(player);
                                boolean isInventoryFull = player.checkInventoryFull();
                                boolean notEnoughMoney = false;
                                String selected_item = UserInterface.getInput();// The choice of what to do with the vendor.

                                if(selected_item.equals("0")){
                                    boughtItem = true;
                                    break;
                                }

                                if(isInventoryFull){
                                    System.out.println("\n" + UserInterface.ANSI_RED + "SYSTEM : Your inventory is full.\n" + UserInterface.ANSI_RESET);
                                    UserInterface.getInput();
                                    continue;
                                }

                                if(selected_item.equals("1")){
                                    // Health Potion
                                    int item_price = new HealthPotion().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new HealthPotion());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("2")){
                                    // Super Health Potion
                                    int item_price = new SuperHealthPotion().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new SuperHealthPotion());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("3")){
                                    // Hyper Health Potion
                                    int item_price = new HyperHealthPotion().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new HyperHealthPotion());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("4")){
                                    // Stamina Potion
                                    int item_price = new StaminaPotion().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new StaminaPotion());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("5")){
                                    // Super Stamina Potion
                                    int item_price = new SuperStaminaPotion().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new SuperStaminaPotion());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("6")){
                                    // Hyper Stamina Potion
                                    int item_price = new HyperStaminaPotion().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new HyperStaminaPotion());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("7")){
                                    // Mix Potion
                                    int item_price = new MixPotion().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new MixPotion());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("8")){
                                    // Super Mix Potion
                                    int item_price = new SuperMixPotion().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new SuperMixPotion());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("9")){
                                    // Hyper Mix Potion
                                    int item_price = new HyperMixPotion().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new HyperMixPotion());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("10")){
                                    // MetalVest
                                    int item_price = new MetalVest().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new MetalVest());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("11")){
                                    // SpecialArmor
                                    int item_price = new SpecialArmor().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new SpecialArmor());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("12")){
                                    // Metal Greaves
                                    int item_price = new MetalGreaves().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new MetalGreaves());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("13")){
                                    // Special Greaves
                                    int item_price = new SpecialGreaves().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new SpecialGreaves());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("14")){
                                    // Katana
                                    int item_price = new Katana().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new Katana());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("15")){
                                    // Rapier
                                    int item_price = new Rapier().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new Rapier());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("16")){
                                    // Pipe
                                    int item_price = new Pipe().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new Pipe());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("17")){
                                    // Bat
                                    int item_price = new Bat().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new Bat());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("18")){
                                    // Magnum Revolver
                                    int item_price = new Magnum().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new Magnum());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else if(selected_item.equals("19")){
                                    // Shotgun
                                    int item_price = new Shotgun().getPrice();
                                    if(player.getMoney() >= item_price){
                                        player.buyItem(new Shotgun());
                                    } else {
                                        notEnoughMoney = true;
                                    }
                                } else {
                                    UserInterface.invalidateInput();
                                }

                                if(notEnoughMoney){
                                    System.out.println(UserInterface.ANSI_RED + "\nSYSTEM : You do not have enough money." + UserInterface.ANSI_RESET);
                                    System.out.println("\nSYSTEM : " + "Press 'Enter' key to continue.");
                                    UserInterface.getInput();
                                }
                            }
                        } else if (trade_select.equals("2")) {
                            // Sell items
                            // TODO
                            boolean soldItem = false;
                            while(!soldItem) {
                                UserInterface.clearConsole();
                                ShopInterface.showSalableItems(player);
                                String inventoryIndex = UserInterface.getInput();

                                if(inventoryIndex.equals("1")) {
                                    player.sellItem(Integer.parseInt("0"));
                                } else if(inventoryIndex.equals("2")){
                                    player.sellItem(Integer.parseInt("1"));
                                } else if(inventoryIndex.equals("3")){
                                    player.sellItem(Integer.parseInt("2"));
                                } else if(inventoryIndex.equals("4")){
                                    player.sellItem(Integer.parseInt("3"));
                                } else if(inventoryIndex.equals("5")){
                                    player.sellItem(Integer.parseInt("4"));
                                } else if(inventoryIndex.equals("6")){
                                    player.sellItem(Integer.parseInt("5"));
                                } else if(inventoryIndex.equals("7")){
                                    player.sellItem(Integer.parseInt("6"));
                                } else if(inventoryIndex.equals("8")){
                                    player.sellItem(Integer.parseInt("7"));
                                } else if(inventoryIndex.equals("9")){
                                    player.sellItem(Integer.parseInt("8"));
                                } else if(inventoryIndex.equals("10")){
                                    player.sellItem(Integer.parseInt("9"));
                                } else if(inventoryIndex.equals("0")) {
                                    soldItem = true;
                                } else {
                                    UserInterface.invalidateInput();
                                }
                            }
                        } else if (trade_select.equals("0")) {
                            // Stop shopping
                        System.out.println(UserInterface.ANSI_GREEN + "\n　　　Merchant " + UserInterface.ANSI_RESET + " : " + vendor.getFarewell_msg());
                        isShopping = false;
                    } else {
//                        UserInterface.invalidateInput();
                    }
                }
                System.out.println("\nSYSTEM : " + "Press 'Enter' key to continue.");
                UserInterface.getInput();
                activateTasks();
            } else if (action_choice.equals("8")) {
                // Show the manual for the game.

                //Stop threads to allow users to read the manual.
                shutDownTasks();
                UserInterface.clearConsole();
                UserInterface.showManual(); // Show the manual.
                System.out.println("\nSYSTEM : " + "To close the manual, press 'Enter' key.");
                UserInterface.getInput(); // Wait for the user to finish reading the manual.
                UserInterface.clearConsole();

                // Resume all the threads.
                activateTasks();
            } else if (action_choice.equals("9")) {
                // Turn on/off the BGM.
                if (MusicInterface.isMusicOn()) {
                    music.pauseMusic();
                } else {
                    music.resumeMusic();
                }
            } else if (action_choice.equals("+")) {
                    // Increase the volume
                    music.raiseVolume();
            } else if (action_choice.equals("-")) {
                    // Decrease the volume.
                    music.decreaseVolume();
            } else {
                // When none of the given options are selected, or input was incorrect.
                BattleUI.showAllBattleUI();
            }

            if (boss.getCurrent_health_point() == 0) {
                // If the current stage boss is dead, quit the current stage.
//                pauseAllThreads();
                shutDownTasks();
                stageMap = null; // Erase current map.
                BattleUI.resetAttackingPlayerResult(); // The battle message will reset.
                current_stage.setBoss(0); // Change the number of the current stage boss.
                Story.showClearedMsg(); // Show that the stage is cleared.
                player.recover(); // Restore the health point and stamina point of the player fully.
                System.out.println("\nSYSTEM : " + "Press 'Enter' key to continue.");
                UserInterface.getInput();
                break;
            }
        }
    }

    /**
     * Show the epilogue of the game.
     */
    private static void showEpilogue() {
        if(player.getCurrent_health_point() == 0){
            // Don't show the epilogue when the player gets killed.
            return;
        }
        UserInterface.clearConsole();
        Story.showStoryEpilogue();
        if(!player.isAwakened()){
            System.out.println("\nSYSTEM : " + "Skills are unlocked! You can use skills in the battle.\n");
        }
        player.setAwakened(true); // Unlock the hidden power.
        player.getSkill(); // Get skills
        music.pauseMusic();
        UserInterface.getInput();
    }

    /**
     * Get runnable tasks from classes.
     */
    public static void activateTasks(){
        BattleUI.targetCharacters(player, boss, enemies, vendor); // Connect the battle interface with all the characters.

        scheduler = Executors.newScheduledThreadPool( 9);

        for(int index = 0 ; index < enemies.length ; index++){
            scheduler.scheduleAtFixedRate(enemies[index].getRunnable(), 0, 2000, TimeUnit.MILLISECONDS);
        }
        scheduler.scheduleAtFixedRate(boss.getRunnable(), 1, 2000, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(vendor.getRunnable(), 2, 2000, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(BattleUI.getRunnable(), 100, 2000, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(player.getRunnable(), 200, 200, TimeUnit.MILLISECONDS);
    }

    /**
     * Check is the threadpool is running.
     * @return True, if the scheduler is terminated.
     */
    public static boolean checkScheduler(){
        return scheduler.isShutdown();
    }

    /**
     * Terminate the threadpool.
     */
    public static void shutDownTasks(){
        scheduler.shutdownNow();
    }

    /**
     * Activate all the threads that are necessary.
     */
    public static void activateAllThreads(){
        BattleUI.targetCharacters(player, boss, enemies, vendor); // Connect the battle interface with all the characters.
        player.activatePlayerStatus(); // Activate player's thread.
        BattleUI.activateBattleUI(); // Show the battle interface.
        boss.activateBoss(); // Activate auto-mode.
        for (int index = 0; index < enemies.length; index++) {
            enemies[index].activateEnemy();// Activate auto-mode.
        }
    }

    /**
     * Hold all the threads from running.
     */
    public static void pauseAllThreads(){
        // Hold all the threads.
        BattleUI.pauseBattleUI();
        player.pausePlayerStatus();
        boss.pauseBoss();
        for (int index = 0; index < enemies.length; index++) {
            enemies[index].pauseEnemy();
        }
    }

    /**
     * Resume all the halted threads.
     */
    public static void resumeAllThreads(){
        // Resume all the threads.
        BattleUI.resumeBattleUI();
        player.resumePlayerStatus();
        boss.resumeBoss();
        for (int index = 0; index < enemies.length; index++) {
            enemies[index].resumeEnemy();
        }
    }

    /**
     * Show loading animation
     */
    public static void showLoading(){
        int columns = 67; // The number of triangles to be printed.
        for(int index = 0 ; index < columns ; index++){
            UserInterface.clearConsole();
            System.out.println("Loading...  " + (int)((index / (columns + 0.0f) * 100)) + "%");
            for(int subIndex = 0; subIndex <= index; subIndex++)
                System.out.print("▶");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
    //                e.printStackTrace();
                }
            }
        System.out.println("\n");
    }

    /**
     * Play the background Music
     */
    public static void resumeMusic(){
        music.resumeMusic();
    }

    /**
     * Stop playing the background music.
     */
    public static void pauseMusic(){
        music.pauseMusic();
    }
}
