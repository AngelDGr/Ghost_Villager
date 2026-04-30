package mors.ghost_villager.fabric;

import mors.ghost_villager.client.GhostVillagerModel;
import mors.ghost_villager.client.GhostVillagerRenderer;
import mors.ghost_villager.registry.GhostVillager_Entities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public final class GhostVillager_ClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(GhostVillager_Entities.GHOST_VILLAGER.get(), GhostVillagerRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(GhostVillagerModel.LAYER_LOCATION, GhostVillagerModel::createLayer);
    }
}
