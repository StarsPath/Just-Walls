package com.starspath.justwalls.blocks;

import com.starspath.justwalls.blocks.abstracts.MultiBlock;
import com.starspath.justwalls.blocks.abstracts.StructureBlock;
import com.starspath.justwalls.init.ModBlocks;
import com.starspath.justwalls.utils.Tiers;
import com.starspath.justwalls.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Wall extends StructureBlock {

    public Wall(Properties properties, Tiers.TIER tier) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(MASTER, false).setValue(TIER, tier));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return super.getStateForPlacement(blockPlaceContext);
    }

    @Override
    public void onRemove(BlockState prevBlockState, Level level, BlockPos blockPos, BlockState newBlockState, boolean flag) {
        super.onRemove(prevBlockState, level, blockPos, newBlockState, flag);
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        super.destroy(levelAccessor, blockPos, blockState);
    }

    protected Boolean isMaster(BlockState blockState, BlockState self){
        return blockState.getBlock() == self.getBlock() && blockState.getValue(BlockStateProperties.FACING) == self.getValue(BlockStateProperties.FACING) && blockState.getValue(MASTER);
    }

    protected void masterBreak(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState){
        Direction direction = blockState.getValue(BlockStateProperties.FACING);

        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                BlockPos checkPos = blockPos.relative(direction.getClockWise(), i).above(j);
                BlockState checkBlockState = levelAccessor.getBlockState(checkPos);
                if(checkBlockState.getBlock() == blockState.getBlock() && checkBlockState.getValue(BlockStateProperties.FACING) == direction){
                    levelAccessor.destroyBlock(checkPos, true);
                }
            }
        }
    }

    @Override
    protected BlockPos getMasterPos(LevelAccessor level, BlockPos blockPos, BlockState blockState) {
        Direction direction = blockState.getValue(BlockStateProperties.FACING);
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                BlockPos checkPos = blockPos.relative(direction.getClockWise(), i).above(j);
                if(isMaster(level.getBlockState(checkPos), blockState)){
                    return checkPos;
                }
            }
        }
        return null;
    }

    @Override
    public void upgrade(LevelAccessor level, BlockPos blockPos, BlockState blockState) {
        if(level.isClientSide()){
            return;
        }

        Boolean isMaster = blockState.getValue(MASTER);
        if(blockState.getValue(TIER) == Tiers.TIER.values()[Tiers.TIER.values().length - 1]){
            return;
        }

        if(isMaster){
            Direction direction = blockState.getValue(BlockStateProperties.FACING);
            ArrayList<BlockPos> childPosList = new ArrayList<>();
            ArrayList<BlockState> childBlockStateList = new ArrayList<>();
            for(int i = -1; i <= 1; i++){
                for(int j = -1; j <= 1; j++){
                    BlockPos checkPos = blockPos.relative(direction.getClockWise(), i).above(j);
                    BlockState blockStateToUpgrade = level.getBlockState(checkPos);
                    if(blockStateToUpgrade.getBlock() == this){
                        childPosList.add(checkPos);
                        childBlockStateList.add(blockStateToUpgrade);
                    }
                }
            }

            masterBreak(level, blockPos, blockState);

            for(int i = 0; i < childPosList.size(); i++){
                BlockPos childPos = childPosList.get(i);
                BlockState childState = childBlockStateList.get(i);

                Tiers.TIER nextTier =  blockState.getValue(TIER).getNext();
                Block nexTierBlock = getNextTierBlock(nextTier);
                Utils.debug(nextTier, nexTierBlock);
                BlockState newState = nexTierBlock.defaultBlockState()
                        .setValue(BlockStateProperties.FACING, childState.getValue(BlockStateProperties.FACING))
                        .setValue(MASTER, childState.getValue(MASTER))
                        .setValue(TIER, nextTier);

                level.setBlock(childPos, newState, UPDATE_NONE);
            }

            return;
        }

        BlockPos masterPos = getMasterPos(level, blockPos, blockState);
        if(masterPos != null){
            Wall masterWall = (Wall)level.getBlockState(masterPos).getBlock();
            masterWall.upgrade(level, masterPos, level.getBlockState(masterPos));
        }
        super.upgrade(level, blockPos, blockState);
    }

    protected Block getNextTierBlock(Tiers.TIER tier){
        Utils.debug(tier);
        switch (tier){
            case THATCH -> {
                return ModBlocks.THATCH_WALL.get();
            }
            case WOOD -> {
                return ModBlocks.WOODEN_WALL.get();
            }
            case STONE -> {
                return ModBlocks.STONE_WALL.get();
            }
            case METAL -> {
                return ModBlocks.METAL_WALL.get();
            }
            case ARMOR -> {
                return ModBlocks.ARMORED_WALL.get();
            }
        }
        return ModBlocks.THATCH_WALL.get();
    }
}
