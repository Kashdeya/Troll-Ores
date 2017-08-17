package com.kashdeya.trolloresreborn.init;

import com.kashdeya.trolloresreborn.blocks.TrollBlock;
import com.kashdeya.trolloresreborn.handlers.ConfigHandler;
import com.kashdeya.trolloresreborn.proxy.CommonProxy;
import com.kashdeya.trolloresreborn.ref.Reference;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class TrollOresReborn {
	
	@Instance(Reference.MOD_ID)
    public static TrollOresReborn instance;
	
	@SidedProxy(clientSide=Reference.PROXY_CLIENT, serverSide=Reference.PROXY_COMMON)
	public static CommonProxy proxy;
	
	public static Block oreTrollOre = new TrollBlock();
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent e) {
		ConfigHandler.initOreConfigs();
		proxy.registerHandlers();
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
    	GameRegistry.register(oreTrollOre, new ResourceLocation(Reference.MOD_ID + ":troll_ore"));
    	GameRegistry.register(new ItemBlock(oreTrollOre), new ResourceLocation(Reference.MOD_ID + ":troll_ore"));
    	proxy.registerRenderers();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    }

}
