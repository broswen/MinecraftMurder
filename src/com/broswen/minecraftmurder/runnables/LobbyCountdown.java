package com.broswen.minecraftmurder.runnables;

import com.broswen.minecraftmurder.MinecraftMurder;
import com.broswen.minecraftmurder.core.Arena;
import com.broswen.minecraftmurder.core.constants.ArenaState;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by broswen on 11/29/2015.
 */
public class LobbyCountdown extends BukkitRunnable{

    int t = 30;

    Arena a;
    public LobbyCountdown(Arena a){
        this.a = a;
        a.setState(ArenaState.STARTING);
        a.setForceStart(false);
    }

    @Override
    public void run() {

        if(t==30){
            a.broadcast("Game starting in 30 seconds.");
            a.playSound(Sound.NOTE_PLING);
        }

        if(t==15){
            a.broadcast("Game starting in 15 seconds.");
        }

        if(t<=10 && t>0){
            a.broadcast("Game starting in " + t + " seconds.");
            a.playSound(Sound.NOTE_STICKS);
        }

        if(t <= 0){
            //start or wait for players

            if(a.canStart()){
                a.playSound(Sound.NOTE_PLING);
                a.broadcast(ChatColor.GREEN + "Game Starting!");
                BukkitTask task = new Game(a).runTaskTimer(MinecraftMurder.getPlugin(), 0L, 1L);
                cancel();
            }else{
                a.playSound(Sound.ANVIL_LAND);
                a.broadcast(ChatColor.RED + "Not enough players.");
                BukkitTask task = new LobbyWaiting(a).runTaskTimer(MinecraftMurder.getPlugin(), 0L, 5L);
                cancel();
            }

        }

        t--;
    }
}
