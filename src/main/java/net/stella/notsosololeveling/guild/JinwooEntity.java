package net.stella.notsosololeveling.guild;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


public class JinwooEntity extends PathfinderMob {
    public JinwooEntity(EntityType<? extends JinwooEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (!level().isClientSide()) {
            player.displayClientMessage(
                    Component.literal(
                            "Jinwoo: So you're the one I spoke to. Good. I'll be here to advise you on your new guild."
                            + " When a Gate opens, clear the dungeon before it breaks."
                    ),
                    false
            );
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer){
        return false;
    }
}
