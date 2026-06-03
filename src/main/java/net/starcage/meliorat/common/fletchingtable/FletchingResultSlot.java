package net.starcage.meliorat.common.fletchingtable;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FletchingResultSlot extends Slot {

    private final Container inputSlots;

    public FletchingResultSlot(
            Container resultSlots,
            Container inputSlots,
            int slot,
            int x,
            int y
    ) {
        super(resultSlots, slot, x, y);

        this.inputSlots = inputSlots;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public void onTake(
            Player player,
            ItemStack stack
    ) {
        inputSlots.removeItem(0, 1);
        inputSlots.removeItem(1, 1);
        inputSlots.removeItem(2, 1);

        inputSlots.setChanged();

        super.onTake(player, stack);
    }
}
