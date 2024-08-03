package com.starspath.justwalls.utils;

import com.starspath.justwalls.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Objects;

public class RadialMenuItem {

    private final String name;
    private final Item item;
    private final ArrayList<RadialMenuItem> nestedMenuItems;

    public RadialMenuItem(String name, Item item){
        this(name, item, null);
    }

    public RadialMenuItem(String name, Item item, ArrayList<RadialMenuItem> nestedMenuItems){
        this.name = name;
        this.item = item;
        this.nestedMenuItems = nestedMenuItems;
    }

    @Override
    public String toString() {
        return name;
    }

    public Component getComponent(){
        return Component.translatable("gui.justwalls.super_hammer." + name);
    }

    public ItemStack getItemToRender(){
        return new ItemStack(Objects.requireNonNullElse(this.item, Items.COBBLESTONE));
    }

    public boolean hasNestedMenu(){
        return nestedMenuItems != null && nestedMenuItems.size() > 0;
    }

    public ArrayList<RadialMenuItem> getNestedMenuItems(){
        return nestedMenuItems != null? nestedMenuItems : new ArrayList<RadialMenuItem>();
    }

    public static RadialMenuItem getRadialMenuItemByName(ArrayList<RadialMenuItem> menu, String name){
        for(RadialMenuItem radialMenuItem : menu){
            if(radialMenuItem.name.equals(name)){
                return radialMenuItem;
            }
        }
        return null;
    }

    public static final RadialMenuItem PILLAR_3 = new RadialMenuItem("pillar_3", ModItems.THATCH_WALL_PILLAR_ITEM_3.get());
    public static final RadialMenuItem PILLAR_4 = new RadialMenuItem("pillar_4", ModItems.THATCH_WALL_PILLAR_ITEM_4.get());
    public static final RadialMenuItem PILLAR_5 = new RadialMenuItem("pillar_5", ModItems.THATCH_WALL_PILLAR_ITEM_5.get());

    public static final ArrayList<RadialMenuItem> PILLAR_MENU = new ArrayList<>(){
        {
            add(PILLAR_3);
            add(PILLAR_4);
            add(PILLAR_5);
        }
    };

    public static final RadialMenuItem WALL = new RadialMenuItem("wall", ModItems.THATCH_WALL_ITEM.get());
    public static final RadialMenuItem FLOOR = new RadialMenuItem("floor", ModItems.THATCH_WALL_FLOOR_ITEM.get());
    public static final RadialMenuItem DOOR_FRAME = new RadialMenuItem("door_frame", ModItems.THATCH_WALL_DOOR_FRAME_ITEM.get());
    public static final RadialMenuItem WINDOW_FRAME = new RadialMenuItem("window_frame", ModItems.THATCH_WALL_WINDOW_FRAME_ITEM.get());
    public static final RadialMenuItem WALL_PILLAR = new RadialMenuItem("wall_pillar", ModItems.THATCH_WALL_PILLAR_ITEM_3.get(), PILLAR_MENU);
    public static final RadialMenuItem UPGRADE = new RadialMenuItem("upgrade", ModItems.SUPER_HAMMER.get());

    public static final ArrayList<RadialMenuItem> MAIN_MENU = new ArrayList<>(){
        {
            add(WALL);
            add(FLOOR);
            add(DOOR_FRAME);
            add(WINDOW_FRAME);
            add(WALL_PILLAR);
            add(UPGRADE);
        }
    };

    public static final ArrayList<RadialMenuItem> ALL_ITEMS = new ArrayList<>(){
        {
            add(RadialMenuItem.PILLAR_3);
            add(RadialMenuItem.PILLAR_4);
            add(RadialMenuItem.PILLAR_5);
            add(RadialMenuItem.WALL);
            add(RadialMenuItem.FLOOR);
            add(RadialMenuItem.DOOR_FRAME);
            add(RadialMenuItem.WINDOW_FRAME);
            add(RadialMenuItem.WALL_PILLAR);
            add(RadialMenuItem.UPGRADE);
        }
    };
}
