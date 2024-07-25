package com.starspath.justwalls.client.handler;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.client.Keybindings;
import com.starspath.justwalls.client.RadialMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JustWalls.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event){
        Minecraft minecraft = Minecraft.getInstance();
        if(Keybindings.INSTANCE.guiKey.consumeClick() && minecraft.player != null){
            Minecraft.getInstance().setScreen(new RadialMenu());
            minecraft.player.displayClientMessage(Component.literal("Key Pressed"), true);
        }
    }
}
