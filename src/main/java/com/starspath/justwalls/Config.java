package com.starspath.justwalls;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = JustWalls.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue MATERIAL_PER_BLOCK = BUILDER
            .comment("Amount of material to build or upgrade a tile per block; total material = number of block x this value (default 2)")
            .defineInRange("materialPerBlock", 2, 1, 7);

    private static final ForgeConfigSpec.IntValue DESTRUCTION_MODE = BUILDER
            .comment("Structure Block destruction integration with TACZ mod. 0 - no destruction, 1 - all structure blocks from justwalls, 2 - whitelist, 3 - all blocks")
            .defineInRange("mode", 1, 0, 3);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> WHITELIST = BUILDER
            .comment("whitelist for blocks that can be destroyed by TACZ")
            .defineList("whitelist", Arrays.asList("minecraft:stone"), entry -> true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int materialPerBlock;

    public static int thatchResistance = 2;
    public static int woodenResistance = 5;
    public static int stoneResistance = 6;
    public static int metalResistance = 9;
    public static int armoredResistance = 12;

    public static int thatchFlammability;
    public static int thatchFireSpread;

    public static int woodenFlammability;
    public static int woodenFireSpread;

    public static int destructionMode = 1;
    public static List<? extends String> destructionWhiteList;


//    public static boolean logDirtBlock;
//    public static int magicNumber;
//    public static String magicNumberIntroduction;
//    public static Set<Item> items;
//
//    private static boolean validateItemName(final Object obj)
//    {
//        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
//    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        materialPerBlock = MATERIAL_PER_BLOCK.get();
        destructionMode = DESTRUCTION_MODE.get();
        destructionWhiteList = WHITELIST.get();
//        thatchResistance = THATCH_RESISTANCE.get();
//        woodenResistance = WOODEN_RESISTANCE.get();
//        stoneResistance = STONE_RESISTANCE.get();
//        metalResistance = METAL_RESISTANCE.get();
//        armoredResistance = ARMORED_RESISTANCE.get();
//        logDirtBlock = LOG_DIRT_BLOCK.get();
//        magicNumber = MAGIC_NUMBER.get();
//        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
//
//        // convert the list of strings into a set of items
//        items = ITEM_STRINGS.get().stream()
//                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
//                .collect(Collectors.toSet());
    }
}
