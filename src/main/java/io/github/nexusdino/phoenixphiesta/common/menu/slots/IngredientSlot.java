package io.github.nexusdino.phoenixphiesta.common.menu.slots;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class IngredientSlot extends SlotItemHandler {
    private final TagKey<Item> validInput;

    public IngredientSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, TagKey<Item> validInput) {
        super(itemHandler, index, xPosition, yPosition);
        this.validInput = validInput;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.is(validInput);
    }
}
