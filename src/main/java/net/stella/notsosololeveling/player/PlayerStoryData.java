package net.stella.notsosololeveling.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public final class PlayerStoryData {
    private static final String INTRO_COMPLETE =
            "notsosololeveling_intro_complete";

    private PlayerStoryData(){

    }

    public static boolean hasCompletedIntro(Player player){
        CompoundTag storyData = getStoryData(player);

        return storyData.getBoolean(INTRO_COMPLETE);
    }

    public static void setIntroComplete(
            Player player,
            boolean complete) {
        CompoundTag persistentData = player.getPersistentData();

        CompoundTag storyData = persistentData.getCompound(
                Player.PERSISTED_NBT_TAG
        );

        storyData.putBoolean(
                INTRO_COMPLETE,
                complete
        );

        persistentData.put(
                Player.PERSISTED_NBT_TAG,
                storyData
        );
    }

    private static CompoundTag getStoryData(Player player) {
        CompoundTag persistentData =
                player.getPersistentData();

        return persistentData.getCompound(
                Player.PERSISTED_NBT_TAG
        );
    }
}
