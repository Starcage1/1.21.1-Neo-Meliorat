package net.starcage.meliorat.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.starcage.meliorat.common.itemrenaming.ItemRenameScreen;

public class ItemRenamingPayloadHandler {

    public static void openScreen(
            OpenRenameScreenPayload payload
    ) {

        Minecraft.getInstance().setScreen(
                new ItemRenameScreen(
                        payload.currentName()
                )
        );
    }

    public static void rename(
            RenameItemPayload payload,
            ServerPlayer player
    ) {

        String name =
                payload.name().trim();

        if (name.isBlank()) {
            return;
        }

        ItemStack stack =
                player.getMainHandItem();

        ItemStack offhand =
                player.getOffhandItem();


        boolean validItem =
                stack.is(Items.NAME_TAG)
                        || stack.is(Items.MAP)
                        || stack.is(Items.FILLED_MAP);

        if (!validItem) {
            return;
        }

        if (
                !offhand.is(Items.INK_SAC)
                        &&
                        !offhand.is(Items.GLOW_INK_SAC)
        ) {
            return;
        }

        stack.set(
                DataComponents.CUSTOM_NAME,
                Component.literal(name)
        );

        if (!player.getAbilities().instabuild) {
            offhand.shrink(1);
        }
    }
}
