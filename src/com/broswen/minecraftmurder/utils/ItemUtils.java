package com.broswen.minecraftmurder.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by broswen on 11/25/2015.
 */
public class ItemUtils {

    public static ItemStack createItem(Material m, int size, String name){
        ItemStack item = new ItemStack(m, size);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        item.setItemMeta(im);
        return item;
    }

}
