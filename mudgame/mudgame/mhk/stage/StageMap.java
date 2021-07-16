package com.mudgame.mhk.stage;

import com.mudgame.mhk.person.Vendor;
import com.mudgame.mhk.person.brawler.Brawler;
import com.mudgame.mhk.person.brawler.Player;
import com.mudgame.mhk.person.brawler.enemy.Enemy;
import com.mudgame.mhk.person.brawler.enemy.EnemyBoss;
import com.mudgame.mhk.userinterface.UserInterface;

import java.util.Arrays;
import java.util.Random;

public class StageMap extends Thread {


    /**
     * All of the logical maps used in the game.
     * 8 different maps are created, and each map's has a dimension of 20(row) by 40(column).
     *
     * (int)
     * rows : A number of rows in a map.
     * cols : A number of columns in a map
     * sections : A number of different maps
     * stageMapArr : Stores ONE map for each stage.
     * stageMapsArr : Store three maps for each stage.
     *
     * (int [])
     * movable_rows, movable_cols : The allowed space for dispatching characters. The minimum value is stored in [0], the maximum value is stored in [1].
     *
     * //TODO PLAYER COMMENT
     */
    public static final int rows = 20;
    public static final int cols = 40;
    public static final int[] movable_rows = {1, 19};
    public static final int[] movable_cols = {1, cols - 1};
    private static int sections = 3;
    private static volatile int current_section = -1;
    private static int stage_num = 0;
    private static String[][] stageMapArr;
    private static String[][][] stageMapsArr;
    private static boolean thread_flag = true;

    public static String[][] getStageMap() {
        return stageMapArr;
    }

    public static String[][][] getStageMaps() {
        return stageMapsArr;
    }

    public StageMap(int stage_number) {
        if(stage_number == 1) {
            // Create the 1st stage map.
            stage_num = stage_number;
            createOneStageMap();
        } else if (stage_number == 2 || stage_number == 3) {
            // Create the 2nd and 3rd stage map.
            stage_num = stage_number;
            createStageMaps();
        } else if (stage_number == 4) {
            // Create the 4th stage map.
            stage_num = stage_number;
            createLastStageMap();
        }
    }

    private void createOneStageMap() {
        stageMapArr = new String[rows][cols]; // Set size of the map.
        stageMapsArr = null; // Save memory by emptying the 2nd, 3rd stage map.
        current_section = -1; // Mark that the stage has a single map.
        Random random = new Random(); // For making obstacles in the map.

        // Create the 1st stage map
        for(int row = 0 ; row < rows ; row++) {
            for (int col = 0 ; col < cols ; col++) {
                // Make a border of the map
                if (row == 0 || row == rows - 1) {
                    // Borderline for upper and lower parts of the map
                    stageMapArr[row][col] = "ー";
                } else if ((row != 0 && row != rows - 1) && (col == 0 || col == cols - 1)) {
                    // Borderline for left and right parts of the map
                    stageMapArr[row][col] = "｜";
                } else {
                    // Insert obstacles or props on the map.
                    boolean is_block_available = random.nextInt(cols) % rows == 0;
                    if(is_block_available) {
                        stageMapArr[row][col] = "▩";
                    } else {
                        // Insert an empty space (movable area)
                        stageMapArr[row][col] = "　";
                    }
                }
            }
        }
    }

