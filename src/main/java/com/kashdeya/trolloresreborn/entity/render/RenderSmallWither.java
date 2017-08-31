package com.kashdeya.trolloresreborn.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.entity.boss.EntityWither;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSmallWither extends RenderWither {

	public RenderSmallWither(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.3F;
	}

	@Override
	protected void preRenderCallback(EntityWither entitylivingbaseIn, float partialTickTime) {
		float f = 0.55F;
		int i = entitylivingbaseIn.getInvulTime();

		if (i > 0)
			f -= ((float) i - partialTickTime) / 220.0F * 0.5F;
		GlStateManager.translate(0F, 0.1F, 0F);
		GlStateManager.scale(f, f, f);
	}
}
