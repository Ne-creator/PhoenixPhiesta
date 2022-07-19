package io.github.nexusdino.phoenixphiesta.common.menu;

import io.github.nexusdino.phoenixphiesta.common.blockentity.WattSteamEngineBlockEntity;
import io.github.nexusdino.phoenixphiesta.common.menu.slots.BaseFuelSlot;
import io.github.nexusdino.phoenixphiesta.core.init.ModBlocks;
import io.github.nexusdino.phoenixphiesta.core.init.ModMenuTypes;
import io.github.nexusdino.phoenixphiesta.data.tags.ModTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WattSteamEngineMenu extends AbstractContainerMenu {
    private final ContainerData data;
    public final WattSteamEngineBlockEntity blockEntity;
    private FluidStack fluid;

    public WattSteamEngineMenu(int windowId, Inventory inv, ContainerData data, WattSteamEngineBlockEntity blockEntity) {
        super(ModMenuTypes.WATT_STEAM_ENGINE.get(), windowId);
        this.data = data;
        this.blockEntity = blockEntity;
        this.fluid = blockEntity.createTank().getFluid();

        addDataSlots(data);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new BaseFuelSlot(iItemHandler, 0, 17, 17, ModTags.Items.STEAM_ENGINE_FUELS));
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, i * 18 + 51));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1, 8 + i1 * 18, 109));
        }
    }
    public WattSteamEngineMenu(int windowId, Inventory inv, FriendlyByteBuf buf) {
        this(windowId, inv, new SimpleContainerData(1), (WattSteamEngineBlockEntity) Objects.requireNonNull(inv.player.level.getBlockEntity(buf.readBlockPos())));
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 26; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            if (index < this.blockEntity.getContainerSize()) {
                if (!this.moveItemStackTo(itemStack1, this.blockEntity.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack1, 0, this.blockEntity.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        Level level = Objects.requireNonNull(blockEntity.getLevel());
        var access = ContainerLevelAccess.create(level, blockEntity.getBlockPos());
        return stillValid(access, player, ModBlocks.STEAM_ENGINE.get());
    }

    public boolean isCrafting() {
        return data.get(0) != data.get(1);
    }
}
