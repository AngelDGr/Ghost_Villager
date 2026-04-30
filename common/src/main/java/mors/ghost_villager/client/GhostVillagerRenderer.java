package mors.ghost_villager.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import mors.ghost_villager.GhostVillager_Main;
import mors.ghost_villager.client.layer.GhostLevelLayer;
import mors.ghost_villager.entity.GhostVillagerEntity;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class GhostVillagerRenderer extends MobRenderer<GhostVillagerEntity, GhostVillagerModel<GhostVillagerEntity>> {
    public GhostVillagerRenderer(EntityRendererProvider.Context context) {
        super(context, new GhostVillagerModel<>(context.bakeLayer(GhostVillagerModel.LAYER_LOCATION)), 0.0f);
        this.addLayer(new GhostLevelLayer<>(this));
    }

    public static final Function<ResourceLocation, RenderType> GHOST_VILLAGER_RENDERER = Util.memoize(
            resourceLocation -> {
                RenderType.CompositeState state = RenderType.CompositeState.builder()
                        .setShaderState(RenderType.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, true))
                        .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
                        .setCullState(RenderType.CULL)
                        .setLightmapState(RenderType.LIGHTMAP)
                        .setOverlayState(RenderType.OVERLAY)
                        .createCompositeState(true);

                return RenderType.create(
                        "ghost_villager_renderer",
                        DefaultVertexFormat.NEW_ENTITY,
                        VertexFormat.Mode.QUADS,
                        1536,
                        true,
                        false,
                        state
                );
            }
    );

    @Override
    protected @Nullable RenderType getRenderType(GhostVillagerEntity livingEntity, boolean bl, boolean bl2, boolean bl3) {
        return GHOST_VILLAGER_RENDERER.apply(this.getTextureLocation(livingEntity));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(GhostVillagerEntity entity) {
        return new ResourceLocation(GhostVillager_Main.MOD_ID, "textures/entity/ghost_villager/ghost_villager.png");
    }
}