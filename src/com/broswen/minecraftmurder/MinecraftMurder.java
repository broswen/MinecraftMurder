package com.broswen.minecraftmurder;

import com.broswen.minecraftmurder.core.ArenaManager;
import com.broswen.minecraftmurder.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by broswen on 11/25/2015.
 */
public class MinecraftMurder extends JavaPlugin {

    static Plugin plugin;
    static FileConfiguration config;

    @Override
    public void onEnable(){
        this.plugin = this;
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginCommand("murder").setExecutor(new MurderCommand());
        loadConfig();
        ArenaManager.loadArenas();
        this.config = this.getConfig();
    }

    public static Plugin getPlugin(){
        return plugin;
    }

    public void loadConfig(){
        saveDefaultConfig();
        saveConfig();
    }
}
