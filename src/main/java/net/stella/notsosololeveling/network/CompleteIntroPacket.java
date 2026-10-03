package net.stella.notsosololeveling.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.stella.notsosololeveling.NotSoSoloLeveling;
import net.stella.notsosololeveling.player.PlayerStoryData;

public record CompleteIntroPacket() implements CustomPacketPayload {

    public static final Type<CompleteIntroPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(
                    NotSoSoloLeveling.MODID,
                    "complete_intro"
            ));

    public static final StreamCodec<ByteBuf, CompleteIntroPacket> STREAM_CODEC =
            StreamCodec.unit(new CompleteIntroPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(
            CompleteIntroPacket payload,
            IPayloadContext context
    ){
        if (context.player() instanceof ServerPlayer player) {
            PlayerStoryData.setIntroComplete(player, true);
        }
    }
}