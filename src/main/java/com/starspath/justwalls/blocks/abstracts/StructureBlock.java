package com.starspath.justwalls.blocks.abstracts;

import com.starspath.justwalls.blocks.Wall;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public abstract class StructureBlock extends MultiBlock {
    public StructureBlock(Properties properties) {
        super(properties);
    }

    public void upgrade(LevelAccessor level, BlockPos blockPos, BlockState blockState){

    }

    protected abstract Boolean isMaster(BlockState blockState, BlockState self);

    protected abstract BlockPos getMasterPos(LevelAccessor level, BlockPos blockPos, BlockState blockState);

    protected abstract void masterBreak(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState);


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

}
