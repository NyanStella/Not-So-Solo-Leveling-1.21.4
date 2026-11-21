package net.stella.notsosololeveling.citizen;

import net.minecraft.world.entity.EntityType;
import  net.minecraft.world.entity.Mob;
import  net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.stella.notsosololeveling.NotSoSoloLeveling;
import net.stella.notsosololeveling.guild.JinwooEntity;

public final class ModEntities {
    public static final DeferredRegister.Entities ENTITY_TYPES =
            DeferredRegister.createEntities(NotSoSoloLeveling.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<CitizenEntity>> CITIZEN =
            ENTITY_TYPES.registerEntityType(
                    "citizen",
                    CitizenEntity::new,
                    MobCategory.MISC,
                    builder -> builder.sized(0.6F, 1.95F)
            );

    public static final DeferredHolder<EntityType<?>, EntityType<JinwooEntity>> JINWOO =
            ENTITY_TYPES.registerEntityType(
                    "jinwoo",
                    JinwooEntity::new,
                    MobCategory.MISC,
                    builder -> builder.sized(0.6F, 1.8F)
            );

    private ModEntities(){}

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(CITIZEN.get(), Mob.createMobAttributes().build());
        event.put(JINWOO.get(), Mob.createMobAttributes().build());
    }
}
