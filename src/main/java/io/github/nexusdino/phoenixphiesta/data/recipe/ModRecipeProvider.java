package io.github.nexusdino.phoenixphiesta.data.recipe;

import io.github.nexusdino.phoenixphiesta.core.init.ModBlocks;
import io.github.nexusdino.phoenixphiesta.core.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, ModItems.GARMET_INGOT.get(), ModBlocks.GARMET_BLOCK.get());

        smeltingResultFromBase(pFinishedRecipeConsumer, ModItems.GARMET_INGOT.get(), ModItems.RAW_GARMET.get());
        smeltingResultFromBase(pFinishedRecipeConsumer, ModItems.GARMET_INGOT.get(), ModBlocks.GARMET_ORE.get());
        smeltingResultFromBase(pFinishedRecipeConsumer, ModItems.GARMET_INGOT.get(), ModBlocks.DEEPSLATE_GARMET_ORE.get());
    }
}
