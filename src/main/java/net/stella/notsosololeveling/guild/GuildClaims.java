package net.stella.notsosololeveling.guild;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class GuildClaims extends SavedData {
    private static final SavedData.Factory<GuildClaims> FACTORY =
            new SavedData.Factory<>(GuildClaims::new, GuildClaims::load);

    private final Map<Long, UUID> owners = new HashMap<>();

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

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag claims = new ListTag();

        owners.forEach((chunk, owner) -> {
            CompoundTag entry = new CompoundTag();
            entry.putLong("chunk", chunk);
            entry.putUUID("owner", owner);
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
                data.owners.put(entry.getLong("chunk"), entry.getUUID("owner"));
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
