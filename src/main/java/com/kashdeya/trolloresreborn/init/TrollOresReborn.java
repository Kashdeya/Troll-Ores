package com.kashdeya.trolloresreborn.init;

import com.kashdeya.trolloresreborn.blocks.TrollBlock;
import com.kashdeya.trolloresreborn.entity.EntityOreTroll;
import com.kashdeya.trolloresreborn.handlers.ConfigHandler;
import com.kashdeya.trolloresreborn.handlers.TOREventHandler;
import com.kashdeya.trolloresreborn.proxy.CommonProxy;
import com.kashdeya.trolloresreborn.ref.Reference;
import com.kashdeya.trolloresreborn.world.WorldGen;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = "com.kashdeya.trolloresreborn.handlers.ConfigGuiFactory")

public class TrollOresReborn {
	
	@Instance(Reference.MOD_ID)
    public static TrollOresReborn instance;
	
	@SidedProxy(clientSide=Reference.PROXY_CLIENT, serverSide=Reference.PROXY_COMMON)
	public static CommonProxy PROXY;
	public static Block TROLL_ORE;
	public static Item TROLL_ORE_ITEM;
	public static SoundEvent TROLL_LIVING, TROLL_HURT, TROLL_DEATH;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.INSTANCE.loadConfig(event);
		
		TROLL_ORE = new TrollBlock();
		TROLL_ORE_ITEM = new ItemBlock(TROLL_ORE);
		GameRegistry.register(TROLL_ORE.setRegistryName(Reference.MOD_ID, "troll_ore").setUnlocalizedName("troll_ore"));
		GameRegistry.register(TROLL_ORE_ITEM.setRegistryName(TROLL_ORE.getRegistryName()).setUnlocalizedName("troll_ore"));

		EntityRegistry.registerModEntity(EntityOreTroll.class, "Ore-Troll", 1, this, 120, 1, true, 0x7F8287, 0x8CEDFF);
		MinecraftForge.EVENT_BUS.register(new WorldGen());
		PROXY.registerRenderers();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	TROLL_LIVING = new SoundEvent(new ResourceLocation(Reference.MOD_ID, "troll_living")).setRegistryName(Reference.MOD_ID, "troll_living");
		GameRegistry.register(TROLL_LIVING);
		TROLL_HURT = new SoundEvent(new ResourceLocation(Reference.MOD_ID, "troll_hurt")).setRegistryName(Reference.MOD_ID, "troll_hurt");
		GameRegistry.register(TROLL_HURT);
		TROLL_DEATH = new SoundEvent(new ResourceLocation(Reference.MOD_ID, "troll_death")).setRegistryName(Reference.MOD_ID, "troll_death");
		GameRegistry.register(TROLL_DEATH);
    	MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
    	MinecraftForge.EVENT_BUS.register(new TOREventHandler());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

}
