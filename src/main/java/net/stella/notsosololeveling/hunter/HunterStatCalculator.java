package net.stella.notsosololeveling.hunter;

import net.stella.notsosololeveling.citizen.CitizenStats;

public class HunterStatCalculator {

    private HunterStatCalculator(){

    }

    public static HunterStats calculate(
            CitizenStats citizenstats,
            HunterRank rank
    ){

        double multiplier = rank.getStatMultiplier();

        return new HunterStats(
                citizenstats.strength() * multiplier,
                citizenstats.agility() * multiplier,
                citizenstats.perception() * multiplier,
                citizenstats.vitality() * multiplier,
                citizenstats.intelligence() * multiplier
        );
    }
}
