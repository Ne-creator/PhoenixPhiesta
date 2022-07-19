package io.github.nexusdino.phoenixphiesta.data.jei;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.client.screen.WattSteamEngineScreen;
import io.github.nexusdino.phoenixphiesta.core.init.ModBlocks;
import io.github.nexusdino.phoenixphiesta.data.recipe.SteamEngineRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SteamEngineRecipeCategory implements IRecipeCategory<SteamEngineRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public SteamEngineRecipeCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.STEAM_ENGINE.get()));
        this.background = helper.createDrawable(WattSteamEngineScreen.ENGINE_LOCATION, 0, 0, 176, 85);
    }

    @Override
    public RecipeType<SteamEngineRecipe> getRecipeType() {
        return new RecipeType<>(new ResourceLocation(PhoenixPhiesta.MOD_ID, "steam_engine"), SteamEngineRecipe.class);
    }

    @Override
    public Component getTitle() {
        return ModBlocks.STEAM_ENGINE.get().getName();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SteamEngineRecipe recipe, IFocusGroup focuses) {

    }
}
