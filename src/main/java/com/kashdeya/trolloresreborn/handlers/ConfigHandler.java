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
	public static int TROLL_EXP_DROPS;
	public static boolean FORTUNE_MULTIPLIER;
	public static String[] TROLL_NAME;
	public static String[] WITHER_NAME;
	public static float TROLL_HEALTH;
	public static float TROLL_ATTACK_DAMAGE;
	public static float WITHER_HEALTH;
	public static float WITHER_FOLLOW_RANGE;
	public static float WITHER_ARMOUR;
	public static boolean WITHER_FIRE;
	public static int WITHER_EXP_DROPS;
	public static float TROLL_LEAP_HEIGHT;
	public static boolean TROLL_EFFECTS;
	public static int TROLL_EFFECTS_DURATION;
	public static float TROLL_FOLLOW_RANGE;
	public static boolean TROLL_IMMUNE_TO_FIRE_DAMAGE;
	public static boolean TROLL_IMMUNE_TO_FALL_DAMAGE;
	public static boolean TROLL_IMMUNE_TO_SUFFOCATION_DAMAGE;
	public static boolean TROLL_IMMUNE_TO_PROJECTILE_DAMAGE;
	public static boolean TROLL_IMMUNE_TO_FALLING_BLOCK_DAMAGE;
	public static boolean TROLL_IMMUNE_TO_CACTUS_DAMAGE;
	public static boolean TROLL_IMMUNE_TO_NON_PLAYER_DAMAGE;
	public static boolean TROLL_CLIMBING_AI;
	
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
        TROLL_PRECENT = CONFIG.getInt("Ore Troll Percent", "TOR-Ore Troll", 90, 1, 100, "Percent that Ore Troll will spawn, Withers will spawn above this #.\n(Example = 90 means Withers have a 10% chance to Spawn if ENABLED below!)");
        SILENT_TROLL = CONFIG.getBoolean("Ore Troll Silent", "TOR-Ore Troll", false, "Make Ore Trolls Silent!");
        TROLL_EXPLOSION = CONFIG.getBoolean("Ore Troll Particles", "TOR-Ore Troll", true, "Add Particles when Ore Trolls Spawn!");
        TROLL_SPRINTING = CONFIG.getBoolean("Ore Troll Sprinting", "TOR-Ore Troll", true, "Make Ore Trolls Sprint!");
        TROLL_NAME = CONFIG.getStringList("Troll Name", "TOR-Ore Troll", new String[] {"Vash_505", "Vadis365", "Funwayguy", "Arclight_TW", "Ore Troll", "Biggie"}, "Allows you to change the name of the Ore Troll!\n[You can have more then one name. It will randomly pick a name at spawn for each Ore Troll.]");
        TROLL_HEALTH = CONFIG.getFloat("Ore Troll Health","TOR-Ore Troll", 20F, 1F, Float.MAX_VALUE, "Allows you to change the Max Health of the Troll!");
        TROLL_ATTACK_DAMAGE = CONFIG.getFloat("Ore Troll Attack Damage", "TOR-Ore Troll", 2F, 1F, Float.MAX_VALUE, "Allows you to change the Attack Damage of the Troll!");
        TROLL_LEAP_HEIGHT = CONFIG.getFloat("Ore Troll Leap Height", "TOR-Ore Troll", 0.5F, 0F, Float.MAX_VALUE, "Allows you to change the Jump Height of the Troll!");
        TROLL_EFFECTS = CONFIG.getBoolean("Ore Troll will Attack with Random Negative Potion Effect", "TOR-Ore Troll", true, "Make Ore Trolls Have Effects!");
        TROLL_EFFECTS_DURATION = CONFIG.getInt("Ore Troll Effects Duration", "TOR-Ore Troll", 5, 5, Integer.MAX_VALUE, "Duration Troll Potion Effects Last (Seconds)");
        TROLL_FOLLOW_RANGE = CONFIG.getFloat("Ore Troll Follow Distance", "TOR-Ore Troll", 32F, 8F, Float.MAX_VALUE, "Allows you to change the Follow Distance of the Troll!");
        TROLL_IMMUNE_TO_FIRE_DAMAGE = CONFIG.getBoolean("Ore Troll Fire Damage Resitance", "TOR-Ore Troll", true, "Make Ore Trolls Immune to Fire Damage!");
        TROLL_IMMUNE_TO_FALL_DAMAGE = CONFIG.getBoolean("Ore Troll Fall Damage Resitance", "TOR-Ore Troll", true, "Make Ore Trolls Immune to Fall Damage!");
        TROLL_IMMUNE_TO_SUFFOCATION_DAMAGE = CONFIG.getBoolean("Ore Troll Suffocation Damage Resitance", "TOR-Ore Troll", true, "Make Ore Trolls Immune to Suffocation Damage!");
        TROLL_IMMUNE_TO_PROJECTILE_DAMAGE = CONFIG.getBoolean("Ore Troll Projectile Damage Resitance", "TOR-Ore Troll", true, "Make Ore Trolls Immune to Projectile Damage!");
        TROLL_IMMUNE_TO_FALLING_BLOCK_DAMAGE = CONFIG.getBoolean("Ore Troll Falling Block Damage Resitance", "TOR-Ore Troll", true, "Make Ore Trolls Immune to Falling Block Damage!");
        TROLL_IMMUNE_TO_CACTUS_DAMAGE = CONFIG.getBoolean("Ore Troll Cactus Damage Resitance", "TOR-Ore Troll", true, "Make Ore Trolls Immune to Cactus Damage!");
        TROLL_IMMUNE_TO_NON_PLAYER_DAMAGE = CONFIG.getBoolean("Ore Troll Only Damaged By Players", "TOR-Ore Troll", false, "Make Ore Trolls Immune to all non-Player Damage!");
        TROLL_EXP_DROPS = CONFIG.getInt("Ore Troll Experience Drop", "TOR-Ore Troll", 10, 0, Integer.MAX_VALUE, "How much Exp the Ore Trolls Drops!");
        TROLL_CLIMBING_AI = CONFIG.getBoolean("Ore Troll Can Climb Like Spiders", "TOR-Ore Troll", true, "Make Ore Trolls Climb Like Spiders!");

        // Wither
        CONFIG.addCustomCategoryComment("TOR-Wither", "");
        ENABLE_WITHER = CONFIG.getBoolean("Wither", "TOR-Wither", true, "Enable Small Wither to spawn from Troll Ores.\nPercent is set above!");
        WITHER_IGNITE = CONFIG.getBoolean("Wither Ignite", "TOR-Wither", true, "Make Small Wither Ignite at spawn!");
        SILENT_WITHER = CONFIG.getBoolean("Wither Silent", "TOR-Wither", false, "Make Small Wither Silent!");
        WITHER_NAME = CONFIG.getStringList("Wither Name", "TOR-Wither", new String[] { "Kashdeya", "Prince of Darkness", "Bam & Dlast", "Jordaaaaaaaaaaaaan!", "Mini Kahann", "Baby Bams", "5 Head - 2" }, "Allows you to change the name of the Wither!");
        WITHER_HEALTH = CONFIG.getFloat("Wither Health", "TOR-Wither", 50F, 1F, Float.MAX_VALUE, "Allows you to change the Max Health of the Small Wither!");
        WITHER_FOLLOW_RANGE = CONFIG.getFloat("Wither Follow Distance", "TOR-Wither", 16F, 8F, Float.MAX_VALUE, "Allows you to change the Follow Distance of the Small Wither!");
        WITHER_ARMOUR = CONFIG.getFloat("Wither Armor", "TOR-Wither", 4F, 0F, Float.MAX_VALUE, "Allows you to change the Armor of the Small Wither!");
        WITHER_EXP_DROPS = CONFIG.getInt("Wither Experience Drop", "TOR-Wither", 20, 0, Integer.MAX_VALUE, "How much Exp the TOR Wither Drops!");
        WITHER_FIRE = CONFIG.getBoolean("Wither Immune To Fire", "TOR-Wither", true, "Allows Wither to be Immune to fire!");
        
		if (CONFIG.hasChanged())
			CONFIG.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Reference.MOD_ID))
			syncConfigs();
	}
}