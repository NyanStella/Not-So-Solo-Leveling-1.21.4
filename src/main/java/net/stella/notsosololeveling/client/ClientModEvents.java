package net.stella.notsosololeveling.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.stella.notsosololeveling.NotSoSoloLeveling;
import net.stella.notsosololeveling.citizen.ModEntities;


@EventBusSubscriber(modid = NotSoSoloLeveling.MODID, value = Dist.CLIENT)
public class ClientModEvents {
    private ClientModEvents(){}

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntities.CITIZEN.get(), CitizenRenderer::new);

        event.registerEntityRenderer(ModEntities.JINWOO.get(), JinwooRenderer::new);
    }
}
