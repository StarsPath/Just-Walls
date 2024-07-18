package com.starspath.justwalls.blocks;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class WallWindow extends Block {
    public static BooleanProperty MASTER = BooleanProperty.create("master");

    public WallWindow(Properties properties) {
        super(properties.pushReaction(PushReaction.BLOCK));
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(MASTER, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.FACING);
        builder.add(MASTER);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return super.getStateForPlacement(blockPlaceContext);
    }

    @Override
    public float getShadeBrightness(BlockState p_220080_1_, BlockGetter p_220080_2_, BlockPos p_220080_3_) {
        return 1.0f;
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
            for(int j = -1; j <= 1; j++){
                BlockPos checkPos = blockPos.relative(direction.getClockWise(), i).above(j);
                if(isMaster(level.getBlockState(checkPos), prevBlockState)){
                    LogUtils.getLogger().debug("found master " + i + " " + j);

                    WallWindow masterWall = (WallWindow)level.getBlockState(checkPos).getBlock();
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
            for(int j = -1; j <= 1; j++){
                BlockPos checkPos = blockPos.relative(direction.getClockWise(), i).above(j);
                if(isMaster(levelAccessor.getBlockState(checkPos), blockState)){
                    LogUtils.getLogger().debug("found master " + i + " " + j);

                    WallWindow masterWall = (WallWindow)levelAccessor.getBlockState(checkPos).getBlock();
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
            for(int j = -1; j <= 1; j++){
                BlockPos checkPos = blockPos.relative(direction.getClockWise(), i).above(j);
                BlockState checkBlockState = levelAccessor.getBlockState(checkPos);
                if(checkBlockState.getBlock() == blockState.getBlock() && checkBlockState.getValue(BlockStateProperties.FACING) == direction){
                    LogUtils.getLogger().debug("try to break " + i + " " + j);
                    levelAccessor.destroyBlock(checkPos, true);
                }
            }
        }
    }
}
