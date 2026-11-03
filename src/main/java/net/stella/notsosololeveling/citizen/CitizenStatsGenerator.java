package net.stella.notsosololeveling.citizen;

import net.minecraft.util.RandomSource;

public final class CitizenStatsGenerator {

    private CitizenStatsGenerator(){}

    public static CitizenStats generate(RandomSource random){

        return new CitizenStats(
                rollStat(random),
                rollStat(random),
                rollStat(random),
                rollStat(random),
                rollStat(random)
        );
    }

    private static int rollStat(RandomSource random){

        return 3
                + random.nextInt(6)
                + random.nextInt(6)
                + random.nextInt(6);
    }
}
