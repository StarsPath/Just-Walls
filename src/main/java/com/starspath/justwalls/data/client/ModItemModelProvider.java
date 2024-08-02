package com.starspath.justwalls.data.client;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, JustWalls.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("thatch_wall", modLoc("block/thatch_wall_inventory"));
        withExistingParent("wooden_wall", modLoc("block/wooden_wall_inventory"));
        withExistingParent("stone_wall", modLoc("block/stone_wall_inventory"));
        withExistingParent("metal_wall", modLoc("block/metal_wall"));
        withExistingParent("armored_wall", modLoc("block/armored_wall"));

        withExistingParent("thatch_wall_floor", modLoc("block/thatch_wall_floor_inventory"));
        withExistingParent("wooden_wall_floor", modLoc("block/wooden_wall_floor_inventory"));
        withExistingParent("stone_wall_floor", modLoc("block/stone_wall_floor_inventory"));
        withExistingParent("metal_wall_floor", modLoc("block/metal_wall_floor_inventory"));
        withExistingParent("armored_wall_floor", modLoc("block/armored_wall_floor_inventory"));

        withExistingParent("loot_crate", modLoc("block/loot_crate"));
        withExistingParent("metal_wall_window", modLoc("block/metal_wall_window"));

        withExistingParent("thatch_wall_window_frame", modLoc("block/thatch_wall_window_frame_inventory"));
        withExistingParent("wooden_wall_window_frame", modLoc("block/wooden_wall_window_frame_inventory"));
        withExistingParent("stone_wall_window_frame", modLoc("block/stone_wall_window_frame_inventory"));
        withExistingParent("metal_wall_window_frame", modLoc("block/metal_wall_window_frame_inventory"));
        withExistingParent("armored_wall_window_frame", modLoc("block/armored_wall_window_frame_inventory"));

        withExistingParent("thatch_wall_door_frame", modLoc("block/thatch_wall_door_frame_inventory"));
        withExistingParent("wooden_wall_door_frame", modLoc("block/wooden_wall_door_frame_inventory"));
        withExistingParent("stone_wall_door_frame", modLoc("block/stone_wall_door_frame_inventory"));
        withExistingParent("metal_wall_door_frame", modLoc("block/metal_wall_door_frame_inventory"));
        withExistingParent("armored_wall_door_frame", modLoc("block/armored_wall_door_frame_inventory"));


        simpleItem(ModItems.STRAW_SCRAP);
        simpleItem(ModItems.WOOD_SCRAP);
        simpleItem(ModItems.STONE_SCRAP);
        simpleItem(ModItems.METAL_SCRAP);
        simpleItem(ModItems.ARMORED_SCRAP);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(JustWalls.MODID, "item/" + item.getId().getPath()));
    }
}