package mors.ghost_villager.neoforge;

import mors.ghost_villager.GhostVillager_Main;
import mors.ghost_villager.client.GhostVillagerModel;
import mors.ghost_villager.client.GhostVillagerRenderer;
import mors.ghost_villager.registry.GhostVillager_Entities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = GhostVillager_Main.MOD_ID, value = Dist.CLIENT)
@Mod(value = GhostVillager_Main.MOD_ID, dist = Dist.CLIENT)
public class GhostVillager_ClientNeoForge {

    public GhostVillager_ClientNeoForge(final IEventBus modEventBus){
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GhostVillagerModel.LAYER_LOCATION, GhostVillagerModel::createLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {

        event.registerEntityRenderer(GhostVillager_Entities.GHOST_VILLAGER.get(), GhostVillagerRenderer::new);
    }
}
