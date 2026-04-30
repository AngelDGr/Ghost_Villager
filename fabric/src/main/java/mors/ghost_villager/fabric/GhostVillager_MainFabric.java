package mors.ghost_villager.fabric;

import mors.ghost_villager.entity.GhostVillagerEntity;
import mors.ghost_villager.registry.GhostVillager_Entities;
import mors.ghost_villager.registry.GhostVillager_Items;
import net.fabricmc.api.ModInitializer;

import mors.ghost_villager.GhostVillager_Main;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

public final class GhostVillager_MainFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        GhostVillager_Main.init();

        registerAttribute();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(content ->
                content.addAfter(Items.GHAST_SPAWN_EGG, GhostVillager_Items.GHOST_VILLAGER_SPAWN_EGG.get())
        );
    }

    public static void registerAttribute() {
        FabricDefaultAttributeRegistry.register(GhostVillager_Entities.GHOST_VILLAGER.get(), GhostVillagerEntity.createAttributes());
    }

}
