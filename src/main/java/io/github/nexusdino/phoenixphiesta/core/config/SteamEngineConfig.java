package io.github.nexusdino.phoenixphiesta.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SteamEngineConfig {
    public static ForgeConfigSpec.IntValue MAX_PROGRESS;
    public static ForgeConfigSpec.IntValue CAPACITY;
    public static ForgeConfigSpec.IntValue MAX_TRANSFER;

    public static void registerServerConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Settings for the Steam Engine").push("steam_engine");

        assignConfigValues(builder);

        builder.pop();
    }

    private static void assignConfigValues(ForgeConfigSpec.Builder builder) {
        MAX_PROGRESS = builder.comment("Max Progress").defineInRange("maxProgress", 100, 10, Integer.MAX_VALUE);

        CAPACITY = builder.comment("Capacity of the steam engine").defineInRange("capacity", 1000, 10, Integer.MAX_VALUE);
        MAX_TRANSFER = builder.comment("max extract and transfer").defineInRange("maxTransfer", 100, 10, CAPACITY.getDefault());
    }
}
