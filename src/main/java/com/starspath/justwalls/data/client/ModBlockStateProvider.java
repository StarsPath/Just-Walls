package com.starspath.justwalls.data.client;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, JustWalls.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

//        blockWithItem(ModBlocks.WOODEN_WALL);
//        blockWithItem(ModBlocks.METAL_WALL);
//        blockWithItem(ModBlocks.ARMORED_WALL);
    }

    private void blockWithItem(RegistryObject<Block> registryObject){
        simpleBlockWithItem(registryObject.get(), cubeAll(registryObject.get()));
    }
}
