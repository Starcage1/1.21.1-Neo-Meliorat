package net.starcage.meliorat.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.starcage.meliorat.Meliorat;

public record OpenRenameScreenPayload(
        String currentName
)
        implements CustomPacketPayload {

    public static final Type<OpenRenameScreenPayload> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            Meliorat.MOD_ID,
                            "open_rename_screen"
                    )
            );

    public static final StreamCodec<
            RegistryFriendlyByteBuf,
            OpenRenameScreenPayload
            > STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) ->
                            buf.writeUtf(
                                    payload.currentName(),
                                    50
                            ),

                    buf ->
                            new OpenRenameScreenPayload(
                                    buf.readUtf(50)
                            )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
