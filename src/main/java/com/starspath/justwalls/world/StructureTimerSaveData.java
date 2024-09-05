package com.starspath.justwalls.world;

import com.starspath.justwalls.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class StructureTimerSaveData extends SavedData {

    public HashMap<Long,Long> storage = new HashMap<>();

    public boolean hasBlock(BlockPos pos){
        return storage.containsKey(pos.asLong());
    }

    public void setTime(BlockPos pos, long time){
        storage.put(pos.asLong(), time);
        setDirty();
    }

    public long getTime(BlockPos pos){
        return storage.get(pos.asLong());
    }

    public void removeTime(BlockPos pos){
        storage.remove(pos.asLong());
        setDirty();
    }

    public Component getGraceTime(Level level, BlockPos pos){
        if(inGracePeriod(level, pos)){
            long ticks = level.getGameTime() - getTime(pos);
            return Component.literal(Utils.formatTicksToTime(ticks));
        }
        return Component.translatable("message.justwalls.not_in_grace");
    }

    public boolean inGracePeriod(Level level, BlockPos pos){
        if(hasBlock(pos)){
            boolean result = level.getGameTime() - getTime(pos) < 12000;
            if(!result){
                removeTime(pos);
            }
            return result;
        }
        return false;
    }

    @Nonnull
    public static StructureTimerSaveData get(LevelAccessor level) {
        if (level.isClientSide()) {
            throw new RuntimeException("Don't access this client-side!");
        }
        DimensionDataStorage storage = ((ServerLevel)level).getDataStorage();
        return storage.computeIfAbsent(StructureTimerSaveData::new, StructureTimerSaveData::new, "timemanager");
    }

    public StructureTimerSaveData(){

    }

    public StructureTimerSaveData(CompoundTag tag){
        ListTag list = tag.getList("timeTag", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag timeTag = (CompoundTag) t;
            Long timePlaced = timeTag.getLong("timePlaced");
            Long pos = timeTag.getLong("pos");
            storage.put(pos, timePlaced);
        }
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        storage.forEach((longPos, timePlaced) -> {
            CompoundTag timeTag = new CompoundTag();
            timeTag.putLong("pos", longPos);
            timeTag.putLong("timePlaced", timePlaced);
            list.add(timeTag);
        });
        tag.put("timeTag", list);
        return tag;
    }
}
