package com.starspath.justwalls.item;

import com.starspath.justwalls.blocks.WallPillar;
import com.starspath.justwalls.item.abstracts.StructureBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.starspath.justwalls.blocks.WallPillar.HEIGHT;
import static com.starspath.justwalls.blocks.abstracts.MultiBlock.MASTER;

public class WallPillarItem extends StructureBlockItem {
    public int height;

    public WallPillarItem(Block block, Properties properties) {
        super(block, properties);
        this.height = 3;
    }

    public WallPillarItem(Block block, Properties properties, int height) {
        super(block, properties);
        this.height = height;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("tooltip.justwalls.pillar_height").append(String.valueOf(this.height)));
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

    @Override
    public InteractionResult place(BlockPlaceContext blockPlaceContext) {
        Level level = blockPlaceContext.getLevel();
        if(level.isClientSide){
            return InteractionResult.PASS;
        }

        Player player = blockPlaceContext.getPlayer();
        BlockPos pos = blockPlaceContext.getClickedPos();
        Direction direction = blockPlaceContext.getClickedFace();

        ArrayList<BlockPos> blockPosList  = new ArrayList<>();
        ArrayList<BlockPos> otherPillarPosList  = new ArrayList<>();

        for(int i = 0; i < height; i++){
            blockPosList.add(pos.relative(direction, i));

            for(int j = -2; j <=2; j++){
                for(int k = -2; k <= 2; k++){
                    if(direction.getAxis().isHorizontal()){
                        BlockPos otherPillarPos = pos.relative(direction, i).relative(direction.getClockWise(), j).above(k);
                        otherPillarPosList.add(otherPillarPos);
                    }
                    else{
                        BlockPos otherPillarPos = pos.relative(direction, i).north(j).east(k);
                        otherPillarPosList.add(otherPillarPos);
                    }
                }
            }

        }

        boolean result = placementCheck(blockPosList, otherPillarPosList, blockPlaceContext);
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

    protected boolean placementCheck(ArrayList<BlockPos> blockPosList, ArrayList<BlockPos> otherPillarPosList, BlockPlaceContext blockPlaceContext){
        Direction direction = blockPlaceContext.getClickedFace();
        Level level = blockPlaceContext.getLevel();
        for (BlockPos otherPillar : otherPillarPosList) {
            if (level.getBlockState(otherPillar).getBlock() instanceof WallPillar && level.getBlockState(otherPillar).getValue(BlockStateProperties.FACING).getAxis() == direction.getAxis()) {
                Player player = blockPlaceContext.getPlayer();
                player.displayClientMessage(Component.translatable("message.justwalls.wall_space_occupied"), true);
                return false;
            }
        }
        return placementCheck(blockPosList, blockPlaceContext);
    }

    @Override
    protected void doPlacement(ArrayList<BlockPos> blockPosList, BlockPlaceContext blockPlaceContext){
        Level level = blockPlaceContext.getLevel();
        for(int i = 0; i < blockPosList.size(); i++){
            BlockPos pos = blockPosList.get(i);
            BlockState state = getPlacementState(blockPlaceContext);
            if(i == (blockPosList.size() - 1)/2){
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
                .setValue(BlockStateProperties.FACING, blockPlaceContext.getClickedFace())
                .setValue(HEIGHT, height)
                .setValue(MASTER, false);
    }
}
