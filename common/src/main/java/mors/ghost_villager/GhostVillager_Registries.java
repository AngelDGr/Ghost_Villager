package mors.ghost_villager;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class GhostVillager_Registries {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(GhostVillager_Main.MOD_ID, Registries.ENTITY_TYPE);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(GhostVillager_Main.MOD_ID, Registries.ITEM);

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(GhostVillager_Main.MOD_ID, Registries.SOUND_EVENT);
}
