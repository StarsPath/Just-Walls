package com.starspath.justwalls.blocks;

import com.starspath.justwalls.blocks.abstracts.StructureBlock;
import com.starspath.justwalls.init.ModBlocks;
import com.starspath.justwalls.utils.Tiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.ArrayList;

public class WallDoor extends StructureBlock {

    public static BooleanProperty OPEN = BooleanProperty.create("open");

    public WallDoor(Properties properties, Tiers.TIER tier) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(MASTER, false).setValue(TIER, tier));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }

    @Override
    public float getShadeBrightness(BlockState p_220080_1_, BlockGetter p_220080_2_, BlockPos p_220080_3_) {
        return 1.0f;
    }

    @Override
    protected Boolean isMaster(BlockState blockState, BlockState self){
        return blockState.getBlock() == self.getBlock() && blockState.getValue(BlockStateProperties.FACING) == self.getValue(BlockStateProperties.FACING) && blockState.getValue(MASTER);
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
    protected ArrayList<BlockPos> getChildPos(LevelAccessor level, BlockPos blockPos, BlockState blockState){
        Direction direction = blockState.getValue(BlockStateProperties.FACING);
        ArrayList<BlockPos> childPos = new ArrayList<>();
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                BlockPos checkPos = blockPos.relative(direction.getClockWise(), i).above(j);
                BlockState checkBlockState = level.getBlockState(checkPos);
                if(checkBlockState.getBlock() == this && checkBlockState.getValue(BlockStateProperties.FACING) == direction){
                    childPos.add(checkPos);
                }
            }
        }
        return childPos;
    }

    @Override
    protected Block getNextTierBlock(Tiers.TIER tier){
        return ModBlocks.THATCH_WALL.get();
    }
}

