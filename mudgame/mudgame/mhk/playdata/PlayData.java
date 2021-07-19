package com.mudgame.mhk.playdata;

import com.mudgame.mhk.person.brawler.Player;

import java.util.Arrays;

public class PlayData {

    private Player player_data; // Store the player's status.
    private int current_stage; // Store the current stage that player is at.
    private boolean [] cleared_stages = new boolean[4];
    private int [] current_pos; // Store the location of the player.
    public PlayData() {
    }

    public Player getPlayer_data() {
        return player_data;
    }

    public void setPlayer_data(Player player_data) {
        this.player_data = player_data;
    }

    public int getCurrent_stage() {
        return current_stage;
    }

    public void setCurrent_stage(int current_stage) {
        this.current_stage = current_stage;
    }

    public boolean[] getCleared_stages() {
        return cleared_stages;
    }

    public void setCleared_stages(boolean[] cleared_stages) {
        this.cleared_stages = cleared_stages;
    }

    public int[] getCurrent_pos() {
        return current_pos;
    }

    public void setCurrent_pos(int[] current_pos) {
        this.current_pos = current_pos;
    }

    static void savePlayData() {
        // Save playData in '.teamnova' format.
        // TODO : https://stackoverflow.com/questions/30413227/how-to-read-and-write-an-object-to-a-text-file-in-java
    }

    static void loadPlayData() {
        // load '.teamnova' formatted data.
    }

    @Override
    public String toString() {
        return "PlayData{" +
                "player_data=" + player_data +
                ", current_stage=" + current_stage +
                ", cleared_stages=" + Arrays.toString(cleared_stages) +
                ", current_pos=" + Arrays.toString(current_pos) +
                '}';
    }
}
