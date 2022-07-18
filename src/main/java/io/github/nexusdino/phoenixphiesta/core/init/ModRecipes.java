package io.github.nexusdino.phoenixphiesta.core.init;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.data.recipe.SteamEngineRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModRecipes {
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(
            ForgeRegistries.RECIPE_TYPES, PhoenixPhiesta.MOD_ID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
            ForgeRegistries.RECIPE_SERIALIZERS, PhoenixPhiesta.MOD_ID);

    public static final RegistryObject<RecipeType<SteamEngineRecipe>> STEAM_ENGINE_TYPE = RECIPE_TYPES
            .register("steam_engine_combustion", () -> RecipeType.simple(new ResourceLocation(PhoenixPhiesta.MOD_ID, "steam_engine_combustion")));

    public static final RegistryObject<RecipeSerializer<SteamEngineRecipe>> STEAM_ENGINE_SERIALIZER = RECIPE_SERIALIZERS
            .register("steam_engine_combustion", SteamEngineRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
