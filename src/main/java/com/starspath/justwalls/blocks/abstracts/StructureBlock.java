package com.starspath.justwalls.blocks.abstracts;

import com.starspath.justwalls.Config;
import com.starspath.justwalls.init.ModItems;
import com.starspath.justwalls.utils.Tiers;
import com.starspath.justwalls.utils.Utils;
import com.starspath.justwalls.world.DamageBlockSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class StructureBlock extends MultiBlock {
    public StructureBlock(Properties properties) {
        super(properties);
    }

    public StructureBlock(Tiers.TIER tier) {
        super(Utils.getBaseProperty(tier).strength(Utils.getStrengthNoConfig(tier)), tier);
    }

    public StructureBlock(Properties properties, Tiers.TIER tier) {
        super(properties.strength(Utils.getStrengthNoConfig(tier)), tier);
    }

    protected abstract Boolean isMaster(BlockState blockState, BlockState self);

    public abstract BlockPos getMasterPos(LevelAccessor level, BlockPos blockPos, BlockState blockState);

    protected void masterBreak(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState){
        ArrayList<BlockPos> childPos = getChildPos(levelAccessor, blockPos, blockState);
        DamageBlockSaveData damageBlockSaveData = DamageBlockSaveData.get(levelAccessor);
        childPos.stream().forEach(blockPos1 -> {
            levelAccessor.destroyBlock(blockPos1, true);
            if(damageBlockSaveData.hasBlock(blockPos1)) {
                damageBlockSaveData.removeBlock(blockPos1);
            }
        });
    }

    protected void masterBreakNoDrop(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState){
        ArrayList<BlockPos> childPos = getChildPos(levelAccessor, blockPos, blockState);
        childPos.stream().forEach(blockPos1 -> levelAccessor.setBlock(blockPos1, Blocks.AIR.defaultBlockState(), UPDATE_SUPPRESS_DROPS));
    }


    protected ArrayList<BlockPos> getChildPos(LevelAccessor level, BlockPos blockPos, BlockState blockState){
        return new ArrayList<>();
    }

    protected Block getNextTierBlock(Tiers.TIER tier){
        return Blocks.AIR;
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        if(level.isClientSide()){
            return;
        }

        Boolean isMaster = state.getValue(MASTER);

        if(isMaster){
            masterBreak(level, pos, state);
            return;
        }

        BlockPos masterPos = getMasterPos(level, pos, state);
        if(masterPos != null){
            StructureBlock masterWall = (StructureBlock)level.getBlockState(masterPos).getBlock();
            masterWall.masterBreak(level, masterPos, level.getBlockState(masterPos));
        }
        super.onBlockExploded(state, level, pos, explosion);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {

        if(level.isClientSide()){
            return;
        }

        Boolean isMaster = blockState.getValue(MASTER);

        if(isMaster){
            masterBreak(level, blockPos, blockState);
            return;
        }

        BlockPos masterPos = getMasterPos(level, blockPos, blockState);
        if(masterPos != null){
            StructureBlock masterWall = (StructureBlock)level.getBlockState(masterPos).getBlock();
            masterWall.masterBreak(level, masterPos, level.getBlockState(masterPos));
        }

        super.playerWillDestroy(level, blockPos, blockState, player);
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        super.destroy(levelAccessor, blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState prevBlockState, Level level, BlockPos blockPos, BlockState newBlockState, boolean flag) {
        super.onRemove(prevBlockState, level, blockPos, newBlockState, flag);
    }

    public InteractionResult upgrade(LevelAccessor level, BlockPos blockPos, BlockState blockState) {
        if(level.isClientSide()){
            return InteractionResult.PASS;
        }

        Boolean isMaster = blockState.getValue(MASTER);
        if(blockState.getValue(TIER) == Tiers.TIER.values()[Tiers.TIER.values().length - 1]){
            return InteractionResult.FAIL;
        }

        if(isMaster){
            ArrayList<BlockPos> childPosList = getChildPos(level, blockPos, blockState);
            ArrayList<BlockState> childBlockStateList = childPosList.stream().map(blockPos1 -> level.getBlockState(blockPos1)).collect(Collectors.toCollection(ArrayList::new));
            DamageBlockSaveData damageBlockSaveData = DamageBlockSaveData.get(level);

            masterBreakNoDrop(level, blockPos, blockState);

            for(int i = 0; i < childPosList.size(); i++){
                BlockPos childPos = childPosList.get(i);
                BlockState childState = childBlockStateList.get(i);

                Tiers.TIER nextTier =  blockState.getValue(TIER).getNext();
                Block nexTierBlock = getNextTierBlock(nextTier);
                BlockState newState = nexTierBlock.withPropertiesOf(childState).setValue(TIER, nextTier);
                level.setBlock(childPos, newState, UPDATE_ALL);

                damageBlockSaveData.setBlockHP(childPos, damageBlockSaveData.getDefaultResistance(level, childPos));
            }
            return InteractionResult.SUCCESS;
        }

        BlockPos masterPos = getMasterPos(level, blockPos, blockState);
        if(masterPos != null){
            StructureBlock masterWall = (StructureBlock)level.getBlockState(masterPos).getBlock();
            return masterWall.upgrade(level, masterPos, level.getBlockState(masterPos));
        }
        return InteractionResult.FAIL;
    }

    public InteractionResult repair(LevelAccessor level, BlockPos blockPos, BlockState blockState){
        if(level.isClientSide()){
            return InteractionResult.PASS;
        }

        Boolean isMaster = blockState.getValue(MASTER);
        if(isMaster){
            DamageBlockSaveData damageBlockSaveData = DamageBlockSaveData.get(level);

            int currentHP = damageBlockSaveData.getBlockHP(blockPos);
            int maxHP = damageBlockSaveData.getDefaultResistance(level, blockPos);
            int newHP = Math.min(maxHP, currentHP + (int)(maxHP * Config.REPAIR_PERCENTAGE.get()) + Config.REPAIR_AMOUNT.get());
            damageBlockSaveData.setBlockHP(blockPos, newHP);
            return InteractionResult.SUCCESS;
        }
        BlockPos masterPos = getMasterPos(level, blockPos, blockState);
        if(masterPos != null){
            StructureBlock masterWall = (StructureBlock)level.getBlockState(masterPos).getBlock();
            return masterWall.repair(level, masterPos, level.getBlockState(masterPos));
        }
        return InteractionResult.FAIL;
    }

    public ItemStack getRequiredItemForUpgrade(BlockState blockState){
        return getRequiredItemForUpgrade(blockState, 9);
    }

    public ItemStack getRequiredItemForUpgrade(BlockState blockState, int numberBlocks){
        int materialPerBlock = Config.MATERIAL_PER_BLOCK.get();
        return switch (blockState.getValue(TIER)){
            case THATCH -> new ItemStack(ModItems.WOOD_SCRAP.get(), numberBlocks * materialPerBlock);
            case WOOD -> new ItemStack(ModItems.STONE_SCRAP.get(), numberBlocks * materialPerBlock);
            case STONE -> new ItemStack(ModItems.METAL_SCRAP.get(), numberBlocks * materialPerBlock);
            case METAL -> new ItemStack(ModItems.ARMORED_SCRAP.get(), numberBlocks * materialPerBlock);
            default -> new ItemStack(ModItems.STRAW_SCRAP.get(), numberBlocks * materialPerBlock);
        };
    }

    public ItemStack getRequiredItemForRepair(BlockState blockState){
        int materialRepair = Config.MATERIAL_REPAIR.get();
        return switch (blockState.getValue(TIER)){
            case THATCH -> new ItemStack(ModItems.STRAW_SCRAP.get(), materialRepair);
            case WOOD -> new ItemStack(ModItems.WOOD_SCRAP.get(), materialRepair);
            case STONE -> new ItemStack(ModItems.STONE_SCRAP.get(), materialRepair);
            case METAL -> new ItemStack(ModItems.METAL_SCRAP.get(), materialRepair);
            case ARMOR -> new ItemStack(ModItems.ARMORED_SCRAP.get(), materialRepair);
        };
    }
}
