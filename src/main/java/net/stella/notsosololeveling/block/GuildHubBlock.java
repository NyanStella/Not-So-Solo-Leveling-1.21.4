package net.stella.notsosololeveling.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class GuildHubBlock extends Block {
    public GuildHubBlock(Properties properties){
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult
    ){
        if (!level.isClientSide()){
            player.displayClientMessage(
                    Component.literal("You're at the guild master's desk."),
                    true
            );
        }

        return InteractionResult.SUCCESS;
    }
}