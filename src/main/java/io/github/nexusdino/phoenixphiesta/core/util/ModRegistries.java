package io.github.nexusdino.phoenixphiesta.core.util;

import io.github.nexusdino.phoenixphiesta.core.BoltType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class ModRegistries {
    static final DeferredRegister<BoltType> DEFFERRED_BOLT_TYPES = DeferredRegister.create(Keys.BOLT_TYPES, Keys.BOLT_TYPES.location().getNamespace());
    public static final Supplier<IForgeRegistry<BoltType>> BOLT_TYPES = DEFFERRED_BOLT_TYPES.makeRegistry(ModRegistries::getBoltTypeRegistryBuilder);

    public static RegistryBuilder<BoltType> getBoltTypeRegistryBuilder() {
        return makeRegistry(Keys.BOLT_TYPES, "lightning");
    }

    private static <T> RegistryBuilder<T> makeRegistry(ResourceKey<? extends Registry<T>> key, String _default)
    {
        return new RegistryBuilder<T>().setName(key.location()).setMaxID(Integer.MAX_VALUE - 1).setDefaultKey(new ResourceLocation(_default));
    }

    public static class Keys {
        public static final ResourceKey<Registry<BoltType>> BOLT_TYPES = ResourceKey.createRegistryKey(new ResourceLocation("phoenixphiesta:bolt_type"));
    }
}
