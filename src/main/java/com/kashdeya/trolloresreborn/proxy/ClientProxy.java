package com.kashdeya.trolloresreborn.proxy;

import com.kashdeya.trolloresreborn.entity.EntityOreTroll;
import com.kashdeya.trolloresreborn.entity.EntitySmallWither;
import com.kashdeya.trolloresreborn.entity.EntitySmallWitherSkull;
import com.kashdeya.trolloresreborn.entity.render.RenderOreTroll;
import com.kashdeya.trolloresreborn.entity.render.RenderSmallWither;

import net.minecraft.client.renderer.entity.RenderWitherSkull;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityOreTroll.class, RenderOreTroll::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySmallWither.class, RenderSmallWither::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySmallWitherSkull.class, RenderWitherSkull::new);
	}

}
