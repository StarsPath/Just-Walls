package com.starspath.justwalls.utils;

import net.minecraft.util.StringRepresentable;

public class Tiers {
    public enum TIER implements StringRepresentable {
        THATCH ("thatch"),
        WOOD ("wood"),
        STONE ("stone"),
        METAL ("metal"),
        ARMOR ("armor");

        private final String name;
        TIER(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        public TIER getNext(){
            switch (name){
                default -> {
                    return WOOD;
                }
                case "wood" -> {
                    return STONE;
                }
                case "stone" -> {
                    return METAL;
                }
                case "metal" -> {
                    return ARMOR;
                }
            }
        }
    }
}
