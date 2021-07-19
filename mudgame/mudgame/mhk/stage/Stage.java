package com.mudgame.mhk.stage;

import com.mudgame.mhk.userinterface.UserInterface;

import java.nio.file.FileSystems;

public class Stage {

    private int enemies = 5; // The number of enemies left on the map.
    private int boss = 1; // The number of enemies left on the map.
    private String fileName; // The file name of showing an image of the stage introduction.

    public Stage(int stage_num) {
        if(stage_num == 1) {
            this.fileName = "akiba.txt";
        } else if (stage_num == 2) {
            this.fileName = "kabukicho.txt";
        } else if (stage_num == 3) {
            this.fileName = "toho.txt";
        } else {
            this.fileName = "roof.txt";
        }
    }

    public int getEnemies() {
        return enemies;
    }

    public int getBoss() {
        return boss;
    }

    public void setBoss(int boss) {
        this.boss = boss;
    }

    /**
     * Display the stage's ASCII art.
     */
    public void showStageImg() {
        // The absolute path of the text file.
        String path = FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/com/mudgame/mhk/stage/"+ this.fileName;
        String context = "";
        context += UserInterface.ANSI_CYAN; // Change the console's font color to cyan.
        context += UserInterface.showContext(path);
        context += UserInterface.ANSI_RESET; // Reset the console's font color.
        System.out.println(context);
    }
}
