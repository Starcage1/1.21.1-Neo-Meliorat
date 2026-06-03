package net.starcage.meliorat.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.common.fletchingtable.recipe.FletchingRecipe;
import net.starcage.meliorat.registry.ModRecipeTypes;
import net.starcage.meliorat.registry.ModBlocks;

@EmiEntrypoint
public class MelioratEmiPlugin implements EmiPlugin {

    public static final EmiRecipeCategory FLETCHING_CATEGORY =
            new EmiRecipeCategory(
                    ResourceLocation.fromNamespaceAndPath(
                            Meliorat.MOD_ID,
                            "fletching"
                    ),
                    EmiStack.of(Items.FLETCHING_TABLE)
            );

    @Override
    public void register(EmiRegistry registry) {

        registry.addCategory(FLETCHING_CATEGORY);

        registry.addWorkstation(
                FLETCHING_CATEGORY,
                EmiStack.of(Items.FLETCHING_TABLE)
        );

        for (RecipeHolder<FletchingRecipe> recipe :
                registry.getRecipeManager()
                        .getAllRecipesFor(
                                ModRecipeTypes.FLETCHING.get()
                        )) {

            registry.addRecipe(
                    new FletchingEmiRecipe(
                            recipe
                    )
            );
        }

        registry.removeEmiStacks(stack ->
                stack.getItemStack().is(ModBlocks.CLOCK.asItem()));

    }

}