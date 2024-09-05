package com.starspath.justwalls.init;

import com.starspath.justwalls.JustWalls;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JustWalls.MODID);

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> JUSTWALLS_TAB = CREATIVE_MODE_TABS.register("just_wall_tab", () -> CreativeModeTab.builder()
            .icon(() -> ModItems.METAL_WALL_ITEM.get().getDefaultInstance())
            .title(Component.translatable("creativetabe.just_wall_tab"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.METAL_WALL_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());
}
