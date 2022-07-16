package io.github.nexusdino.phoenixphiesta.common.menu;

import io.github.nexusdino.phoenixphiesta.common.blockentity.WattSteamEngineBlockEntity;
import io.github.nexusdino.phoenixphiesta.core.init.ModMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;

public class WattSteamEngineMenu extends AbstractContainerMenu {
    private final ContainerData data;

    public WattSteamEngineMenu(int windowId, Inventory inv, ContainerData data, WattSteamEngineBlockEntity blockEntity) {
        super(ModMenuTypes.WATT_STEAM_ENGINE.get(), windowId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
