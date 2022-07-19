package io.github.nexusdino.phoenixphiesta.data.jei;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(PhoenixPhiesta.MOD_ID, "jei_plugin");
    }
}
