package io.github.nexusdino.phoenixphiesta.common.menu.slots;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class BaseFuelSlot extends SlotItemHandler {
    private final TagKey<Item> validFuels;

    public BaseFuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, TagKey<Item> validFuels) {
        super(itemHandler, index, xPosition, yPosition);
        this.validFuels = validFuels;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.is(validFuels);
    }
}
