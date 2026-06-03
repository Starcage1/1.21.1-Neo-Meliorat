package net.starcage.meliorat.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.common.fletchingtable.recipe.FletchingRecipe;
import net.starcage.meliorat.common.fletchingtable.recipe.FletchingRecipeSerializer;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(
                    Registries.RECIPE_SERIALIZER,
                    Meliorat.MOD_ID
            );

    public static final DeferredHolder<
            RecipeSerializer<?>,
            FletchingRecipeSerializer
            > FLETCHING =
            RECIPE_SERIALIZERS.register(
                    "fletching",
                    FletchingRecipeSerializer::new
            );

    public static void register(IEventBus bus) {
        RECIPE_SERIALIZERS.register(bus);
    }
}
