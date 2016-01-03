package com.broswen.minecraftmurder.runnables;

import com.broswen.minecraftmurder.MinecraftMurder;
import com.broswen.minecraftmurder.core.Arena;
import com.broswen.minecraftmurder.core.constants.ArenaState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

/**
 * Created by broswen on 11/29/2015.
 */
public class GameEnd extends BukkitRunnable{

    //run every 20 ticks to reduce lag

    int t = 10;
    Arena a;
    boolean murdererWon;
    public GameEnd(Arena a, boolean mW){
        this.a = a;
        a.setState(ArenaState.STOPPED);
        a.broadcast("A team won!");
        this.murdererWon = mW;
    }

    @Override
    public void run() {

        if(t==8){

            if(a.canAutoStart()){
                a.broadcast("A new game will begin shortly.");
            }else{
                a.broadcast("You will be returned to the lobby shortly.");
            }

        }

        if(t<=0){
            cancel();

            if(a.canAutoStart()){
                BukkitTask task = new Game(a).runTaskTimer(MinecraftMurder.getPlugin(), 0, 5L);
            }else{
                for(UUID uuid : a.getPlayers()){
                    Player p = Bukkit.getPlayer(uuid);
                    p.teleport(a.getLobby());
                }
                BukkitTask task = new LobbyWaiting(a).runTaskTimer(MinecraftMurder.getPlugin(), 0, 5L);
            }
        }

        t--;
    }
}
