package com.starspath.justwalls.data.client;

import com.starspath.justwalls.JustWalls;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, JustWalls.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("metal_wall", modLoc("block/metal_wall"));
        withExistingParent("armored_wall", modLoc("block/armored_wall"));
        withExistingParent("loot_crate", modLoc("block/loot_crate"));
        withExistingParent("metal_wall_window", modLoc("block/metal_wall_window"));

        withExistingParent("thatch_wall", modLoc("block/thatch_wall_inventory"));
        withExistingParent("thatch_wall_window_frame", modLoc("block/thatch_wall_window_frame_inventory"));
        withExistingParent("thatch_wall_door_frame", modLoc("block/thatch_wall_door_frame_inventory"));
    }
}