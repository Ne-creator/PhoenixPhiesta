package io.github.nexusdino.phoenixphiesta.client;

import io.github.nexusdino.phoenixphiesta.client.IEnergyScreen;
import net.minecraft.client.Minecraft;

public final class ClientNetwork {

    public static void updateEnergy(int energy) {
        ((IEnergyScreen) Minecraft.getInstance().screen).setEnergy(energy);
    }

    private ClientNetwork() {}
}
