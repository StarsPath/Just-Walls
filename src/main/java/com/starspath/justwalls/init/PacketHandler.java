package com.starspath.justwalls.init;

import com.starspath.justwalls.JustWalls;
import com.starspath.justwalls.Network.ServerBoundLoaderPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketHandler {
    private static final String PROTOCOL_VERSIION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(JustWalls.MODID, "main"),
            ()->PROTOCOL_VERSIION, PROTOCOL_VERSIION::equals, PROTOCOL_VERSIION::equals);

    private PacketHandler(){

    }

    public static void register(){
        int index = 0;
        INSTANCE.messageBuilder(ServerBoundLoaderPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerBoundLoaderPacket::encode)
                .decoder(ServerBoundLoaderPacket::new)
                .consumerMainThread(ServerBoundLoaderPacket::handle)
                .add();
    }
}
