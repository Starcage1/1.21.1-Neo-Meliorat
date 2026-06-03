package net.starcage.meliorat.common.fletchingtable.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class FletchingRecipeSerializer
        implements RecipeSerializer<FletchingRecipe> {

    private static final MapCodec<FletchingRecipe> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            Ingredient.CODEC_NONEMPTY.listOf()
                                    .fieldOf("ingredients")
                                    .forGetter(
                                            FletchingRecipe::getIngredients
                                    ),

                            ItemStack.STRICT_CODEC
                                    .fieldOf("result")
                                    .forGetter(recipe ->
                                            recipe.getResultItem(null)
                                    )
                    ).apply(
                            instance,
                            (ingredients, result) ->
                                    new FletchingRecipe(
                                            NonNullList.copyOf(
                                                    ingredients
                                            ),
                                            result
                                    )
                    )
            );

    @Override
    public MapCodec<FletchingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<
            RegistryFriendlyByteBuf,
            FletchingRecipe
            > streamCodec() {

        throw new UnsupportedOperationException(
                "Not implemented yet"
        );
    }
}