package net.starcage.meliorat.common.fletchingtable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.config.MelioratConfig;

@EventBusSubscriber(modid = Meliorat.MOD_ID)
public class FletchingTableHandler {

    @SubscribeEvent
    public static void onRightClickBlock(
            PlayerInteractEvent.RightClickBlock event
    ) {

        if (!MelioratConfig.ENABLE_FLETCHING_TABLE.get()) {
            return;
        }

        if (event.getLevel().isClientSide()) {
            return;
        }

        if (!event.getLevel()
                .getBlockState(event.getPos())
                .is(Blocks.FLETCHING_TABLE)) {
            return;
        }

        MenuProvider provider =
                new SimpleMenuProvider(
                        (containerId, inventory, player) ->
                                new FletchingMenu(
                                        containerId,
                                        inventory
                                ),
                        Component.literal("Fletching Table")
                );

        event.getEntity().openMenu(provider);

        event.setCanceled(true);
    }
}
