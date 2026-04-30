package mors.ghost_villager.forge;

import mors.ghost_villager.GhostVillager_Main;
import mors.ghost_villager.client.GhostVillagerModel;
import mors.ghost_villager.client.GhostVillagerRenderer;
import mors.ghost_villager.registry.GhostVillager_Entities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GhostVillager_Main.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GhostVillager_ClientForge {

    public GhostVillager_ClientForge(final IEventBus modEventBus){
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
