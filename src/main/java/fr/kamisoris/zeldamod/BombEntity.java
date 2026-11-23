package fr.kamisoris.zeldamod;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class BombEntity extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Integer> FUSE_TICKS =
            SynchedEntityData.defineId(BombEntity.class, EntityDataSerializers.INT);

    private static final DustParticleOptions RED_SPARK =
            new DustParticleOptions(new Vector3f(1.0F, 0.15F, 0.05F), 1.15F);
    private static final DustParticleOptions ORANGE_SPARK =
            new DustParticleOptions(new Vector3f(1.0F, 0.55F, 0.05F), 1.15F);

    private int fuse = 40; // ~2 secondes (20 ticks = 1s)

    public BombEntity(EntityType<? extends BombEntity> type, Level level) {
        super(type, level);
    }

    public BombEntity(EntityType<? extends BombEntity> type, LivingEntity owner, Level level) {
        super(type, owner, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FUSE_TICKS, 40);
    }

    public int getFuseTicks() {
        return this.entityData.get(FUSE_TICKS);
    }

    @Override
    protected Item getDefaultItem() {
        return Itemtest.bomb.get();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.onGround()) {
            Vec3 mot = this.getDeltaMovement();
            this.setDeltaMovement(mot.x * 0.4D, Math.min(0.0D, mot.y), mot.z * 0.4D);
        }

        if (this.level().isClientSide) {
            spawnFlashParticles();
        } else {
            this.fuse--;
            this.entityData.set(FUSE_TICKS, this.fuse);
            if (this.fuse <= 0) {
                explodeBomb();
            }
        }
    }

    private void spawnFlashParticles() {
        boolean urgent = this.tickCount > 20;
        if (!urgent && this.tickCount % 3 != 0) {
            return;
        }

        DustParticleOptions color = (this.tickCount % 4 < 2) ? RED_SPARK : ORANGE_SPARK;
        double ox = (this.random.nextDouble() - 0.5D) * 0.45D;
        double oy = this.random.nextDouble() * 0.45D;
        double oz = (this.random.nextDouble() - 0.5D) * 0.45D;

        this.level().addParticle(color, this.getX() + ox, this.getY() + 0.15D + oy, this.getZ() + oz, 0.0D, 0.04D, 0.0D);

        if (urgent) {
            this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY() + 0.45D, this.getZ(), 0.0D, 0.03D, 0.0D);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.setDeltaMovement(Vec3.ZERO);
        this.setOnGround(true);
    }

    private void explodeBomb() {
        if (!this.level().isClientSide) {
            this.level().explode(
                    this,
                    this.getX(),
                    this.getY() + 0.1D,
                    this.getZ(),
                    3.0F,
                    Level.ExplosionInteraction.TNT
            );
            this.discard();
        }
    }
}