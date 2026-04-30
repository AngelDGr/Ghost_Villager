package mors.ghost_villager.neoforge;

import mors.ghost_villager.entity.GhostVillagerEntity;
import mors.ghost_villager.registry.GhostVillager_Entities;
import mors.ghost_villager.registry.GhostVillager_Items;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import mors.ghost_villager.GhostVillager_Main;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = GhostVillager_Main.MOD_ID)
@Mod(GhostVillager_Main.MOD_ID)
public final class GhostVillager_MainNeoForge {
    public GhostVillager_MainNeoForge() {
        // Run our common setup.
        GhostVillager_Main.init();
    }

    @SubscribeEvent
    public static void registerAttribute(@NotNull EntityAttributeCreationEvent event) {
        event.put(GhostVillager_Entities.GHOST_VILLAGER.get(), GhostVillagerEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void modifyEntriesEvent(@NotNull BuildCreativeModeTabContentsEvent event) {

        if(event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS)){
            event.insertAfter(Items.GHAST_SPAWN_EGG.getDefaultInstance(), GhostVillager_Items.GHOST_VILLAGER_SPAWN_EGG.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
