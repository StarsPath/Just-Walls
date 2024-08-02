package com.starspath.justwalls.blocks.abstracts;

import com.starspath.justwalls.utils.Tiers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class StructureBlock extends MultiBlock {
    public StructureBlock(Properties properties) {
        super(properties);
    }


    protected abstract Boolean isMaster(BlockState blockState, BlockState self);

    protected abstract BlockPos getMasterPos(LevelAccessor level, BlockPos blockPos, BlockState blockState);

    protected void masterBreak(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState){
        ArrayList<BlockPos> childPos = getChildPos(levelAccessor, blockPos, blockState);
        childPos.stream().forEach(blockPos1 -> levelAccessor.destroyBlock(blockPos1, true));
    }

    protected ArrayList<BlockPos> getChildPos(LevelAccessor level, BlockPos blockPos, BlockState blockState){
        return new ArrayList<>();
    }

    protected Block getNextTierBlock(Tiers.TIER tier){
        return Blocks.AIR;
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {

        if(levelAccessor.isClientSide()){
            return;
        }

        Boolean isMaster = blockState.getValue(MASTER);

        if(isMaster){
            masterBreak(levelAccessor, blockPos, blockState);
            return;
        }

        BlockPos masterPos = getMasterPos(levelAccessor, blockPos, blockState);
        if(masterPos != null){
            StructureBlock masterWall = (StructureBlock)levelAccessor.getBlockState(masterPos).getBlock();
            masterWall.masterBreak(levelAccessor, masterPos, levelAccessor.getBlockState(masterPos));
        }

        super.destroy(levelAccessor, blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState prevBlockState, Level level, BlockPos blockPos, BlockState newBlockState, boolean flag) {
        if(level.isClientSide()){
            return;
        }

        Boolean isMaster = prevBlockState.getValue(MASTER);

        if(isMaster){
            masterBreak(level, blockPos, prevBlockState);
            return;
        }

        BlockPos masterPos = this.getMasterPos(level, blockPos, prevBlockState);
        if(masterPos != null) {
            StructureBlock masterWall = (StructureBlock) level.getBlockState(masterPos).getBlock();
            masterWall.masterBreak(level, masterPos, level.getBlockState(masterPos));
        }

        super.onRemove(prevBlockState, level, blockPos, newBlockState, flag);
    }

    public void upgrade(LevelAccessor level, BlockPos blockPos, BlockState blockState) {
        if(level.isClientSide()){
            return;
        }

        Boolean isMaster = blockState.getValue(MASTER);
        if(blockState.getValue(TIER) == Tiers.TIER.values()[Tiers.TIER.values().length - 1]){
            return;
        }

        if(isMaster){
            ArrayList<BlockPos> childPosList = getChildPos(level, blockPos, blockState);
            ArrayList<BlockState> childBlockStateList = childPosList.stream().map(blockPos1 -> level.getBlockState(blockPos1)).collect(Collectors.toCollection(ArrayList::new));

            masterBreak(level, blockPos, blockState);

            for(int i = 0; i < childPosList.size(); i++){
                BlockPos childPos = childPosList.get(i);
                BlockState childState = childBlockStateList.get(i);

                Tiers.TIER nextTier =  blockState.getValue(TIER).getNext();
                Block nexTierBlock = getNextTierBlock(nextTier);
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
            StructureBlock masterWall = (StructureBlock)level.getBlockState(masterPos).getBlock();
            masterWall.upgrade(level, masterPos, level.getBlockState(masterPos));
        }
    }

}