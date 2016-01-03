package com.broswen.minecraftmurder.runnables;

import com.broswen.minecraftmurder.MinecraftMurder;
import com.broswen.minecraftmurder.core.Arena;
import com.broswen.minecraftmurder.core.constants.ArenaState;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

/**
 * Created by broswen on 11/29/2015.
 */
public class Game extends BukkitRunnable {

    int t = 0;
    Arena a;
    public Game(Arena a){
        this.a = a;
        a.setState(ArenaState.INGAME);
        a.setMurderer();
        a.giveKnife();
        a.randomGiveGun();
        a.playSound(Sound.LEVEL_UP);
        for(UUID uuid : a.getPlayers()){
            Player p = Bukkit.getPlayer(uuid);
            a.teleportToSpawn(p);
        }
    }

    @Override
    public void run() {

        if(t==6000){
            //murderer starts smoking
        }


        if(a.hasBystandersWon()){
            BukkitTask task = new GameEnd(a, false).runTaskTimer(MinecraftMurder.getPlugin(), 0L, 20L);
            cancel();
        }else if(a.hasMurdererWon()){
            BukkitTask task = new GameEnd(a, true).runTaskTimer(MinecraftMurder.getPlugin(), 0L, 20L);
            cancel();
        }

        t++;
    }
}
