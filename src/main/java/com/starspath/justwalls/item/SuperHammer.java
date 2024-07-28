package com.starspath.justwalls.item;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SuperHammer extends Item {

    public static final String modeNBTString = "justwalls.hammermode";

    public enum TOOL_MODE {
        WALL("wall", ModItems.THATCH_WALL_ITEM.get()),
        FLOOR("floor", ModItems.THATCH_WALL_FLOOR_ITEM.get()),
        DOOR_FRAME("door_frame", ModItems.THATCH_WALL_DOOR_FRAME_ITEM.get()),
        WINDOW_FRAME("window_frame", ModItems.THATCH_WALL_WINDOW_FRAME_ITEM.get()),
        LOOT_CRATE("loot_crate", ModItems.LOOT_CRATE_ITEM.get()),
//        PILLAR("loot_crate", ModItems.LOOT_CRATE_ITEM.get()),
        UPGRADE("upgrade", ModItems.SUPER_HAMMER.get());

        private final String name;
        private final Item item;

        TOOL_MODE(String name, Item item) {
            this.name = name;
            this.item = item;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getAlias() {
            return "gui.justwalls.super_hammer." + name;
        }

        public ItemStack getItem(){ return new ItemStack(item); }

        public static TOOL_MODE fromString(String name){
            for (TOOL_MODE mode : TOOL_MODE.values()) {
                if (mode.name.equalsIgnoreCase(name)) {
                    return mode;
                }
            }
            throw new IllegalArgumentException("No enum constant with dayName " + name);
        }
    }

    public SuperHammer(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
//        System.out.println("USE");
//        ItemStack itemStack = player.getItemInHand(interactionHand);
        return super.use(level, player, interactionHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        if(level.isClientSide){
            return super.useOn(useOnContext);
        }
        TOOL_MODE mode = getMode(useOnContext.getItemInHand());
        System.out.println(mode);
        switch (mode) {
            case WALL, FLOOR, WINDOW_FRAME, DOOR_FRAME, LOOT_CRATE ->
                    ((BlockItem) mode.getItem().getItem()).place(new BlockPlaceContext(useOnContext));
        }

        return InteractionResult.SUCCESS;
//        return super.useOn(useOnContext);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        if(itemStack.hasTag()){
            String tag = itemStack.getTag().getString(modeNBTString);
            list.add(Component.literal(tag));
        }
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

    public void setMode(ItemStack itemStack, TOOL_MODE mode){
        if(!itemStack.hasTag()){
            CompoundTag tag = new CompoundTag();
            tag.putString(modeNBTString, mode.toString());
            itemStack.setTag(tag);
        }
        else{
            CompoundTag tag = itemStack.getTag();
            tag.putString(modeNBTString, mode.toString());
            itemStack.setTag(tag);
        }
    }

    public TOOL_MODE getMode(ItemStack itemStack){
        if(itemStack.hasTag()){
            CompoundTag tag = itemStack.getTag();
            return TOOL_MODE.fromString(tag.getString(modeNBTString));
        }
        return TOOL_MODE.WALL;
    }
}
