package com.mudgame.mhk.userinterface;

import com.mudgame.mhk.stage.StageMap;

import java.io.File;
import java.io.FileNotFoundException;
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

        boolean saveFileExist = checkSaveFile(); // Store the result of searching the saved file.
        int columns = 67; // The number of diamonds to be printed.
        String path = "C:\\Users\\MHK\\IdeaProjects\\mudGame\\src\\com\\mudgame\\mhk\\userinterface\\title.txt";

        String context = "";

        context += showContext(path);

        context += "\n　　　1. 게임 시작\n";
//        if (saveFileExist) {
//            context += "　　　2. 이어서 하기";
//        } else {
//            context += "　　　2. \uD83D\uDD12";
//        }
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
        String path = "C:\\Users\\MHK\\IdeaProjects\\mudGame\\src\\com\\mudgame\\mhk\\playdata\\savedData.txt";
        // Check if the saved file exist.
        boolean result = new File(path).isFile();
        return result;
    }

    /**
     * Tell player that the save data does not exist.
     */
    public static void informNoSavedData() {
        System.out.println();
        System.out.println(ANSI_RED + "SYSTEM : " + "\n이 기능은 게임 세이브 데이터가 필요합니다.\n하지만 현재는 게임 세이브 데이터가 존재하지 않습니다.\n\n(Enter 키를 눌러서 계속 진행하기.)\n" + ANSI_RESET);
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
        System.out.println(ANSI_RED + "\nSYSTEM : " + "※ 올바른 값이 아닙니다. 바른 값을 입력해 주세요.\n(Enter 키를 눌러서 계속 진행하기.)" + ANSI_RESET);
        // For the player's reading sake, pause the logic until Enter is pressed.
        new Scanner(System.in).nextLine();
    }

    /**
     * Terminate the program.
     */
    public static void exitGame() {
        System.out.println(ANSI_RED + "SYSTEM : " + "\n게임을 종료합니다.\n" + ANSI_RESET);
        System.exit(0);
    }


    /**
     * Show the manual for the game.
     */
    public static void showManual() {
        System.out.println("◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇");
        System.out.println("\n　　　" + ANSI_CYAN + "※ Pain Station 게임 매뉴얼 ※" + ANSI_RESET);
        System.out.println("\n　　・" + ANSI_YELLOW + "목표" + ANSI_RESET + " : 불법 조직에게 빼앗긴 플레이스테이션5를 되찾자! \uD83D\uDE08");
//        System.out.println("\n　　・" + ANSI_YELLOW + "게임 진행 순서" + ANSI_RESET + " : " + ANSI_GREEN + "플레이어" + ANSI_RESET + "가 선행동개시 (이동, 아이템 사용, 공격, 스킬사용 등) \u27A1 " + ANSI_RED + "적" + ANSI_RESET + "이 행동 개시 (이동, 공격 등)");
        System.out.println("\n　　・" + ANSI_YELLOW + "각 스테이지 승리 조건" + ANSI_RESET + " : 해당 스테이지의 " + ANSI_RED + "보스" + ANSI_RESET + "를 쓰러트리면 다음 스테이지로 이동하게 됩니다.");
        System.out.println("\n　　・" + ANSI_YELLOW + "게임 내의 모든 범주" + ANSI_RESET + " : ");
        System.out.println("　　           남아있는 HP가 50% 초과  :  남아있는 HP가 50% 이하  :  남아있는 HP가 0");
        System.out.println("　　  플레이어 :          " + ANSI_YELLOW + "♥" + ANSI_RESET +
                                  "                       " + ANSI_YELLOW + "♡" + ANSI_RESET +
                                  "                    " + ANSI_YELLOW + "\uD83D\uDC80" + ANSI_RESET);
        System.out.println("플레이어(각성) :          " + ANSI_YELLOW_BG + "♥" + ANSI_RESET +
                "                       " + ANSI_YELLOW_BG + "♡" + ANSI_RESET +
                "                    " + ANSI_YELLOW_BG + "\uD83D\uDC80" + ANSI_RESET);
        System.out.println("　　  　일반적 :          " + ANSI_RED + "▣" + ANSI_RESET +
                "                       " + ANSI_RED + "□" + ANSI_RESET +
                "                    " + ANSI_RED + "\uD83D\uDC80" + ANSI_RESET);
        System.out.println("　특수요원(적) :          " + ANSI_RED + "◈" + ANSI_RESET +
                "                       " + ANSI_RED + "◇" + ANSI_RESET +
                "                    " + ANSI_RED + "\uD83D\uDC80" + ANSI_RESET);
        System.out.println("　　　보스(적) :          " + ANSI_RED + "★" + ANSI_RESET +
                "                       " + ANSI_RED + "☆" + ANSI_RESET +
                "                    " + ANSI_RED + "\uD83D\uDC80" + ANSI_RESET);
        System.out.println("\n　　           　 　　  기타 범주");
        System.out.println("　         암거래 상인 :  " + UserInterface.ANSI_CYAN + "\uD83D\uDD30" + UserInterface.ANSI_RESET);
        System.out.println("　　　 이동 불가능한 벽 :  ー , ｜ , ♨");
        System.out.println("다음 구역으로 진입 가능 :  ・ , \u27A1 , \u2B05");
        System.out.println("　　　 　　　 　   상자 :  ▩");
        System.out.println("　　　 　　　 　   나무 :  ♣");
        System.out.println("\n　　・" + ANSI_YELLOW + "TIP" + ANSI_RESET + " : ");
        System.out.println("　　　　　　- 적을 쓰러트리면 일정 확률로 전투에 도움이 되는 아이템을 얻을 수 있습니다.");
        System.out.println("　　　　　　- 1회차를 클리어하면 2회차부터는 플레이어가 각성하게 되며, 스킬을 사용할 수 있게 됩니다.");
        System.out.println("\n◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇");
    }
}
