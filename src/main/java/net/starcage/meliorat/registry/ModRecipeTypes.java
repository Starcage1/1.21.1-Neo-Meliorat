package net.starcage.meliorat.registry;

import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.common.fletchingtable.recipe.FletchingRecipe;

public class ModRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(
                    net.minecraft.core.registries.Registries.RECIPE_TYPE,
                    Meliorat.MOD_ID
            );

    public static final net.neoforged.neoforge.registries.DeferredHolder<
            RecipeType<?>,
            RecipeType<FletchingRecipe>
            > FLETCHING =
            RECIPE_TYPES.register(
                    "fletching",
                    () -> new RecipeType<>() {
                        @Override
                        public String toString() {
                            return "meliorat:fletching";
                        }
                    }
            );

    public static void register(IEventBus bus) {
        RECIPE_TYPES.register(bus);
    }
}
