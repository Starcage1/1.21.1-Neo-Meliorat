package net.starcage.meliorat.network;

import net.minecraft.client.Minecraft;

public class ClientPayloadHandler {

    public static void handle(
            ClearChatPayload payload
    ) {

        Minecraft.getInstance()
                .gui
                .getChat()
                .clearMessages(true);
    }
}
