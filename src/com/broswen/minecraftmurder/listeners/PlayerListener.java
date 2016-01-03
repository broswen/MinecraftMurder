package com.broswen.minecraftmurder.listeners;

import com.broswen.minecraftmurder.MinecraftMurder;
import com.broswen.minecraftmurder.core.Arena;
import com.broswen.minecraftmurder.core.ArenaManager;
import com.broswen.minecraftmurder.core.constants.ArenaState;
import com.broswen.minecraftmurder.core.constants.Messages;
import com.broswen.minecraftmurder.runnables.ShootGun;
import com.broswen.minecraftmurder.runnables.ThrowKnife;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by broswen on 11/25/2015.
 */
public class PlayerListener implements Listener{


    @EventHandler
    public void onEDBE(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player || !(event.getDamager() instanceof Player))){
            return;
        }
        Player p = (Player) event.getEntity();
        Player d = (Player) event.getDamager();

        if(ArenaManager.getArena(p) == null || ArenaManager.getArena(d) == null){
            return;
        }
        event.setCancelled(true);
        Arena ap = ArenaManager.getArena(p);
        Arena ad = ArenaManager.getArena(d);
        if(!ad.equals(ap)){
            return;
        }

        if(!ad.getState().equals(ArenaState.INGAME)){
            return;
        }

        if(!(d.getUniqueId() == ad.murderer)){
           return;
        }

        ad.setSpectator(p);
        p.sendMessage(Messages.PREFIX + "You were murdered by " + d.getName() + ".");
        d.sendMessage(Messages.PREFIX + "You murdered " + p.getName() + ".");

    }

    @EventHandler
    public void onED(EntityDamageEvent event){
        Entity e = event.getEntity();

        if(!(e instanceof Player)){
            return;
        }
        Player p = (Player) event.getEntity();

        if(ArenaManager.getArena(p) != null && !event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)){
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player p = event.getPlayer();

        if(ArenaManager.getArena(p) == null){
            return;
        }
        Arena a = ArenaManager.getArena(p);

        if(p.getItemInHand().getType().equals(Material.SHEARS) && event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            BukkitTask task = new ThrowKnife(a, p).runTaskTimer(MinecraftMurder.getPlugin(), 0L, 1L);
            p.setItemInHand(new ItemStack(Material.AIR));
            return;
        }

        if(p.getItemInHand().getType().equals(Material.IRON_BARDING) && event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            BukkitTask task = new ShootGun(a, p, p.getItemInHand()).runTaskTimer(MinecraftMurder.getPlugin(), 0L, 1L);
            return;
        }

    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event){
        Player p = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();

        if(ArenaManager.getArena(p) != null){
            Arena a = ArenaManager.getArena(p);

            if(item.getType().equals(Material.SHEARS) && a.murderer == p.getUniqueId()){
                return;
            }
            if(item.getType().equals(Material.IRON_BARDING) && a.murderer != p.getUniqueId()){
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event){
        Player p = event.getPlayer();

        if(ArenaManager.getArena(p) != null){
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player p = event.getPlayer();

        if(ArenaManager.getArena(p) != null){
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player p = event.getPlayer();

        if(ArenaManager.getArena(p) != null){
            event.setCancelled(true);
            return;
        }
    }
}
