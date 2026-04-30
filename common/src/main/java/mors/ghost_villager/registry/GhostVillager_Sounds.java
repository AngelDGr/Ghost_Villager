package mors.ghost_villager.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import mors.ghost_villager.GhostVillager_Main;
import mors.ghost_villager.GhostVillager_Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class GhostVillager_Sounds {

    public static final RegistrySupplier<SoundEvent> GHOST_VILLAGER_IDLE = register("entity.ghost_villager.idle");

    public static final RegistrySupplier<SoundEvent> GHOST_VILLAGER_YES = register("entity.ghost_villager.yes");

    public static final RegistrySupplier<SoundEvent> GHOST_VILLAGER_NO = register("entity.ghost_villager.no");

    public static final RegistrySupplier<SoundEvent> GHOST_VILLAGER_POOF = register("entity.ghost_villager.poof");

    public static void init(){}

    public static RegistrySupplier<SoundEvent> register(final String name) {
        return GhostVillager_Registries.SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(GhostVillager_Main.MOD_ID, name)));
    }
}
