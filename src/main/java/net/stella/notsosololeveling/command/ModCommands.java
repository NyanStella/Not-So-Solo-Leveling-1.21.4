package net.stella.notsosololeveling.command;

import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.stella.notsosololeveling.hunter.HunterRank;
import net.stella.notsosololeveling.hunter.HunterRankRoller;

@EventBusSubscriber(modid = "notsosololeveling")

public final class ModCommands {

    private ModCommands(){

    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event){

        event.getDispatcher().register(
                Commands.literal("nssl")
                        .requires(source -> source.hasPermission(2))
                        .then(
                                Commands.literal("roll_rank")
                                        .executes(context -> {
                                            try {
                                                ServerPlayer player = context
                                                        .getSource()
                                                        .getPlayerOrException();

                                                HunterRank rank =
                                                        HunterRankRoller.roll(
                                                                player.getRandom()
                                                        );

                                                context.getSource().sendSuccess(
                                                        () -> Component.literal(
                                                                "Rolled Hunter rank: " + rank.getDisplayName()
                                                        ),
                                                        false
                                                );
                                                return 1;
                                            } catch (Throwable error) {

                                                error.printStackTrace();
                                                String details = error.getMessage() == null
                                                        ? ""
                                                        : "-" + error.getMessage();

                                                context.getSource().sendFailure(
                                                        Component.literal(
                                                                "Rank roll failed: "
                                                                        + error.getClass().getSimpleName()
                                                                        + details
                                                        )
                                                );
                                                return 0;
                                            }
                                        })));
    }
}