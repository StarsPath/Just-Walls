package com.starspath.justwalls.events;

import com.starspath.justwalls.blocks.abstracts.StructureBlock;
import com.starspath.justwalls.utils.Utils;
import com.starspath.justwalls.world.DamageBlockSaveData;
import com.tacz.guns.api.event.server.AmmoHitBlockEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TACZEventHandler {

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event){
        if(!(event.getLevel() instanceof ServerLevel)) return;
        ServerLevel world = (ServerLevel) event.getLevel();
        DamageBlockSaveData damageBlockSaveData = DamageBlockSaveData.get(world);

        damageBlockSaveData.removeBlock(event.getPos());
    }

    @SubscribeEvent
    public void TACZHitBlockEventHandler(AmmoHitBlockEvent event) {
//        System.out.println("Caught OtherModEvent: " + event);
//        Utils.debug(event.getState(), event.getAmmo(), event.getHitResult());
        Level level = event.getLevel();
        BlockPos pos = event.getHitResult().getBlockPos();
        BlockState blockState = level.getBlockState(pos);
        float damage = event.getAmmo().getDamage(event.getHitResult().getLocation());

        DamageBlockSaveData damageBlockSaveData = DamageBlockSaveData.get(level);

        if(blockState.getBlock() instanceof StructureBlock structureBlock){
            BlockPos masterPos = structureBlock.getMasterPos(level, pos, blockState);
            if (damageBlockSaveData.damageBlock(level, masterPos, (int)damage)<=0){
                structureBlock.playerWillDestroy(level, masterPos, blockState, null);
            }
        }
    }
}
