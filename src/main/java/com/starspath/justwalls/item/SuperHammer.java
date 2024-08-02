package com.starspath.justwalls.item;

import com.starspath.justwalls.blocks.abstracts.StructureBlock;
import com.starspath.justwalls.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SuperHammer extends Item {

    public static final String modeNBTString = "justwalls.hammermode";

    public final int MATERIAL_COUNT = 18;

    public enum TOOL_MODE {
        WALL("wall", ModItems.THATCH_WALL_ITEM.get()),
        FLOOR("floor", ModItems.THATCH_WALL_FLOOR_ITEM.get()),
        DOOR_FRAME("door_frame", ModItems.THATCH_WALL_DOOR_FRAME_ITEM.get()),
        WINDOW_FRAME("window_frame", ModItems.THATCH_WALL_WINDOW_FRAME_ITEM.get()),
        PILLAR("wall_pillar", ModItems.THATCH_WALL_PILLAR_ITEM.get()),
        LOOT_CRATE("loot_crate", ModItems.LOOT_CRATE_ITEM.get()),
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
        return super.use(level, player, interactionHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        if(level.isClientSide){
            return super.useOn(useOnContext);
        }
        TOOL_MODE mode = getMode(useOnContext.getItemInHand());
        Player player = useOnContext.getPlayer();
        player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 40);

        switch (mode) {
            case WALL, FLOOR, WINDOW_FRAME, DOOR_FRAME, PILLAR, LOOT_CRATE -> {
                if(!player.isCreative()){
                    int playerHas = countMaterialInInventory(player.getInventory(), ModItems.STRAW_SCRAP.get());
                    if(playerHas >= MATERIAL_COUNT){
                        removeSticksFromInventory(player.getInventory(), ModItems.STRAW_SCRAP.get(), MATERIAL_COUNT);
                    }
                    else{
                        player.displayClientMessage(Component.literal("Not enough material " + playerHas + "/" + MATERIAL_COUNT), true);
                        return InteractionResult.FAIL;
                    }
                }
                level.playSound(null, useOnContext.getClickedPos(), SoundEvents.WITHER_BREAK_BLOCK, player.getSoundSource(), 1.0F, 1.0F);
                ((BlockItem) mode.getItem().getItem()).place(new BlockPlaceContext(useOnContext));
            }

            case UPGRADE -> {
                BlockPos blockPos = useOnContext.getClickedPos();
                BlockState blockState = level.getBlockState(useOnContext.getClickedPos());
                if(blockState.getBlock() instanceof StructureBlock structureBlock){
                    structureBlock.upgrade(level, blockPos, blockState);
                    level.playSound(null, useOnContext.getClickedPos(), SoundEvents.ANVIL_USE, player.getSoundSource(), 1.0F, 1.0F);
                }
            }
        }

        return InteractionResult.SUCCESS;
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

    private int countMaterialInInventory(Inventory inventory, Item item){
        return inventory.items.stream().filter(stack -> stack.getItem() == item).mapToInt(ItemStack::getCount).sum();
    }

    private void removeSticksFromInventory(Inventory inventory, Item item, int count) {
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
