package com.starspath.justwalls.init;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.blocks.LootCrate;
import com.starspath.justwalls.blocks.Wall;
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

    public static final RegistryObject<Block> WOODEN_WALL = BLOCKS.register("wooden_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> METAL_WALL = BLOCKS.register("metal_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ARMORED_WALL = BLOCKS.register("armored_wall", () -> new Wall(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(9)));

    public static final RegistryObject<Block> LOOT_CRATE = BLOCKS.register("loot_crate", () -> new LootCrate(
            BlockBehaviour.Properties.copy(Blocks.CHEST).noOcclusion()));
}
