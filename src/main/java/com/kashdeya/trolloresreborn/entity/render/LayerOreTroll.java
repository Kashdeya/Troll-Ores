package com.kashdeya.trolloresreborn.entity.render;

import com.kashdeya.trolloresreborn.entity.EntityOreTroll;
import com.kashdeya.trolloresreborn.entity.model.ModelOreTroll;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerOreTroll implements LayerRenderer<EntityOreTroll> {
	private final RenderOreTroll trollRenderer;
	private final ModelOreTroll trollModel = new ModelOreTroll();

	public LayerOreTroll(RenderOreTroll livingEntityRendererIn) {
		this.trollRenderer = livingEntityRendererIn;
	}

	@Override
	public void doRenderLayer(EntityOreTroll entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		ItemStack stack = entity.getHeldItemMainhand();

		if (stack != null)
			this.renderHeldItem(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, stack);
	}

	private void renderHeldItem(EntityOreTroll entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, ItemStack stack) {
		if (stack != null) {
			float tick = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

			GlStateManager.pushMatrix();
			float animation = MathHelper.sin((entity.limbSwing * 0.4F) * 1.5F) * 0.3F * entity.limbSwingAmount * 0.3F;
			GlStateManager.translate(0.0F, 0.33F, 0F);
			GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(tick * 5F, 0F, 1F, 0F);
			GlStateManager.scale(0.125F, 0.125F, 0.125F);
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, (World) null, (EntityLivingBase) null));
			GlStateManager.popMatrix();
		}
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}
