package io.github.nexusdino.phoenixphiesta.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public final class Config {
    public static void registerConfigs() {
        registerServerConfigs();
        registerCommonConfigs();
        registerClientConfigs();
    }

    private static void registerClientConfigs() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, builder.build());
    }

    private static void registerCommonConfigs() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, builder.build());
    }

    private static void registerServerConfigs() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        SteamEngineConfig.registerServerConfig(builder);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, builder.build());
    }
}
