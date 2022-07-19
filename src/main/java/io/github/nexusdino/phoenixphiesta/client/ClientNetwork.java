package io.github.nexusdino.phoenixphiesta.client;

import io.github.nexusdino.phoenixphiesta.client.IEnergyScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.Objects;

public final class ClientNetwork {

    public static void updateEnergy(int energy) {
        ((IEnergyScreen) Minecraft.getInstance().screen).setEnergy(energy);
    }

    public static void updateFluid(BlockPos pos, FluidStack fluidStack) {
        ClientLevel level = Minecraft.getInstance().level;
        assert level != null;
        BlockEntity blockEntity = level.getBlockEntity(pos);
        assert blockEntity != null;
        blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent((iFluidHandler) -> {
            ((FluidTank) iFluidHandler).setFluid(fluidStack);
        });
    }

    private ClientNetwork() {}
}
