package com.mudgame.mhk.userinterface;

import com.mudgame.mhk.item.ItemData;
import com.mudgame.mhk.person.brawler.Player;
import com.mudgame.mhk.stage.StageMap;

public class ShopInterface {

    private static ItemData itemData = new ItemData();

    /**
     * Show the options of the shop.
     */
    public static void showShopMenu(){
        String context = "\n";
        for(int index = 0; index < (StageMap.cols + 10)  ; index++){
            context += "▩";
        }

        context += UserInterface.ANSI_GREEN + "\n\n　　　Merchant" + UserInterface.ANSI_RESET + " : Got a selection of good things!";
        context += "\n\n　　　1. Buy";
        context += "\n\n　　　2. Sell";
        context += "\n\n　　　0. Stop shopping.";
        context += "\n\n";

        for(int index = 0; index < (StageMap.cols + 10) ; index++){
            context += "▩";
        }
        context += "\n>>> ";
        System.out.print(context);
    }

    /**
     * Show a list of items that can be bought.
     * @param player : For updating player's money.
     */
    public static void showBuyableItems(Player player) {

        String context = "\n";

        for(int index = 0; index < (StageMap.cols + 10) ; index++){
            context += "▩";
        }

        context += UserInterface.ANSI_GREEN + "\n\n　　　Merchant" + UserInterface.ANSI_RESET + " : What are you buying?　　　　　　　　　　　　Money : " + player.getMoney() + "￥";
        context += "\n　　　　　　　　　　　　　　　　　　　　　　　　　Available inventory slots [" + player.countEmptyInventorySlot() + " / " + player.getInventory().length + "]\n";

        context += "\n\n";
        // TODO
        int choice = 1;
        for (String key : itemData.getItem_data().keySet()) {
            if(player.getMoney() >= itemData.getItem_data().get(key).getPrice()){
                // When player can afford the current item.
                context += UserInterface.ANSI_CYAN + "　　　" + choice + ".   " +itemData.getItem_data().get(key).getName() + "    Price : " + itemData.getItem_data().get(key).getPrice() + "￥      " + itemData.getItem_data().get(key).getDescription() + UserInterface.ANSI_RESET + "\n";
            } else {
                // When player cannot afford the current item.
                context += UserInterface.ANSI_RED + "　　　" + choice + ".   " +itemData.getItem_data().get(key).getName() + "     Price : " + itemData.getItem_data().get(key).getPrice() + "￥      " + "※ Not enough money." + UserInterface.ANSI_RESET + "\n";
            }
            choice++;
        }

        context += "\n\n";

        context += "　　　0. Stop buying.\n\n";

        for(int index = 0; index < (StageMap.cols + 10) ; index++){
            context += "▩";
        }
        context += "\n>>> ";
        System.out.print(context);
    }

    /**
     * Show a list of items that can be sold
     */
    public static void showSalableItems(Player player) {
        int possessingItems = player.getInventory().length - player.countEmptyInventorySlot();
        String context = "\n";

        for(int index = 0; index < (StageMap.cols + 10) ; index++){
            context += "▩";
        }

        context += UserInterface.ANSI_GREEN + "\n\n　　　Merchant" + UserInterface.ANSI_RESET + " : What are you selling??　　　　　　　　　　　　　　Money : " + player.getMoney() + "￥";
        context += "\n　　　　　　　　　　　　　　　　　　　　　　　　　Available inventory slots [" + player.countEmptyInventorySlot() + " / " + player.getInventory().length + "]\n";

        if(possessingItems == 0){
            context += UserInterface.ANSI_RED + "\n\n　　　You do not have any items.\n\n" + UserInterface.ANSI_RESET;
        } else {
            for(int index = 0 ; index < player.getInventory().length ; index++){
                if(player.getInventory()[index] != null){
                    context += UserInterface.ANSI_CYAN + "\n　　　" + (index + 1) +".  " + player.getInventory()[index].getName() + "    Price : " + player.getInventory()[index].getPrice() / 2 + "     " + player.getInventory()[index].getDescription() + UserInterface.ANSI_RESET;
                }
            }
        }

        context += "\n\n";

        context += "　　　0. Stop selling.\n\n";

        for(int index = 0; index < (StageMap.cols + 10) ; index++){
            context += "▩";
        }
        context += "\n>>> ";
        System.out.print(context);
    }
}
