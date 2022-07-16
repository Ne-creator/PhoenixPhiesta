package io.github.nexusdino.phoenixphiesta.core;

import net.minecraftforge.items.IItemHandler;

public interface IItemHandlerBlockEntity<T extends IItemHandler> {
    T createHandler();
}
