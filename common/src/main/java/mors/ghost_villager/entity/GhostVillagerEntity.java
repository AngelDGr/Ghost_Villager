package mors.ghost_villager.entity;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import mors.ghost_villager.registry.GhostVillager_Items;
import mors.ghost_villager.registry.GhostVillager_Sounds;
import mors.ghost_villager.registry.GhostVillager_VillagerTrades;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GhostVillagerEntity extends AbstractVillager {

    //──────────────────────────────────── Init ────────────────────────────────────
    public GhostVillagerEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.20F).add(Attributes.FOLLOW_RANGE, 48.0F);
    }

    private static final EntityDataAccessor<Boolean> MUST_DIE = SynchedEntityData.defineId(GhostVillagerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> MUST_APPEAR = SynchedEntityData.defineId(GhostVillagerEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> VILLAGER_LEVEL = SynchedEntityData.defineId(GhostVillagerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> VILLAGER_XP = SynchedEntityData.defineId(GhostVillagerEntity.class, EntityDataSerializers.INT);

    private int updateMerchantTimer;
    private boolean increaseProfessionLevelOnUpdate;

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(MUST_DIE, false);
        builder.define(MUST_APPEAR, false);

        builder.define(VILLAGER_LEVEL, 1);
        builder.define(VILLAGER_XP, 0);
    }

    public boolean mustDie() {
        return this.entityData.get(MUST_DIE);
    }

    public void setMustDie(boolean mustDie) {
        this.entityData.set(MUST_DIE, mustDie);
    }

    public boolean mustAppear() {
        return this.entityData.get(MUST_APPEAR);
    }

    public void setMustAppear(boolean mustDie) {
        this.entityData.set(MUST_APPEAR, mustDie);
    }

    public int getVillagerXp() {
        return this.entityData.get(VILLAGER_XP);
    }

    public void setVillagerXp(int xp) {
        this.entityData.set(VILLAGER_XP, xp);
    }

    public int getVillagerLevel() {
        return this.entityData.get(VILLAGER_LEVEL);
    }

    public void setVillagerLevel(int level) {
        this.entityData.set(VILLAGER_LEVEL, level);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("MustDie", this.mustDie());
        compound.putBoolean("MustAppear", this.mustAppear());

        compound.putInt("VillagerXP", this.getVillagerXp());
        compound.putInt("VillagerLevel", this.getVillagerLevel());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setMustDie(compound.getBoolean("MustDie"));
        this.setMustAppear(compound.getBoolean("MustAppear"));

        this.setVillagerXp(compound.getInt("VillagerXP"));
        this.setVillagerLevel(compound.getInt("VillagerLevel"));
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        var finalize = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
        this.setMustAppear(true);
        //To avoid rendering on the first tick
        this.setInvisible(true);
        return finalize;
    }

    //──────────────────────────────────── AI ────────────────────────────────────
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
//        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.75));
        this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }

    @Override
    protected void doPush(Entity entity) {}

    @Override
    public boolean hurt(@NotNull final DamageSource source, final float amount) {
        if(amount>0){
            if(!this.level().isClientSide) {
                this.setMustDie(true);
            }
        }

        return super.hurt(source, amount);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        //Damages the ghost on the day
        if(this.level().isDay()){
            this.hurt(this.level().damageSources().magic(), 5);
        }

        if (!this.isTrading() && this.updateMerchantTimer > 0) {
            --this.updateMerchantTimer;
            if (this.updateMerchantTimer <= 0) {
                if (this.increaseProfessionLevelOnUpdate) {
                    this.increaseMerchantCareer();
                    this.increaseProfessionLevelOnUpdate = false;
                }

                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0));
            }
        }
    }

    @Override
    public boolean isDeadOrDying() {
        return super.isDeadOrDying() || this.mustDie();
    }

    //──────────────────────────────────── Trades ────────────────────────────────────
    @Override
    protected void rewardTradeXp(MerchantOffer merchantOffer) {
        int i = 3 + this.random.nextInt(4);
        this.setVillagerXp(this.getVillagerXp() + merchantOffer.getXp());
        if (this.shouldIncreaseLevel()) {
            this.updateMerchantTimer = 40;
            this.increaseProfessionLevelOnUpdate = true;
            i += 5;
        }

        if (merchantOffer.shouldRewardExp()) {
            this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + (double)0.5F, this.getZ(), i));
        }
    }

    private static final int[] NEXT_LEVEL_XP_THRESHOLDS = new int[]{0, 10, 70, 150, 250};
    private boolean shouldIncreaseLevel() {
        int i = this.getVillagerLevel();
        return canLevelUp(i) && this.getVillagerXp() >= getMaxXpPerLevel(i);
    }

    public static boolean canLevelUp(int i) {
        return i >= 1 && i < 5;
    }

    public static int getMinXpPerLevel(int i) {
        return canLevelUp(i) ? NEXT_LEVEL_XP_THRESHOLDS[i - 1] : 0;
    }

    public static int getMaxXpPerLevel(int i) {
        return canLevelUp(i) ? NEXT_LEVEL_XP_THRESHOLDS[i] : 0;
    }

    private void increaseMerchantCareer() {
        this.setVillagerLevel(this.getVillagerLevel() + 1);
        this.updateTrades();
    }

    @Override
    public boolean showProgressBar() {
        return true;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!itemStack.is(GhostVillager_Items.GHOST_VILLAGER_SPAWN_EGG.get()) && this.isAlive() && !this.isTrading() && !this.isBaby()) {
            if (interactionHand == InteractionHand.MAIN_HAND) {
                player.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (!this.level().isClientSide) {
                if (this.getOffers().isEmpty()) {
                    return InteractionResult.CONSUME;
                }

                this.setTradingPlayer(player);
                this.openTradingScreen(player, this.getDisplayName(), this.getVillagerLevel());
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(player, interactionHand);
        }
    }

    @Override
    protected void updateTrades() {
        Int2ObjectMap<VillagerTrades.ItemListing[]> int2ObjectMap2 = GhostVillager_VillagerTrades.GHOST_VILLAGER_TRADES;
        if (!int2ObjectMap2.isEmpty()) {
            VillagerTrades.ItemListing[] itemListings = int2ObjectMap2.get(this.getVillagerLevel());
            if (itemListings != null) {
                MerchantOffers merchantOffers = this.getOffers();
                this.addOffersFromItemListings(merchantOffers, itemListings, 2);
            }
        }
    }

    //──────────────────────────────────── Visuals ────────────────────────────────────
    public final AnimationState idleAnimationState = new AnimationState();

    public final AnimationState spawnAnimationState = new AnimationState();
    public final AnimationState dispawnAnimationState = new AnimationState();

    int tickAppearing=0;
    @Override
    public void tick() {
        //To render it above the first tick
        if(this.tickCount>0 && this.isInvisible()) this.setInvisible(false);

        if(this.mustAppear()){
            this.tickAppearing++;

            if(this.tickAppearing>15){

                this.setMustAppear(false);
            }
        }

        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    int tickDead=0;
    @Override
    protected void tickDeath() {
        if(this.mustDie()){
            tickDead++;

            if(tickDead>14){
                this.level().broadcastEntityEvent(this, DEATH_EFFECT);
                this.dead = true;
                this.remove(Entity.RemovalReason.KILLED);
                this.playSound(this.getDisappearSound(), 1.0f, 1.0f);
            }
        }
    }

    public static final byte DEATH_EFFECT = 43;
    @Override
    public void handleEntityEvent(byte status) {
        if(status == DEATH_EFFECT) {
            this.vanishParticles();
        } else {
            super.handleEntityEvent(status);
        }
    }

    private void vanishParticles(){
        for (int i = 0; i < 12; i++) {
            var x = this.getX();
            var y = this.getY() + getBoundingBox().getYsize() / 2;
            var z = this.getZ();

            double speedX = (this.random.nextDouble() - 0.5) * 0.3;
            double speedY = this.random.nextDouble() * 0.3;
            double speedZ = (this.random.nextDouble() - 0.5) * 0.3;

            // Soul particles
            this.level().addParticle(ParticleTypes.SOUL, x, y, z, speedX, speedY, speedZ);

            // Smoke
            this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z,
                    speedX * 0.5, speedY * 0.5, speedZ * 0.5);
        }

        // single flash at the end
        this.level().addParticle(ParticleTypes.FLASH,
                this.getX(),
                this.getY() + getBoundingBox().getYsize() / 2,
                this.getZ(),
                0, 0, 0);
    }

    private void setupAnimationStates() {
        this.spawnAnimationState.animateWhen(this.mustAppear() && !this.mustDie(), this.tickCount);
        this.dispawnAnimationState.animateWhen(this.mustDie(), this.tickCount);
        this.idleAnimationState.animateWhen(!spawnAnimationState.isStarted() && !dispawnAnimationState.isStarted(), this.tickCount);
    }

    //TODO: Add custom sounds
    //──────────────────────────────────── Sounds ────────────────────────────────────
    @Override
    protected SoundEvent getAmbientSound() {
        return GhostVillager_Sounds.GHOST_VILLAGER_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected @NotNull SoundEvent getTradeUpdatedSound(boolean bl) {
        return bl ? GhostVillager_Sounds.GHOST_VILLAGER_YES.get() : GhostVillager_Sounds.GHOST_VILLAGER_NO.get();
    }

    @Override
    public @NotNull SoundEvent getNotifyTradeSound() {
        return GhostVillager_Sounds.GHOST_VILLAGER_YES.get();
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {}

    public @NotNull SoundEvent getDisappearSound() {
        return GhostVillager_Sounds.GHOST_VILLAGER_POOF.get();
    }

    //──────────────────────────────────── Misc ────────────────────────────────────
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        return false;
    }
}
