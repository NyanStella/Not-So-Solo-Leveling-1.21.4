package net.stella.notsosololeveling.event;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.stella.notsosololeveling.NotSoSoloLeveling;
import net.stella.notsosololeveling.player.PlayerStoryData;

public final class IntroProtectionEvents {

    private IntroProtectionEvents() {}

    public static void onIncomingDamage(LivingIncomingDamageEvent event) {

        NotSoSoloLeveling.LOGGER.info(
                "DAMAGE EVENT FIRED!: {}",
                event.getSource()
        );
        if (event.getEntity() instanceof ServerPlayer player
               // && !PlayerStoryData.hasCompletedIntro(player)
         ) {
            boolean introComplete =
                    PlayerStoryData.hasCompletedIntro(player);
            NotSoSoloLeveling.LOGGER.info(
                    "DAMAGE CHECK: player ={}, introComplete={}, source={}",
                    player.getName().getString(),
                    introComplete,
                    event.getSource()
            );

            if (!introComplete){
                event.setCanceled(true);

                NotSoSoloLeveling.LOGGER.info(
                        "INTRO DAMAGE CANCELLED"
                );
            }

        }
    }
}