package com.kashdeya.trolloresreborn.entity.render;

import com.kashdeya.trolloresreborn.entity.EntityOreTroll;
import com.kashdeya.trolloresreborn.entity.model.ModelOreTroll;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerOreTroll implements LayerRenderer<EntityOreTroll> {
	private final RenderOreTroll trollRenderer;

	public LayerOreTroll(RenderOreTroll livingEntityRendererIn) {
		this.trollRenderer = livingEntityRendererIn;
	}

	@Override
	public void doRenderLayer(EntityOreTroll entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		ItemStack stack = entity.getHeldItemMainhand();

		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			renderHeldItem(entity, stack, TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
			GlStateManager.popMatrix();
		}
	}

	private void renderHeldItem(EntityOreTroll entity, ItemStack stack, TransformType transform, EnumHandSide handSide) {
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.75F, 0.75F, 0.75F);
			((ModelOreTroll) trollRenderer.getMainModel()).bum.postRender(0.0625F);
			((ModelOreTroll) trollRenderer.getMainModel()).body_main.postRender(0.0625F);
			((ModelOreTroll) trollRenderer.getMainModel()).right_arm_1.postRender(0.0625F);
			((ModelOreTroll) trollRenderer.getMainModel()).right_arm_2.postRender(0.0625F);
			((ModelOreTroll) trollRenderer.getMainModel()).postRenderArm(0.0625F);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.5F, -0.125F, 0.5F);
			Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, transform, false);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}
