package com.starspath.justwalls.data.client;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.init.ModBlocks;
import com.starspath.justwalls.utils.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider extends BlockTagsProvider {
    public ModTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, JustWalls.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.THATCH_WALL.get())
                .add(ModBlocks.THATCH_WALL_FLOOR.get())
                .add(ModBlocks.THATCH_WALL_PILLAR.get())
                .add(ModBlocks.THATCH_WALL_WINDOW_FRAME.get())
                .add(ModBlocks.THATCH_WALL_DOOR_FRAME.get())
                .add(ModBlocks.THATCH_WALL_HATCH.get())
                .add(ModBlocks.WOODEN_WALL.get())
                .add(ModBlocks.WOODEN_WALL_FLOOR.get())
                .add(ModBlocks.WOODEN_WALL_PILLAR.get())
                .add(ModBlocks.WOODEN_WALL_WINDOW_FRAME.get())
                .add(ModBlocks.WOODEN_WALL_DOOR_FRAME.get())
                .add(ModBlocks.WOODEN_WALL_HATCH.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.STONE_WALL.get())
                .add(ModBlocks.STONE_WALL_FLOOR.get())
                .add(ModBlocks.STONE_WALL_PILLAR.get())
                .add(ModBlocks.STONE_WALL_WINDOW_FRAME.get())
                .add(ModBlocks.STONE_WALL_DOOR_FRAME.get())
                .add(ModBlocks.STONE_WALL_HATCH.get())
                .add(ModBlocks.METAL_WALL.get())
                .add(ModBlocks.METAL_WALL_FLOOR.get())
                .add(ModBlocks.METAL_WALL_PILLAR.get())
                .add(ModBlocks.METAL_WALL_WINDOW_FRAME.get())
                .add(ModBlocks.METAL_WALL_DOOR_FRAME.get())
                .add(ModBlocks.METAL_WALL_HATCH.get())
                .add(ModBlocks.ARMORED_WALL.get())
                .add(ModBlocks.ARMORED_WALL_FLOOR.get())
                .add(ModBlocks.ARMORED_WALL_PILLAR.get())
                .add(ModBlocks.ARMORED_WALL_WINDOW_FRAME.get())
                .add(ModBlocks.ARMORED_WALL_DOOR_FRAME.get())
                .add(ModBlocks.ARMORED_WALL_HATCH.get());
    }
}
