package net.stella.notsosololeveling.citizen;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.units.qual.C;

public class CitizenEntity extends PathfinderMob{
    private static final String CITIZEN_DATA = "citizen_data";

    private CitizenData citizenData;
    public CitizenEntity(EntityType<? extends CitizenEntity> type, Level level){
        super(type, level);
    }

    public CitizenData getCitizenData(){
        if (citizenData == null){
            citizenData = new CitizenData(CitizenStatsGenerator.generate(getRandom()));
        }

        return citizenData;
    }


    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put(CITIZEN_DATA, CitizenDataSerializer.save(getCitizenData()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag){
        super.readAdditionalSaveData(tag);
        if (tag.contains(CITIZEN_DATA, tag.TAG_COMPOUND)){
            citizenData = CitizenDataSerializer.load(tag.getCompound(CITIZEN_DATA));
        }
    }
}
