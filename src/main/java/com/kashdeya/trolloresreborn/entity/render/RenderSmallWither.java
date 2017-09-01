package com.kashdeya.trolloresreborn.entity.render;

import com.kashdeya.trolloresreborn.entity.EntitySmallWither;
import com.kashdeya.trolloresreborn.entity.model.ModelSmallWither;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSmallWither extends RenderLiving<EntitySmallWither> {
    private static final ResourceLocation INVULNERABLE_WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
    private static final ResourceLocation WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither.png");

    public RenderSmallWither(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelSmallWither(0.0F), 1.0F);
        this.addLayer(new LayerSmallWitherAura(this));
    }

	@Override
    protected ResourceLocation getEntityTexture(EntitySmallWither entity) {
        int i = entity.getInvulTime();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? INVULNERABLE_WITHER_TEXTURES : WITHER_TEXTURES;
    }

	@Override
	protected void preRenderCallback(EntitySmallWither entitylivingbaseIn, float partialTickTime) {
		float f = 0.55F;
		int i = entitylivingbaseIn.getInvulTime();

		if (i > 0)
			f -= ((float) i - partialTickTime) / 220.0F * 0.5F;
		GlStateManager.translate(0F, 0.1F, 0F);
		GlStateManager.scale(f, f, f);
	}
}
