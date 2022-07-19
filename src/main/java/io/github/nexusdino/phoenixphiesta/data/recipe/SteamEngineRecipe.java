package io.github.nexusdino.phoenixphiesta.data.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.nexusdino.phoenixphiesta.core.init.ModRecipes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record SteamEngineRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack output, double fluidFillAmount) implements Recipe<SimpleContainer> {
    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param pContainer
     * @param pLevel
     */
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return !pLevel.isClientSide && ingredients.get(0).test(pContainer.getItem(0));
    }

    /**
     * Returns an Item that is the result of this recipe
     *
     * @param pContainer
     */
    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     *
     * @param pWidth
     * @param pHeight
     */
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.STEAM_ENGINE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.STEAM_ENGINE_TYPE.get();
    }

    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public static class Serializer implements RecipeSerializer<SteamEngineRecipe> {

        @Override
        public SteamEngineRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.create();

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            double fillAmount = GsonHelper.getAsDouble(pSerializedRecipe, "fluid_fill_amount");

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            return new SteamEngineRecipe(pRecipeId, inputs, output, fillAmount);
        }

        @Override
        public @Nullable SteamEngineRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));
            ItemStack output = pBuffer.readItem();
            return new SteamEngineRecipe(pRecipeId, inputs, output, pBuffer.readDouble());
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SteamEngineRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            pBuffer.writeResourceLocation(pRecipe.id);
            pRecipe.getIngredients().forEach(ingredient -> ingredient.toNetwork(pBuffer));
            pBuffer.writeDouble(pRecipe.fluidFillAmount);
        }
    }
}
