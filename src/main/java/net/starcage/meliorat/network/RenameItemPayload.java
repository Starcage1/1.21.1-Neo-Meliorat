package net.starcage.meliorat.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.starcage.meliorat.Meliorat;

public record RenameItemPayload(
        String name
) implements CustomPacketPayload {

    public static final Type<RenameItemPayload> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            Meliorat.MOD_ID,
                            "rename_nametag"
                    )
            );

    public static final StreamCodec<
            RegistryFriendlyByteBuf,
            RenameItemPayload
            > STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) ->
                            buf.writeUtf(
                                    payload.name(),
                                    50
                            ),

                    buf ->
                            new RenameItemPayload(
                                    buf.readUtf(50)
                            )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
