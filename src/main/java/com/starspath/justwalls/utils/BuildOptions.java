//package com.starspath.justwalls.utils;
//
//import com.starspath.justwalls.init.ModItems;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//
//public class BuildOptions {
//    public enum TOOL_MODE {
//        WALL("wall", ModItems.THATCH_WALL_ITEM.get()),
//        FLOOR("floor", ModItems.THATCH_WALL_FLOOR_ITEM.get()),
//        DOOR_FRAME("door_frame", ModItems.THATCH_WALL_DOOR_FRAME_ITEM.get()),
//        WINDOW_FRAME("window_frame", ModItems.THATCH_WALL_WINDOW_FRAME_ITEM.get()),
//        PILLAR("wall_pillar", ModItems.THATCH_WALL_PILLAR_ITEM.get()),
//        LOOT_CRATE("loot_crate", ModItems.LOOT_CRATE_ITEM.get()),
//        UPGRADE("upgrade", ModItems.SUPER_HAMMER.get());
//
//        private final String name;
//        private final Item item;
//
//        TOOL_MODE(String name, Item item) {
//            this.name = name;
//            this.item = item;
//        }
//
//        @Override
//        public String toString() {
//            return name;
//        }
//
//        public String getAlias() {
//            return "gui.justwalls.super_hammer." + name;
//        }
//
//        public ItemStack getItem(){ return new ItemStack(item); }
//
//        public static TOOL_MODE fromString(String name){
//            for (TOOL_MODE mode : TOOL_MODE.values()) {
//                if (mode.name.equalsIgnoreCase(name)) {
//                    return mode;
//                }
//            }
//            throw new IllegalArgumentException("No enum constant with dayName " + name);
//        }
//    }
//
//    public enum PILLAR_OPTIONS{
//        PILLAR_3("pillar_3", ModItems.THATCH_WALL_PILLAR_ITEM.get()),
//        PILLAR_4("pillar_4", ModItems.THATCH_WALL_PILLAR_ITEM.get()),
//        PILLAR_5("pillar_5", ModItems.THATCH_WALL_PILLAR_ITEM.get());
//
//        private final String name;
//        private final Item item;
//
//        PILLAR_OPTIONS(String name, Item item){
//            this.name = name;
//            this.item = item;
//        }
//
//        @Override
//        public String toString() {
//            return name;
//        }
//
//        public String getAlias() {
//            return "gui.justwalls.super_hammer." + name;
//        }
//
//        public ItemStack getItem(){ return new ItemStack(item); }
//
//        public static TOOL_MODE fromString(String name){
//            for (TOOL_MODE mode : TOOL_MODE.values()) {
//                if (mode.name.equalsIgnoreCase(name)) {
//                    return mode;
//                }
//            }
//            throw new IllegalArgumentException("No enum constant with dayName " + name);
//        }
//    }
//}
