package io.github.nexusdino.phoenixphiesta.core.init;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    static final DeferredRegister<Item> ITEMS = DeferredRegister
            .create(ForgeRegistries.ITEMS, PhoenixPhiesta.MOD_ID);

    public static final RegistryObject<Item> GARMET_INGOT = ITEMS.register("garmet_ingot", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS))
    );
    public static final RegistryObject<Item> RAW_GARMET = ITEMS.register("raw_garmet", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS))
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
