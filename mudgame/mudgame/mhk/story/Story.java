package com.mudgame.mhk.story;

import com.mudgame.mhk.userinterface.UserInterface;

public class Story {

    private static String path = "C:\\Users\\MHK\\IdeaProjects\\mudGame\\src\\com\\mudgame\\mhk\\story\\"; // An absolute path of the text file.

    /**
     * Display the story of the game.
     */
    public static void showIntro() {
        // The name of the text file.
        String fileName = "storyIntro.txt";
        // Show the context of the file.
        String context = "";
        context += UserInterface.ANSI_YELLOW;
        context += UserInterface.showContext(path + fileName);
        context += UserInterface.ANSI_RESET;
        System.out.println(context);
    }

    /**
     * Shows the context of a text file that corresponds to the stage number.
     * @param stage_num : The number of the stage to initiate.
     */
    public static void showStageDialogue (int stage_num) {
        String fileName = "";
        if(stage_num == 1){
            fileName = "storyStage1.txt";
        } else if (stage_num == 2) {
            fileName = "storyStage2.txt";
        } else if (stage_num == 3) {
            fileName = "storyStage3.txt";
        } else {
            fileName = "storyStage4.txt";
        }
        UserInterface.showContext(path + fileName);
    }

    /**
     * Displays an epilogue of the game.
     */
    public static void showStoryEpilogue() {
        // The name of the text file.
        String fileName = "storyEpilogue.txt";
        // Show the context of the file.
        String context = "";
        context += UserInterface.ANSI_YELLOW;
        context += UserInterface.showContext(path + fileName);
        context += UserInterface.ANSI_RESET;
        System.out.println(context);
    }

    /**
     * Show the 'stage cleared' message.
     */
    public static void showClearedMsg() {
        // The name of the text file.
        String fileName = "clearedMsg.txt";
        // Show the context of the file.
        String context = "\n";
        context += UserInterface.ANSI_YELLOW;
        context += UserInterface.showContext(path + fileName);
        context += UserInterface.ANSI_RESET;
        System.out.println(context);
    }

    /**
     * Show the 'game over' message.
     */
    public static void showGameOverMsg() {
        // The name of the text file.
        String fileName = "gameover.txt";
        // Show the context of the file.
        String context = "";
        context += UserInterface.ANSI_RED;
        context += UserInterface.showContext(path + fileName);
        context += UserInterface.ANSI_RESET;
        System.out.println(context);
    }

}
