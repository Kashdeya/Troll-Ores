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
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityOreTroll.class, RenderOreTroll::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySmallWither.class, RenderSmallWither::new);
	}

}
