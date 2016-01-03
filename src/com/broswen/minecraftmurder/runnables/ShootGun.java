package com.broswen.minecraftmurder.runnables;

import com.broswen.minecraftmurder.core.Arena;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by broswen on 12/2/2015.
 */
public class ShootGun extends BukkitRunnable{

    Player p;
    Arena a;
    ItemStack item;
    int t = 0;

    public ShootGun(Arena a, Player p, ItemStack item){
        this.a = a;
        this.p = p;
        this.item = item;

        a.playSound(Sound.CLICK, p.getLocation());

        item.setType(Material.GOLD_BARDING);

        Arrow arrow = (Arrow) p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.ARROW);
        arrow.setShooter(p);
        arrow.setVelocity(p.getEyeLocation().getDirection().multiply(2));
    }

    @Override
    public void run() {
        p.sendMessage(String.valueOf(t));

        if(t==20){
            item.setType(Material.DIAMOND_BARDING);
        }else if(t>=40){
            item.setType(Material.IRON_BARDING);
            p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
            cancel();
        }

        t++;
    }
}
