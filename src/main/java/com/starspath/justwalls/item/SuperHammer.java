package com.starspath.justwalls.item;

import com.starspath.justwalls.Config;
import com.starspath.justwalls.blocks.abstracts.StructureBlock;
import com.starspath.justwalls.init.ModItems;
import com.starspath.justwalls.utils.RadialMenuItem;
import com.starspath.justwalls.utils.Tiers;
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

import static com.starspath.justwalls.blocks.abstracts.MultiBlock.TIER;


public class SuperHammer extends Item {

    public static final String modeNBTString = "justwalls.hammermode";
    public static final String extraNBTString = "justwalls.hammermode_extra";

    public final int MATERIAL_COUNT = 18;

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
        String mode = getMode(useOnContext.getItemInHand());
        Player player = useOnContext.getPlayer();
        player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 40);

        switch (mode) {
            case "wall", "floor", "door_frame", "window_frame", "pillar_3", "pillar_4", "pillar_5" -> {
                RadialMenuItem radialMenuItem = RadialMenuItem.getRadialMenuItemByName(RadialMenuItem.ALL_ITEMS, mode);

                ItemStack itemStack = getRequiredItemForConstruction(mode);
                int playerHas = countMaterialInInventory(player.getInventory(), itemStack.getItem());
                if(playerHas >= itemStack.getCount() || player.isCreative()){
                    InteractionResult interactionResult = ((BlockItem) radialMenuItem.getItemToRender().getItem()).place(new BlockPlaceContext(useOnContext));
                    if(interactionResult == InteractionResult.SUCCESS){
                        if(!player.isCreative()){
                            consumeIfAvailable(player, itemStack);
                        }
                        level.playSound(null, useOnContext.getClickedPos(), SoundEvents.WITHER_BREAK_BLOCK, player.getSoundSource(), 1.0F, 1.0F);
                        level.playSound(null, useOnContext.getClickedPos(), SoundEvents.ANVIL_DESTROY, player.getSoundSource(), 1.0F, 1.0F);
                    }
                }
                else{
                    player.displayClientMessage(Component.translatable("gui.justwalls.not_enough_material").append(Component.translatable(ModItems.STRAW_SCRAP.get().getDescriptionId())).append(" " + playerHas + "/" + itemStack.getCount()), true);
                    return InteractionResult.FAIL;
                }
            }

            case "upgrade" -> {
                BlockPos blockPos = useOnContext.getClickedPos();
                BlockState blockState = level.getBlockState(useOnContext.getClickedPos());
                if(blockState.getBlock() instanceof StructureBlock structureBlock){
                    if(blockState.getValue(TIER) == Tiers.TIER.ARMOR){
                        return InteractionResult.FAIL;
                    }
                    ItemStack itemStack = structureBlock.getRequiredItemForUpgrade(blockState);
                    int playerHas = countMaterialInInventory(player.getInventory(), itemStack.getItem());

                    if(playerHas >= itemStack.getCount() || player.isCreative()){
                        InteractionResult interactionResult = structureBlock.upgrade(level, blockPos, blockState);
                        if(interactionResult == InteractionResult.SUCCESS){
                            if(!player.isCreative()){
                                consumeIfAvailable(player, itemStack);
                            }
                            level.playSound(null, useOnContext.getClickedPos(), SoundEvents.ANVIL_USE, player.getSoundSource(), 1.0F, 1.0F);
                        }
                    }
                    else{
                        player.displayClientMessage(Component.translatable("gui.justwalls.not_enough_material").append(itemStack.getHoverName()).append(" " + playerHas + "/" + itemStack.getCount()), true);
                        return InteractionResult.FAIL;
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        if(itemStack.hasTag()){
            String tag = itemStack.getTag().getString(modeNBTString);
            list.add(Component.translatable("tooltip.justwalls.super_hammer").append(Component.translatable("gui.justwalls.super_hammer."+tag)));
        }
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

    public void setMode(ItemStack itemStack, String mode){
        setMode(itemStack, mode, 0);
    }

    public void setMode(ItemStack itemStack, String mode, int extra){
        if(!itemStack.hasTag()){
            CompoundTag tag = new CompoundTag();
            tag.putString(modeNBTString, mode);
            tag.putInt(extraNBTString, extra);
            itemStack.setTag(tag);
        }
        else{
            CompoundTag tag = itemStack.getTag();
            tag.putString(modeNBTString, mode);
            tag.putInt(extraNBTString, extra);
            itemStack.setTag(tag);
        }
    }

    public String getMode(ItemStack itemStack){
        if(itemStack.hasTag()){
            CompoundTag tag = itemStack.getTag();
            return tag.getString(modeNBTString);
        }
        return "";
    }

    public int getExtra(ItemStack itemStack){
        if(itemStack.hasTag()){
            CompoundTag tag = itemStack.getTag();
            return tag.getInt(extraNBTString);
        }
        return 0;
    }

    private boolean consumeIfAvailable(Player player, ItemStack itemStack){
        int playerHas = countMaterialInInventory(player.getInventory(), itemStack.getItem());
        if(playerHas >= itemStack.getCount()){
            removeSticksFromInventory(player.getInventory(), itemStack.getItem(), itemStack.getCount());
            return true;
        }
        return false;
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

    private ItemStack getRequiredItemForConstruction(String mode){
        int materialPerBlock = Config.materialPerBlock;
        return switch (mode){
            case "wall", "floor" -> new ItemStack(ModItems.STRAW_SCRAP.get(), materialPerBlock * 9);
            case "door_frame" -> new ItemStack(ModItems.STRAW_SCRAP.get(), materialPerBlock * 7);
            case "window_frame" -> new ItemStack(ModItems.STRAW_SCRAP.get(), materialPerBlock * 8);
            case "pillar_3" -> new ItemStack(ModItems.STRAW_SCRAP.get(), materialPerBlock * 3);
            case "pillar_4" -> new ItemStack(ModItems.STRAW_SCRAP.get(), materialPerBlock * 4);
            case "pillar_5" -> new ItemStack(ModItems.STRAW_SCRAP.get(), materialPerBlock * 5);
            default -> new ItemStack(Items.AIR);
        };
    }
}
