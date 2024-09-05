package com.starspath.justwalls.data.loot;

import com.starspath.justwalls.init.ModBlocks;
import com.starspath.justwalls.init.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Set;

import static com.starspath.justwalls.init.ModBlocks.*;

public class ModBlockLootProvider extends BlockLootSubProvider {
    public ModBlockLootProvider(){
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.add(ModBlocks.THATCH_WALL.get(), block -> createStructureDrops(block, ModItems.STRAW_SCRAP.get()));
        this.add(ModBlocks.THATCH_WALL_FLOOR.get(), block -> createStructureDrops(block, ModItems.STRAW_SCRAP.get()));
        this.add(ModBlocks.THATCH_WALL_DOOR_FRAME.get(), block -> createStructureDrops(block, ModItems.STRAW_SCRAP.get()));
        this.add(ModBlocks.THATCH_WALL_WINDOW_FRAME.get(), block -> createStructureDrops(block, ModItems.STRAW_SCRAP.get()));
        this.add(ModBlocks.THATCH_WALL_WINDOW.get(), block -> createStructureDrops(block, ModItems.STRAW_SCRAP.get()));
        this.add(ModBlocks.THATCH_WALL_PILLAR.get(), block -> createStructureDrops(block, ModItems.STRAW_SCRAP.get()));
        this.add(ModBlocks.THATCH_WALL_HATCH.get(), block -> createStructureDrops(block, ModItems.STRAW_SCRAP.get()));
        this.add(ModBlocks.THATCH_WALL_DOOR.get(), block -> createStructureDrops(block, ModItems.STRAW_SCRAP.get()));

        this.add(ModBlocks.WOODEN_WALL.get(), block -> createStructureDrops(block, ModItems.WOOD_SCRAP.get()));
        this.add(ModBlocks.WOODEN_WALL_FLOOR.get(), block -> createStructureDrops(block, ModItems.WOOD_SCRAP.get()));
        this.add(ModBlocks.WOODEN_WALL_DOOR_FRAME.get(), block -> createStructureDrops(block, ModItems.WOOD_SCRAP.get()));
        this.add(ModBlocks.WOODEN_WALL_WINDOW_FRAME.get(), block -> createStructureDrops(block, ModItems.WOOD_SCRAP.get()));
        this.add(ModBlocks.WOODEN_WALL_WINDOW.get(), block -> createStructureDrops(block, ModItems.WOOD_SCRAP.get()));
        this.add(ModBlocks.WOODEN_WALL_PILLAR.get(), block -> createStructureDrops(block, ModItems.WOOD_SCRAP.get()));
        this.add(ModBlocks.WOODEN_WALL_HATCH.get(), block -> createStructureDrops(block, ModItems.WOOD_SCRAP.get()));
        this.add(ModBlocks.WOODEN_WALL_DOOR.get(), block -> createStructureDrops(block, ModItems.WOOD_SCRAP.get()));

        this.add(ModBlocks.STONE_WALL.get(), block -> createStructureDrops(block, ModItems.STONE_SCRAP.get()));
        this.add(ModBlocks.STONE_WALL_FLOOR.get(), block -> createStructureDrops(block, ModItems.STONE_SCRAP.get()));
        this.add(ModBlocks.STONE_WALL_DOOR_FRAME.get(), block -> createStructureDrops(block, ModItems.STONE_SCRAP.get()));
        this.add(ModBlocks.STONE_WALL_WINDOW_FRAME.get(), block -> createStructureDrops(block, ModItems.STONE_SCRAP.get()));
        this.add(ModBlocks.STONE_WALL_WINDOW.get(), block -> createStructureDrops(block, ModItems.STONE_SCRAP.get()));
        this.add(ModBlocks.STONE_WALL_PILLAR.get(), block -> createStructureDrops(block, ModItems.STONE_SCRAP.get()));
        this.add(ModBlocks.STONE_WALL_HATCH.get(), block -> createStructureDrops(block, ModItems.STONE_SCRAP.get()));
        this.add(ModBlocks.STONE_WALL_DOOR.get(), block -> createStructureDrops(block, ModItems.STONE_SCRAP.get()));

        this.add(ModBlocks.METAL_WALL.get(), block -> createStructureDrops(block, ModItems.METAL_SCRAP.get()));
        this.add(ModBlocks.METAL_WALL_FLOOR.get(), block -> createStructureDrops(block, ModItems.METAL_SCRAP.get()));
        this.add(ModBlocks.METAL_WALL_DOOR_FRAME.get(), block -> createStructureDrops(block, ModItems.METAL_SCRAP.get()));
        this.add(ModBlocks.METAL_WALL_WINDOW_FRAME.get(), block -> createStructureDrops(block, ModItems.METAL_SCRAP.get()));
        this.add(ModBlocks.METAL_WALL_WINDOW.get(), block -> createStructureDrops(block, ModItems.METAL_SCRAP.get()));
        this.add(ModBlocks.METAL_WALL_PILLAR.get(), block -> createStructureDrops(block, ModItems.METAL_SCRAP.get()));
        this.add(ModBlocks.METAL_WALL_HATCH.get(), block -> createStructureDrops(block, ModItems.METAL_SCRAP.get()));
        this.add(ModBlocks.METAL_WALL_DOOR.get(), block -> createStructureDrops(block, ModItems.METAL_SCRAP.get()));

        this.add(ModBlocks.ARMORED_WALL.get(), block -> createStructureDrops(block, ModItems.ARMORED_SCRAP.get()));
        this.add(ModBlocks.ARMORED_WALL_FLOOR.get(), block -> createStructureDrops(block, ModItems.ARMORED_SCRAP.get()));
        this.add(ModBlocks.ARMORED_WALL_DOOR_FRAME.get(), block -> createStructureDrops(block, ModItems.ARMORED_SCRAP.get()));
        this.add(ModBlocks.ARMORED_WALL_WINDOW_FRAME.get(), block -> createStructureDrops(block, ModItems.ARMORED_SCRAP.get()));
        this.add(ModBlocks.ARMORED_WALL_WINDOW.get(), block -> createStructureDrops(block, ModItems.ARMORED_SCRAP.get()));
        this.add(ModBlocks.ARMORED_WALL_PILLAR.get(), block -> createStructureDrops(block, ModItems.ARMORED_SCRAP.get()));
        this.add(ModBlocks.ARMORED_WALL_HATCH.get(), block -> createStructureDrops(block, ModItems.ARMORED_SCRAP.get()));
        this.add(ModBlocks.ARMORED_WALL_DOOR.get(), block -> createStructureDrops(block, ModItems.ARMORED_SCRAP.get()));
    }

    protected LootTable.Builder createStructureDrops(Block block, Item item) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(this.applyExplosionDecay(block, LootItem.lootTableItem(item)))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {

        ArrayList<RegistryObject<Block>> excludeList = new ArrayList<RegistryObject<Block>>(){
            {
                add(LOOT_CRATE);
            }
        };
        return ModBlocks.BLOCKS.getEntries().stream()
                .filter(registryObject -> !excludeList.contains(registryObject))
                .map(RegistryObject::get)::iterator;
    }
}
