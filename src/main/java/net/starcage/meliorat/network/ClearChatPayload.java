package net.starcage.meliorat.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.starcage.meliorat.Meliorat;

public record ClearChatPayload()
        implements CustomPacketPayload {

    public static final Type<ClearChatPayload> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            Meliorat.MOD_ID,
                            "clear_chat"
                    )
            );

    public static final StreamCodec<
            FriendlyByteBuf,
            ClearChatPayload
            > STREAM_CODEC =
            StreamCodec.unit(
                    new ClearChatPayload()
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}