package com.mudgame.mhk.person.brawler.enemy;

import com.mudgame.mhk.person.brawler.Player;
import com.mudgame.mhk.stage.StageMap;
import com.mudgame.mhk.userinterface.BattleInterface;
import com.mudgame.mhk.userinterface.UserInterface;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class EnemyBoss extends Enemy implements Runnable {

    private static Player player; // TODO
    private AtomicBoolean thread_flag = new AtomicBoolean(true); // TODO
    private Thread thread;
    private Runnable runnable;
    private volatile int current_section = -1;


    public EnemyBoss(int stage_num, Player player) {
        // Generate boss's name and EXP points automatically, depending on the stage number.
        if(stage_num == 1){
            super.setName("쿠니오 이노우에");
            super.setExperience_point(super.getExperience_point() * 5);
            super.setPossessing_money(super.getPossessing_money() * 5);
        } else if (stage_num == 2 ){
            super.setName("신병국");
            super.setExperience_point(super.getExperience_point() * 5 * stage_num);
            super.setPossessing_money(super.getPossessing_money() * 5 * stage_num);
        } else if (stage_num == 3 ){
            super.setName("이사오 사이키");
            super.setExperience_point(super.getExperience_point() * 5 * stage_num);
            super.setPossessing_money(super.getPossessing_money() * 5 * stage_num);
        } else if (stage_num == 4 ){
            super.setName("켄이치 시노다");
            super.setExperience_point(super.getExperience_point() * 5 * stage_num);
            super.setPossessing_money(super.getPossessing_money() * 5 * stage_num);
        }

        // Store icon that reflects the condition of the entity.
        super.getStatus_icon().put(0, UserInterface.ANSI_RED + "\uD83D\uDC80" + UserInterface.ANSI_RESET);
        super.getStatus_icon().put(50, UserInterface.ANSI_RED + "☆" + UserInterface.ANSI_RESET);
        super.getStatus_icon().put(51, UserInterface.ANSI_RED + "★" + UserInterface.ANSI_RESET);

        // Set a position for the boss.
        int [] current_pos = {StageMap.rows / 2, StageMap.cols - 3};
        super.setCurrent_pos(current_pos);

        // Set status for the enemy boss.
        this.setStatus(player.getLevel());

        // TODO
        this.player = player;

        this.runnable = this;
        this.thread = new Thread(this);
    }

    public void setStatus(int player_level) {
        if(player_level > 99) {
            // For debugging mode.
            player_level = 99;
        }
        super.setStatus(player_level + 1);
        super.setHealth_point(super.getLevel() * 20);
        super.setCurrent_health_point(super.getHealth_point());
        super.setStamina_point(super.getLevel() * 2);
        super.setCurrent_stamina_point(super.getStamina_point());
        super.setAttack_point(super.getLevel() * 4);
        super.setDefense_point(super.getLevel() * 2);
        super.setAccuracy(65);
        super.setEvasion(20);
        super.setCritical_rate(super.getLevel() + 10);
        super.setCritical_ratio(1);
        super.setBuff_point(0);
        super.setAttack_range(1);
        super.setMove_distance((super.getLevel() / 20) + 4);
        super.setEquipped_weapon(null);
    }

    public Runnable getRunnable() {
        return this.runnable;
    }

    public Thread getThread() {
        return this.thread;
    }

    public void activateBoss(){
        this.thread.setPriority(Thread.MAX_PRIORITY);
        this.thread.start();
    }

    public void pauseBoss(){
        this.thread_flag.set(false);
        this.thread.interrupt();
        this.thread = null;
    }

    public void resumeBoss(){
        this.thread_flag.set(true);
        this.thread = new Thread(this);
        this.thread.setPriority(Thread.MAX_PRIORITY);
        this.thread.start();
    }

    public int getCurrent_section() {
        return current_section;
    }

    public void setCurrent_section(int current_section) {
        this.current_section = current_section;
    }

    public Thread getBossThread() {
        return this.thread;
    }

    public String sendAttackResult() {
        return super.attackPlayer();
    }

    @Override
    public void run() {
        if(this.current_section == -1 || this.current_section == 2){
            if(player.getCurrent_health_point() == 0 || this.getCurrent_health_point() == 0) {
                return;
            }

            StageMap.updateStageMap(this);
            this.approachPlayer();

            boolean isPlayerNear = super.isPlayerNear(player.getCurrent_pos());

            if (isPlayerNear && player.getCurrent_health_point() > 0) {
                this.setAttackResult(this.attackPlayer());
                StageMap.updateStageMap(player);
            }
        }
    }
}
