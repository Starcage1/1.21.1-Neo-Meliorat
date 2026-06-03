package net.starcage.meliorat.compat.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.starcage.meliorat.common.fletchingtable.recipe.FletchingRecipe;

public class FletchingEmiRecipe
        extends BasicEmiRecipe {

    private final FletchingRecipe recipe;

    public FletchingEmiRecipe(
            RecipeHolder<FletchingRecipe> holder
    ) {

        super(
                MelioratEmiPlugin.FLETCHING_CATEGORY,
                holder.id(),
                120,
                40
        );

        this.recipe = holder.value();

        for (Ingredient ingredient :
                this.recipe.getIngredients()) {

            this.inputs.add(
                    EmiIngredient.of(
                            ingredient
                    )
            );
        }

        this.outputs.add(
                EmiStack.of(
                        this.recipe.getResult()
                )
        );
    }

    @Override
    public void addWidgets(
            WidgetHolder widgets
    ) {

        for (int i = 0; i < this.inputs.size(); i++) {

            widgets.addSlot(
                    this.inputs.get(i),
                    i * 18,
                    10
            );
        }

        widgets.addSlot(
                this.outputs.get(0),
                90,
                10
        ).recipeContext(this);
    }
}