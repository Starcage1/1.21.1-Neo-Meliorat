package net.starcage.meliorat.common.fletchingtable;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.starcage.meliorat.common.fletchingtable.recipe.FletchingRecipe;
import net.starcage.meliorat.common.fletchingtable.recipe.FletchingRecipeInput;
import net.starcage.meliorat.registry.ModMenus;
import net.starcage.meliorat.registry.ModRecipeTypes;

import java.util.Optional;

public class FletchingMenu extends AbstractContainerMenu {

    private final Inventory playerInventory;
    private final CraftingContainer inputSlots;
    private final ResultContainer resultSlots;;

    public FletchingMenu(int containerId, Inventory inventory) {
        super(ModMenus.FLETCHING_MENU.get(), containerId);
        this.playerInventory = inventory;

        this.inputSlots = new TransientCraftingContainer(
                this,
                3,
                1
        );

        this.resultSlots = new ResultContainer();

        this.addSlot(new Slot(inputSlots, 0, 8, 48));
        this.addSlot(new Slot(inputSlots, 1, 26, 48));
        this.addSlot(new Slot(inputSlots, 2, 44, 48));

        this.addSlot(
                new FletchingResultSlot(
                        resultSlots,
                        inputSlots,
                        0,
                        98,
                        48
                )
        );

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        this.clearContainer(
                player,
                this.inputSlots
        );
    }

    @Override
    public ItemStack quickMoveStack(
            Player player,
            int index
    ) {

        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (index < 4) {

            if (!moveItemStackTo(
                    stack,
                    4,
                    this.slots.size(),
                    true
            )) {
                return ItemStack.EMPTY;
            }

        } else {

            if (!moveItemStackTo(
                    stack,
                    0,
                    3,
                    false
            )) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return copy;
    }

    @Override
    public boolean stillValid(
            net.minecraft.world.entity.player.Player player
    ) {
        return true;
    }

    private void addPlayerInventory(Inventory inventory) {

        for (int row = 0; row < 3; row++) {

            for (int column = 0; column < 9; column++) {

                addSlot(new Slot(
                        inventory,
                        column + row * 9 + 9,
                        8 + column * 18,
                        84 + row * 18
                ));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {

        for (int column = 0; column < 9; column++) {

            addSlot(new Slot(
                    inventory,
                    column,
                    8 + column * 18,
                    142
            ));
        }
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);

        updateResult();
    }

    private void updateResult() {

        Level level = playerInventory.player.level();

        FletchingRecipeInput input =
                new FletchingRecipeInput(
                        inputSlots.getItem(0),
                        inputSlots.getItem(1),
                        inputSlots.getItem(2)
                );

        Optional<RecipeHolder<FletchingRecipe>> recipe =
                level.getRecipeManager()
                        .getRecipeFor(
                                ModRecipeTypes.FLETCHING.get(),
                                input,
                                level
                        );

        if (recipe.isPresent()) {

            resultSlots.setItem(
                    0,
                    recipe.get()
                            .value()
                            .assemble(
                                    input,
                                    level.registryAccess()
                            )
            );
        } else {

            resultSlots.setItem(
                    0,
                    ItemStack.EMPTY
            );
        }
    }
}
