package com.starspath.justwalls.blocks.abstracts;

import com.starspath.justwalls.utils.Tiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;

public abstract class MultiBlock extends Block {
    public static BooleanProperty MASTER = BooleanProperty.create("master");
    public static EnumProperty<Tiers.TIER> TIER = EnumProperty.create("tier", Tiers.TIER.class);

    public MultiBlock(Properties properties) {
        super(properties.pushReaction(PushReaction.BLOCK));
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        Tiers.TIER tier = state.getValue(TIER);
        switch (tier){
            case THATCH -> {
                return 10;
            }
            case WOOD -> {
                return 5;
            }
            case STONE, METAL, ARMOR -> {
                return 0;
            }
        }
        return super.getFlammability(state, level, pos, direction);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        Tiers.TIER tier = state.getValue(TIER);
        switch (tier){
            case THATCH -> {
                return 100;
            }
            case WOOD -> {
                return 5;
            }
            case STONE, METAL, ARMOR -> {
                return 0;
            }
        }
        return super.getFireSpreadSpeed(state, level, pos, direction);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MASTER);
        builder.add(BlockStateProperties.FACING);
        builder.add(TIER);
    }
}
