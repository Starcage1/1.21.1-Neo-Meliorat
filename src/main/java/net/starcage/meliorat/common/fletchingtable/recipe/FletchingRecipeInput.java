package net.starcage.meliorat.common.fletchingtable.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record FletchingRecipeInput(
        ItemStack slot0,
        ItemStack slot1,
        ItemStack slot2
) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> slot0;
            case 1 -> slot1;
            case 2 -> slot2;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 3;
    }

    public List<ItemStack> items() {
        return List.of(
                slot0,
                slot1,
                slot2
        );
    }
}
