package mors.ghost_villager.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mors.ghost_villager.GhostVillager_Main;
import mors.ghost_villager.entity.GhostVillagerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("FieldCanBeLocal, unused")
@Environment(EnvType.CLIENT)
public class GhostVillagerModel<T extends GhostVillagerEntity> extends AgeableHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GhostVillager_Main.MOD_ID, "ghost_villager"), "main");
    private final ModelPart root;
    private final ModelPart ghost_villager;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart arms;
    private final ModelPart mirrored;
    private final ModelPart tentacle_right;
    private final ModelPart tentacle_right_tip;
    private final ModelPart tentacle_left;
    private final ModelPart tentacle_left_tip;
    private final ModelPart tentacle_back;
    private final ModelPart tentacle_back_tip;
    private final ModelPart tentacle_front;
    private final ModelPart tentacle_front_tip;

    public GhostVillagerModel(ModelPart root) {
        super(1f, 0.f);
        this.root=root;
        this.ghost_villager = root.getChild("ghost_villager");
        this.body = this.ghost_villager.getChild("body");
        this.head = this.body.getChild("head");
        this.nose = this.head.getChild("nose");
        this.arms = this.body.getChild("arms");
        this.mirrored = this.arms.getChild("mirrored");
        this.tentacle_right = this.body.getChild("tentacle_right");
        this.tentacle_right_tip = this.tentacle_right.getChild("tentacle_right_tip");
        this.tentacle_left = this.body.getChild("tentacle_left");
        this.tentacle_left_tip = this.tentacle_left.getChild("tentacle_left_tip");
        this.tentacle_back = this.body.getChild("tentacle_back");
        this.tentacle_back_tip = this.tentacle_back.getChild("tentacle_back_tip");
        this.tentacle_front = this.body.getChild("tentacle_front");
        this.tentacle_front_tip = this.tentacle_front.getChild("tentacle_front_tip");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ghost_villager = partdefinition.addOrReplaceChild("ghost_villager", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = ghost_villager.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, -10.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 45).addBox(-4.0F, -10.0F, -3.0F, 8.0F, 13.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -11.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 0.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -4.0F));

        PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.05F, -1.05F, -0.7505F, 0.0F, 0.0F));

        PartDefinition mirrored = arms.addOrReplaceChild("mirrored", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -23.05F, -3.05F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 21.05F, 1.05F));

        PartDefinition tentacle_right = body.addOrReplaceChild("tentacle_right", CubeListBuilder.create(), PartPose.offset(-3.5F, 3.5F, 0.0F));

        PartDefinition cube_r1 = tentacle_right.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(-8, 26).mirror().addBox(-3.5F, 0.0F, -8.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition tentacle_right_tip = tentacle_right.addOrReplaceChild("tentacle_right_tip", CubeListBuilder.create(), PartPose.offset(-8.0F, 0.0F, 0.0F));

        PartDefinition cube_r2 = tentacle_right_tip.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(-7, 34).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition tentacle_left = body.addOrReplaceChild("tentacle_left", CubeListBuilder.create(), PartPose.offset(3.5F, 3.5F, 0.0F));

        PartDefinition cube_r3 = tentacle_left.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(-8, 26).addBox(-3.5F, 0.0F, -8.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition tentacle_left_tip = tentacle_left.addOrReplaceChild("tentacle_left_tip", CubeListBuilder.create(), PartPose.offset(8.0F, 0.0F, 0.0F));

        PartDefinition cube_r4 = tentacle_left_tip.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(-7, 34).mirror().addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition tentacle_back = body.addOrReplaceChild("tentacle_back", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 3.0F));

        PartDefinition cube_r5 = tentacle_back.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(-8, 26).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition tentacle_back_tip = tentacle_back.addOrReplaceChild("tentacle_back_tip", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 8.0F));

        PartDefinition cube_r6 = tentacle_back_tip.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(-7, 34).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition tentacle_front = body.addOrReplaceChild("tentacle_front", CubeListBuilder.create().texOffs(-8, 26).addBox(-3.5F, 0.0F, -8.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.5F, -3.0F));

        PartDefinition tentacle_front_tip = tentacle_front.addOrReplaceChild("tentacle_front_tip", CubeListBuilder.create().texOffs(-7, 34).addBox(-2.5F, 0.0F, -8.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -8.0F));

        return LayerDefinition.create(meshdefinition, 80, 80);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, int k) {
        int alpha = (int)(0.55f * 255.0f);
        int rgb = 0xededed; // dimmer than white
        int color = (alpha << 24) | rgb;
        super.renderToBuffer(poseStack, vertexConsumer, i, j, color);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.ghost_villager.visible=!entity.isInvisible();

        this.root().getAllParts().forEach(ModelPart::resetPose);

        //──────────────────────────────────── Head Rotations ────────────────────────────────────
        this.head.xRot = Mth.clamp(headPitch, -entity.getMaxHeadXRot(), entity.getMaxHeadXRot()) * (float) (Math.PI / 180.0);
        this.head.yRot = Mth.clamp(netHeadYaw, -entity.getMaxHeadYRot(), entity.getMaxHeadYRot()) * (float) (Math.PI / 180.0);

        //──────────────────────────────────── Idle ────────────────────────────────────
        float speed = (float) entity.getDeltaMovement().length();
        speed = Mth.clamp(speed, 0.0f, 1.0f);
        float animSpeed = (float) Mth.clamp((speed * 15), 0.6, 2.0f);

        this.animate(entity.idleAnimationState, GhostVillagerAnimations.IDLE, ageInTicks, animSpeed);

        //──────────────────────────────────── Movement ────────────────────────────────────
        if(!entity.mustAppear() && !entity.mustDie()) {
            this.animateWalk(GhostVillagerAnimations.WALK, limbSwing, limbSwingAmount, animSpeed, 2.5f);
        }

        //──────────────────────────────────── Disappear ────────────────────────────────────
        this.animate(entity.dispawnAnimationState, GhostVillagerAnimations.DISAPPEAR, ageInTicks, animSpeed);

        //──────────────────────────────────── Appear ────────────────────────────────────
        this.animate(entity.spawnAnimationState, GhostVillagerAnimations.APPEAR, ageInTicks, animSpeed);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}