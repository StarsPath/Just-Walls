package com.starspath.justwalls.item;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static com.starspath.justwalls.blocks.abstracts.MultiBlock.MASTER;

public class WallFloorItem extends BlockItem {
    public int placementStrategy;
    public WallFloorItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext blockPlaceContext) {
        Level level = blockPlaceContext.getLevel();
        if(level.isClientSide){
            return InteractionResult.PASS;
        }

        Player player = blockPlaceContext.getPlayer();
        BlockPos pos = blockPlaceContext.getClickedPos();

        if(blockPlaceContext.getClickedFace().getAxis().isVertical()){
            placementStrategy = 1;
        }
        else if(blockPlaceContext.getClickedFace().getAxis().isHorizontal()){
            placementStrategy = 2;
        }

        LogUtils.getLogger().debug("" + placementStrategy);

        ArrayList<BlockPos> blockPosList  = new ArrayList<>();

        switch (placementStrategy) {
            case 1 -> {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        BlockPos newPos = pos.relative(Direction.NORTH, i).relative(Direction.EAST, j);
                        blockPosList.add(newPos);
                    }
                }
            }
            case 2 -> {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        BlockPos newPos = pos.relative(blockPlaceContext.getClickedFace()).relative(Direction.NORTH, i).relative(Direction.EAST, j);
                        blockPosList.add(newPos);
                    }
                }
            }
        }
        boolean result = placementCheck(blockPosList, blockPlaceContext);
        if(result){
            doPlacement(blockPosList, blockPlaceContext);
            if(!player.isCreative() && blockPlaceContext.getItemInHand().getItem() == this){
                blockPlaceContext.getItemInHand().grow(-1);
            }
        }
        else{
            Minecraft.getInstance().gui.setOverlayMessage(Component.literal("Space Occupied"), true);
        }

        LogUtils.getLogger().debug("" + result);

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
            if(i == blockPosList.size()/2){
                state = state.setValue(MASTER, true);
            }
            level.setBlockAndUpdate(pos, state);
        }
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext blockPlaceContext) {
        return getBlock().defaultBlockState()
                .setValue(BlockStateProperties.FACING, Direction.DOWN)
                .setValue(MASTER, false);
    }
}
