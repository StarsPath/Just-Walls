package com.starspath.justwalls.init;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.item.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, JustWalls.MODID);

    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(ModBlocks.EXAMPLE_BLOCK.get(), new Item.Properties()));

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(2f).build())));

    public static final RegistryObject<Item> THATCH_WALL_ITEM = ITEMS.register("thatch_wall", () -> new WallItem(ModBlocks.THATCH_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_WALL_ITEM = ITEMS.register("wooden_wall", () -> new WallItem(ModBlocks.WOODEN_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> STONE_WALL_ITEM = ITEMS.register("stone_wall", () -> new WallItem(ModBlocks.STONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> METAL_WALL_ITEM = ITEMS.register("metal_wall", () -> new WallItem(ModBlocks.METAL_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARMORED_WALL_ITEM = ITEMS.register("armored_wall", () -> new WallItem(ModBlocks.ARMORED_WALL.get(), new Item.Properties()));

    public static final RegistryObject<Item> THATCH_WALL_FLOOR_ITEM = ITEMS.register("thatch_wall_floor", () -> new WallFloorItem(ModBlocks.THATCH_WALL_FLOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_WALL_FLOOR_ITEM = ITEMS.register("wooden_wall_floor", () -> new WallFloorItem(ModBlocks.WOODEN_WALL_FLOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> STONE_WALL_FLOOR_ITEM = ITEMS.register("stone_wall_floor", () -> new WallFloorItem(ModBlocks.STONE_WALL_FLOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> METAL_WALL_FLOOR_ITEM = ITEMS.register("metal_wall_floor", () -> new WallFloorItem(ModBlocks.METAL_WALL_FLOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARMORED_WALL_FLOOR_ITEM = ITEMS.register("armored_wall_floor", () -> new WallFloorItem(ModBlocks.ARMORED_WALL_FLOOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> METAL_WALL_WINDOW_ITEM = ITEMS.register("metal_wall_window", () -> new WallWindowItem(ModBlocks.METAL_WALL_WINDOW.get(), new Item.Properties()));
    public static final RegistryObject<Item> METAL_WALL_DOOR_ITEM = ITEMS.register("metal_wall_door", () -> new WallDoorItem(ModBlocks.METAL_WALL_DOOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> THATCH_WALL_WINDOW_FRAME_ITEM = ITEMS.register("thatch_wall_window_frame", () -> new WallWindowFrameItem(ModBlocks.THATCH_WALL_WINDOW_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_WALL_WINDOW_FRAME_ITEM = ITEMS.register("wooden_wall_window_frame", () -> new WallWindowFrameItem(ModBlocks.WOODEN_WALL_WINDOW_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> STONE_WALL_WINDOW_FRAME_ITEM = ITEMS.register("stone_wall_window_frame", () -> new WallWindowFrameItem(ModBlocks.STONE_WALL_WINDOW_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> METAL_WALL_WINDOW_FRAME_ITEM = ITEMS.register("metal_wall_window_frame", () -> new WallWindowFrameItem(ModBlocks.METAL_WALL_WINDOW_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARMORED_WALL_WINDOW_FRAME_ITEM = ITEMS.register("armored_wall_window_frame", () -> new WallWindowFrameItem(ModBlocks.ARMORED_WALL_WINDOW_FRAME.get(), new Item.Properties()));

    public static final RegistryObject<Item> THATCH_WALL_DOOR_FRAME_ITEM = ITEMS.register("thatch_wall_door_frame", () -> new WallDoorFrameItem(ModBlocks.THATCH_WALL_DOOR_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_WALL_DOOR_FRAME_ITEM = ITEMS.register("wooden_wall_door_frame", () -> new WallDoorFrameItem(ModBlocks.WOODEN_WALL_DOOR_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> STONE_WALL_DOOR_FRAME_ITEM = ITEMS.register("stone_wall_door_frame", () -> new WallDoorFrameItem(ModBlocks.STONE_WALL_DOOR_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> METAL_WALL_DOOR_FRAME_ITEM = ITEMS.register("metal_wall_door_frame", () -> new WallDoorFrameItem(ModBlocks.METAL_WALL_DOOR_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARMORED_WALL_DOOR_FRAME_ITEM = ITEMS.register("armored_wall_door_frame", () -> new WallDoorFrameItem(ModBlocks.ARMORED_WALL_DOOR_FRAME.get(), new Item.Properties()));

    public static final RegistryObject<Item> LOOT_CRATE_ITEM = ITEMS.register("loot_crate", () -> new LootCrateItem(ModBlocks.LOOT_CRATE.get(), new Item.Properties()));

    public static final RegistryObject<Item> SUPER_HAMMER = ITEMS.register("super_hammer", ()-> new SuperHammer(new Item.Properties()));

    public static final RegistryObject<Item> STRAW_SCRAP = ITEMS.register("straw_scrap", () -> new ConstructionMaterial(new Item.Properties()));
    public static final RegistryObject<Item> WOOD_SCRAP = ITEMS.register("wood_scrap", () -> new ConstructionMaterial(new Item.Properties()));
    public static final RegistryObject<Item> STONE_SCRAP = ITEMS.register("stone_scrap", () -> new ConstructionMaterial(new Item.Properties()));
    public static final RegistryObject<Item> METAL_SCRAP = ITEMS.register("metal_scrap", () -> new ConstructionMaterial(new Item.Properties()));
    public static final RegistryObject<Item> ARMORED_SCRAP = ITEMS.register("armored_scrap", () -> new ConstructionMaterial(new Item.Properties()));
}
