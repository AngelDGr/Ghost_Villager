package mors.ghost_villager.forge;

import dev.architectury.platform.forge.EventBuses;
import mors.ghost_villager.entity.GhostVillagerEntity;
import mors.ghost_villager.registry.GhostVillager_Entities;
import mors.ghost_villager.registry.GhostVillager_Items;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import mors.ghost_villager.GhostVillager_Main;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = GhostVillager_Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(GhostVillager_Main.MOD_ID)
public final class GhostVillager_MainForge {
    public GhostVillager_MainForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(GhostVillager_Main.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

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
            event.getEntries().putAfter(Items.GHAST_SPAWN_EGG.getDefaultInstance(), GhostVillager_Items.GHOST_VILLAGER_SPAWN_EGG.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
