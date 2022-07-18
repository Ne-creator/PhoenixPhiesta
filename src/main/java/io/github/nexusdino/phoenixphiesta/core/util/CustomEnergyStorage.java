package io.github.nexusdino.phoenixphiesta.core.util;

import net.minecraftforge.energy.EnergyStorage;

public abstract class CustomEnergyStorage extends EnergyStorage {
    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public abstract void onEnergyChange();

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extractEnergy = super.extractEnergy(maxExtract, simulate);
        if (extractEnergy != 0) onEnergyChange();
        return extractEnergy;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int receiveEnergy = super.receiveEnergy(maxReceive, simulate);
        if (receiveEnergy != 0) onEnergyChange();
        return receiveEnergy;
    }
}
