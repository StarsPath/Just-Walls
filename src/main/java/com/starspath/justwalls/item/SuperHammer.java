package com.starspath.justwalls.item;

import com.starspath.justwalls.JustWalls;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SuperHammer extends Item {

    public static final String modeNBTString = "justwalls.hammermode";

    public enum TOOL_MODE {
        WALL("wall"),
        FLOOR("floor"),
        DOOR_FRAME("door_frame"),
        WINDOW_FRAME("window_frame"),
        UPGRADE("upgrade"),
        NONE("none");

        private final String name;

        TOOL_MODE(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public SuperHammer(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        System.out.println("USE");
        ItemStack itemStack = player.getItemInHand(interactionHand);

        return super.use(level, player, interactionHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos pos = useOnContext.getClickedPos();
        System.out.println("USE ON");
        return super.useOn(useOnContext);
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
            return TOOL_MODE.valueOf(tag.getString(modeNBTString));
        }
        return TOOL_MODE.NONE;
    }
}
