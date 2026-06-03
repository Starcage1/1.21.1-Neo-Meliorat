package net.starcage.meliorat.common.fletchingtable.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.starcage.meliorat.registry.ModRecipeSerializers;
import net.starcage.meliorat.registry.ModRecipeTypes;

public class FletchingRecipe
        implements Recipe<FletchingRecipeInput> {

    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;

    public FletchingRecipe(
            NonNullList<Ingredient> ingredients,
            ItemStack result
    ) {
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public boolean matches(
            FletchingRecipeInput input,
            Level level
    ) {

        if (ingredients.size() != 3)
            return false;

        return ingredients.get(0).test(input.getItem(0))
                && ingredients.get(1).test(input.getItem(1))
                && ingredients.get(2).test(input.getItem(2));
    }

    @Override
    public ItemStack assemble(
            FletchingRecipeInput input,
            HolderLookup.Provider registries
    ) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(
            int width,
            int height
    ) {
        return true;
    }

    @Override
    public ItemStack getResultItem(
            HolderLookup.Provider registries
    ) {
        return result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.FLETCHING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.FLETCHING.get();
    }
}
