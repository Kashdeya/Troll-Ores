package com.kashdeya.trolloresreborn.init;

import com.kashdeya.trolloresreborn.entity.EntityOreTroll;
import com.kashdeya.trolloresreborn.entity.EntitySmallWither;
import com.kashdeya.trolloresreborn.handlers.ConfigHandler;
import com.kashdeya.trolloresreborn.handlers.TOREventHandler;
import com.kashdeya.trolloresreborn.proxy.CommonProxy;
import com.kashdeya.trolloresreborn.ref.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = "com.kashdeya.trolloresreborn.handlers.ConfigGuiFactory")

public class TrollOresReborn {
	
	@Instance(Reference.MOD_ID)
    public static TrollOresReborn instance;
	
	@SidedProxy(clientSide=Reference.PROXY_CLIENT, serverSide=Reference.PROXY_COMMON)
	public static CommonProxy PROXY;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.INSTANCE.loadConfig(event);
		ModSounds.init();

		EntityRegistry.registerModEntity(getEntityResource("ore_troll"), EntityOreTroll.class, "Ore-Troll", 1, this, 120, 1, true, 0x7F8287, 0x8CEDFF);
		EntityRegistry.registerModEntity(getEntityResource("small_wither"), EntitySmallWither.class, "Small-Wither", 2, this, 120, 1, true, 0x000000, 0x8CEDFF);
		PROXY.registerRenderers();
    }
	
	private static ResourceLocation getEntityResource(String entityName) {
		return new ResourceLocation(Reference.MOD_ID, entityName);
	}

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
    	MinecraftForge.EVENT_BUS.register(new TOREventHandler());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

}
