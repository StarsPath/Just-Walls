package com.starspath.justwalls.events;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.Network.ServerBoundHoverOverBlockPacket;
import com.starspath.justwalls.client.StructureBlockHPScreen;
import com.starspath.justwalls.init.PacketHandler;
import com.starspath.justwalls.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JustWalls.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class WallHPDisplayHandler {

    static boolean shouldRenderOverlay = false;

    @SubscribeEvent
    public static void mouseMove(TickEvent.ClientTickEvent event){
        Minecraft minecraft = Minecraft.getInstance();
        HitResult hitResult = minecraft.hitResult;
        if(hitResult != null){
            shouldRenderOverlay = hitResult instanceof BlockHitResult;
            if(hitResult instanceof BlockHitResult blockHitResult){
                BlockPos blockPos = blockHitResult.getBlockPos();
                if(minecraft.getConnection() != null){
                    PacketHandler.INSTANCE.sendToServer(new ServerBoundHoverOverBlockPacket(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void renderOverlay(RenderGuiOverlayEvent.Post event){
        if(shouldRenderOverlay){
            GuiGraphics guiGraphics = event.getGuiGraphics();
            StructureBlockHPScreen.renderOverlay(guiGraphics);
        }
    }
}
