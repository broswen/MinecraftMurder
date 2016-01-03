package com.broswen.minecraftmurder.core;

import com.broswen.minecraftmurder.core.constants.ArenaState;
import com.broswen.minecraftmurder.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by broswen on 11/25/2015.
 */
public class Arena {

    static ArrayList<UUID> players = new ArrayList<>();
    static ArrayList<UUID> spectators = new ArrayList<>();
    public static UUID murderer;
    static Location spawnBounds1;
    static Location spawnBounds2;
    Location arenaBounds1;
    Location arenaBounds2;
    static Location lobby;
    static ArenaState state;
    static Random rand = new Random();
    static int MAX_PLAYERS = 15;
    static int MIN_PLAYERS = 2;
    static int MIN_AUTO_START = 10;
    static int ID;
    static public boolean forceStart = false;

    public Arena(int ID, Location spawnBounds1, Location spawnBounds2, Location lobby){
        this.ID = ID;
        this.spawnBounds1 = spawnBounds1;
        this.spawnBounds2 = spawnBounds2;
        this.state = ArenaState.WAITING;
        this.lobby = lobby;
    }

    public static int getID(){
        return ID;
    }

    public static boolean isFull(){

        if(getPlayers().size() >= MAX_PLAYERS){
            return true;
        }
        return false;
    }

    public static void setForceStart(boolean b){
        forceStart = b;
    }

    public static boolean canStart(){

        if(getPlayers().size() >= MIN_PLAYERS){
            return true;
        }
        return false;
    }

    public static boolean canAutoStart(){

        if(getPlayers().size() >= MIN_AUTO_START){
            return true;
        }
        return false;
    }

    public static ArenaState getState(){
        return state;
    }

    public static void setState(ArenaState s){
        state = s;
    }

    public static ArrayList<UUID> getPlayers(){
        return players;
    }

    public static ArrayList<UUID> getSpectators(){
        return spectators;
    }

    public static void addPlayer(Player p){
        players.add(p.getUniqueId());
    }

    public static void removePlayer(Player p){
        players.remove(p.getUniqueId());
    }

    public static void setSpectator(Player p){
        spectators.add(p.getUniqueId());
        p.setAllowFlight(true);
        p.setFlying(true);
        p.teleport(p.getLocation().add(0,0.5,0));
    }

    public static void teleportToSpawn(Player p){
        double diffx = spawnBounds1.getX() + (spawnBounds2.getX() - spawnBounds1.getX()) * rand.nextDouble();
        double diffz = spawnBounds1.getZ() + (spawnBounds2.getZ() - spawnBounds1.getZ()) * rand.nextDouble();
        Location loc = new Location(spawnBounds1.getWorld(), diffx, spawnBounds1.getY(), diffz);
        p.teleport(loc);
    }

    public static Location getLobby(){
        return lobby;
    }

    public static void setMurderer(){
        int rand1 = rand.nextInt(players.size());
        murderer = players.get(rand1);
    }

    public static void giveKnife(){
        Player p = Bukkit.getPlayer(murderer);
        p.getInventory().addItem(ItemUtils.createItem(Material.SHEARS, 1, "Knife"));
    }

    public static void randomGiveGun(){
        int rand1 = rand.nextInt(players.size());
        Player p = Bukkit.getPlayer(players.get(rand1));
        p.getInventory().addItem(ItemUtils.createItem(Material.IRON_BARDING, 1, "Gun"));
    }

    public static void broadcast(String message){
        for(UUID uuid : players){
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(message);
        }
    }

    public static void playSound(Sound sound){
        for(UUID uuid : players){
            Player p = Bukkit.getPlayer(uuid);
            p.playSound(p.getLocation(), sound, 1, 1);
        }
    }

    public static boolean hasBystandersWon(){

        if(getSpectators().contains(murderer)){
            return true;
        }

        return false;
    }

    public static boolean hasMurdererWon(){

        if(getSpectators().size() == getPlayers().size()-1 && !getSpectators().contains(murderer)){
            return true;
        }

        return false;
    }
}
