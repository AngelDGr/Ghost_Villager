package mors.ghost_villager.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import mors.ghost_villager.GhostVillager_Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class GhostVillager_Items {

    public static RegistrySupplier<Item> GHOST_VILLAGER_SPAWN_EGG;

    public static void init(){
        GHOST_VILLAGER_SPAWN_EGG = registerItem("ghost_villager_spawn_egg",
                ()->new SpawnEggItem(GhostVillager_Entities.GHOST_VILLAGER.get(), 0xbfc9c7, 0x5c6b66, new Item.Properties()));
    }

    public static RegistrySupplier<Item> registerItem(final String name, final Supplier<Item> item) {
        return GhostVillager_Registries.ITEMS.register(name, item);
    }
}
