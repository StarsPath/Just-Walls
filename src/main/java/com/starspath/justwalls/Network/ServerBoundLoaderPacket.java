package com.starspath.justwalls.Network;

import com.starspath.justwalls.item.SuperHammer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerBoundLoaderPacket {

    public final int mode;

    public ServerBoundLoaderPacket(int mode){
        this.mode = mode;
    }

    public ServerBoundLoaderPacket(FriendlyByteBuf buffer){
        this(buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(this.mode);
    }

    public boolean handle(Supplier<NetworkEvent.Context> context){
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(()->{
            ServerPlayer player = ctx.getSender();
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
//            Item item = itemStack.getItem();
            if(itemStack.getItem() instanceof SuperHammer superHammer){
                superHammer.setMode(itemStack, SuperHammer.TOOL_MODE.values()[mode]);
            }
        });
        ctx.setPacketHandled(true);
        return false;
    }
}
