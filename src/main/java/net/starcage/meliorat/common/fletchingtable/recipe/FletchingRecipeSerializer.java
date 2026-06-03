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
                                    .forGetter(FletchingRecipe::getResult
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

    public static final StreamCodec<
            RegistryFriendlyByteBuf,
            FletchingRecipe
            > STREAM_CODEC =
            StreamCodec.of(
                    FletchingRecipeSerializer::toNetwork,
                    FletchingRecipeSerializer::fromNetwork
            );

    @Override
    public StreamCodec<
            RegistryFriendlyByteBuf,
            FletchingRecipe
            > streamCodec() {
        return STREAM_CODEC;
    }

    private static FletchingRecipe fromNetwork(
            RegistryFriendlyByteBuf buffer
    ) {

        int count = buffer.readVarInt();

        NonNullList<Ingredient> ingredients =
                NonNullList.withSize(
                        count,
                        Ingredient.EMPTY
                );

        ingredients.replaceAll(
                ignored ->
                        Ingredient.CONTENTS_STREAM_CODEC.decode(
                                buffer
                        )
        );

        ItemStack result =
                ItemStack.STREAM_CODEC.decode(
                        buffer
                );

        return new FletchingRecipe(
                ingredients,
                result
        );
    }

    private static void toNetwork(
            RegistryFriendlyByteBuf buffer,
            FletchingRecipe recipe
    ) {

        buffer.writeVarInt(
                recipe.getIngredients().size()
        );

        for (Ingredient ingredient :
                recipe.getIngredients()) {

            Ingredient.CONTENTS_STREAM_CODEC.encode(
                    buffer,
                    ingredient
            );
        }

        ItemStack.STREAM_CODEC.encode(
                buffer,
                recipe.getResult()
        );
    }
}