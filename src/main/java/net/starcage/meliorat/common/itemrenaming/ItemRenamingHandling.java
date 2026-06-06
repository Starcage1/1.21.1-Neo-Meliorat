package net.starcage.meliorat.common.itemrenaming;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.starcage.meliorat.Meliorat;
import net.starcage.meliorat.config.MelioratConfig;
import net.starcage.meliorat.network.OpenRenameScreenPayload;

@EventBusSubscriber(modid = Meliorat.MOD_ID)
public class ItemRenamingHandling {

    @SubscribeEvent
    public static void onRightClickItem(
            PlayerInteractEvent.RightClickItem event
    ) {

        if (!MelioratConfig.ENABLE_ITEM_RENAMING.get())
            return;

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }

        ItemStack stack =
                player.getMainHandItem();

        boolean validItem =
                stack.is(Items.NAME_TAG)
                        || stack.is(Items.MAP)
                        || stack.is(Items.FILLED_MAP);

        if (!validItem) {
            return;
        }

        if (
                !player.getOffhandItem().is(Items.INK_SAC)
                        &&
                !player.getOffhandItem().is(Items.GLOW_INK_SAC)
        ) {
            return;
        }

        String currentName = "";

        if (player.getMainHandItem().has(DataComponents.CUSTOM_NAME)) {

            currentName =
                    player.getMainHandItem()
                            .getHoverName()
                            .getString();
        }

        PacketDistributor.sendToPlayer(
                player,
                new OpenRenameScreenPayload(
                        currentName
                )
        );

        event.setCanceled(true);
    }
}