package net.starcage.meliorat.common.fletchingtable;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.starcage.meliorat.registry.ModMenus;

public class FletchingMenu extends AbstractContainerMenu {

    private final CraftingContainer inputSlots;
    private final ResultContainer resultSlots;;

    public FletchingMenu(int containerId, Inventory inventory) {
        super(ModMenus.FLETCHING_MENU.get(), containerId);

        this.inputSlots = new TransientCraftingContainer(
                this,
                3,
                1
        );

        this.resultSlots = new ResultContainer();

        this.addSlot(new Slot(inputSlots, 0, 8, 48));
        this.addSlot(new Slot(inputSlots, 1, 26, 48));
        this.addSlot(new Slot(inputSlots, 2, 44, 48));

        this.addSlot(new Slot(
                resultSlots,
                3,
                98,
                48
        ) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

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
            net.minecraft.world.entity.player.Player player,
            int index
    ) {
        return ItemStack.EMPTY;
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
}
