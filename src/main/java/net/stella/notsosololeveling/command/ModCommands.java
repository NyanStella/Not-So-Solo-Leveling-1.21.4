package net.stella.notsosololeveling.command;

import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.stella.notsosololeveling.citizen.CitizenData;
import net.stella.notsosololeveling.citizen.CitizenStats;
import net.stella.notsosololeveling.hunter.HunterRank;
import net.stella.notsosololeveling.hunter.HunterRankRoller;
import net.stella.notsosololeveling.hunter.HunterStatCalculator;
import net.stella.notsosololeveling.hunter.HunterStats;
import net.stella.notsosololeveling.citizen.CitizenStatsGenerator;
import net.stella.notsosololeveling.citizen.AwakeningRoller;

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
                                Commands.literal("test_stats")
                                        .executes(context ->{
                                            ServerPlayer player = context
                                                    .getSource()
                                                    .getPlayerOrException();

                                            CitizenData citizenData =
                                                    new CitizenData(
                                                            CitizenStatsGenerator.generate(
                                                                    player.getRandom()
                                                            )
                                                    );

                                            CitizenStats citizenStats =
                                                    citizenData.getBaseStats();

                                            double awakeningChance =
                                                    AwakeningRoller.calculateChance(
                                                            citizenStats
                                                    );

                                            double awakeningPercent =
                                                    Math.round(
                                                            awakeningChance * 1000.0
                                                    )/ 10.0;

                                            boolean awakened =
                                                    AwakeningRoller.roll(
                                                            citizenStats,
                                                            player.getRandom()
                                                    );

                                            context.getSource().sendSuccess(
                                                    () -> Component.literal(
                                                            "Citizen Base Stats:"
                                                            +" | STR: " + citizenStats.strength()
                                                            +" | AGI: " + citizenStats.agility()
                                                            +" | PER: " + citizenStats.perception()
                                                            +" | VIT: " + citizenStats.vitality()
                                                            +" | INT: " + citizenStats.intelligence()
                                                            +" | AVG: " + citizenStats.average()
                                                            +" | Awakening: " + awakeningPercent + "%"
                                                    ),
                                                    false
                                            );

                                            if (!awakened){
                                                citizenData.recordAwakeningFailure();
                                                context.getSource().sendSuccess(
                                                        () -> Component.literal(
                                                                "Result: Citizen did not awaken. "
                                                        ),
                                                        false
                                                );

                                                return 1;
                                            }

                                            HunterRank rank =
                                                    HunterRankRoller.roll(
                                                            player.getRandom()
                                                    );

                                            citizenData.recordAwakeningSuccess(rank);

                                            HunterStats hunterStats =
                                                    HunterStatCalculator.calculate(
                                                            citizenStats,
                                                            rank
                                                    );

                                            context.getSource().sendSuccess(
                                                    () -> Component.literal(
                                                            "Result: Awakened as "
                                                            + rank.getDisplayName()
                                                            +"-Rank"
                                                            +" | STR: " + hunterStats.strength()
                                                                    +" | AGI: " + hunterStats.agility()
                                                                    +" | PER: " + hunterStats.perception()
                                                                    +" | VIT: " + hunterStats.vitality()
                                                                    +" | INT: " + hunterStats.intelligence()
                                                    ),
                                                    false
                                            );

                                            return 1;

                                        } )
                        )


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