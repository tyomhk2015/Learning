package com.mudgame.mhk.userinterface;

import com.mudgame.mhk.stage.StageMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.util.Scanner;

public class UserInterface {

    /**
     * Font colors for displaying dialogue.
     */
    public static final String ANSI_RESET  =  "\u001B[0m";
    public static final String ANSI_YELLOW =  "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN  = "\u001B[36m";
    public static final String ANSI_GREEN  = "\u001B[32m";

    /**
     * Background-colors for displaying dialogue.
     */
    public static final String ANSI_YELLOW_BG =  "\u001b[43m";
    public static final String ANSI_RED_BG = "\u001b[41m";
    public static final String ANSI_CYAN_BG  = "\u001b[46m";
    public static final String ANSI_GREEN_BG  = "\u001b[42m";

    /**
     * Clear out the console logs by iterating new lines.
     */
    public static void clearConsole() {
        String emptyLines = "";
        int clear_lines = StageMap.cols + StageMap.rows;
        while(clear_lines > 0){
            emptyLines += "\n";
            clear_lines--;
        }
        System.out.println(emptyLines);
    }

    /**
     * Show the title of the game.
     */
    public static void showTitle() {

        int columns = 67; // The number of diamonds to be printed.
        String path = "C:\\Users\\MHK\\IdeaProjects\\mudGame\\src\\com\\mudgame\\mhk\\userinterface\\title.txt";

        String context = "";

        context += showContext(path);
        context += "\n　　　1. 게임 시작\n";
        context +="　　　0. 게임 종료\n\n";

        for (int index = 0; index < columns; index++){
            context += "◇";
        }
        context += "\n";
        System.out.println(context);
    }

    /**
     * Display the context of the text file.
     * @param path : A text file that contains ASCII art or story context.
     */
    public static String showContext(String path) {
        String context = "";
        // Just in case of the file's non-existence, wrap in try & catch scope.
        try {
            // Get the desired text file from the given path.
            File txtFile = new File(path);
            // Get scanner for loading the context of the text file.
            Scanner reader = new Scanner(txtFile);

            // As long as there are lines in the text file, display the context of the file.
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                context += data + "\n";
            }

            // Close the scanner when the loading is finished.
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("SYSTEM : " + "An error occurred.");
            e.printStackTrace();
        }
        return context;
    }

    /**
     * Check if the saved play data exists.
     * @return true: save data exists.
     */
    public static boolean checkSaveFile() {
        // The location of the saved file.
         String path = FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/com/mudgame/mhk/playdata/savedData.txt";
        // Check if the saved file exist.
        boolean result = new File(path).isFile();
        return result;
    }

    /**
     * Tell player that the save data does not exist.
     */
    public static void informNoSavedData() {
        System.out.println();
        System.out.println(ANSI_RED + "SYSTEM : " + "\nYou need a saved game data to use this feature.\nCurrently, you do not have any saved game data.\n\n(Press 'Enter' key to proceed.)\n" + ANSI_RESET);
        // For the player's reading sake, pause the logic until Enter is pressed.
        new Scanner(System.in).nextLine();
    }

    /**
     * Receive user's input.
     */
    public static String getInput() {
        String input = new Scanner(System.in).nextLine();
        new Scanner(System.in);
        return input;
    }

    /**
     * Warn to double-check the input value
     */
    public static void invalidateInput() {
        // Display the warning message.
        System.out.println(ANSI_RED + "\nSYSTEM : " + "※ Invalid input. Put valid input.\n(Press 'Enter' key to proceed.)" + ANSI_RESET);
        // For the player's reading sake, pause the logic until Enter is pressed.
        new Scanner(System.in).nextLine();
    }

    /**
     * Terminate the program.
     */
    public static void exitGame() {
        System.out.println(ANSI_RED + "SYSTEM : " + "\nYou will exit the game soon.\n" + ANSI_RESET);
        System.exit(0);
    }


    /**
     * Show the manual for the game.
     */
    public static void showManual() {
        System.out.println("◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇");
        System.out.println("\n　　　" + ANSI_CYAN + "※ Manual ※" + ANSI_RESET);
        System.out.println("\n　　・" + ANSI_YELLOW + "Objective" + ANSI_RESET + " : Retrieve your PS5 from the yakuza.! \uD83D\uDE08");
        System.out.println("\n　　・" + ANSI_YELLOW + "Main objective in each stage" + ANSI_RESET + " : Defeat the " + ANSI_RED + "boss" + ANSI_RESET + " of each stage.");
        System.out.println("\n　　・" + ANSI_YELLOW + "Legend" + ANSI_RESET + " : ");
        System.out.println("　　           Leftover HP more than 50%  :  Leftover HP is equal or less than 50%  :  No leftover HP");
        System.out.println("　　　　　Player :          " + ANSI_YELLOW + "♥" + ANSI_RESET +
                                  "                       " + ANSI_YELLOW + "♡" + ANSI_RESET +
                                  "                    " + ANSI_YELLOW + "\uD83D\uDC80" + ANSI_RESET);
        System.out.println("Player(Awakened) :          " + ANSI_YELLOW_BG + "♥" + ANSI_RESET +
                "                       " + ANSI_YELLOW_BG + "♡" + ANSI_RESET +
                "                    " + ANSI_YELLOW_BG + "\uD83D\uDC80" + ANSI_RESET);
        System.out.println("　　Normal Enemy :          " + ANSI_RED + "▣" + ANSI_RESET +
                "                       " + ANSI_RED + "□" + ANSI_RESET +
                "                    " + ANSI_RED + "\uD83D\uDC80" + ANSI_RESET);
        System.out.println("　　Agent(Enemy) :          " + ANSI_RED + "◈" + ANSI_RESET +
                "                       " + ANSI_RED + "◇" + ANSI_RESET +
                "                    " + ANSI_RED + "\uD83D\uDC80" + ANSI_RESET);
        System.out.println("　　Boss(Enemy) :          " + ANSI_RED + "★" + ANSI_RESET +
                "                       " + ANSI_RED + "☆" + ANSI_RESET +
                "                    " + ANSI_RED + "\uD83D\uDC80" + ANSI_RESET);
        System.out.println("\n　　Other legend");
        System.out.println("　　Merchant :  " + UserInterface.ANSI_CYAN + "\uD83D\uDD30" + UserInterface.ANSI_RESET);
        System.out.println("　　Wall :  ー , ｜ , ♨");
        System.out.println("　　Next  :  ・ , \u27A1 , \u2B05");
        System.out.println("　　Box :  ▩");
        System.out.println("　　Tree :  ♣");
        System.out.println("\n　・" + ANSI_YELLOW + "TIP" + ANSI_RESET + " : ");
        System.out.println("　　　　　　- You have a small chance of looting a item from enemies by defeating them.");
        System.out.println("　　　　　　- After finishing the whole game at least once, you will be able to use skills.");
        System.out.println("\n◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇");
    }
}
