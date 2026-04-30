package mors.ghost_villager.mixin;

import mors.ghost_villager.GhostVillager_Main;
import mors.ghost_villager.entity.GhostVillagerEntity;
import mors.ghost_villager.registry.GhostVillager_Entities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BellBlockEntity.class)
public class BellBlockEntityMixin {

    @Inject(method = "serverTick", at = @At("TAIL"))
    private static void ghostVillager$spawnGhosts(Level level, BlockPos blockPos, BlockState blockState, BellBlockEntity bellBlockEntity, CallbackInfo ci){
        //Tries each 5s
        if(level instanceof ServerLevel serverLevel && level.getGameTime()%100==0 &&
                level.isNight() && level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)
                //Only it can have 3 ghosts in a 36 block radius
                && level.getEntitiesOfClass(GhostVillagerEntity.class, new AABB(blockPos).inflate(36)).size()<3){

            var poiManager = serverLevel.getPoiManager();
            poiManager.getType(blockPos).ifPresent(type -> {
                if (type.is(PoiTypes.MEETING)) {
                    boolean hasUsers = poiManager.getInRange(
                            holder -> holder.is(PoiTypes.MEETING),
                            blockPos,
                            1,
                            PoiManager.Occupancy.IS_OCCUPIED
                    ).findAny().isPresent();

                                                                  //5% probability after each check
                    if (hasUsers && serverLevel.random.nextFloat()<0.05f) {
                        int x = blockPos.getX() + serverLevel.random.nextInt(24);
                        int z = blockPos.getZ() + serverLevel.random.nextInt(24);
                        int y = serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

                        BlockPos groundPos = new BlockPos(x, y - 1, z);
                        BlockPos spawnPos = new BlockPos(x, y, z);

                        if (serverLevel.hasChunkAt(spawnPos)) {
                            if (!serverLevel.getBlockState(groundPos).isAir()
                                    && serverLevel.getBlockState(spawnPos).isAir()
                                    && serverLevel.getBlockState(spawnPos.above()).isAir()) {

                                GhostVillagerEntity ghost = GhostVillager_Entities.GHOST_VILLAGER.get().create(serverLevel);

                                if (ghost != null) {
                                    ghost.moveTo(x, y, z, 0.0F, 0.0F);

                                    ghost.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(spawnPos), MobSpawnType.EVENT, null);

                                    serverLevel.addFreshEntity(ghost);
                                    serverLevel.sendParticles(ParticleTypes.FLASH,
                                            x, y, z,
                                            1,
                                            0f, 0f, 0f,
                                            0f);

                                    GhostVillager_Main.LOGGER.info("Ghost Villager has spawned on: X:{}, Y:{}, Z:{}", x, y, z);
                                }
                            }
                        }
                    }
                }
            });
        }
    }
}
