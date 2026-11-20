package net.stella.notsosololeveling.citizen;

import net.minecraft.util.RandomSource;
import net.stella.notsosololeveling.hunter.HunterRankRoller;

public final class AwakeningRoller {

    private static final double MINIMUM_AVERAGE = 3.0;
    private static final double MAXIMUM_AVERAGE = 18.0;

    private static final double MINIMUM_CHANCE = 0.05;
    private static final double MAXIMUM_CHANCE = 0.40;

    private AwakeningRoller(){}

    public static boolean attemptAwakening(CitizenData citizen, RandomSource random){
        if (citizen.hasAttemptedAwakening()){
            return false;
        }

        if (roll(citizen.getBaseStats(), random)){
            citizen.recordAwakeningSuccess(HunterRankRoller.roll(random));
        } else{
            citizen.recordAwakeningFailure();
        }

        return true;
    }

    public static boolean roll(
            CitizenStats citizenStats,
            RandomSource random) {

        double awakeningChance =
                calculateChance(citizenStats);

        return random.nextDouble() < awakeningChance;
    }

    public static double calculateChance(
            CitizenStats citizenStats){
        double normalizedAverage =
                (citizenStats.average() - MINIMUM_AVERAGE)
                / (MAXIMUM_AVERAGE - MINIMUM_AVERAGE);

        normalizedAverage = Math.max(
                0.0,
                Math.min(1.0, normalizedAverage)
        );

        return MINIMUM_CHANCE
                + normalizedAverage
                * (MAXIMUM_CHANCE - MINIMUM_CHANCE);
    }

}
