package com.starspath.justwalls.init;

import com.starspath.justwalls.Config;
import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.blocks.*;
import com.starspath.justwalls.utils.Tiers;
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
//    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));

    public static final RegistryObject<Block> THATCH_WALL = BLOCKS.register("thatch_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.thatchResistance), Tiers.TIER.THATCH));
    public static final RegistryObject<Block> WOODEN_WALL = BLOCKS.register("wooden_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.woodenResistance), Tiers.TIER.WOOD));
    public static final RegistryObject<Block> STONE_WALL = BLOCKS.register("stone_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(Config.stoneResistance), Tiers.TIER.STONE));
    public static final RegistryObject<Block> METAL_WALL = BLOCKS.register("metal_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.metalResistance), Tiers.TIER.METAL));
    public static final RegistryObject<Block> ARMORED_WALL = BLOCKS.register("armored_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.armoredResistance), Tiers.TIER.ARMOR));

    public static final RegistryObject<Block> THATCH_WALL_FLOOR = BLOCKS.register("thatch_wall_floor", () -> new WallFloor(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.thatchResistance), Tiers.TIER.THATCH));
    public static final RegistryObject<Block> WOODEN_WALL_FLOOR = BLOCKS.register("wooden_wall_floor", () -> new WallFloor(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.woodenResistance), Tiers.TIER.WOOD));
    public static final RegistryObject<Block> STONE_WALL_FLOOR = BLOCKS.register("stone_wall_floor", () -> new WallFloor(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(Config.stoneResistance), Tiers.TIER.STONE));
    public static final RegistryObject<Block> METAL_WALL_FLOOR = BLOCKS.register("metal_wall_floor", () -> new WallFloor(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.metalResistance), Tiers.TIER.METAL));
    public static final RegistryObject<Block> ARMORED_WALL_FLOOR = BLOCKS.register("armored_wall_floor", () -> new WallFloor(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.armoredResistance), Tiers.TIER.ARMOR));

    public static final RegistryObject<Block> METAL_WALL_WINDOW = BLOCKS.register("metal_wall_window", () -> new WallWindow(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion(), Tiers.TIER.METAL));

    public static final RegistryObject<Block> THATCH_WALL_DOOR = BLOCKS.register("thatch_wall_door", () -> new WallDoor(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.thatchResistance), Tiers.TIER.THATCH));
    public static final RegistryObject<Block> WOODEN_WALL_DOOR = BLOCKS.register("wooden_wall_door", () -> new WallDoor(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.woodenResistance), Tiers.TIER.WOOD));
    public static final RegistryObject<Block> STONE_WALL_DOOR = BLOCKS.register("stone_wall_door", () -> new WallDoor(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(Config.stoneResistance), Tiers.TIER.STONE));
    public static final RegistryObject<Block> METAL_WALL_DOOR = BLOCKS.register("metal_wall_door", () -> new WallDoor(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.metalResistance), Tiers.TIER.METAL));
    public static final RegistryObject<Block> ARMORED_WALL_DOOR = BLOCKS.register("armored_wall_door", () -> new WallDoor(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.armoredResistance), Tiers.TIER.ARMOR));

    public static final RegistryObject<Block> THATCH_WALL_WINDOW_FRAME = BLOCKS.register("thatch_wall_window_frame", () -> new WallWindowFrame(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.thatchResistance), Tiers.TIER.THATCH));
    public static final RegistryObject<Block> WOODEN_WALL_WINDOW_FRAME = BLOCKS.register("wooden_wall_window_frame", () -> new WallWindowFrame(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.woodenResistance), Tiers.TIER.WOOD));
    public static final RegistryObject<Block> STONE_WALL_WINDOW_FRAME = BLOCKS.register("stone_wall_window_frame", () -> new WallWindowFrame(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(Config.stoneResistance), Tiers.TIER.STONE));
    public static final RegistryObject<Block> METAL_WALL_WINDOW_FRAME = BLOCKS.register("metal_wall_window_frame", () -> new WallWindowFrame(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.metalResistance), Tiers.TIER.METAL));
    public static final RegistryObject<Block> ARMORED_WALL_WINDOW_FRAME = BLOCKS.register("armored_wall_window_frame", () -> new WallWindowFrame(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.armoredResistance), Tiers.TIER.ARMOR));

    public static final RegistryObject<Block> THATCH_WALL_DOOR_FRAME = BLOCKS.register("thatch_wall_door_frame", () -> new WallDoorFrame(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.thatchResistance), Tiers.TIER.THATCH));
    public static final RegistryObject<Block> WOODEN_WALL_DOOR_FRAME = BLOCKS.register("wooden_wall_door_frame", () -> new WallDoorFrame(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.woodenResistance), Tiers.TIER.WOOD));
    public static final RegistryObject<Block> STONE_WALL_DOOR_FRAME = BLOCKS.register("stone_wall_door_frame", () -> new WallDoorFrame(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(Config.stoneResistance), Tiers.TIER.STONE));
    public static final RegistryObject<Block> METAL_WALL_DOOR_FRAME = BLOCKS.register("metal_wall_door_frame", () -> new WallDoorFrame(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.metalResistance), Tiers.TIER.METAL));
    public static final RegistryObject<Block> ARMORED_WALL_DOOR_FRAME = BLOCKS.register("armored_wall_door_frame", () -> new WallDoorFrame(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.armoredResistance), Tiers.TIER.ARMOR));

    public static final RegistryObject<Block> THATCH_WALL_PILLAR = BLOCKS.register("thatch_wall_pillar", () -> new WallPillar(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.thatchResistance), Tiers.TIER.THATCH));
    public static final RegistryObject<Block> WOODEN_WALL_PILLAR = BLOCKS.register("wooden_wall_pillar", () -> new WallPillar(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.woodenResistance), Tiers.TIER.WOOD));
    public static final RegistryObject<Block> STONE_WALL_PILLAR = BLOCKS.register("stone_wall_pillar", () -> new WallPillar(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(Config.stoneResistance), Tiers.TIER.STONE));
    public static final RegistryObject<Block> METAL_WALL_PILLAR = BLOCKS.register("metal_wall_pillar", () -> new WallPillar(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.metalResistance), Tiers.TIER.METAL));
    public static final RegistryObject<Block> ARMORED_WALL_PILLAR = BLOCKS.register("armored_wall_pillar", () -> new WallPillar(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.armoredResistance), Tiers.TIER.ARMOR));

    public static final RegistryObject<Block> THATCH_WALL_HATCH = BLOCKS.register("thatch_wall_hatch", () -> new WallHatch(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.thatchResistance), Tiers.TIER.THATCH));
    public static final RegistryObject<Block> WOODEN_WALL_HATCH = BLOCKS.register("wooden_wall_hatch", () -> new WallHatch(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(Config.woodenResistance), Tiers.TIER.WOOD));
    public static final RegistryObject<Block> STONE_WALL_HATCH = BLOCKS.register("stone_wall_hatch", () -> new WallHatch(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(Config.stoneResistance), Tiers.TIER.STONE));
    public static final RegistryObject<Block> METAL_WALL_HATCH = BLOCKS.register("metal_wall_hatch", () -> new WallHatch(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.metalResistance), Tiers.TIER.METAL));
    public static final RegistryObject<Block> ARMORED_WALL_HATCH = BLOCKS.register("armored_wall_hatch", () -> new WallHatch(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(Config.armoredResistance), Tiers.TIER.ARMOR));

    public static final RegistryObject<Block> LOOT_CRATE = BLOCKS.register("loot_crate", () -> new LootCrate(
            BlockBehaviour.Properties.copy(Blocks.CHEST).noOcclusion()));
}
