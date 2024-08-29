package com.starspath.justwalls.blocks;

import com.starspath.justwalls.blocks.abstracts.StructureBlock;
import com.starspath.justwalls.init.ModBlocks;
import com.starspath.justwalls.init.ModItems;
import com.starspath.justwalls.utils.Tiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.ArrayList;

public class WallPillar extends StructureBlock {
    public static IntegerProperty HEIGHT = IntegerProperty.create("height", 2, 5);

    public WallPillar(Tiers.TIER tier){
        super(tier);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(MASTER, false).setValue(TIER, tier).setValue(HEIGHT, 3));
    }

    public WallPillar(Properties properties, Tiers.TIER tier) {
        super(properties, tier);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(MASTER, false).setValue(TIER, tier).setValue(HEIGHT, 3));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HEIGHT);
    }

    @Override
    protected Boolean isMaster(BlockState blockState, BlockState self) {
        return blockState.getBlock() == self.getBlock()
                && blockState.getValue(BlockStateProperties.FACING) == self.getValue(BlockStateProperties.FACING)
                && blockState.getValue(MASTER)
                && blockState.getValue(HEIGHT).equals(self.getValue(HEIGHT))
                && blockState.getValue(TIER) == self.getValue(TIER);
    }

    @Override
    public BlockPos getMasterPos(LevelAccessor level, BlockPos blockPos, BlockState blockState) {
        int height = blockState.getValue(HEIGHT);
        int offset = height / 2;

        for (int i = 0; i < height; i++) {
            int index = i - offset;
            BlockPos pos = blockPos.relative(blockState.getValue(BlockStateProperties.FACING), index);
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
        int height = blockState.getValue(HEIGHT);
        int offset = (height - 1) / 2;
        for (int i = 0; i < height; i++) {
            int index = i - offset;
            BlockPos checkPos = blockPos.relative(direction, index);
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

    @Override
    public ItemStack getRequiredItemForUpgrade(BlockState blockState){
        return getRequiredItemForUpgrade(blockState, blockState.getValue(HEIGHT));
    }
}
