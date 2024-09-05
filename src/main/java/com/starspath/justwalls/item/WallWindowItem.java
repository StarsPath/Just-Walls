package com.starspath.justwalls.item;

import com.starspath.justwalls.item.abstracts.StructureBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static com.starspath.justwalls.blocks.abstracts.MultiBlock.MASTER;

public class WallWindowItem extends StructureBlockItem {
    public WallWindowItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext blockPlaceContext) {
        Level level = blockPlaceContext.getLevel();
        if(level.isClientSide){
            return InteractionResult.PASS;
        }

        Player player = blockPlaceContext.getPlayer();
        BlockPos pos = blockPlaceContext.getClickedPos();

        ArrayList<BlockPos> blockPosList  = new ArrayList<>();
        ArrayList<BlockPos> pillarPosList = new ArrayList<>();

        Direction direction = blockPlaceContext.getHorizontalDirection();
        for (int i = -1; i <= 1; i++) {
            for (int j = 0; j <= 2; j++) {
                BlockPos newPos = pos.relative(direction.getClockWise(), i).above(j);
                blockPosList.add(newPos);
            }
        }

        for(int i = 0; i <= 2; i++){
            BlockPos pillarPos = pos.relative(direction.getClockWise(), -2).above(i);
            pillarPosList.add(pillarPos);
            BlockPos pillarPos2 = pos.relative(direction.getClockWise(), 2).above(i);
            pillarPosList.add(pillarPos2);
        }

        boolean result = placementCheck(blockPosList, pillarPosList, blockPlaceContext);
        if(result){
            doPlacement(blockPosList, blockPlaceContext);
            if(!player.isCreative() && blockPlaceContext.getItemInHand().getItem() == this){
                blockPlaceContext.getItemInHand().grow(-1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    protected boolean placementCheck(ArrayList<BlockPos> blockPosList, BlockPlaceContext blockPlaceContext){
        Level level = blockPlaceContext.getLevel();
        for (BlockPos pos : blockPosList) {
            if (!level.getBlockState(pos).canBeReplaced()) {
                Player player = blockPlaceContext.getPlayer();
                player.displayClientMessage(Component.translatable("message.justwalls.wall_space_occupied"), true);
                return false;
            }
        }
        return true;
    }

    protected boolean placementCheck(ArrayList<BlockPos> toPlaceList, ArrayList<BlockPos> pillarPosList, BlockPlaceContext blockPlaceContext){
//        Level level = blockPlaceContext.getLevel();
//        for(BlockPos pillarPos: pillarPosList){
//            if(!(level.getBlockState(pillarPos).getBlock() instanceof WallPillar)){
//                Player player = blockPlaceContext.getPlayer();
//                player.displayClientMessage(Component.literal("No Pillar Nearby"), true);
//                return false;
//            }
//        }
        return placementCheck(toPlaceList, blockPlaceContext);
    }

    @Override
    protected void doPlacement(ArrayList<BlockPos> blockPosList, BlockPlaceContext blockPlaceContext){
        Level level = blockPlaceContext.getLevel();
        for(int i = 0; i < blockPosList.size(); i++){
            BlockPos pos = blockPosList.get(i);
            BlockState state = getPlacementState(blockPlaceContext);
            if(i == blockPosList.size()/2){
                state = state.setValue(MASTER, true);
                updateMasterPlacedTime(level, pos);
            }
            level.setBlockAndUpdate(pos, state);
        }
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext blockPlaceContext) {
        return getBlock().defaultBlockState()
                .setValue(BlockStateProperties.FACING, blockPlaceContext.getHorizontalDirection())
                .setValue(MASTER, false);
    }
}
