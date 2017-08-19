package com.kashdeya.trolloresreborn.handlers;

import java.io.File;
import java.util.Arrays;

import com.kashdeya.trolloresreborn.ref.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	
	public static final ConfigHandler INSTANCE = new ConfigHandler();
	public Configuration CONFIG;
	public static final File CONFIG_DIR = new File("config/trolloresreborn");
	public final String[] usedCategories = { "TOR-Main Settings", "TOR-Silverfish", "TOR-Wither"};

	public void loadConfig(FMLPreInitializationEvent event) {
		CONFIG = new Configuration(new File(CONFIG_DIR, "Troll Ores Reborn.cfg"));
		CONFIG.load();
		syncConfigs();
	}
	
	private void syncConfigs() {

        // Troll Ores
		CONFIG.addCustomCategoryComment("TOR-Main Settings", "");
        ConfigSettings.extraOres.clear();
        ConfigSettings.extraOres.addAll(Arrays.asList(CONFIG.getStringList("Extra Ores", "TOR-Main Settings", new String[]{}, "Additional blocks that should be treated as 'Troll Ores'.. EXAMPLE = 'minecraft:dirt'")));
        ConfigSettings.chance = CONFIG.getFloat("Chance of Troll Ore", "TOR-Main Settings", 0.1F, 0.0F, 1.0F, "Chance a random Ore will be a Troll.\nDefault is 10%");
        ConfigSettings.fakePlayers = CONFIG.getBoolean("Enable Fake Players", "TOR-Main Settings", false, "Enable machines acting as player to trigger Troll Ores!");
        ConfigSettings.fortuneMult = CONFIG.getBoolean("Fortune Multiplier", "TOR-Main Settings", true, "Fortune enchantments multiply the chance of triggering a Troll ore!");
        ConfigSettings.silkImmunity = CONFIG.getBoolean("Silk Touch Immunity", "TOR-Main Settings", true, "Using silk touch on ores prevents triggering Troll Ores!");
        
        // Silverfish
        CONFIG.addCustomCategoryComment("TOR-Silverfish", "");
        ConfigSettings.silverfishSpawn = CONFIG.getInt("Silverfish", "TOR-Silverfish", 1, 1, Integer.MAX_VALUE, "How many Silverfish spawns from Troll Ores");
        ConfigSettings.silverfishpercent = CONFIG.getInt("Silverfish Percent", "TOR-Silverfish", 99, 0, 100, "Percent that Silverfish will spawn, Withers will spawn above this #.\n(Example = 99 means Withers have a 1% chance to Spawn!)");
        ConfigSettings.silentFish = CONFIG.getBoolean("Silverfish Silent", "TOR-Silverfish", true, "Make Silverfish Silent!");
        ConfigSettings.fishExplosion = CONFIG.getBoolean("Silverfish Particles", "TOR-Silverfish", true, "Add Particles when Silverfish Spawn!");
        ConfigSettings.fishSprinting = CONFIG.getBoolean("Silverfish Sprinting", "TOR-Silverfish", true, "Make Silverfish Sprint!");
        ConfigSettings.silverfishName = CONFIG.getString("Troll Fish Name", "TOR-Silverfish", "Troll Fish", "Allows you to change the name of the Troll Fish!");
        // Wither
        CONFIG.addCustomCategoryComment("TOR-Wither", "");
        ConfigSettings.enableWither = CONFIG.getBoolean("Wither", "TOR-Wither", true, "Enable Wither to spawn from Troll Ores.\nPercent is set above!");
        ConfigSettings.witherIgnite = CONFIG.getBoolean("Wither Ignite", "TOR-Wither", false, "Make Wither Ignite at spawn!");
        ConfigSettings.silentWither = CONFIG.getBoolean("Wither Silent", "TOR-Wither", true, "Make Wither Silent!");
        ConfigSettings.witherName = CONFIG.getString("Wither Name", "TOR-Wither", "Prince of Darkness", "Allows you to change the name of the Wither!");
        
		if (CONFIG.hasChanged())
			CONFIG.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Reference.MOD_ID))
			syncConfigs();
	}
}