package io.github.nexusdino.phoenixphiesta.core.init;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.common.blockentity.WattSteamEngineBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITY_TYPES, PhoenixPhiesta.MOD_ID);

    public static final RegistryObject<BlockEntityType<WattSteamEngineBlockEntity>> STEAM_ENGINE =
            BLOCK_ENTITIES.register("watt_steam_engine",
                    () -> BlockEntityType.Builder.of(WattSteamEngineBlockEntity::new, ModBlocks.STEAM_ENGINE.get())
                            .build(null)
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
