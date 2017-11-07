package com.kashdeya.trolloresreborn.init;

import com.kashdeya.trolloresreborn.ref.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModSounds {
	public static SoundEvent TROLL_LIVING, TROLL_HURT, TROLL_DEATH;

	public static void init() {
    	TROLL_LIVING = new SoundEvent(new ResourceLocation(Reference.MOD_ID, "troll_living")).setRegistryName(Reference.MOD_ID, "troll_living");
		TROLL_HURT = new SoundEvent(new ResourceLocation(Reference.MOD_ID, "troll_hurt")).setRegistryName(Reference.MOD_ID, "troll_hurt");
		TROLL_DEATH = new SoundEvent(new ResourceLocation(Reference.MOD_ID, "troll_death")).setRegistryName(Reference.MOD_ID, "troll_death");
	}

	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandlerSounds {
		@SubscribeEvent
		public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
			event.getRegistry().registerAll(TROLL_LIVING, TROLL_HURT, TROLL_DEATH);
		}
	}
}

