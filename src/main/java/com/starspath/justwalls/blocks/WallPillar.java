package com.starspath.justwalls.blocks;

import com.starspath.justwalls.blocks.abstracts.StructureBlock;
import com.starspath.justwalls.init.ModBlocks;
import com.starspath.justwalls.utils.Tiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;

public class WallPillar extends StructureBlock {
    public int height;

    public WallPillar(Properties properties, Tiers.TIER tier) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(MASTER, false).setValue(TIER, tier));
        this.height = 3;
    }

    public WallPillar(Properties properties, Tiers.TIER tier, int height) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(MASTER, false).setValue(TIER, tier));
        this.height = height;
    }

    @Override
    protected Boolean isMaster(BlockState blockState, BlockState self) {
        return blockState.getBlock() == self.getBlock() && blockState.getValue(BlockStateProperties.FACING) == self.getValue(BlockStateProperties.FACING) && blockState.getValue(MASTER);
    }

    @Override
    protected BlockPos getMasterPos(LevelAccessor level, BlockPos blockPos, BlockState blockState) {
        int offset = height / 2;

        for (int i = 0; i < height; i++) {
            int index = i - offset;
            BlockPos pos = blockPos.above(index);
            if(isMaster(level.getBlockState(pos), blockState)){
                return pos;
            }
        }
        return null;
    }

    @Override
    protected ArrayList<BlockPos> getChildPos(LevelAccessor level, BlockPos blockPos, BlockState blockState){
        Direction direction = blockState.getValue(BlockStateProperties.FACING);
        ArrayList<BlockPos> childPos = new ArrayList<>();
        int offset = (height - 1) / 2;
        for (int i = 0; i < height; i++) {
            int index = i - offset;
            BlockPos checkPos = blockPos.above(index);
            BlockState checkBlockState = level.getBlockState(checkPos);
            if(checkBlockState.getBlock() == this && checkBlockState.getValue(BlockStateProperties.FACING) == direction){
                childPos.add(checkPos);
            }
        }
        return childPos;
    }

    @Override
    protected Block getNextTierBlock(Tiers.TIER tier){
        switch (tier){
            case THATCH -> {
                return ModBlocks.THATCH_WALL_PILLAR.get();
            }
            case WOOD -> {
                return ModBlocks.WOODEN_WALL_PILLAR.get();
            }
            case STONE -> {
                return ModBlocks.STONE_WALL_PILLAR.get();
            }
            case METAL -> {
                return ModBlocks.METAL_WALL_PILLAR.get();
            }
            case ARMOR -> {
                return ModBlocks.ARMORED_WALL_PILLAR.get();
            }
        }
        return ModBlocks.THATCH_WALL_FLOOR.get();
    }
}
