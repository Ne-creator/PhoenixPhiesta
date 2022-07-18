package io.github.nexusdino.phoenixphiesta.core.init;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.common.menu.WattSteamEngineMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModMenuTypes {
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES,
            PhoenixPhiesta.MOD_ID);

    public static final RegistryObject<MenuType<WattSteamEngineMenu>> WATT_STEAM_ENGINE = MENU_TYPES
            .register("watt_steam_engine", () -> IForgeMenuType.create(WattSteamEngineMenu::new));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
