package com.starspath.justwalls.item;

import com.starspath.justwalls.blocks.LootCrate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;

import static com.starspath.justwalls.blocks.abstracts.MultiBlock.MASTER;

public class LootCrateItem extends BlockItem {
    public LootCrateItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext blockPlaceContext) {

        Direction direction = blockPlaceContext.getHorizontalDirection();

        BlockPos masterPos = blockPlaceContext.getClickedPos();
        BlockPos pos1 = masterPos.relative(direction);
        BlockPos pos2 = masterPos.relative(direction.getClockWise());
        BlockPos pos3 = masterPos.relative(direction.getClockWise()).relative(direction);
        BlockPos pos4 = masterPos.above();
        BlockPos pos5 = masterPos.relative(direction).above();
        BlockPos pos6 = masterPos.relative(direction.getClockWise()).above();
        BlockPos pos7 = masterPos.relative(direction.getClockWise()).relative(direction).above();

        ArrayList<BlockPos> blockPosList =  new ArrayList<>();
        blockPosList.add(masterPos);
        blockPosList.add(pos1);
        blockPosList.add(pos2);
        blockPosList.add(pos3);
        blockPosList.add(pos4);
        blockPosList.add(pos5);
        blockPosList.add(pos6);
        blockPosList.add(pos7);

        Player player = blockPlaceContext.getPlayer();
        boolean result = placementCheck(blockPosList, blockPlaceContext);
        if(result){
            doPlacement(blockPosList, blockPlaceContext);
            if(!player.isCreative() && blockPlaceContext.getItemInHand().getItem() == this){
                blockPlaceContext.getItemInHand().grow(-1);
            }
        }

        return InteractionResult.SUCCESS;
    }

    protected boolean placementCheck(ArrayList<BlockPos> blockPosList, BlockPlaceContext blockPlaceContext) {
        Level level = blockPlaceContext.getLevel();
        for (BlockPos pos : blockPosList) {
            if (!level.getBlockState(pos).canBeReplaced()) {
                return false;
            }
        }
        return true;
    }

    protected void doPlacement(ArrayList<BlockPos> blockPosList, BlockPlaceContext blockPlaceContext) {
        Level level = blockPlaceContext.getLevel();

        for(int i = 0; i < blockPosList.size(); i++){
            BlockPos pos = blockPosList.get(i);
            BlockState state = getPlacementState(blockPlaceContext);
            state = state.setValue(LootCrate.ID, i);
            if(i == 0){
                state = state.setValue(MASTER, true);
            }
            level.setBlockAndUpdate(pos, state);
        }
    }

    protected BlockState getPlacementState(BlockPlaceContext blockPlaceContext) {
        Direction facing = blockPlaceContext.getHorizontalDirection();

        return getBlock().defaultBlockState()
                .setValue(BlockStateProperties.FACING, facing)
                .setValue(MASTER, false);
    }
}
