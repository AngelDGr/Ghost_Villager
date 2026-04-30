package mors.ghost_villager.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import mors.ghost_villager.GhostVillager_Main;
import mors.ghost_villager.client.GhostVillagerModel;
import mors.ghost_villager.client.GhostVillagerRenderer;
import mors.ghost_villager.entity.GhostVillagerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GhostLevelLayer<T extends GhostVillagerEntity, M extends GhostVillagerModel<T>> extends RenderLayer<T, M> {
    public GhostLevelLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, @NotNull T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (livingEntity.isInvisible()) return;

        RenderType badgeRenderType = switch (livingEntity.getVillagerLevel()) {
            case 2 -> GhostVillagerRenderer.GHOST_VILLAGER_RENDERER.apply(ResourceLocation.fromNamespaceAndPath(GhostVillager_Main.MOD_ID,
                    "textures/entity/ghost_villager/profession_level/iron.png"));
            case 3 -> GhostVillagerRenderer.GHOST_VILLAGER_RENDERER.apply(ResourceLocation.fromNamespaceAndPath(GhostVillager_Main.MOD_ID,
                    "textures/entity/ghost_villager/profession_level/gold.png"));
            case 4 -> GhostVillagerRenderer.GHOST_VILLAGER_RENDERER.apply(ResourceLocation.fromNamespaceAndPath(GhostVillager_Main.MOD_ID,
                    "textures/entity/ghost_villager/profession_level/emerald.png"));
            case 5 -> GhostVillagerRenderer.GHOST_VILLAGER_RENDERER.apply(ResourceLocation.fromNamespaceAndPath(GhostVillager_Main.MOD_ID,
                    "textures/entity/ghost_villager/profession_level/diamond.png"));
            default -> GhostVillagerRenderer.GHOST_VILLAGER_RENDERER.apply(ResourceLocation.fromNamespaceAndPath(GhostVillager_Main.MOD_ID,
                    "textures/entity/ghost_villager/profession_level/stone.png"));
        };

        this.getParentModel().renderToBuffer(poseStack, bufferSource.getBuffer(badgeRenderType), packedLight, OverlayTexture.NO_OVERLAY);
    }
}
