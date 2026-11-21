package net.stella.notsosololeveling.guild;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.entity.EntitySpawnReason;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.stella.notsosololeveling.citizen.AwakeningRoller;
import net.stella.notsosololeveling.citizen.CitizenEntity;
import net.stella.notsosololeveling.citizen.ModEntities;

public final class GuildClaims extends SavedData {
    private static final SavedData.Factory<GuildClaims> FACTORY =
            new SavedData.Factory<>(GuildClaims::new, GuildClaims::load);

    private final Map<Long, UUID> owners = new HashMap<>();

    private final Map<Long, UUID> starterCitizens = new HashMap<>();

    private GuildClaims() {
    }

    public static GuildClaims get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, "nssl_guild_claims");
    }

    public ClaimResult claim(ChunkPos chunk, UUID playerId) {
        UUID existingOwner = owners.putIfAbsent(chunk.toLong(), playerId);

        if (existingOwner == null) {
            setDirty();
            return ClaimResult.CLAIMED;
        }

        return existingOwner.equals(playerId)
                ? ClaimResult.ALREADY_YOURS
                : ClaimResult.OWNED_BY_OTHER;


    }

    public RecruitResult recruitStarterCitizen(ServerLevel level, BlockPos hubPos, UUID playerId) {
        long chunk = new ChunkPos(hubPos).toLong();

        if (!playerId.equals(owners.get(chunk))){
            return RecruitResult.NOT_OWNER;
        }

        if (starterCitizens.containsKey(chunk)) {
            return RecruitResult.ALREADY_RECRUITED;
        }

        CitizenEntity citizen = ModEntities.CITIZEN.get().create(
                level, null, hubPos.above(), EntitySpawnReason.TRIGGERED, false, false
        );

        if (citizen == null){
            return RecruitResult.SPAWN_FAILED;
        }

        citizen.setAge(0);
        if (!level.noCollision(citizen) || level.containsAnyLiquid(citizen.getBoundingBox())) {
            return RecruitResult.NO_SPACE;
        }

        citizen.setPersistenceRequired();
        AwakeningRoller.attemptAwakening(citizen.getCitizenData(), citizen.getRandom());

        if (!level.addFreshEntity(citizen)) {
            return RecruitResult.SPAWN_FAILED;
        }

        starterCitizens.put(chunk, citizen.getUUID());
        setDirty();
        return RecruitResult.RECRUITED;
    }

    public enum RecruitResult {
        RECRUITED,
        NOT_OWNER,
        ALREADY_RECRUITED,
        NO_SPACE,
        SPAWN_FAILED
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag claims = new ListTag();

        owners.forEach((chunk, owner) -> {
            CompoundTag entry = new CompoundTag();
            entry.putLong("chunk", chunk);
            entry.putUUID("owner", owner);
            UUID starterCitizen = starterCitizens.get(chunk);
            if (starterCitizen!= null) {
                entry.putUUID("starter_citizen", starterCitizen);
            }
            claims.add(entry);
        });

        tag.put("claims", claims);
        return tag;
    }

    private static GuildClaims load(CompoundTag tag, HolderLookup.Provider registries) {
        GuildClaims data = new GuildClaims();

        for (Tag savedClaim : tag.getList("claims", Tag.TAG_COMPOUND)) {
            CompoundTag entry = (CompoundTag) savedClaim;

            if (entry.contains("chunk", Tag.TAG_LONG) && entry.hasUUID("owner")) {
                long chunk = entry.getLong("chunk");
                data.owners.put(chunk, entry.getUUID("owner"));

                if (entry.hasUUID("starter_citizen")) {
                    data.starterCitizens.put(chunk, entry.getUUID("starter_citizen"));
                }
            }
        }

        return data;
    }

    public enum ClaimResult {
        CLAIMED,
        ALREADY_YOURS,
        OWNED_BY_OTHER
    }
}
