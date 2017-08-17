package com.kashdeya.trolloresreborn.handlers;

import java.io.File;
import java.util.Arrays;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	
	public static Configuration config;
	public static final File configDir = new File("config/trolloresreborn");
	
	public static void initOreConfigs()
	{
		
		File f = new File(configDir, "Troll Ores Reborn.cfg");
        config = new Configuration(f);
        
        config.load();
        
        String category;
		category = "TOR-";
   	
        // Troll Ores
		config.addCustomCategoryComment(category + "Main Settings", "");
        ConfigSettings.extraOres.clear();
        ConfigSettings.extraOres.addAll(Arrays.asList(config.getStringList("Extra Ores", category + "Main Settings", new String[]{}, "Additional blocks that should be treated as 'Troll Ores'.. EXAMPLE = 'minecraft:dirt'")));
        ConfigSettings.chance = config.getFloat("Chance of Troll Ore", category + "Main Settings", 0.1F, 0.0F, 1.0F, "Chance a random Ore will be a Troll.\nDefault is 10%");
        ConfigSettings.fakePlayers = config.getBoolean("Enable Fake Players", category + "Main Settings", false, "Enable machines acting as player to trigger Troll Ores!");
        ConfigSettings.fortuneMult = config.getBoolean("Fortune Multiplier", category + "Main Settings", true, "Fortune enchantments multiply the chance of triggering a Troll ore!");
        ConfigSettings.silkImmunity = config.getBoolean("Silk Touch Immunity", category + "Main Settings", true, "Using silk touch on ores prevents triggering Troll Ores!");
        
        // Silverfish
        config.addCustomCategoryComment(category + "Silverfish", "");
        ConfigSettings.silverfishSpawn = config.getInt("Silverfish", category + "Silverfish", 1, 1, Integer.MAX_VALUE, "How many Silverfish spawns from Troll Ores");
        ConfigSettings.silverfishpercent = config.getInt("Silverfish Percent", category + "Silverfish", 99, 0, 100, "Percent that Silverfish will spawn, Withers will spawn above this #.\n(Example = 99 means Withers have a 1% chance to Spawn!)");
        ConfigSettings.silentFish = config.getBoolean("Silverfish Silent", category + "Silverfish", true, "Make Silverfish Silent!");
        ConfigSettings.fishExplosion = config.getBoolean("Silverfish Particles", category + "Silverfish", true, "Add Particles when Silverfish Spawn!");
        ConfigSettings.fishSprinting = config.getBoolean("Silverfish Sprinting", category + "Silverfish", true, "Make Silverfish Sprint!");
        ConfigSettings.silverfishName = config.getString("Troll Fish Name", category + "Silverfish", "Troll Fish", "Allows you to change the name of the Troll Fish!");
        // Wither
        config.addCustomCategoryComment(category + "Wither", "");
        ConfigSettings.enableWither = config.getBoolean("Wither", category + "Wither", true, "Enable Wither to spawn from Troll Ores.\nPercent is set above!");
        ConfigSettings.witherIgnite = config.getBoolean("Wither Ignite", category + "Wither", false, "Make Wither Ignite at spawn!");
        ConfigSettings.silentWither = config.getBoolean("Wither Silent", category + "Wither", true, "Make Wither Silent!");
        ConfigSettings.witherName = config.getString("Wither Name", category + "Wither", "Prince of Darkness", "Allows you to change the name of the Wither!");
        
        if (config.hasChanged())
        config.save();

    }
}