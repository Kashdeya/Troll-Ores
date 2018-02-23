package com.kashdeya.trolloresreborn.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.kashdeya.trolloresreborn.handlers.ConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySmallWither extends EntityMob implements IRangedAttackMob {
	private static final DataParameter<Integer> FIRST_HEAD_TARGET = EntityDataManager.<Integer>createKey(EntitySmallWither.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> SECOND_HEAD_TARGET = EntityDataManager.<Integer>createKey(EntitySmallWither.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> THIRD_HEAD_TARGET = EntityDataManager.<Integer>createKey(EntitySmallWither.class, DataSerializers.VARINT);
	private static final DataParameter<Integer>[] HEAD_TARGETS = new DataParameter[] { FIRST_HEAD_TARGET,SECOND_HEAD_TARGET, THIRD_HEAD_TARGET };
	private static final DataParameter<Integer> INVULNERABILITY_TIME = EntityDataManager.<Integer>createKey(EntitySmallWither.class, DataSerializers.VARINT);
	private final float[] xRotationHeads = new float[2];
	private final float[] yRotationHeads = new float[2];
	private final float[] xRotOHeads = new float[2];
	private final float[] yRotOHeads = new float[2];
	private final int[] nextHeadUpdate = new int[2];
	private final int[] idleHeadUpdates = new int[2];
	private int blockBreakCounter;
	private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);

	private static final Predicate<Entity> NOT_UNDEAD = new Predicate<Entity>() {
		public boolean apply(@Nullable Entity entity) {
			return entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
		}
	};

	public EntitySmallWither(World world) {
		super(world);
		setHealth(getMaxHealth());
		setSize(1.2F, 1.5F);
		isImmuneToFire = ConfigHandler.WITHER_FIRE;
		((PathNavigateGround) getNavigator()).setCanSwim(true);
		experienceValue = ConfigHandler.WITHER_EXP_DROPS;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double) ConfigHandler.WITHER_HEALTH);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)ConfigHandler.WITHER_SPEED);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue((double) ConfigHandler.WITHER_FOLLOW_RANGE);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue((double) ConfigHandler.WITHER_ARMOUR);
	}

	@Override
	protected float getSoundPitch() {
		return 2F;
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntitySmallWither.AIDoNothing());
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackRanged(this, 1.0D, 60, 10.0F));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(3, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(FIRST_HEAD_TARGET, Integer.valueOf(0));
		dataManager.register(SECOND_HEAD_TARGET, Integer.valueOf(0));
		dataManager.register(THIRD_HEAD_TARGET, Integer.valueOf(0));
		dataManager.register(INVULNERABILITY_TIME, Integer.valueOf(0));
	}

	public static void registerFixesWither(DataFixer fixer) {
		EntityLiving.registerFixesMob(fixer, EntitySmallWither.class);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("Invul", getInvulTime());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		setInvulTime(compound.getInteger("Invul"));
        if(hasCustomName())
        	bossInfo.setName(this.getDisplayName());
	}

	@Override
    public void setCustomNameTag(String name) {
        super.setCustomNameTag(name);
        bossInfo.setName(this.getDisplayName());
    }

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_WITHER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damage) {
		return SoundEvents.ENTITY_WITHER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_WITHER_DEATH;
	}

	@Override
	public void onLivingUpdate() {
		motionY *= 0.6000000238418579D;

		if (!getEntityWorld().isRemote && getWatchedTargetId(0) > 0) {
			Entity entity = getEntityWorld().getEntityByID(getWatchedTargetId(0));

			if (entity != null) {
				if (posY < entity.posY || !isArmored() && posY < entity.posY + 1.0D) {
					if (motionY < 0.0D) {
						motionY = 0.0D;
					}

					motionY += (0.5D - motionY) * 0.6000000238418579D;
				}

				double d0 = entity.posX - posX;
				double d1 = entity.posZ - posZ;
				double d3 = d0 * d0 + d1 * d1;

				if (d3 > 9.0D) {
					double d5 = (double) MathHelper.sqrt(d3);
					motionX += (d0 / d5 * 0.5D - motionX) * 0.6000000238418579D;
					motionZ += (d1 / d5 * 0.5D - motionZ) * 0.6000000238418579D;
				}
			}
		}

		if (motionX * motionX + motionZ * motionZ > 0.05000000074505806D) {
			rotationYaw = (float) MathHelper.atan2(motionZ, motionX) * (180F / (float) Math.PI) - 90.0F;
		}

		super.onLivingUpdate();

		for (int i = 0; i < 2; ++i) {
			yRotOHeads[i] = yRotationHeads[i];
			xRotOHeads[i] = xRotationHeads[i];
		}

		for (int j = 0; j < 2; ++j) {
			int k = getWatchedTargetId(j + 1);
			Entity entity1 = null;

			if (k > 0) {
				entity1 = getEntityWorld().getEntityByID(k);
			}

			if (entity1 != null) {
				double d11 = getHeadX(j + 1);
				double d12 = getHeadY(j + 1);
				double d13 = getHeadZ(j + 1);
				double d6 = entity1.posX - d11;
				double d7 = entity1.posY + (double) entity1.getEyeHeight() - d12;
				double d8 = entity1.posZ - d13;
				double d9 = (double) MathHelper.sqrt(d6 * d6 + d8 * d8);
				float f = (float) (MathHelper.atan2(d8, d6) * (180D / Math.PI)) - 90.0F;
				float f1 = (float) (-(MathHelper.atan2(d7, d9) * (180D / Math.PI)));
				xRotationHeads[j] = rotlerp(xRotationHeads[j], f1, 40.0F);
				yRotationHeads[j] = rotlerp(yRotationHeads[j], f, 10.0F);
			} else {
				yRotationHeads[j] = rotlerp(yRotationHeads[j], renderYawOffset, 10.0F);
			}
		}

		boolean flag = isArmored();

		for (int l = 0; l < 3; ++l) {
			double d10 = getHeadX(l);
			double d2 = getHeadY(l);
			double d4 = getHeadZ(l);
			getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + rand.nextGaussian() * 0.30000001192092896D, posY + rand.nextGaussian() * 0.30000001192092896D, d4 + rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);

			if (flag && getEntityWorld().rand.nextInt(4) == 0) {
				getEntityWorld().spawnParticle(EnumParticleTypes.SPELL_MOB, d10 + rand.nextGaussian() * 0.30000001192092896D, posY + rand.nextGaussian() * 0.30000001192092896D, d4 + rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]);
			}
		}

		if (getInvulTime() > 0) {
			for (int i1 = 0; i1 < 3; ++i1) {
				getEntityWorld().spawnParticle(EnumParticleTypes.SPELL_MOB, posX + rand.nextGaussian(), posY + (double) (rand.nextFloat()), posZ + rand.nextGaussian(), 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);
			}
		}
	}

	@Override
	protected void updateAITasks() {
		if (getInvulTime() > 0) {
			int j1 = getInvulTime() - 1;

			if (j1 <= 0) {
				getEntityWorld().newExplosion(this, posX, posY + (double) getEyeHeight(), posZ, ConfigHandler.WITHER_EXPLOSION, false, getEntityWorld().getGameRules().getBoolean("mobGriefing"));
				getEntityWorld().playBroadcastSound(1023, new BlockPos(this), 0);
			}

			setInvulTime(j1);

			if (ticksExisted % 10 == 0) {
				heal(10.0F);
			}
		} else {
			super.updateAITasks();

			for (int i = 1; i < 3; ++i) {
				if (ticksExisted >= nextHeadUpdate[i - 1]) {
					nextHeadUpdate[i - 1] = ticksExisted + 10 + rand.nextInt(10);

					if (getEntityWorld().getDifficulty() == EnumDifficulty.NORMAL
							|| getEntityWorld().getDifficulty() == EnumDifficulty.HARD) {
						int j3 = i - 1;
						int k3 = idleHeadUpdates[i - 1];
						idleHeadUpdates[j3] = idleHeadUpdates[i - 1] + 1;

						if (k3 > 15) {
							float f = 10.0F;
							float f1 = 5.0F;
							double d0 = MathHelper.nextDouble(rand, posX - 10.0D, posX + 10.0D);
							double d1 = MathHelper.nextDouble(rand, posY - 5.0D, posY + 5.0D);
							double d2 = MathHelper.nextDouble(rand, posZ - 10.0D, posZ + 10.0D);
							launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
							idleHeadUpdates[i - 1] = 0;
						}
					}

					int k1 = getWatchedTargetId(i);

					if (k1 > 0) {
						Entity entity = getEntityWorld().getEntityByID(k1);

						if (entity != null && entity.isEntityAlive() && getDistanceSq(entity) <= 900.0D && canEntityBeSeen(entity)) {
							if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.disableDamage) {
								updateWatchedTargetId(i, 0);
							} else {
								launchWitherSkullToEntity(i + 1, (EntityLivingBase) entity);
								nextHeadUpdate[i - 1] = ticksExisted + 40 + rand.nextInt(20);
								idleHeadUpdates[i - 1] = 0;
							}
						} else {
							updateWatchedTargetId(i, 0);
						}
					} else {
						List<EntityLivingBase> list = getEntityWorld().<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(20.0D, 8.0D, 20.0D), Predicates.<EntityLivingBase>and(NOT_UNDEAD, EntitySelectors.NOT_SPECTATING));

						for (int j2 = 0; j2 < 10 && !list.isEmpty(); ++j2) {
							EntityLivingBase entitylivingbase = (EntityLivingBase) list.get(rand.nextInt(list.size()));

							if (entitylivingbase != this && entitylivingbase.isEntityAlive() && canEntityBeSeen(entitylivingbase)) {
								if (entitylivingbase instanceof EntityPlayer) {
									if (!((EntityPlayer) entitylivingbase).capabilities.disableDamage) {
										updateWatchedTargetId(i, entitylivingbase.getEntityId());
									}
								} else {
									updateWatchedTargetId(i, entitylivingbase.getEntityId());
								}

								break;
							}

							list.remove(entitylivingbase);
						}
					}
				}
			}

			if (getAttackTarget() != null) {
				updateWatchedTargetId(0, getAttackTarget().getEntityId());
			} else {
				updateWatchedTargetId(0, 0);
			}

			if (blockBreakCounter > 0) {
				--blockBreakCounter;

				if (blockBreakCounter == 0 && getEntityWorld().getGameRules().getBoolean("mobGriefing")) {
					int i1 = MathHelper.floor(posY);
					int l1 = MathHelper.floor(posX);
					int i2 = MathHelper.floor(posZ);
					boolean flag = false;

					for (int k2 = -1; k2 <= 1; ++k2) {
						for (int l2 = -1; l2 <= 1; ++l2) {
							for (int j = 0; j <= 3; ++j) {
								int i3 = l1 + k2;
								int k = i1 + j;
								int l = i2 + l2;
								BlockPos blockpos = new BlockPos(i3, k, l);
								IBlockState iblockstate = getEntityWorld().getBlockState(blockpos);
								Block block = iblockstate.getBlock();

								if (!block.isAir(iblockstate, getEntityWorld(), blockpos) && block.canEntityDestroy(iblockstate, getEntityWorld(), blockpos, this)) {
									flag = getEntityWorld().destroyBlock(blockpos, true) || flag;
								}
							}
						}
					}

					if (flag) {
						getEntityWorld().playEvent((EntityPlayer) null, 1022, new BlockPos(this), 0);
					}
				}
			}

			if (ticksExisted % 20 == 0) {
				heal(1.0F);
			}

			bossInfo.setPercent(getHealth() / getMaxHealth());
		}
	}

	public static boolean canDestroyBlock(Block blockIn) {
		return blockIn != Blocks.BEDROCK && blockIn != Blocks.END_PORTAL && blockIn != Blocks.END_PORTAL_FRAME && blockIn != Blocks.COMMAND_BLOCK && blockIn != Blocks.REPEATING_COMMAND_BLOCK && blockIn != Blocks.CHAIN_COMMAND_BLOCK && blockIn != Blocks.BARRIER;
	}

	public void ignite() {
		setInvulTime(220);
		setHealth(getMaxHealth() / 3.0F);
	}

	@Override
	public void setInWeb() {
	}

	@Override
	public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
		bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		bossInfo.removePlayer(player);
	}

	private double getHeadX(int p_82214_1_) {
		if (p_82214_1_ <= 0) {
			return posX;
		} else {
			float f = (renderYawOffset + (float) (180 * (p_82214_1_ - 1))) * 0.017453292F;
			float f1 = MathHelper.cos(f);
			return posX + (double) f1 * 1.3D;
		}
	}

	private double getHeadY(int p_82208_1_) {
		return p_82208_1_ <= 0 ? posY + 3.0D : posY + 2.2D;
	}

	private double getHeadZ(int p_82213_1_) {
		if (p_82213_1_ <= 0) {
			return posZ;
		} else {
			float f = (renderYawOffset + (float) (180 * (p_82213_1_ - 1))) * 0.017453292F;
			float f1 = MathHelper.sin(f);
			return posZ + (double) f1 * 1.3D;
		}
	}

	private float rotlerp(float p_82204_1_, float p_82204_2_, float p_82204_3_) {
		float f = MathHelper.wrapDegrees(p_82204_2_ - p_82204_1_);

		if (f > p_82204_3_) {
			f = p_82204_3_;
		}

		if (f < -p_82204_3_) {
			f = -p_82204_3_;
		}

		return p_82204_1_ + f;
	}

	private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase entity) {
		launchWitherSkullToCoords(p_82216_1_, entity.posX, entity.posY + (double) entity.getEyeHeight() * 0.5D, entity.posZ, p_82216_1_ == 0 && rand.nextFloat() < 0.001F);
	}

	private void launchWitherSkullToCoords(int p_82209_1_, double x, double y, double z, boolean invulnerable) {
		getEntityWorld().playEvent((EntityPlayer) null, 1024, new BlockPos(this), 0);
		double d0 = getHeadX(p_82209_1_);
		double d1 = getHeadY(p_82209_1_);
		double d2 = getHeadZ(p_82209_1_);
		double d3 = x - d0;
		double d4 = y - d1;
		double d5 = z - d2;
		EntitySmallWitherSkull entitywitherskull = new EntitySmallWitherSkull(getEntityWorld(), this, d3, d4, d5);

		if (invulnerable) {
			entitywitherskull.setInvulnerable(true);
		}

		entitywitherskull.posY = posY + getEyeHeight() * 0.5D;
		entitywitherskull.posX = posX;
		entitywitherskull.posZ = posZ;
		getEntityWorld().spawnEntity(entitywitherskull);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		launchWitherSkullToEntity(0, target);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (isEntityInvulnerable(source) || source.isExplosion()) {
			return false;
		} else if (source != DamageSource.DROWN && !(source.getTrueSource() instanceof EntitySmallWither)) {
			if (getInvulTime() > 0 && source != DamageSource.OUT_OF_WORLD) {
				return false;
			} else {
				if (isArmored()) {
					Entity entity = source.getTrueSource();

					if (entity instanceof EntityArrow) {
						return false;
					}
				}

				Entity entity1 = source.getTrueSource();

				if (entity1 != null && !(entity1 instanceof EntityPlayer) && entity1 instanceof EntityLivingBase && ((EntityLivingBase) entity1).getCreatureAttribute() == getCreatureAttribute()) {
					return false;
				} else {
					if (blockBreakCounter <= 0) {
						blockBreakCounter = 20;
					}

					for (int i = 0; i < idleHeadUpdates.length; ++i) {
						idleHeadUpdates[i] += 3;
					}

					return super.attackEntityFrom(source, amount);
				}
			}
		} else {
			return false;
		}
	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
		// No nether stars here scrub!
	}

	@Override
    protected void despawnEntity() {
		idleTime = 0;
    }

    @SideOnly(Side.CLIENT)
	@Override
    public int getBrightnessForRender() {
        return 15728880;
    }

	@Override
	public void fall(float distance, float damageMultiplier) {
	}

	@Override
	public void addPotionEffect(PotionEffect potioneffectIn) {
	}

	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);

		if (ConfigHandler.WITHER_IGNITE)
			ignite();

		if (ConfigHandler.SILENT_WITHER)
			setSilent(true);

		setCustomNameTag(ConfigHandler.WITHER_NAME[rand.nextInt(ConfigHandler.WITHER_NAME.length)]);
		getAlwaysRenderNameTag();
		playLivingSound();
		return livingdata;
	}

	@SideOnly(Side.CLIENT)
	public float getHeadYRotation(int p_82207_1_) {
		return yRotationHeads[p_82207_1_];
	}

	@SideOnly(Side.CLIENT)
	public float getHeadXRotation(int p_82210_1_) {
		return xRotationHeads[p_82210_1_];
	}

	public int getInvulTime() {
		return ((Integer) dataManager.get(INVULNERABILITY_TIME)).intValue();
	}

	public void setInvulTime(int time) {
		dataManager.set(INVULNERABILITY_TIME, Integer.valueOf(time));
	}

	public int getWatchedTargetId(int head) {
		return ((Integer) dataManager.get(HEAD_TARGETS[head])).intValue();
	}

	public void updateWatchedTargetId(int targetOffset, int newId) {
		dataManager.set(HEAD_TARGETS[targetOffset], Integer.valueOf(newId));
	}

	public boolean isArmored() {
		return getHealth() <= getMaxHealth() / 2.0F;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return false;
	}

	@Override
	public boolean isNonBoss() {
		return false;
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {
	}

	class AIDoNothing extends EntityAIBase {
		public AIDoNothing() {
			setMutexBits(7);
		}

		@Override
		public boolean shouldExecute() {
			return EntitySmallWither.this.getInvulTime() > 0;
		}
	}
}