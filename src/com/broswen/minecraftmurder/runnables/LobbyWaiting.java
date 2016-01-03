package com.broswen.minecraftmurder.runnables;

import com.broswen.minecraftmurder.MinecraftMurder;
import com.broswen.minecraftmurder.core.Arena;
import com.broswen.minecraftmurder.core.constants.ArenaState;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by broswen on 11/29/2015.
 */
public class LobbyWaiting extends BukkitRunnable {

    //run every 5 ticks to reduce lag

    Arena a;
    public LobbyWaiting(Arena a){
        this.a = a;
        a.setState(ArenaState.WAITING);
    }

    @Override
    public void run() {

        if(a==null){
            cancel();
        }

        if(a.canAutoStart() || a.forceStart){
            BukkitTask task = new LobbyCountdown(a).runTaskTimer(MinecraftMurder.getPlugin(), 0L, 20L);

            cancel();
        }

    }
}
