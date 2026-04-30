package mors.ghost_villager.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import mors.ghost_villager.GhostVillager_Registries;
import mors.ghost_villager.entity.GhostVillagerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class GhostVillager_Entities {

    public static final RegistrySupplier<EntityType<GhostVillagerEntity>> GHOST_VILLAGER = GhostVillager_Registries.ENTITY_TYPES.register(
            "ghost_villager",
            ()-> EntityType.Builder.of(GhostVillagerEntity::new, MobCategory.MISC)
                    //Hitbox
                    .sized(0.6F, 2.45F)
                    .eyeHeight(2.00F)
                    .clientTrackingRange(10)
                    .build("ghost_villager"));

    public static void init(){}
}
