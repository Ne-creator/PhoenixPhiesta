package io.github.nexusdino.phoenixphiesta.core.event;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.common.entity.Phoenix;
import io.github.nexusdino.phoenixphiesta.core.init.ModEntities;
import io.github.nexusdino.phoenixphiesta.core.util.ModRegistries;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.NewRegistryEvent;

@Mod.EventBusSubscriber(modid = PhoenixPhiesta.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEvents {

    @SubscribeEvent
    public static void onRegisterBuilders(NewRegistryEvent event) {
        event.create(ModRegistries.getBoltTypeRegistryBuilder());
    }

    @SubscribeEvent
    public static void onCreateAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.PHOENIX.get(), Phoenix.createAttributes().build());
    }

    private ModEvents() {}
}
