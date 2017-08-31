package com.kashdeya.trolloresreborn.entity;

import javax.annotation.Nullable;

import com.kashdeya.trolloresreborn.handlers.ConfigHandler;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySmallWither extends EntityWither {

	public EntitySmallWither(World world) {
		super(world);
		setSize(0.3F, 0.9F);
        experienceValue = 10;
	}

	@Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.WITHER_HEALTH);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6000000238418579D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue((double)ConfigHandler.WITHER_FOLLOW_RANGE);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue((double)ConfigHandler.WITHER_ATTACK_DAMAGE);
    }

	@Override
	protected float getSoundPitch() {
		return 2F;
	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
		// No nether stars here scrub!
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
}