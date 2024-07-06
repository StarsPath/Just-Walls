package com.starspath.justwalls.blocks;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WoodenWall extends Wall{
    public WoodenWall() {
        super(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    }
}
