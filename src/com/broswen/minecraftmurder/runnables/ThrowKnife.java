package com.broswen.minecraftmurder.runnables;

import com.broswen.minecraftmurder.core.Arena;
import com.broswen.minecraftmurder.core.ArenaManager;
import com.broswen.minecraftmurder.core.constants.Messages;
import com.broswen.minecraftmurder.utils.ParticleEffect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by broswen on 12/2/2015.
 */
public class ThrowKnife extends BukkitRunnable{

    Arena a;
    Player p;
    int t = 0;
    Item knife;

    public ThrowKnife(Arena a, Player p){
        this.a = a;
        this.p = p;

        knife = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.SHEARS));
        Vector v = p.getLocation().getDirection().multiply(1);
        if(p.getLocation().getDirection().getY() > 0.6){
            v.setY(0.6);
        }
        knife.setVelocity(v);
    }

    @Override
    public void run() {

        ParticleEffect.SUSPENDED_DEPTH.display(0.1f,0.1f,0.1f,0,2,knife.getLocation(), 100);

        if(!knife.isOnGround()){
            for(Entity e : knife.getNearbyEntities(0.5,0.5,0.5)){
                if(e instanceof Player){
                    Player p2 = (Player) e;
                    if(ArenaManager.getArena(p2).equals(a)){
                        if(!p2.getUniqueId().equals(a.murderer) && !a.getSpectators().contains(p2.getUniqueId())){
                            a.setSpectator(p2);
                            p2.sendMessage(Messages.PREFIX + "You were murdered by " + p.getName() + ".");
                            p.sendMessage(Messages.PREFIX + "You murdered " + p2.getName() + ".");
                            knife.setVelocity(new org.bukkit.util.Vector(0,-0.1,0));
                        }
                    }
                }
            }
        }

        if(knife.isDead()){
            cancel();
        }else if(t == 12000){
            knife.remove();
            a.giveKnife();
        }

    }
}
