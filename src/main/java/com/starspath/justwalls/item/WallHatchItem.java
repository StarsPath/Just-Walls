package com.starspath.justwalls.item;

import com.starspath.justwalls.blocks.WallFloor;
import com.starspath.justwalls.blocks.WallHatch;
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

import java.util.ArrayList;

import static com.starspath.justwalls.blocks.abstracts.MultiBlock.MASTER;

public class WallHatchItem extends BlockItem {
    public int placementStrategy;
    public WallHatchItem(Block block, Properties properties) {
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

        ArrayList<BlockPos> blockPosList  = new ArrayList<>();
        ArrayList<BlockPos> otherFloorPosList = new ArrayList<>();

        switch (placementStrategy) {
            case 1 -> {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        BlockPos newPos = pos.relative(Direction.NORTH, i).relative(Direction.EAST, j);
                        blockPosList.add(newPos);
                        for(int k = -2; k <= 2; k++){
                            BlockPos otherFloorPos = newPos.above(k);
                            otherFloorPosList.add(otherFloorPos);
                        }
                    }
                }
            }
            case 2 -> {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        BlockPos newPos = pos.relative(blockPlaceContext.getClickedFace()).relative(Direction.NORTH, i).relative(Direction.EAST, j);
                        blockPosList.add(newPos);
                        for(int k = -2; k <= 2; k++){
                            BlockPos otherFloorPos = newPos.above(k);
                            otherFloorPosList.add(otherFloorPos);
                        }
                    }
                }
            }
        }
        boolean result = placementCheck(blockPosList, otherFloorPosList, blockPlaceContext);
        if(result){
            doPlacement(blockPosList, blockPlaceContext);
            if(!player.isCreative() && blockPlaceContext.getItemInHand().getItem() == this){
                blockPlaceContext.getItemInHand().grow(-1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    protected boolean placementCheck(ArrayList<BlockPos> blockPosList, BlockPlaceContext blockPlaceContext){
        Level level = blockPlaceContext.getLevel();
        for (BlockPos pos : blockPosList) {
            if (!level.getBlockState(pos).canBeReplaced()) {
                Player player = blockPlaceContext.getPlayer();
                player.displayClientMessage(Component.literal("Space Occupied"), true);
                return false;
            }
        }
        return true;
    }

    protected boolean placementCheck(ArrayList<BlockPos> toPlaceList, ArrayList<BlockPos> floorPoslist, BlockPlaceContext blockPlaceContext){
        Level level = blockPlaceContext.getLevel();
        for(BlockPos floorPos: floorPoslist){
            Block block = level.getBlockState(floorPos).getBlock();
            if(block instanceof WallHatch || block instanceof WallFloor){
                Player player = blockPlaceContext.getPlayer();
                player.displayClientMessage(Component.literal("Hatch or Floor Already Exist"), true);
                return false;
            }
        }
        return placementCheck(toPlaceList, blockPlaceContext);
    }

    protected void doPlacement(ArrayList<BlockPos> blockPosList, BlockPlaceContext blockPlaceContext){
        Level level = blockPlaceContext.getLevel();
        for(int i = 0; i < blockPosList.size(); i++){
            if(i == blockPosList.size()/2){
                continue;
            }
            BlockPos pos = blockPosList.get(i);
            BlockState state = getPlacementState(blockPlaceContext);
            if(i == 5){
                state = state.setValue(MASTER, true);
            }
            level.setBlockAndUpdate(pos, state);
        }
    }
}
