package net.starcage.meliorat.network;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModPayloads {

    public static void register(
            RegisterPayloadHandlersEvent event
    ) {

        PayloadRegistrar registrar =
                event.registrar("1");

        registrar.playToClient(
                ClearChatPayload.TYPE,
                ClearChatPayload.STREAM_CODEC,
                (payload, context) ->
                        context.enqueueWork(() ->
                                ClientPayloadHandler.handle(payload)
                        )
        );
        registrar.playToClient(
                OpenRenameScreenPayload.TYPE,
                OpenRenameScreenPayload.STREAM_CODEC,
                (payload, context) ->
                        context.enqueueWork(() ->
                                ItemRenamingPayloadHandler.openScreen(
                                        payload
                                )
                        )
        );
        registrar.playToServer(
                RenameItemPayload.TYPE,
                RenameItemPayload.STREAM_CODEC,
                (payload, context) ->
                        context.enqueueWork(() ->
                                ItemRenamingPayloadHandler.rename(
                                        payload,
                                        (ServerPlayer) context.player()
                                )
                        )
        );
    }
}
