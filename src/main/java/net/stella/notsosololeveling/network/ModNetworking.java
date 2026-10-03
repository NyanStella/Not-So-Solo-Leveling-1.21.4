package net.stella.notsosololeveling.network;


import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;


public final class ModNetworking{

    private ModNetworking() {

    }

    public static void registerPayloads(RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                OpenIntroPacket.TYPE,
                OpenIntroPacket.STREAM_CODEC,
                OpenIntroPacket::handle
        );

        registrar.playToServer(
                CompleteIntroPacket.TYPE,
                CompleteIntroPacket.STREAM_CODEC,
                CompleteIntroPacket::handle
        );
}
}
