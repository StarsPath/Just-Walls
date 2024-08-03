package com.starspath.justwalls.Network;

import com.starspath.justwalls.item.SuperHammer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerBoundLoaderPacket {

    public final String name;
    public final int extra;

    public ServerBoundLoaderPacket(String name){
        this(name, 0);
    }

    public ServerBoundLoaderPacket(String name, int extra){
        this.name = name;
        this.extra = extra;
    }

    public ServerBoundLoaderPacket(FriendlyByteBuf buffer){
        this(buffer.readUtf(), buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeUtf(this.name);
        buffer.writeInt(this.extra);
    }

    public boolean handle(Supplier<NetworkEvent.Context> context){
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(()->{
            ServerPlayer player = ctx.getSender();
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(itemStack.getItem() instanceof SuperHammer superHammer){
                superHammer.setMode(itemStack, name, extra);
            }
        });
        ctx.setPacketHandled(true);
        return false;
    }
}
