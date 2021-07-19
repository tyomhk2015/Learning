package com.mudgame.mhk.person;

import com.mudgame.mhk.person.brawler.Player;
import com.mudgame.mhk.stage.StageMap;
import com.mudgame.mhk.userinterface.UserInterface;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Vendor extends Person implements Runnable{

    // The welcome message will be displayed when the player interact with the vendor.
    private final String greeting_msg = "Have selection of good things!";
    // The farewell message for leaving the shop.
    private final String farewell_msg = "Come back any time.";
    private final String confirm_msg = "Need anything else?";
    // Set a icon for the vendor
    private String icon = UserInterface.ANSI_CYAN + "\uD83D\uDD30" + UserInterface.ANSI_RESET;
    // Set a current position and a temporary position.
    private int [] current_pos = new int[2]; // [0] = row, [1] = column.
    private int [] checking_pos = new int[2]; // [0] = row, [1] = column.
    // The section where the vendor appears.
    private final int section = 1;
    private static Player player; // Keep the player's reference for controlling the thread.
    private AtomicBoolean thread_flag = new AtomicBoolean(true); // A flag for controlling the thread.
    private Thread thread;
    private Runnable runnable;

    public Vendor(Player player) {
        this.setCurrent_pos(this.generatePos());
        this.player = player;
        this.runnable = this;
        this.thread = new Thread(this);
    }

    /**
     * Set a position for the vendor.
     * @return pos : An array of the position on the map.
     */
    public int [] generatePos() {
        // Generate random co-ordinate of the vendor.
        // The range is limited by 'movable' values from the 'Stage Map'.
        // [0] has minimum movable value, [1] has the maximum movable value on the map.
        Random random = new Random();
        int limit_cols = 10; // The number of area to limit where the enemy can be created.
        int [] pos = new int[2];
        pos[0] = (random.nextInt(StageMap.movable_rows[1]) + StageMap.movable_rows[0]);
        pos[1] = (random.nextInt(StageMap.movable_cols[1]- limit_cols) + limit_cols);

        return pos;
    }

    /**
     * Make vendor flee from to the player.
     */
    public void fleeFromPlayer(){
        int [] player_pos = this.player.getCurrent_pos();
        boolean is_pos_changed = false; // A flag for detecting the change of the enemy's position.

        // Store enemy's recent position.
        if(this.getCurrent_pos()[1] > player_pos[1]){
            // Try to match columns : When player is further on the right side.
            int [] temp_pos = new int[2];
            temp_pos[0] = this.getCurrent_pos()[0]; // Keep the rows
            temp_pos[1] = this.getCurrent_pos()[1] + 1; // Move to right, one step.
            this.setChecking_pos(temp_pos);
            is_pos_changed = true;
        } else if (this.getCurrent_pos()[1] < player_pos[1]){
            // Try to match columns : When player is further on the left side.
            int [] temp_pos = new int[2];
            temp_pos[0] = this.getCurrent_pos()[0]; // Keep the rows
            temp_pos[1] = this.getCurrent_pos()[1] - 1; // Move to left, one step.
            this.setChecking_pos(temp_pos);
            is_pos_changed = true;
        } else if (this.getCurrent_pos()[0] < player_pos[0]){
            // Try to match rows : When player is further on the upper side.
            int [] temp_pos = new int[2];
            temp_pos[0] = this.getCurrent_pos()[0] - 1; // Move up, one step.
            temp_pos[1] = this.getCurrent_pos()[1]; // Keep the column.
            this.setChecking_pos(temp_pos);
            is_pos_changed = true;
        } else if (this.getCurrent_pos()[0] > player_pos[0]){
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
        if(!collideWithPlayer && is_pos_changed && StageMap.getStage_num() == 2 && StageMap.getCurrent_section() == 1){
            // Update the 2nd, 3rd stage map.
            boolean collided = StageMap.collideWithObstacle(StageMap.getStageMaps()[StageMap.getCurrent_section()][this.getChecking_pos()[0]][this.getChecking_pos()[1]]); // See if the new position collides with any obstacles.

            // Update the map only when there is no collision.
            if(!collided){
                StageMap.getStageMaps()[StageMap.getCurrent_section()][this.getCurrent_pos()[0]][this.getCurrent_pos()[1]] = "　"; // Make old position empty.
                this.setCurrent_pos(this.getChecking_pos()); // Update the current position.
                StageMap.getStageMaps()[StageMap.getCurrent_section()][this.getCurrent_pos()[0]][this.getCurrent_pos()[1]] = this.getIcon(); // Update the current position on the map
            }
        }
    }

    /**
     * Check if the player is within the range.
     * @param player_pos : The position of the player.
     * @return True, if the player is near the vendor.
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
     * Erase the icon from the map.
     */
    public void eraseVendorIcon(){

    }

    public int[] getCurrent_pos() {
        return current_pos;
    }

    public void setCurrent_pos(int[] current_pos) {
        this.current_pos = current_pos;
    }

    public int[] getChecking_pos() {
        return checking_pos;
    }

    public void setChecking_pos(int[] checking_pos) {
        this.checking_pos = checking_pos;
    }

    public String getIcon() {
        return icon;
    }

    public String getFarewell_msg() {
        // Show the farewell message.
        return farewell_msg;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    @Override
    public void run() {
        if(StageMap.getStage_num() == 2 && StageMap.getCurrent_section() == this.section){
            // Keep the vendor wandering.
            if(this.player.getCurrent_health_point() == 0) {
                return;
            }

            StageMap.updateStageMap(this);

            boolean isPlayerNear = this.isPlayerNear(this.player.getCurrent_pos());

            if(!isPlayerNear){
                // Flee only when the player is not near the vendor.
                this.fleeFromPlayer();
            }

            StageMap.updateStageMap(this);
        }
    }
}
