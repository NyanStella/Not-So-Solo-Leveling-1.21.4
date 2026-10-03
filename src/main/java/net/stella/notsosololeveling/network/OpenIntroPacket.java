package net.stella.notsosololeveling.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.stella.notsosololeveling.NotSoSoloLeveling;
import net.stella.notsosololeveling.client.ClientPacketHandler;

public record OpenIntroPacket() implements  CustomPacketPayload {

    public static final Type<OpenIntroPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(
                    NotSoSoloLeveling.MODID,
                    "open_intro"
            ));

    public static final StreamCodec<ByteBuf, OpenIntroPacket> STREAM_CODEC =
            StreamCodec.unit(new OpenIntroPacket());

    @Override
    public Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }

    public static void handle(
            OpenIntroPacket payload,
            IPayloadContext context
    ){
        ClientPacketHandler.openIntro();
    }
}