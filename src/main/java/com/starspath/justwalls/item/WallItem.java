package com.starspath.justwalls.item;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

import static com.starspath.justwalls.blocks.LootCrate.MASTER;


public class WallItem extends BlockItem {
    public int placementStrategy;
    public WallItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext blockPlaceContext) {
        Level level = blockPlaceContext.getLevel();
        if(level.isClientSide){
            return InteractionResult.PASS;
        }
        LogUtils.getLogger().debug("placing block");

        Player player = blockPlaceContext.getPlayer();
        BlockPos pos = blockPlaceContext.getClickedPos();

        if(blockPlaceContext.getClickedPos().getY() == player.getBlockY()){
            placementStrategy = 1;
        }
        else if(blockPlaceContext.getNearestLookingDirection().getAxis().isHorizontal() && blockPlaceContext.getClickedFace() == Direction.UP){
            placementStrategy = 1; // place on top of a block but looking horizontal (MASTER)
        }
        else if(blockPlaceContext.getClickedFace().getAxis().isHorizontal()){
            placementStrategy = 2; // place on the side of a block (MASTER ABOVE)
        }
        else if(blockPlaceContext.getClickedFace().getAxis().isVertical()){
            placementStrategy = 3; // place while looking up or down (MASTER)
        }

        LogUtils.getLogger().debug("" + placementStrategy);

        ArrayList<BlockPos> blockPosList  = new ArrayList<>();

        switch (placementStrategy) {
            case 1 -> {
                Direction direction = blockPlaceContext.getHorizontalDirection();
                for (int i = -1; i <= 1; i++) {
                    for (int j = 0; j <= 2; j++) {
                        BlockPos newPos = pos.relative(direction.getClockWise(), i).above(j);
                        blockPosList.add(newPos);
                    }
                }
            }
            case 2 -> {
                Direction direction = blockPlaceContext.getClickedFace();
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        BlockPos newPos = pos.relative(direction.getClockWise(), i).above(j);
                        blockPosList.add(newPos);
                    }
                }
            }
            case 3 -> {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        BlockPos newPos = pos.relative(Direction.NORTH, i).relative(Direction.EAST, j);
                        blockPosList.add(newPos);
                    }
                }
            }
        }
        boolean result = placementCheck(blockPosList, blockPlaceContext);
        if(result){
            doPlacement(blockPosList, blockPlaceContext);
            if(!player.isCreative()){
                blockPlaceContext.getItemInHand().grow(-1);
            }
        }

        LogUtils.getLogger().debug("" + result);

        return InteractionResult.SUCCESS;
//        return super.place(blockPlaceContext);
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
        Direction facing;
        if(placementStrategy == 1){
            facing = blockPlaceContext.getHorizontalDirection();
        }
        else if(placementStrategy == 2){
            facing = blockPlaceContext.getClickedFace();
        }
        else if(placementStrategy == 3){
            facing = blockPlaceContext.getClickedFace();
        }
        else{
            facing = blockPlaceContext.getNearestLookingDirection();
        }

        return getBlock().defaultBlockState()
                .setValue(BlockStateProperties.FACING, facing)
                .setValue(MASTER, false);
    }
}
