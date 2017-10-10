package com.kashdeya.trolloresreborn.proxy;

import com.kashdeya.trolloresreborn.entity.EntityOreTroll;
import com.kashdeya.trolloresreborn.entity.EntitySmallWither;
import com.kashdeya.trolloresreborn.entity.render.RenderOreTroll;
import com.kashdeya.trolloresreborn.entity.render.RenderSmallWither;
import com.kashdeya.trolloresreborn.init.TrollOresReborn;
import com.kashdeya.trolloresreborn.ref.Reference;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public boolean isClient() {
		return true;
	}

	@Override
	public void registerRenderers() {
		ModelLoader.setCustomModelResourceLocation(TrollOresReborn.TROLL_ORE_ITEM, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + "troll_ore", "inventory"));
		RenderingRegistry.registerEntityRenderingHandler(EntityOreTroll.class, RenderOreTroll::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySmallWither.class, RenderSmallWither::new);
	}

}
