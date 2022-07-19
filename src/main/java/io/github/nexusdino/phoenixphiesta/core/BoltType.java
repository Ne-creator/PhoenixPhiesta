package io.github.nexusdino.phoenixphiesta.core;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.core.util.ModRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface BoltType {
    DeferredRegister<BoltType> BOLT_TYPES = DeferredRegister.create(ModRegistries.Keys.BOLT_TYPES, PhoenixPhiesta.MOD_ID);

    RegistryObject<BoltType> MOLTEN_BOLT = BOLT_TYPES.register("molten_bolt", () -> new BoltType() {
        @Override
        public String toString() {
            return "molten_bolt";
        }
    });
}
