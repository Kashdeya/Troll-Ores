package com.kashdeya.trolloresreborn.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySmallWitherSkull extends EntityWitherSkull {

	public EntitySmallWitherSkull(World worldIn) {
		super(worldIn);
	}

    public EntitySmallWitherSkull(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.setSize(0.3125F, 0.3125F);
    }

	protected void onImpact(RayTraceResult result) {
		if (!this.world.isRemote) {
			if (result.entityHit != null && !(result.entityHit instanceof EntitySmallWither)) {
				if (this.shootingEntity != null) {
					if (result.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0F)) {
						if (result.entityHit.isEntityAlive()) {
							this.applyEnchantments(this.shootingEntity, result.entityHit);
						} else {
							this.shootingEntity.heal(5.0F);
						}
					}
				} else {
					result.entityHit.attackEntityFrom(DamageSource.MAGIC, 5.0F);
				}

				if (result.entityHit instanceof EntityLivingBase && !(result.entityHit instanceof EntitySmallWither)) {
					int i = 0;

					if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
						i = 10;
					} else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
						i = 40;
					}

					if (i > 0) {
						((EntityLivingBase) result.entityHit) .addPotionEffect(new PotionEffect(MobEffects.WITHER, 20 * i, 1));
					}
				}
			}
			this.world.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, false, this.world.getGameRules().getBoolean("mobGriefing"));
			this.setDead();
		}
	}
}