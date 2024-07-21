package com.starspath.justwalls.blocks.abstracts;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;

public abstract class MultiBlock extends Block {
    public static BooleanProperty MASTER = BooleanProperty.create("master");

    public MultiBlock(Properties properties) {
        super(properties.pushReaction(PushReaction.BLOCK));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MASTER);
        builder.add(BlockStateProperties.FACING);
    }
}
