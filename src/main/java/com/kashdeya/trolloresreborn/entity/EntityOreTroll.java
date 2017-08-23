package com.kashdeya.trolloresreborn.entity;

import javax.annotation.Nullable;

import com.kashdeya.trolloresreborn.handlers.ConfigHandler;
import com.kashdeya.trolloresreborn.init.TrollOresReborn;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityOreTroll extends EntityMob implements IEntityAdditionalSpawnData {
	private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityOreTroll.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> EFFECT = EntityDataManager.<Byte>createKey(EntityOreTroll.class, DataSerializers.BYTE);
	public final byte[] POTION_IDS = new byte[] { 2, 4, 9, 15, 17, 18, 19, 20, 25 };

	public EntityOreTroll(World world) {
		super(world);
		setSize(0.9F, 0.9F);
		isImmuneToFire = true;
		experienceValue = 10;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(CLIMBING, Byte.valueOf((byte) 0));
		dataManager.register(EFFECT, Byte.valueOf((byte) 2));
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAITrollLeap(this, 0.5F));
		tasks.addTask(2, new EntityOreTroll.AIMonsterAttack(this));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(4, new EntityAIWander(this, 1.0D));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true, new Class[0]));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ConfigHandler.TROLL_ATTACK_DAMAGE);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.TROLL_HEALTH);
	}

	@Override
	protected PathNavigate getNewNavigator(World worldIn) {
		return new PathNavigateClimber(this, worldIn);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!worldObj.isRemote)
			setBesideClimbableBlock(isCollidedHorizontally);

	}

	@Override
	public boolean isOnLadder() {
		return isBesideClimbableBlock();
	}

	public boolean isBesideClimbableBlock() {
		return (((Byte) dataManager.get(CLIMBING)).byteValue() & 1) != 0;
	}

	public void setBesideClimbableBlock(boolean climbing) {
		byte climingState = ((Byte) dataManager.get(CLIMBING)).byteValue();
		if (climbing)
			climingState = (byte) (climingState | 1);
		else
			climingState = (byte) (climingState & -2);
		dataManager.set(CLIMBING, Byte.valueOf(climingState));
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if (entity instanceof EntityLivingBase) {
				if (ConfigHandler.TROLL_EFFECTS) {
					int duration = ConfigHandler.TROLL_EFFECTS_DURATION;
					if (duration > 0)
						((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.getPotionById(POTION_IDS[getPotionEffect()]), duration * 20, 0));
				}
			}
			return true;
		} else
			return false;
	}

	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);

		if (ConfigHandler.TROLL_SPRINTING) {
			setSprinting(true);
			isSprinting();
		}

		if (ConfigHandler.SILENT_TROLL)
			setSilent(true);

		if (ConfigHandler.TROLL_EFFECTS)
			setPotionEffect(Byte.valueOf((byte) rand.nextInt(9)));

		setCustomNameTag(ConfigHandler.TROLL_NAME);
		getAlwaysRenderNameTag();
		playLivingSound();
		return livingdata;
	}

	public byte getPotionEffect() {
		return ((Byte) dataManager.get(EFFECT)).byteValue();
	}

	public void setPotionEffect(byte type) {
		dataManager.set(EFFECT, Byte.valueOf(type));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("effect", getPotionEffect());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setPotionEffect(nbt.getByte("effect"));
	}

	@Override
	public void writeSpawnData(ByteBuf data) {
		data.writeByte(getPotionEffect());
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		setPotionEffect(data.readByte());
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TrollOresReborn.TROLL_LIVING;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return TrollOresReborn.TROLL_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TrollOresReborn.TROLL_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
		playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
	}

	static class AIMonsterAttack extends EntityAIAttackMelee {

		public AIMonsterAttack(EntityOreTroll troll) {
			super(troll, 1D, false);
		}

		@Override
		protected double getAttackReachSqr(EntityLivingBase attackTarget) {
			return (double) (2.0F + attackTarget.width);
		}
	}

}