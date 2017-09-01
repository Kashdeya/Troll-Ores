package com.kashdeya.trolloresreborn.entity.model;

import com.kashdeya.trolloresreborn.entity.EntitySmallWither;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSmallWither extends ModelBase
{
    private final ModelRenderer[] upperBodyParts;
    private final ModelRenderer[] heads;

    public ModelSmallWither(float scale)
    {
        textureWidth = 64;
        textureHeight = 64;
        upperBodyParts = new ModelRenderer[3];
        upperBodyParts[0] = new ModelRenderer(this, 0, 16);
        upperBodyParts[0].addBox(-10.0F, 3.9F, -0.5F, 20, 3, 3, scale);
        upperBodyParts[1] = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
        upperBodyParts[1].setRotationPoint(-2.0F, 6.9F, -0.5F);
        upperBodyParts[1].setTextureOffset(0, 22).addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, scale);
        upperBodyParts[1].setTextureOffset(24, 22).addBox(-4.0F, 1.5F, 0.5F, 11, 2, 2, scale);
        upperBodyParts[1].setTextureOffset(24, 22).addBox(-4.0F, 4.0F, 0.5F, 11, 2, 2, scale);
        upperBodyParts[1].setTextureOffset(24, 22).addBox(-4.0F, 6.5F, 0.5F, 11, 2, 2, scale);
        upperBodyParts[2] = new ModelRenderer(this, 12, 22);
        upperBodyParts[2].addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, scale);
        heads = new ModelRenderer[3];
        heads[0] = new ModelRenderer(this, 0, 0);
        heads[0].addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, scale);
        heads[1] = new ModelRenderer(this, 32, 0);
        heads[1].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, scale);
        heads[1].rotationPointX = -8.0F;
        heads[1].rotationPointY = 4.0F;
        heads[2] = new ModelRenderer(this, 32, 0);
        heads[2].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, scale);
        heads[2].rotationPointX = 10.0F;
        heads[2].rotationPointY = 4.0F;
    }

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

		for (ModelRenderer modelrenderer : heads)
			modelrenderer.render(scale);

		for (ModelRenderer modelrenderer1 : upperBodyParts)
			modelrenderer1.render(scale);
	}

	@Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float f = MathHelper.cos(ageInTicks * 0.1F);
        upperBodyParts[1].rotateAngleX = (0.065F + 0.05F * f) * (float)Math.PI;
        upperBodyParts[2].setRotationPoint(-2.0F, 6.9F + MathHelper.cos(upperBodyParts[1].rotateAngleX) * 10.0F, -0.5F + MathHelper.sin(upperBodyParts[1].rotateAngleX) * 10.0F);
        upperBodyParts[2].rotateAngleX = (0.265F + 0.1F * f) * (float)Math.PI;
        heads[0].rotateAngleY = netHeadYaw * 0.017453292F;
        heads[0].rotateAngleX = headPitch * 0.017453292F;
    }

	@Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
        EntitySmallWither entitywither = (EntitySmallWither)entitylivingbaseIn;

        for (int i = 1; i < 3; ++i) {
            heads[i].rotateAngleY = (entitywither.getHeadYRotation(i - 1) - entitylivingbaseIn.renderYawOffset) * 0.017453292F;
            heads[i].rotateAngleX = entitywither.getHeadXRotation(i - 1) * 0.017453292F;
        }
    }
}