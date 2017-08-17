package com.kashdeya.trolloresreborn.handlers;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

import com.kashdeya.trolloresreborn.ref.Reference;

public class GuiMythicalHandler extends GuiConfig
{
	public GuiMythicalHandler(GuiScreen parent)
	{
		super(parent, new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.MOD_ID, false, false, Reference.MOD_NAME);
	}
}
