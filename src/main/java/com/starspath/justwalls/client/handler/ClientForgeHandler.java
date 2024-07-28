package com.starspath.justwalls.client.handler;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.client.Keybindings;
import com.starspath.justwalls.client.RadialMenu;
import com.starspath.justwalls.item.SuperHammer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
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
            ItemStack itemStack = minecraft.player.getItemInHand(InteractionHand.MAIN_HAND);
            if(itemStack.getItem() instanceof SuperHammer){
                Minecraft.getInstance().setScreen(new RadialMenu());
//                minecraft.player.displayClientMessage(Component.literal("Key Pressed"), true);
            }
        }
    }
}
