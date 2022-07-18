package io.github.nexusdino.phoenixphiesta.core.network;

import io.github.nexusdino.phoenixphiesta.client.ClientNetwork;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundUpdateEnergyPacket(int energy) {

    public ClientboundUpdateEnergyPacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(energy);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> ClientNetwork.updateEnergy(energy)));
        ctx.get().setPacketHandled(true);
    }
}
