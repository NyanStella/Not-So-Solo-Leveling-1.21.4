package net.stella.notsosololeveling.event;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.stella.notsosololeveling.NotSoSoloLeveling;
import net.stella.notsosololeveling.player.PlayerStoryData;

@EventBusSubscriber(modid = NotSoSoloLeveling.MODID)
public final class IntroProtectionEvents {

    private IntroProtectionEvents() {}

    @SubscribeEvent
    public static void onPlayerAttacked(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player
                && !PlayerStoryData.hasCompletedIntro(player)) {

            event.setCanceled(true);
        }
    }
}