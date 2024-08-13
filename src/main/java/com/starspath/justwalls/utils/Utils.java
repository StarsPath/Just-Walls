package com.starspath.justwalls.utils;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Utils {
    public static void debug(Object ... objects){
        StringBuilder s = new StringBuilder("DEBUG: ");
        for(Object obj : objects){
            if(obj == null)
                s.append("EMPTY ");
            else
                s.append(obj.toString()).append(" ");
        }
        System.out.println(s);
//        IndustrialRenewal.LOGGER.info(String.valueOf(s));
    }

    public static boolean consumeIfAvailable(Player player, ItemStack itemStack){
        int playerHas = countMaterialInInventory(player.getInventory(), itemStack.getItem());
        if(playerHas >= itemStack.getCount()){
            removeItemFromInventory(player.getInventory(), itemStack.getItem(), itemStack.getCount());
            return true;
        }
        return false;
    }

    public static int countMaterialInInventory(Inventory inventory, Item item){
        return inventory.items.stream().filter(stack -> stack.getItem() == item).mapToInt(ItemStack::getCount).sum();
    }

    public static void removeItemFromInventory(Inventory inventory, Item item, int count) {
        for (ItemStack stack : inventory.items) {
            if (stack.getItem() == item) {
                int toRemove = Math.min(stack.getCount(), count);
                stack.shrink(toRemove);
                count -= toRemove;
                if (count <= 0) {
                    break;
                }
            }
        }
    }
}
