package io.github.nexusdino.phoenixphiesta.common.menu;

import io.github.nexusdino.phoenixphiesta.common.blockentity.WattSteamEngineBlockEntity;
import io.github.nexusdino.phoenixphiesta.core.init.ModBlocks;
import io.github.nexusdino.phoenixphiesta.core.init.ModMenuTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WattSteamEngineMenu extends AbstractContainerMenu {
    private final ContainerData data;
    private final WattSteamEngineBlockEntity blockEntity;

    public WattSteamEngineMenu(int windowId, Inventory inv, ContainerData data, WattSteamEngineBlockEntity blockEntity) {
        super(ModMenuTypes.WATT_STEAM_ENGINE.get(), windowId);
        this.data = data;
        this.blockEntity = blockEntity;

        addDataSlots(data);
    }
    public WattSteamEngineMenu(int windowId, Inventory inv, FriendlyByteBuf buf) {
        this(windowId, inv, new SimpleContainerData(1), (WattSteamEngineBlockEntity) Objects.requireNonNull(inv.player.level.getBlockEntity(buf.readBlockPos())));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.blockEntity.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.blockEntity.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.blockEntity.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public int getProgress() {
        return data.get(0);
    }
    public int getMaxProgress() {
        return data.get(1);
    }

    @Override
    public boolean stillValid(Player player) {
        Level level = Objects.requireNonNull(blockEntity.getLevel());
        var access = ContainerLevelAccess.create(level, blockEntity.getBlockPos());
        return stillValid(access, player, ModBlocks.STEAM_ENGINE.get());
    }
}
