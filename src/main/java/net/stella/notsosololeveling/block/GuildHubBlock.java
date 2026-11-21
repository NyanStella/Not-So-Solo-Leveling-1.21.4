package net.stella.notsosololeveling.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.stella.notsosololeveling.guild.GuildClaims;

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
       if (level instanceof ServerLevel serverLevel){

           if (player.isShiftKeyDown()){
               GuildClaims.RecruitResult result = GuildClaims.get(serverLevel)
                       .recruitStarterCitizen(serverLevel, pos, player.getUUID());

               String message = switch (result){
                   case RECRUITED -> "A citizen joined your guild.";
                   case NOT_OWNER ->  "Only the chunk owner can recruit here.";
                   case ALREADY_RECRUITED ->  "This chunk's starter citizen has already joined.";
                   case NO_SPACE ->  "Clear some space above the Guild Hub.";
                   case SPAWN_FAILED -> "The citizen couldn't join. Try again.";
               };

               player.displayClientMessage(Component.literal(message), true);
               return InteractionResult.SUCCESS;
           }
           ChunkPos chunk = new ChunkPos(pos);
           GuildClaims.ClaimResult result = GuildClaims.get(serverLevel)
                   .claim(chunk, player.getUUID());

           String message = switch (result){
               case CLAIMED -> "You claimed chunk " + chunk.x + ", " + chunk.z + ".";
               case ALREADY_YOURS -> "This chunk is already yours.";
               case OWNED_BY_OTHER -> "Another player has already claimed this chunk.";
           };

           player.displayClientMessage(Component.literal(message), true);
       }

       return InteractionResult.SUCCESS;
    }
}