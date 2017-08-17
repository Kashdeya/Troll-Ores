package com.kashdeya.trolloresreborn.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.kashdeya.trolloresreborn.handlers.EventHandler;
import com.kashdeya.trolloresreborn.world.WorldGen;

public class CommonProxy {
	
	public boolean isClient()
	{
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void registerHandlers()
	{
		EventHandler handler = new EventHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);
		MinecraftForge.EVENT_BUS.register(new WorldGen());
	}

	public void registerRenderers()
	{
	}


}
