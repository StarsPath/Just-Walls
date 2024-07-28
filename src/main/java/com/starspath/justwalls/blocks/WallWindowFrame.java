package com.starspath.justwalls.blocks;

import com.starspath.justwalls.blocks.abstracts.MultiBlock;
import com.starspath.justwalls.utils.Tiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class WallWindowFrame extends MultiBlock {
    public WallWindowFrame(Properties properties, Tiers.TIER tier) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(MASTER, false).setValue(TIER, tier));
    }

    @Override
    public void onRemove(BlockState prevBlockState, Level level, BlockPos blockPos, BlockState newBlockState, boolean flag) {
        if(level.isClientSide()){
            return;
        }

        Direction direction = prevBlockState.getValue(BlockStateProperties.FACING);
        Boolean isMaster = prevBlockState.getValue(MASTER);

        if(isMaster){
            masterBreak(level, blockPos, prevBlockState);
            return;
        }

        for(int i = -1; i <= 1; i++){
            for(int j = 0; j <= 2; j++){
                BlockPos checkPos = blockPos.relative(direction.getClockWise(), i).above(j);
                if(isMaster(level.getBlockState(checkPos), prevBlockState)){
                    WallWindowFrame masterWall = (WallWindowFrame)level.getBlockState(checkPos).getBlock();
                    masterWall.masterBreak(level, checkPos, level.getBlockState(checkPos));
                    return;
                }
            }
        }

        super.onRemove(prevBlockState, level, blockPos, newBlockState, flag);
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        if(levelAccessor.isClientSide()){
            return;
        }

        Direction direction = blockState.getValue(BlockStateProperties.FACING);
        Boolean isMaster = blockState.getValue(MASTER);

        if(isMaster){
            masterBreak(levelAccessor, blockPos, blockState);
            return;
        }

        for(int i = -1; i <= 1; i++){
            for(int j = 0; j <= 2; j++){
                BlockPos checkPos = blockPos.relative(direction.getClockWise(), i).above(j);
                if(isMaster(levelAccessor.getBlockState(checkPos), blockState)){
                    WallWindowFrame masterWall = (WallWindowFrame)levelAccessor.getBlockState(checkPos).getBlock();
                    masterWall.masterBreak(levelAccessor, checkPos, levelAccessor.getBlockState(checkPos));
                    return;
                }
            }
        }

        super.destroy(levelAccessor, blockPos, blockState);
    }

    protected Boolean isMaster(BlockState blockState, BlockState self){
        return blockState.getBlock() == self.getBlock() && blockState.getValue(BlockStateProperties.FACING) == self.getValue(BlockStateProperties.FACING) && blockState.getValue(MASTER);
    }

    protected void masterBreak(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState){
        Direction direction = blockState.getValue(BlockStateProperties.FACING);

        for(int i = -1; i <= 1; i++){
            for(int j = 0; j >= -2; j--){
                BlockPos checkPos = blockPos.relative(direction.getClockWise(), i).above(j);
                levelAccessor.destroyBlock(checkPos, true);
            }
        }
    }
}
