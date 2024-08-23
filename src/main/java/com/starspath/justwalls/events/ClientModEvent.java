package com.starspath.justwalls.events;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.blocks.abstracts.StructureBlock;
import com.starspath.justwalls.client.Keybindings;
import com.starspath.justwalls.init.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = JustWalls.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvent {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.LOOT_CRATE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.METAL_WALL_WINDOW.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.METAL_WALL_DOOR.get(), RenderType.cutout());
        });
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event){
        event.register(Keybindings.INSTANCE.guiKey);
    }
}
