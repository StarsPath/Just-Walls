package com.starspath.justwalls.item.abstracts;

import com.starspath.justwalls.world.StructureTimerSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;

public abstract class StructureBlockItem extends BlockItem {

    public StructureBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    protected void updateMasterPlacedTime(Level level, BlockPos pos){
        StructureTimerSaveData structureTimerSaveData = StructureTimerSaveData.get(level);
        structureTimerSaveData.setTime(pos, level.getGameTime());
    }

    protected abstract void doPlacement(ArrayList<BlockPos> blockPosList, BlockPlaceContext blockPlaceContext);
}
