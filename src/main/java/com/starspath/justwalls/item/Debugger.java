package com.starspath.justwalls.item;

import com.starspath.justwalls.blocks.abstracts.StructureBlock;
import com.starspath.justwalls.world.DamageBlockSaveData;
import com.starspath.justwalls.world.StructureTimerSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class Debugger extends Item {
    public Debugger(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        if(level.isClientSide){
            return InteractionResult.PASS;
        }

        DamageBlockSaveData damageBlockSaveData = DamageBlockSaveData.get(level);
        BlockPos pos = useOnContext.getClickedPos();
        Block block = level.getBlockState(pos).getBlock();
        Player player = useOnContext.getPlayer();

        if(block instanceof StructureBlock structureBlock){
            pos = structureBlock.getMasterPos(level, pos, level.getBlockState(pos));
        }

        int maxHp = damageBlockSaveData.getDefaultResistance(level, pos);
        int currentHp = damageBlockSaveData.hasBlock(pos)? damageBlockSaveData.getBlockHP(pos): maxHp;

        StructureTimerSaveData structureTimerSaveData = StructureTimerSaveData.get(level);

        player.displayClientMessage(Component.literal(block + " " + currentHp + "/" + maxHp), false);
        player.displayClientMessage(structureTimerSaveData.getGraceTime(level, pos), false);

        return super.useOn(useOnContext);
    }
}
