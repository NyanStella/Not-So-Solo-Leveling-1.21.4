package net.stella.notsosololeveling.citizen;

import net.stella.notsosololeveling.hunter.HunterRank;

import java.util.Objects;
import java.util.Optional;

public final class CitizenData {

    private final CitizenStats baseStats;

    private boolean awakeningAttempted;
    private HunterRank hunterRank;

    public CitizenData(CitizenStats baseStats){

        this.baseStats =
                Objects.requireNonNull(baseStats);

        this.awakeningAttempted = false;
        this.hunterRank = null;
    }

    public  CitizenStats getBaseStats(){

        return baseStats;
    }

    public boolean hasAttemptedAwakening(){

        return awakeningAttempted;
    }

    public boolean isHunter(){

        return hunterRank != null;
    }

    public Optional<HunterRank> getHunterRank(){

        return Optional.ofNullable(hunterRank);
    }

    public void recordAwakeningSuccess(
            HunterRank hunterRank){
        ensureAwakeningNotAttempted();

        this.hunterRank =
                Objects.requireNonNull(
                        hunterRank,
                        "An awakened citizen must have a Hunter Rank"
                );

        this.awakeningAttempted = true;
    }


    public void recordAwakeningFailure(){

        ensureAwakeningNotAttempted();

        this.hunterRank = null;
        this.awakeningAttempted = true;
    }


    private void ensureAwakeningNotAttempted(){
        if(awakeningAttempted){
            throw new IllegalStateException(
                    "This citizen has already attempted awakening."
            );
        }
    }

}
