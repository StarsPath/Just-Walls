package com.starspath.justwalls.init;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.blocks.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, JustWalls.MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));

    public static final RegistryObject<Block> THATCH_WALL = BLOCKS.register("thatch_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2)));
    public static final RegistryObject<Block> WOODEN_WALL = BLOCKS.register("wooden_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(5)));
    public static final RegistryObject<Block> STONE_WALL = BLOCKS.register("stone_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(6)));
    public static final RegistryObject<Block> METAL_WALL = BLOCKS.register("metal_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(9)));
    public static final RegistryObject<Block> ARMORED_WALL = BLOCKS.register("armored_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(12)));

    public static final RegistryObject<Block> METAL_WALL_WINDOW = BLOCKS.register("metal_wall_window", () -> new WallWindow(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> METAL_WALL_DOOR = BLOCKS.register("metal_wall_door", () -> new WallDoor(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> THATCH_WALL_WINDOW_FRAME = BLOCKS.register("thatch_wall_window_frame", () -> new WallWindowFrame(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2)));
    public static final RegistryObject<Block> METAL_WALL_WINDOW_FRAME = BLOCKS.register("metal_wall_window_frame", () -> new WallWindowFrame(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ARMORED_WALL_WINDOW_FRAME = BLOCKS.register("armored_wall_window_frame", () -> new WallWindowFrame(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> THATCH_WALL_DOOR_FRAME = BLOCKS.register("thatch_wall_door_frame", () -> new WallDoorFrame(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2)));
    public static final RegistryObject<Block> METAL_WALL_DOOR_FRAME = BLOCKS.register("metal_wall_door_frame", () -> new WallDoorFrame(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> ARMORED_WALL_DOOR_FRAME = BLOCKS.register("armored_wall_door_frame", () -> new WallDoorFrame(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> LOOT_CRATE = BLOCKS.register("loot_crate", () -> new LootCrate(
            BlockBehaviour.Properties.copy(Blocks.CHEST).noOcclusion()));
}
