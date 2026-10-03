package net.stella.notsosololeveling.hunter;

import net.minecraft.util.RandomSource;

public class HunterRankRoller {

    private HunterRankRoller(){

    }

    public static HunterRank roll(RandomSource random)
    {

        int totalWeight = 0;

        for (HunterRank rank : HunterRank.values()){
            totalWeight += rank.getRollWeight();
        }

        int roll = random.nextInt(totalWeight);
        int cumulativeWeight = 0;

        for (HunterRank rank : HunterRank.values()) {

            cumulativeWeight += rank.getRollWeight();

            if (roll < cumulativeWeight) {

                return rank;
            }
        }

        throw new IllegalStateException("Failed to select a Hunter Rank. "
                +"Roll: " + roll
                + ", total weight: " +totalWeight
        );
    }
}
