package com.starspath.justwalls.Network;

import com.starspath.justwalls.client.StructureBlockHPScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientBoundStructureHPPacket {
    private final int currentHp, maxHp;
    private final boolean render;

    public ClientBoundStructureHPPacket(int currentHp, int maxHp, boolean render){
        this.currentHp = currentHp;
        this.maxHp = maxHp;
        this.render = render;
    }

    public ClientBoundStructureHPPacket(FriendlyByteBuf buf) {
        this.currentHp = buf.readInt();
        this.maxHp = buf.readInt();
        this.render = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(currentHp);
        buf.writeInt(maxHp);
        buf.writeBoolean(render);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            StructureBlockHPScreen.setCurrentHp(currentHp);
            StructureBlockHPScreen.setMaxHp(maxHp);
            StructureBlockHPScreen.setRender(render);
        });
        return true;
    }
}
