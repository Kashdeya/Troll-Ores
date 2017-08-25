package com.kashdeya.trolloresreborn.handlers;

import java.io.File;
import java.util.ArrayList;
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
	public final String[] usedCategories = { "TOR-Main Settings", "TOR-Ore Troll", "TOR-Wither"};
	public static boolean FAKE_PLAYERS;
	public static boolean SILK_IMMUNITY;
	public static float CHANCE;
	public static int TROLL_SPAWN;
	public static ArrayList<String> EXTRA_ORES = new ArrayList<String>();
	public static boolean ENABLE_WITHER;
	public static int TROLL_PRECENT;
	public static boolean SILENT_TROLL;
	public static boolean SILENT_WITHER;
	public static boolean WITHER_IGNITE;
	public static boolean TROLL_EXPLOSION;
	public static boolean TROLL_SPRINTING;
	public static boolean FORTUNE_MULTIPLIER;
	public static String TROLL_NAME;
	public static String WITHER_NAME;
	public static float TROLL_HEALTH;
	public static float TROLL_ATTACK_DAMAGE;
	public static boolean TROLL_EFFECTS;
	public static int TROLL_EFFECTS_DURATION;
	public static float TROLL_FOLLOW_RANGE;

	public void loadConfig(FMLPreInitializationEvent event) {
		CONFIG = new Configuration(new File(CONFIG_DIR, "Troll Ores Reborn.cfg"));
		CONFIG.load();
		syncConfigs();
	}
	
	private void syncConfigs() {

        // Troll Ores
		CONFIG.addCustomCategoryComment("TOR-Main Settings", "");
        EXTRA_ORES.clear();
        EXTRA_ORES.addAll(Arrays.asList(CONFIG.getStringList("Extra Ores", "TOR-Main Settings", new String[]{}, "Additional blocks that should be treated as 'Troll Ores'.. EXAMPLE = 'minecraft:dirt'")));
        CHANCE = CONFIG.getFloat("Chance of Troll Ore", "TOR-Main Settings", 0.1F, 0.0F, 1.0F, "Chance a random Ore will be a Troll.\nDefault is 10%");
        FAKE_PLAYERS = CONFIG.getBoolean("Enable Fake Players", "TOR-Main Settings", false, "Enable machines acting as player to trigger Troll Ores!");
        FORTUNE_MULTIPLIER = CONFIG.getBoolean("Fortune Multiplier", "TOR-Main Settings", true, "Fortune enchantments multiply the chance of triggering a Troll ore!");
        SILK_IMMUNITY = CONFIG.getBoolean("Silk Touch Immunity", "TOR-Main Settings", true, "Using silk touch on ores prevents triggering Troll Ores!");

        // Ore Troll
        CONFIG.addCustomCategoryComment("TOR-Ore Troll", "");
        TROLL_SPAWN = CONFIG.getInt("Ore Troll", "TOR-Ore Troll", 1, 1, Integer.MAX_VALUE, "How many Ore Trolls spawns from Troll Ores");
        TROLL_PRECENT = CONFIG.getInt("Ore Troll Percent", "TOR-Ore Troll", 99, 0, 100, "Percent that Ore Troll will spawn, Withers will spawn above this #.\n(Example = 99 means Withers have a 1% chance to Spawn!)");
        SILENT_TROLL = CONFIG.getBoolean("Ore Troll Silent", "TOR-Ore Troll", false, "Make Ore Trolls Silent!");
        TROLL_EXPLOSION = CONFIG.getBoolean("Ore Troll Particles", "TOR-Ore Troll", true, "Add Particles when Ore Trolls Spawn!");
        TROLL_SPRINTING = CONFIG.getBoolean("Ore Troll Sprinting", "TOR-Ore Troll", true, "Make Ore Trolls Sprint!");
        TROLL_NAME = CONFIG.getString("Troll Name", "TOR-Ore Troll", "Vash_505", "Allows you to change the name of the Troll!");
        TROLL_HEALTH = CONFIG.getFloat("Ore Troll Health","TOR-Ore Troll", 40F, 1F, Float.MAX_VALUE, "Allows you to change the max health of the Troll!");
        TROLL_ATTACK_DAMAGE = CONFIG.getFloat("Ore Troll Attack Damage", "TOR-Ore Troll", 2F, 1F, Float.MAX_VALUE, "Allows you to change the attack damage of the Troll!");
        TROLL_EFFECTS = CONFIG.getBoolean("Ore Troll will Attack with Random Negative Potion Effect", "TOR-Ore Troll", true, "Make Ore Trolls Have Effects!");
        TROLL_EFFECTS_DURATION = CONFIG.getInt("Ore Troll Effects Duration", "TOR-Ore Troll", 5, 5, Integer.MAX_VALUE, "Duration Troll Potion Effects Last (Seconds)");
        TROLL_FOLLOW_RANGE = CONFIG.getInt("Ore Troll Follow Distance", "TOR-Ore Troll", 32, 8, Integer.MAX_VALUE, "Allows you to change the Follow Distance of the Troll!");
        
        // Wither
        CONFIG.addCustomCategoryComment("TOR-Wither", "");
        ENABLE_WITHER = CONFIG.getBoolean("Wither", "TOR-Wither", true, "Enable Wither to spawn from Troll Ores.\nPercent is set above!");
        WITHER_IGNITE = CONFIG.getBoolean("Wither Ignite", "TOR-Wither", false, "Make Wither Ignite at spawn!");
        SILENT_WITHER = CONFIG.getBoolean("Wither Silent", "TOR-Wither", false, "Make Wither Silent!");
        WITHER_NAME = CONFIG.getString("Wither Name", "TOR-Wither", "Prince of Darkness", "Allows you to change the name of the Wither!");
        
		if (CONFIG.hasChanged())
			CONFIG.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Reference.MOD_ID))
			syncConfigs();
	}
}