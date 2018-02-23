package com.kashdeya.trolloresreborn.entity;

import javax.annotation.Nullable;

import com.kashdeya.trolloresreborn.handlers.ConfigHandler;
import com.kashdeya.trolloresreborn.init.ModSounds;

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
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityOreTroll extends EntityMob implements IEntityAdditionalSpawnData {
	private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityOreTroll.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> EFFECT = EntityDataManager.<Byte>createKey(EntityOreTroll.class, DataSerializers.BYTE);
	public final byte[] POTION_IDS = new byte[] { 2, 4, 9, 15, 17, 19, 20, 25, 27 };//2=Slowness, 4=Mining Fatigue, 9=Nausea, 15=Blindness, 17=Hunger, 19=Posion, 20=Wither, 25=Levitation, 27=Bad Luck 
	public NonNullList<ItemStack> inventory;
	public EntityOreTroll(World world) {
		super(world);
		setSize(0.9F, 0.9F);
		experienceValue = ConfigHandler.TROLL_EXP_DROPS;
		inventory = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
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
		tasks.addTask(1, new EntityAITrollLeap(this, ConfigHandler.TROLL_LEAP_HEIGHT));
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
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)ConfigHandler.TROLL_SPEED);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue((double)ConfigHandler.TROLL_FOLLOW_RANGE);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.TROLL_HEALTH);
	}

	@Override
	protected PathNavigate createNavigator(World worldIn) {
		return ConfigHandler.TROLL_CLIMBING_AI ? new PathNavigateClimber(this, worldIn) : super.createNavigator(worldIn);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!getEntityWorld().isRemote)
			setBesideClimbableBlock(collidedHorizontally);
	}

	@Override
	protected boolean canDespawn() {
		if(!getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty())
			return false;
		else
			return true;
	}

	@Override
	protected void dropFewItems(boolean recentlyHit, int looting) {
		for (ItemStack is : inventory)
			if (!is.isEmpty())
				InventoryHelper.spawnItemStack(getEntityWorld(), (int) posX, (int) posY, (int) posZ, is);
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
		dataManager.set(CLIMBING, ConfigHandler.TROLL_CLIMBING_AI ? Byte.valueOf(climingState) : 0);
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
	public boolean attackEntityFrom(DamageSource source, float damage) {

		if (source instanceof EntityDamageSourceIndirect && ConfigHandler.TROLL_IMMUNE_TO_PROJECTILE_DAMAGE) {
			getEntityWorld().playSound((EntityPlayer) null, posX, posY, posZ, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundCategory.HOSTILE, 2.5F, 3F);
			return false;
		}

		else if ((source == DamageSource.ON_FIRE || source == DamageSource.IN_FIRE || source == DamageSource.LAVA || source == DamageSource.HOT_FLOOR) && ConfigHandler.TROLL_IMMUNE_TO_FIRE_DAMAGE)
			return false;

		else if (source == DamageSource.IN_WALL && ConfigHandler.TROLL_IMMUNE_TO_SUFFOCATION_DAMAGE)
			return false;

		else if (source == DamageSource.FALL && ConfigHandler.TROLL_IMMUNE_TO_FALL_DAMAGE)
			return false;

		else if (source == DamageSource.FALLING_BLOCK && ConfigHandler.TROLL_IMMUNE_TO_FALLING_BLOCK_DAMAGE)
			return false;

		else if (source == DamageSource.CACTUS && ConfigHandler.TROLL_IMMUNE_TO_CACTUS_DAMAGE)
			return false;
		
		else if (source == DamageSource.DROWN && ConfigHandler.TROLL_IMMUNE_TO_DROWN_DAMAGE)
			return false;

		else if(!(source.getTrueSource() instanceof EntityPlayer) && ConfigHandler.TROLL_IMMUNE_TO_NON_PLAYER_DAMAGE)
			return false;

		return super.attackEntityFrom(source, damage);
	}

	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);

		if (ConfigHandler.TROLL_EXPLOSION)
			spawnExplosionParticle();

		if (ConfigHandler.TROLL_SPRINTING) {
			setSprinting(true);
			isSprinting();
		}

		if (ConfigHandler.SILENT_TROLL)
			setSilent(true);

		if (ConfigHandler.TROLL_EFFECTS)
			setPotionEffect(Byte.valueOf((byte) rand.nextInt(9)));

		setCustomNameTag(ConfigHandler.TROLL_NAME[rand.nextInt(ConfigHandler.TROLL_NAME.length)]);
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

	public EntityOreTroll setContents(ItemStack stack, int index) {
		inventory.set(index, stack.copy());
		return this;
	}

	public int getSizeInventory() {
		return inventory.size();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		setPotionEffect(compound.getByte("effect"));
		this.loadFromNbt(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setByte("effect", getPotionEffect());
		return this.saveToNbt(compound);
	}
	
	public void loadFromNbt(NBTTagCompound compound) {
		inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if (compound.hasKey("Items", 9))
			ItemStackHelper.loadAllItems(compound, inventory);
	}

	public NBTTagCompound saveToNbt(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, inventory, false);
		return compound;
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
		return ModSounds.TROLL_LIVING;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damage) {
		return ModSounds.TROLL_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.TROLL_DEATH;
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