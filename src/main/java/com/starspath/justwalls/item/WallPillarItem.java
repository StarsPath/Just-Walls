package com.starspath.justwalls.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.starspath.justwalls.blocks.WallPillar.HEIGHT;
import static com.starspath.justwalls.blocks.abstracts.MultiBlock.MASTER;

public class WallPillarItem extends BlockItem {
    public int height;

    public WallPillarItem(Block block, Properties properties) {
        super(block, properties);
        this.height = 3;
    }

    public WallPillarItem(Block block, Properties properties, int height) {
        super(block, properties);
        this.height = height;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("tooltip.justwalls.pillar_height").append(String.valueOf(this.height)));
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

    @Override
    public InteractionResult place(BlockPlaceContext blockPlaceContext) {
        Level level = blockPlaceContext.getLevel();
        if(level.isClientSide){
            return InteractionResult.PASS;
        }

        Player player = blockPlaceContext.getPlayer();
        BlockPos pos = blockPlaceContext.getClickedPos();
        Direction direction = blockPlaceContext.getClickedFace();

        ArrayList<BlockPos> blockPosList  = new ArrayList<>();

        for(int i = 0; i < height; i++){
            blockPosList.add(pos.relative(direction, i));
        }

        boolean result = placementCheck(blockPosList, blockPlaceContext);
        if(result){
            doPlacement(blockPosList, blockPlaceContext);
            if(!player.isCreative() && blockPlaceContext.getItemInHand().getItem() == this){
                blockPlaceContext.getItemInHand().grow(-1);
            }
        }
        else{
            player.displayClientMessage(Component.literal("Space Occupied"), true);
        }

        return InteractionResult.SUCCESS;
    }

    protected boolean placementCheck(ArrayList<BlockPos> blockPosList, BlockPlaceContext blockPlaceContext){
        Level level = blockPlaceContext.getLevel();
        for (BlockPos pos : blockPosList) {
            if (!level.getBlockState(pos).canBeReplaced()) {
                return false;
            }
        }
        return true;
    }

    protected void doPlacement(ArrayList<BlockPos> blockPosList, BlockPlaceContext blockPlaceContext){
        Level level = blockPlaceContext.getLevel();
        for(int i = 0; i < blockPosList.size(); i++){
            BlockPos pos = blockPosList.get(i);
            BlockState state = getPlacementState(blockPlaceContext);
            if(i == (blockPosList.size() - 1)/2){
                state = state.setValue(MASTER, true);
            }
            level.setBlockAndUpdate(pos, state);
        }
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext blockPlaceContext) {
        return getBlock().defaultBlockState()
                .setValue(BlockStateProperties.FACING, blockPlaceContext.getClickedFace())
                .setValue(HEIGHT, height)
                .setValue(MASTER, false);
    }
}
