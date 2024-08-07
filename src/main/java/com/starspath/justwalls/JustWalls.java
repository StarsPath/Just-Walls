package com.starspath.justwalls;

import com.mojang.logging.LogUtils;
import com.starspath.justwalls.init.ModBlockEntity;
import com.starspath.justwalls.init.ModBlocks;
import com.starspath.justwalls.init.ModCreativeModeTab;
import com.starspath.justwalls.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JustWalls.MODID)
public class JustWalls
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "justwalls";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();



    public JustWalls()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        ModBlocks.BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ModItems.ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        ModCreativeModeTab.CREATIVE_MODE_TABS.register(modEventBus);

        ModBlockEntity.BLOCK_ENTITIES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

//        if (Config.logDirtBlock)
//            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
//
//        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
//
//        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == ModCreativeModeTab.JUSTWALLS_TAB.getKey()){
            event.accept(ModItems.THATCH_WALL_ITEM);
            event.accept(ModItems.WOODEN_WALL_ITEM);
            event.accept(ModItems.STONE_WALL_ITEM);
            event.accept(ModItems.METAL_WALL_ITEM);
            event.accept(ModItems.ARMORED_WALL_ITEM);

            event.accept(ModItems.THATCH_WALL_FLOOR_ITEM);
            event.accept(ModItems.WOODEN_WALL_FLOOR_ITEM);
            event.accept(ModItems.STONE_WALL_FLOOR_ITEM);
            event.accept(ModItems.METAL_WALL_FLOOR_ITEM);
            event.accept(ModItems.ARMORED_WALL_FLOOR_ITEM);

            event.accept(ModItems.METAL_WALL_WINDOW_ITEM);
            event.accept(ModItems.METAL_WALL_DOOR_ITEM);

            event.accept(ModItems.THATCH_WALL_WINDOW_FRAME_ITEM);
            event.accept(ModItems.WOODEN_WALL_WINDOW_FRAME_ITEM);
            event.accept(ModItems.STONE_WALL_WINDOW_FRAME_ITEM);
            event.accept(ModItems.METAL_WALL_WINDOW_FRAME_ITEM);
            event.accept(ModItems.ARMORED_WALL_WINDOW_FRAME_ITEM);

            event.accept(ModItems.THATCH_WALL_DOOR_FRAME_ITEM);
            event.accept(ModItems.WOODEN_WALL_DOOR_FRAME_ITEM);
            event.accept(ModItems.STONE_WALL_DOOR_FRAME_ITEM);
            event.accept(ModItems.METAL_WALL_DOOR_FRAME_ITEM);
            event.accept(ModItems.ARMORED_WALL_DOOR_FRAME_ITEM);

            event.accept(ModItems.THATCH_WALL_PILLAR_ITEM_3);
            event.accept(ModItems.THATCH_WALL_PILLAR_ITEM_4);
            event.accept(ModItems.THATCH_WALL_PILLAR_ITEM_5);

            event.accept(ModItems.STRAW_SCRAP);
            event.accept(ModItems.WOOD_SCRAP);
            event.accept(ModItems.STONE_SCRAP);
            event.accept(ModItems.METAL_SCRAP);
            event.accept(ModItems.ARMORED_SCRAP);

            event.accept(ModItems.LOOT_CRATE_ITEM);

            event.accept(ModItems.SUPER_HAMMER);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
