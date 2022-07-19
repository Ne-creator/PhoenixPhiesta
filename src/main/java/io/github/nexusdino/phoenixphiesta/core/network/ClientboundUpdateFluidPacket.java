package io.github.nexusdino.phoenixphiesta.core.network;

import io.github.nexusdino.phoenixphiesta.client.ClientNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundUpdateFluidPacket(BlockPos pos, FluidStack fluidStack) {
    public ClientboundUpdateFluidPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readFluidStack());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeFluidStack(fluidStack);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> ClientNetwork.updateFluid(pos, fluidStack)));
        return true;
    }
}
