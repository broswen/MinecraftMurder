package com.broswen.minecraftmurder.core;

import com.broswen.minecraftmurder.MinecraftMurder;
import com.broswen.minecraftmurder.runnables.LobbyWaiting;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

/**
 * Created by broswen on 11/25/2015.
 */
public class ArenaManager {

    static ArrayList<Arena> arenas = new ArrayList<>();

    public static ArrayList<Arena> getArenas(){
        return arenas;
    }

    public static void createArena(int id, Location sB1, Location sB2, Location lobby){
        Arena a = new Arena(id, sB1,sB2, lobby);
        arenas.add(a);
        BukkitTask task = new LobbyWaiting(a).runTaskTimer(MinecraftMurder.getPlugin(), 0L, 5L);
    }

    public static void deleteArena(Arena a){
        arenas.remove(a);
        a = null;
    }

    public static void loadArenas(){
        int i = 0;
        FileConfiguration config = MinecraftMurder.getPlugin().getConfig();

        if(config.getConfigurationSection("arenas").getKeys(false).isEmpty() || config.getConfigurationSection("arenas").getKeys(false)==null){
            Bukkit.getLogger().info("Loaded 0 arenas. None specified in config.");
            return;
        }

        for(String key : config.getConfigurationSection("arenas").getKeys(false)){
            createArena(Integer.valueOf(key), deserializeLoc(config.getString("arenas." + key + ".spawnBounds1")),  deserializeLoc(config.getString("arenas." + key + ".spawnBounds2")), deserializeLoc(config.getString("arenas." + key + ".lobby")));
            i++;
        }
        Bukkit.getLogger().info("Loaded " + i + " arenas.");
    }

    public static Arena getArena(Player p){

        for(Arena a : arenas){
            if(a.getPlayers().contains(p.getUniqueId())){
                return a;
            }
        }

        return null;
    }

    public static Arena getArena(int id){
        for(Arena a : arenas){
            if(a.getID() == id){
                return a;
            }
        }
        return null;
    }

    public String serializeLoc(Location l){
        return l.getWorld().getName() + "," + l.getX() + "," + l.getY() + "," + l.getZ();
    }

    public static Location deserializeLoc(String s){
        String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Double.parseDouble(st[1]), Double.parseDouble(st[2]), Double.parseDouble(st[3]));
    }

}
