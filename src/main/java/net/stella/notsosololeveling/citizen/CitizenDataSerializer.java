package net.stella.notsosololeveling.citizen;

import net.minecraft.nbt.CompoundTag;
import net.stella.notsosololeveling.hunter.HunterRank;

public final class CitizenDataSerializer {

    private static final String BASE_STATS = "base_stats";
    private static final String STRENGTH = "strength";
    private static final String AGILITY = "agility";
    private static final String PERCEPTION = "perception";
    private static final String VITALITY = "vitality";
    private static final String INTELLIGENCE = "intelligence";
    private static final String AWAKENING_ATTEMPTED = "awakening_attempted";
    private static final String HUNTER_RANK = "hunter_rank";

    private CitizenDataSerializer() {}

    public static CompoundTag save(CitizenData citizen) {
        CitizenStats stats = citizen.getBaseStats();

        CompoundTag statsTag = new CompoundTag();
        statsTag.putInt(STRENGTH, stats.strength());
        statsTag.putInt(AGILITY, stats.agility());
        statsTag.putInt(PERCEPTION, stats.perception());
        statsTag.putInt(VITALITY, stats.vitality());
        statsTag.putInt(INTELLIGENCE, stats.intelligence());

        CompoundTag tag = new CompoundTag();
        tag.put(BASE_STATS, statsTag);
        tag.putBoolean(AWAKENING_ATTEMPTED, citizen.hasAttemptedAwakening());
        citizen.getHunterRank().ifPresent(rank -> tag.putString(HUNTER_RANK, rank.name()));
        return tag;
    }

    public static CitizenData load(CompoundTag tag){
        CompoundTag statsTag = tag.getCompound(BASE_STATS);

        CitizenStats stats = new CitizenStats(
          statsTag.getInt(STRENGTH),
          statsTag.getInt(AGILITY),
          statsTag.getInt(PERCEPTION),
          statsTag.getInt(VITALITY),
          statsTag.getInt(INTELLIGENCE)
        );

        CitizenData citizen = new CitizenData(stats);

        if (tag.contains(HUNTER_RANK)){
            HunterRank rank = HunterRank.valueOf(tag.getString(HUNTER_RANK));
            citizen.recordAwakeningSuccess(rank);}
        else if (tag.getBoolean(AWAKENING_ATTEMPTED)){
            citizen.recordAwakeningFailure();
        }

        return citizen;
    }
}