    private void createStageMaps() {
        stageMapsArr = new String[sections][rows][cols];  // Set size of the map.
        stageMapArr = null; // Save memory by emptying the 2nd, 3rd stage map.
        current_section = 0; // Mark that the stage has several maps.
        
        Random random = new Random(); // For making obstacles in the map.
        // Create the 2nd and 3rd stage maps
        for (int section = 0 ; section < sections; section++) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    // Make a border of the map
                    if (row == 0 || row == rows - 1) {
                        // Borderline for upper and lower parts of the map.
                        stageMapsArr[section][row][col] = "ー";
                    } else if ((row != 0 && row != rows - 1) && (col == 0 || col == cols - 1)) {
                        // Borderline for left and right parts of the map
                        if((section < sections - 1) && (col == cols - 1)) {
                            // Store an indication that moving the right map is possible.
                            if(row % 2 == 0) {
                                stageMapsArr[section][row][col] = "\u27A1";
                            } else {
                                stageMapsArr[section][row][col] = "・";
                            }
                        } else if ((section > 0) && (col == 0)) {
                            // Store an indication that moving the left map is possible.
                            if(row % 2 == 0){
                                stageMapsArr[section][row][col] = "\u2B05";
                            } else {

                                stageMapsArr[section][row][col] = "・";
                            }
                        } else {
                            // Store an indication that going further than the line is not possible.
                            stageMapsArr[section][row][col] = "｜";
                        }
                    } else {
                        // Insert obstacles or props on the map.
                        boolean is_block_available = random.nextInt(cols) % rows == 0;
                        if(is_block_available) {
                            stageMapsArr[section][row][col] = "▩";
                        } else {
                            // Insert an empty space (movable area)
                            stageMapsArr[section][row][col] = "　";
                        }
                    }
                }
            }
        }
    }

    private void createLastStageMap() {
        stageMapArr = new String[rows][cols];  // Set size of the map.
        stageMapsArr = null; // Save memory by emptying the 2nd, 3rd stage map.
        current_section = -1; // Mark that the stage has a single map.

        // Create the 4th stage map -start-
        for(int row = 0 ; row < rows ; row++) {
            for (int col = 0 ; col < cols ; col++) {
                // Make a border of the map
                if (row == 0 || row == rows - 1) {
                    // Borderline for upper and lower parts of the map
                    stageMapArr[row][col] = "♨";
                } else if ((row != 0 && row != rows - 1) && (col == 0 || col == cols - 1)) {
                    // Borderline for left and right parts of the map
                    stageMapArr[row][col] = "♨";
                } else {
                    if((row >= 2 && row <= 5) && (col % 8 == 0)) {
                        // Insert obstacles or props on the map.
                        stageMapArr[row][col] = "♣";
                    } else if((row >= 14 && row <= 17) && col % 8 == 0) {
                        // Insert obstacles or props on the map.
                        stageMapArr[row][col] = "♣";
                    } else {
                        // Insert an empty space (movable area)
                        stageMapArr[row][col] = "　";
                    }
                }
            }
        }
    }

    /**
     * Show the map(s).
     */
    public static void displayStageMap() {
        String map = "\n";
        if(current_section < 0){
            for (int row = 0; row < stageMapArr.length; row++) {
                for (int col = 0; col < stageMapArr[row].length; col++) {
                    map += stageMapArr[row][col];
                }
                map += "\n";
            }
        } else {
            for (int row = 0; row < stageMapsArr[current_section].length; row++) {
                for (int col = 0; col < stageMapsArr[current_section][row].length; col++) {
                    map += stageMapsArr[current_section][row][col];
                }
                map += "\n";
            }
        }
        System.out.print(map);
    }

    /**
     * Update the location of the characters on the map.
     */
    public static void updateStageMap(Brawler brawler) {
        // Update the map(s) that corresponds to the stage.

        if(current_section < 0) {
            // Stage 1 and 4 map.

            // The player's icon.
            if(brawler instanceof Player){
                Player temp_player = (Player)brawler;
                stageMapArr[temp_player.getCurrent_pos()[0]][temp_player.getCurrent_pos()[1]] = temp_player.getIcon();
            } else if(brawler instanceof EnemyBoss) {
                // The enemy boss's icon
                EnemyBoss temp_boss = (EnemyBoss)brawler;
                stageMapArr[temp_boss.getCurrent_pos()[0]][temp_boss.getCurrent_pos()[1]] = temp_boss.getIcon();
            } else if (brawler instanceof Enemy) {
                // The enemies' icon.
                Enemy temp_enemy = (Enemy) brawler;

                boolean collide_flag = false;
                while (!collide_flag) {
                    String tile = stageMapArr[temp_enemy.getCurrent_pos()[0]][temp_enemy.getCurrent_pos()[1]];
                    String enemy_icon = temp_enemy.getIcon();

                    if (tile.equals(enemy_icon)) {
                        // If the tile is as same as the enemy icon, skip the current loop.
                        return;
                    }

                    //Check for any collision with the new location. Prevent overwriting boss's position with the enemy's position.
                    if (!collideWithObstacle(tile)) {
                        // Put the enemy's icon.
                        stageMapArr[temp_enemy.getCurrent_pos()[0]][temp_enemy.getCurrent_pos()[1]] = temp_enemy.getIcon();
                    } else {
                        // Generate a new position for the current enemy.
                        if (isEnemyOnSpot(tile)) {
                            // If the tile shows an ordinary enemy icon, put the enemy icon on the desired spot.
                            stageMapArr[temp_enemy.getCurrent_pos()[0]][temp_enemy.getCurrent_pos()[1]] = temp_enemy.getIcon();
                            collide_flag = true;
                        } else {
                            // Generate new position for the enemy.
                            temp_enemy.setCurrent_pos(temp_enemy.generatePos());
                        }
                    }
                }
            }
        } else {
            // Stage 2 and 3 map.

            // The player's icon.
            if(brawler instanceof Player){
                Player temp_player = (Player)brawler;
                stageMapsArr[current_section][temp_player.getCurrent_pos()[0]][temp_player.getCurrent_pos()[1]] = temp_player.getIcon();
            } else if(brawler instanceof EnemyBoss) {
                if(current_section == -1 || current_section == 2){
                    // The enemy boss's icon
                    EnemyBoss temp_boss = (EnemyBoss)brawler;
                    stageMapsArr[current_section][temp_boss.getCurrent_pos()[0]][temp_boss.getCurrent_pos()[1]] = temp_boss.getIcon();
                }
            } else if (brawler instanceof Enemy) {
                // The enemies' icon.
                Enemy temp_enemy = (Enemy) brawler;

                boolean collide_flag = false;
                while (!collide_flag) {

                    String tile = stageMapsArr[current_section][temp_enemy.getCurrent_pos()[0]][temp_enemy.getCurrent_pos()[1]];
                    String enemy_icon = temp_enemy.getIcon();

                    if (tile.equals(enemy_icon)) {
                        // If the tile is as same as the enemy icon, skip the current loop.
                        return;
                    }

                    //Check for any collision with the new location. Prevent overwriting boss's position with the enemy's position.
                    if (!collideWithObstacle(tile)) {
                        // Put the enemy's icon.
                        stageMapsArr[current_section][temp_enemy.getCurrent_pos()[0]][temp_enemy.getCurrent_pos()[1]] = temp_enemy.getIcon();
                    } else {
                        // Generate a new position for the current enemy.
                        if (isEnemyOnSpot(tile)) {
                            // If the tile shows an ordinary enemy icon, put the enemy icon on the desired spot.
                            stageMapsArr[current_section][temp_enemy.getCurrent_pos()[0]][temp_enemy.getCurrent_pos()[1]] = temp_enemy.getIcon();
                            collide_flag = true;
                        } else {
                            // Generate new position for the enemy.
                            temp_enemy.setCurrent_pos(temp_enemy.generatePos());
                        }
                    }
                }
            }

        }
    }
    public static void updateStageMap(Vendor vendor) {
        // Update the map(s) that corresponds to the stage.
        if(stage_num == 2 && current_section == 1) {
        // Stage 2 map, 2nd section area
            boolean collide_flag = false;
            while (!collide_flag) {

                String tile = stageMapsArr[current_section][vendor.getCurrent_pos()[0]][vendor.getCurrent_pos()[1]];

                //Check for any collision with the new location. Prevent overwriting boss's position with the vendor's position.
                if (!collideWithObstacleNoVendor(tile)) {
                    // Put the enemy's icon.
                    stageMapsArr[current_section][vendor.getCurrent_pos()[0]][vendor.getCurrent_pos()[1]] = vendor.getIcon();
                    collide_flag = true;
                } else {
                    // Generate a new position for the vendor
                    vendor.setCurrent_pos(vendor.generatePos());
                }
            }
        }
    }

    /**
     * Find out if the the new location in occupied by something or someone.
     * @param  pos : A temporarily saved position of the player's location.
     * @return If there is something, return true.
     */
    public boolean checkObstacles(int [] pos) {
        boolean result;
        String tile = stageMapArr[pos[0]][pos[1]];
        result = collideWithObstacle(tile);
        return result;
    }
    public boolean checkObstacles(int [] pos, int section) {
        boolean result;
        String tile = stageMapsArr[section][pos[0]][pos[1]];
        result = collideWithObstacle(tile);
        return result;
    }

    /**
     * Find out if the the new location in occupied by something or someone.
     * @param  pos : A temporarily saved position of the player's location.
     * @return If there is something, return true.
     */
    public static boolean checkCloseEnemy(int [] pos) {
        boolean result = false;
        int checking_directions = 4;
        if(current_section < 0){
            for (int index = 0; index < checking_directions; index++){
                if(index < 2) {
                    // Check upper and lower area of the player.
                    String tile = stageMapArr[pos[0] + (int)Math.pow(-1, index + 1)][pos[1]]; // Temporarily store the object that is at this spot.
                    result = isEnemyNearBy(tile); // Find if there really is an enemy on the spot.
                    if(result){
                        break; // If there is an enemy near by, terminate the loop.
                    }
                } else if (index >= 2 && index < 4){
                    // Check left and right area of the player.
                    String tile = stageMapArr[pos[0]][pos[1]+ (int)Math.pow(-1, index)]; // Temporarily store the object that is at this spot.
                    result = isEnemyNearBy(tile); // Find if there really is an enemy on the spot.
                    if(result){
                        break; // If there is an enemy near by, terminate the loop.
                    }
                }
            }
        } else {
            for (int index = 0; index < checking_directions; index++){
                if(index < 2) {
                    // Check upper and lower area of the player.
                    String tile = stageMapsArr[current_section][pos[0] + (int)Math.pow(-1, index + 1)][pos[1]]; // Temporarily store the object that is at this spot.
                    result = isEnemyNearBy(tile); // Find if there really is an enemy on the spot.
                    if(result){
                        break; // If there is an enemy near by, terminate the loop.
                    }
                } else if (index >= 2 && index < 4){
                    // Check left and right area of the player.
                    String tile = stageMapsArr[current_section][pos[0]][pos[1]+ (int)Math.pow(-1, index)]; // Temporarily store the object that is at this spot.
                    result = isEnemyNearBy(tile); // Find if there really is an enemy on the spot.
                    if(result){
                        break; // If there is an enemy near by, terminate the loop.
                    }
                }
            }
        }
        return result;
    }

    /**
     * Find out if the the new location in occupied by something or someone.
     * @param tile : All the objects that can be displayed on the map.
     * @return If there is collision, return true.
     */
    public static boolean collideWithObstacle(String tile) {
        boolean result;
        result = (
            tile.equals(UserInterface.ANSI_RED + "☆" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "★" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_YELLOW + "♡" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_YELLOW + "♥" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_YELLOW_BG + "♡" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_YELLOW_BG + "♥" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "\uD83D\uDC80" + UserInterface.ANSI_RESET)
            || tile.equals("♨")
            || tile.equals("♣")
            || tile.equals("▩")
            || tile.equals("｜")
            || tile.equals("ー")
            || tile.equals("・")
            || tile.equals("\u27A1")
            || tile.equals("\u2B05")
            || tile.equals(UserInterface.ANSI_RED + "▣" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "□" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "◈" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "◇" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_CYAN + "\uD83D\uDD30" + UserInterface.ANSI_RESET)
        );
        return result;
    }
    public static boolean collideWithObstacleNoVendor(String tile) {
        boolean result;
        result = (
                tile.equals(UserInterface.ANSI_RED + "☆" + UserInterface.ANSI_RESET)
                        || tile.equals(UserInterface.ANSI_RED + "★" + UserInterface.ANSI_RESET)
                        || tile.equals(UserInterface.ANSI_YELLOW + "♡" + UserInterface.ANSI_RESET)
                        || tile.equals(UserInterface.ANSI_YELLOW + "♥" + UserInterface.ANSI_RESET)
                        || tile.equals(UserInterface.ANSI_YELLOW_BG + "♡" + UserInterface.ANSI_RESET)
                        || tile.equals(UserInterface.ANSI_YELLOW_BG + "♥" + UserInterface.ANSI_RESET)
                        || tile.equals(UserInterface.ANSI_RED + "\uD83D\uDC80" + UserInterface.ANSI_RESET)
                        || tile.equals("♨")
                        || tile.equals("♣")
                        || tile.equals("▩")
                        || tile.equals("｜")
                        || tile.equals("ー")
                        || tile.equals("・")
                        || tile.equals("\u27A1")
                        || tile.equals("\u2B05")
                        || tile.equals(UserInterface.ANSI_RED + "▣" + UserInterface.ANSI_RESET)
                        || tile.equals(UserInterface.ANSI_RED + "□" + UserInterface.ANSI_RESET)
                        || tile.equals(UserInterface.ANSI_RED + "◈" + UserInterface.ANSI_RESET)
                        || tile.equals(UserInterface.ANSI_RED + "◇" + UserInterface.ANSI_RESET)
        );
        return result;
    }

    /**
     * Check if the enemy is near the player.
     * @param tile : The place to inspect.
     * @return If there is an enemy, return true.
     */
    public static boolean isEnemyNearBy(String tile) {
        boolean result;
        result = (
            tile.equals(UserInterface.ANSI_RED + "☆" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "★" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "▣" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "□" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "◈" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "◇" + UserInterface.ANSI_RESET)
        );
        return result;
    }

    /**
     * Check if the only the enemy is on the spot
     * @param tile : The enemy's icon
     * @return  If there is an enemy, return true.
     */
    public static boolean isEnemyOnSpot(String tile) {
        boolean result;
        result = (
            tile.equals(UserInterface.ANSI_RED + "\uD83D\uDC80" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "▣" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "□" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "◈" + UserInterface.ANSI_RESET)
            || tile.equals(UserInterface.ANSI_RED + "◇" + UserInterface.ANSI_RESET)
        );
        return result;
    }

    /**
     * Test if the character is beside the a dot, which indicates the player can move to next map.
     * @param pos : All the objects that can be displayed on the map.
     * @return If there is a dot on the new position, return true.
     */
    public static boolean sectionChangeable(int [] pos, int section) {
        boolean result; // True, when there is a dot beside the player.
        String tile; // Store a tile of the designated location.
        if(pos[1] > StageMap.movable_cols[1] / 2){
            // When the player is at the right-end side.
            tile = stageMapsArr[section][pos[0]][pos[1] + 1];
        } else {
            // When the player is at the right-end side.
            tile = stageMapsArr[section][pos[0]][pos[1] - 1];
        }
        result = tile.equals("・") || tile.equals("\u27A1") || tile.equals("\u2B05");
        return result;
    }

    /**
     * Clear the old character icon on the map.
     * @param player_pos : The last position where the player was.
     */
    public void clearCharacterIcon(int[] player_pos){
        stageMapArr[player_pos[0]][player_pos[1]] = "　";
    }

    /**
     * Clear the old character icon on the map.
     * @param player_pos : The last position where the player was.
     * @param section : The number of the section of the whole map.
     */
    public void clearCharacterIcon(int[] player_pos, int section){
        stageMapsArr[section][player_pos[0]][player_pos[1]] = "　";
    }

    /**
     * Attack the enemy on the desired location.
     * @param direction : The player's input of direction for attacking.
     * @param player_pos : The player's current position.
     * @return invalid_input : True, if the player input wrong command.
     */
    public static int [] getEnemyPos(String direction, int [] player_pos){
        int [] target_enemy_pos = null;
        String target = "";
        if(direction.equals("a")){
            // Attack left side
            target = stageMapArr[player_pos[0]][player_pos[1] - 1]; // Check if the enemy exists in the desired area.

            // Save the location of the enemy
            target_enemy_pos = new int[2];
            target_enemy_pos[0] = player_pos[0];
            target_enemy_pos[1] = player_pos[1] - 1;

        } else if (direction.equals("s")){
            // Attack down side
            target = stageMapArr[player_pos[0] + 1][player_pos[1]]; // Check if the enemy exists in the desired area.

            // Save the location of the enemy
            target_enemy_pos = new int[2];
            target_enemy_pos[0] = player_pos[0] + 1;
            target_enemy_pos[1] = player_pos[1];

        } else if (direction.equals("d")){
            // Attack right side
            target = stageMapArr[player_pos[0]][player_pos[1] + 1]; // Check if the enemy exists in the desired area.

            // Save the location of the enemy
            target_enemy_pos = new int[2];
            target_enemy_pos[0] = player_pos[0];
            target_enemy_pos[1] = player_pos[1] + 1;

        } else if (direction.equals("w")){
            // Attack upper side
            target = stageMapArr[player_pos[0] - 1][player_pos[1]]; // Check if the enemy exists in the desired area.

            // Save the location of the enemy
            target_enemy_pos = new int[2];
            target_enemy_pos[0] = player_pos[0] - 1;
            target_enemy_pos[1] = player_pos[1];
        }

        if(!isEnemyNearBy(target)){
            // Show error message if there is no enemy.
            System.out.println(UserInterface.ANSI_RED + "적이 없습니다." + UserInterface.ANSI_RESET);
            target_enemy_pos = null;// Reset the variable.
        }

        return target_enemy_pos;
    }

    /**
     * Attack the enemy on the desired location.
     * @param direction : The player's input of direction for attacking.
     * @param player_pos : The player's current position.
     * @param section : The number of the section of the whole map.
     * @return invalid_input : True, if the player input wrong command.
     */
    public static int [] getEnemyPos(String direction, int [] player_pos, int section){
        int [] target_enemy_pos = null;
        String target = "";
        if(direction.equals("a")){
            // Attack left side
            target = stageMapsArr[section][player_pos[0]][player_pos[1] - 1]; // Check if the enemy exists in the desired area.

            // Save the location of the enemy
            target_enemy_pos = new int[2];
            target_enemy_pos[0] = player_pos[0];
            target_enemy_pos[1] = player_pos[1] - 1;

        } else if (direction.equals("s")){
            // Attack down side
            target = stageMapsArr[section][player_pos[0] + 1][player_pos[1]]; // Check if the enemy exists in the desired area.

            // Save the location of the enemy
            target_enemy_pos = new int[2];
            target_enemy_pos[0] = player_pos[0] + 1;
            target_enemy_pos[1] = player_pos[1];

        } else if (direction.equals("d")){
            // Attack right side
            target = stageMapsArr[section][player_pos[0]][player_pos[1] + 1]; // Check if the enemy exists in the desired area.

            // Save the location of the enemy
            target_enemy_pos = new int[2];
            target_enemy_pos[0] = player_pos[0];
            target_enemy_pos[1] = player_pos[1] + 1;

        } else if (direction.equals("w")){
            // Attack upper side
            target = stageMapsArr[section][player_pos[0] - 1][player_pos[1]]; // Check if the enemy exists in the desired area.

            // Save the location of the enemy
            target_enemy_pos = new int[2];
            target_enemy_pos[0] = player_pos[0] - 1;
            target_enemy_pos[1] = player_pos[1];
        }

        if(!isEnemyNearBy(target)){
            // Show error message if there is no enemy.
            System.out.println("SYSTEM : " + UserInterface.ANSI_RED + "적이 없습니다." + UserInterface.ANSI_RESET);
            target_enemy_pos = null;// Reset the variable.
        }

        return target_enemy_pos;
    }

    public static void setStageMapArr(String[][] stageMapArr) {
        StageMap.stageMapArr = stageMapArr;
    }

    public static void setStageMapsArr(String[][][] stageMapsArr) {
        StageMap.stageMapsArr = stageMapsArr;
    }

    public static int getSections() {
        return sections;
    }

    public static int getCurrent_section() {
        return current_section;
    }

    public static void setCurrent_section(int current_section) {
        StageMap.current_section = current_section;
    }

    public void activateAutoMap(){
        this.setPriority(10);
        this.start();
    }

    public static int getStage_num() {
        return stage_num;
    }

    public static void setStage_num(int stage_num) {
        StageMap.stage_num = stage_num;
    }

    public void pauseAutoMap(){
        this.thread_flag = false;
    }

    public static void stopAutoMap(){
        thread_flag = false;
    }

    public void resumeAutoMap(){
        this.thread_flag = true;
        this.setPriority(9);
        new Thread(this).start();
    }

    @Override
    public  void run() {
        // TODO
//        while (this.thread_flag) {
//            if(!(getStageMaps() != null || getStageMap() != null)){
//                break;
//            }
//
//            UserInterface.clearConsole();
//            displayStageMap();
//            ThreadTracker.isMapDisplayed = true;
//
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                System.out.println(e);
//            }
//            ThreadTracker.isMapDisplayed = false;
//        }
    }
}
