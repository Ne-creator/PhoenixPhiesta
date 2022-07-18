package io.github.nexusdino.phoenixphiesta.core.init;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.common.entity.Phoenix;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister
            .create(ForgeRegistries.ENTITY_TYPES, PhoenixPhiesta.MOD_ID);

    public static final RegistryObject<EntityType<Phoenix>> PHOENIX = ENTITIES.register("phoenix", () ->
            EntityType.Builder.of(Phoenix::new, MobCategory.MONSTER).build(new ResourceLocation(PhoenixPhiesta.MOD_ID, "textures/entities/phoenix.png").toString())
    );

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
