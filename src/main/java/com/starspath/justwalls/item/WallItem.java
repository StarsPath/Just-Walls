package com.starspath.justwalls.item;

import com.starspath.justwalls.blocks.Wall;
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

public class WallItem extends StructureBlockItem {
    public int placementStrategy;
    public WallItem(Block block, Properties properties) {
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

        if(blockPlaceContext.getClickedPos().getY() == player.getBlockY()){
            placementStrategy = 1;
        }
        else if(blockPlaceContext.getNearestLookingDirection().getAxis().isHorizontal() && blockPlaceContext.getClickedFace() == Direction.UP){
            placementStrategy = 1; // place on top of a block but looking horizontal (MASTER)
        }
        else if(blockPlaceContext.getClickedFace().getAxis().isHorizontal()){
            placementStrategy = 2; // place on the side of a block (MASTER ABOVE)
        }

        ArrayList<BlockPos> blockPosList  = new ArrayList<>();
        ArrayList<BlockPos> pillarPosList = new ArrayList<>();
        ArrayList<BlockPos> otherWallPosList = new ArrayList<>();

        switch (placementStrategy) {
            case 1 -> { // place on ground
                Direction direction = blockPlaceContext.getHorizontalDirection();
                for (int i = -1; i <= 1; i++) {
                    for (int j = 0; j <= 2; j++) {
                        BlockPos newPos = pos.relative(direction.getClockWise(), i).above(j);
                        blockPosList.add(newPos);

                        for(int k = -2; k <= 2; k++){
                            BlockPos otherWallPos = newPos.relative(direction, k);
                            otherWallPosList.add(otherWallPos);
                        }
                    }
                }
                for(int i = 0; i <= 2; i++){
                    BlockPos pillarPos = pos.relative(direction.getClockWise(), -2).above(i);
                    pillarPosList.add(pillarPos);
                    BlockPos pillarPos2 = pos.relative(direction.getClockWise(), 2).above(i);
                    pillarPosList.add(pillarPos2);
                }
            }
            case 2 -> { // place on side of a block
                Direction direction = blockPlaceContext.getClickedFace();
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        BlockPos newPos = pos.relative(direction.getClockWise(), i).above(j);
                        blockPosList.add(newPos);

                        for(int k = -2; k <= 2; k++){
                            BlockPos otherWallPos = newPos.relative(direction, k);
                            otherWallPosList.add(otherWallPos);
                        }
                    }
                }
                for(int i = -1; i <= 1; i++){
                    BlockPos pillarPos = pos.relative(direction.getClockWise(), -2).above(i);
                    pillarPosList.add(pillarPos);
                    BlockPos pillarPos2 = pos.relative(direction.getClockWise(), 2).above(i);
                    pillarPosList.add(pillarPos2);
                }
            }
        }
        boolean result = placementCheck(blockPosList, pillarPosList, otherWallPosList, blockPlaceContext);
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

    protected boolean placementCheck(ArrayList<BlockPos> toPlaceList, ArrayList<BlockPos> pillarPosList, ArrayList<BlockPos> otherWallPosList, BlockPlaceContext blockPlaceContext){
        Level level = blockPlaceContext.getLevel();
//        for(BlockPos pillarPos: pillarPosList){
//            if(!(level.getBlockState(pillarPos).getBlock() instanceof WallPillar)){
//                Player player = blockPlaceContext.getPlayer();
//                player.displayClientMessage(Component.literal("No Pillar Nearby"), true);
//                return false;
//            }
//        }

        for(BlockPos wallPos: otherWallPosList){
            if(level.getBlockState(wallPos).getBlock() instanceof Wall){
                Player player = blockPlaceContext.getPlayer();
                player.displayClientMessage(Component.translatable("message.justwalls.wall_space_occupied"), true);
                return false;
            }
        }
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
        Direction facing;
        if(placementStrategy == 1){
            facing = blockPlaceContext.getHorizontalDirection();
        }
        else if(placementStrategy == 2){
            facing = blockPlaceContext.getClickedFace();
        }
        else{
            facing = blockPlaceContext.getNearestLookingDirection();
        }

        return getBlock().defaultBlockState()
                .setValue(BlockStateProperties.FACING, facing)
                .setValue(MASTER, false);
    }
}
