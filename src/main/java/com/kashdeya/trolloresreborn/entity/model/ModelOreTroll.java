package com.kashdeya.trolloresreborn.entity.model;

import com.kashdeya.trolloresreborn.entity.EntityOreTroll;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelOreTroll extends ModelBase {
	public ModelRenderer bum;
	public ModelRenderer body_main;
	public ModelRenderer left_leg1;
	public ModelRenderer right_leg_1;
	public ModelRenderer tail_1;
	public ModelRenderer head_main;
	public ModelRenderer right_arm_1;
	public ModelRenderer left_arm_1;
	public ModelRenderer head_crest;
	public ModelRenderer nose;
	public ModelRenderer brow;
	public ModelRenderer left_cheek;
	public ModelRenderer right_cheek;
	public ModelRenderer jaw_bottom;
	public ModelRenderer jaw_top;
	public ModelRenderer left_horn;
	public ModelRenderer right_horn;
	public ModelRenderer right_arm_2;
	public ModelRenderer right_hand;
	public ModelRenderer right_finger_out;
	public ModelRenderer right_finger_in;
	public ModelRenderer right_thumb;
	public ModelRenderer left_arm_2;
	public ModelRenderer left_hand;
	public ModelRenderer left_finger_in;
	public ModelRenderer left_finger_out;
	public ModelRenderer left_thumb;
	public ModelRenderer left_leg_2;
	public ModelRenderer left_toe_out;
	public ModelRenderer left_toe_in;
	public ModelRenderer right_leg_2;
	public ModelRenderer right_toe_in;
	public ModelRenderer right_toe_out;
	public ModelRenderer tail_2;
	public ModelRenderer tail_3;

	public ModelOreTroll() {
		textureWidth = 64;
		textureHeight = 32;
		right_toe_out = new ModelRenderer(this, 54, 20);
		right_toe_out.setRotationPoint(0.0F, 4.0F, 0.0F);
		right_toe_out.addBox(-1.0F, -0.5F, -3.0F, 1, 2, 2, 0.0F);
		setRotateAngle(right_toe_out, 0.0F, 0.17453292519943295F, 0.0F);
		right_cheek = new ModelRenderer(this, 56, 4);
		right_cheek.setRotationPoint(0.0F, 0.0F, 0.0F);
		right_cheek.addBox(-2.5F, -3.0F, -2.5F, 1, 2, 2, 0.0F);
		left_arm_1 = new ModelRenderer(this, 12, 9);
		left_arm_1.setRotationPoint(4.0F, -3.5F, -0.5F);
		left_arm_1.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 5, 0.0F);
		left_leg_2 = new ModelRenderer(this, 0, 18);
		left_leg_2.setRotationPoint(0.0F, 0.0F, -3.5F);
		left_leg_2.addBox(-1.0F, -0.5F, -1.0F, 2, 6, 2, 0.0F);
		setRotateAngle(left_leg_2, -0.6108652381980153F, 0.0F, 0.0F);
		right_thumb = new ModelRenderer(this, 21, 9);
		right_thumb.setRotationPoint(1.1F, 0.3F, -0.8F);
		right_thumb.addBox(-0.5F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
		setRotateAngle(right_thumb, 0.4363323129985824F, 0.4363323129985824F, 0.6981317007977318F);
		left_leg1 = new ModelRenderer(this, 34, 0);
		left_leg1.setRotationPoint(2.0F, 0.6F, -1.0F);
		left_leg1.addBox(-1.5F, -1.0F, -4.0F, 3, 3, 5, 0.0F);
		setRotateAngle(left_leg1, -0.08726646259971647F, -0.4363323129985824F, 0.3490658503988659F);
		right_finger_in = new ModelRenderer(this, 56, 14);
		right_finger_in.setRotationPoint(0.9F, -0.5F, -2.5F);
		right_finger_in.addBox(-0.5F, -0.3F, -2.0F, 1, 1, 2, 0.0F);
		setRotateAngle(right_finger_in, 0.5235987755982988F, -0.0F, 0.0F);
		right_leg_1 = new ModelRenderer(this, 45, 3);
		right_leg_1.setRotationPoint(-2.0F, 0.6F, -1.0F);
		right_leg_1.addBox(-1.5F, -1.0F, -4.0F, 3, 3, 5, 0.0F);
		setRotateAngle(right_leg_1, -0.08726646259971647F, 0.4363323129985824F, -0.3490658503988659F);
		bum = new ModelRenderer(this, 0, 0);
		bum.setRotationPoint(0.0F, 15.5F, 3.0F);
		bum.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 3, 0.0F);
		setRotateAngle(bum, 0.6108652381980153F, 0.0F, 0.0F);
		right_arm_1 = new ModelRenderer(this, 3, 5);
		right_arm_1.setRotationPoint(-4.0F, -3.5F, -0.5F);
		right_arm_1.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 5, 0.0F);
		left_cheek = new ModelRenderer(this, 56, 0);
		left_cheek.setRotationPoint(0.0F, 0.0F, 0.0F);
		left_cheek.addBox(1.5F, -3.0F, -2.5F, 1, 2, 2, 0.0F);
		right_toe_in = new ModelRenderer(this, 48, 20);
		right_toe_in.setRotationPoint(0.0F, 4.0F, 0.0F);
		right_toe_in.addBox(0.0F, -0.5F, -3.0F, 1, 2, 2, 0.0F);
		setRotateAngle(right_toe_in, 0.0F, -0.17453292519943295F, 0.0F);
		tail_3 = new ModelRenderer(this, 30, 22);
		tail_3.setRotationPoint(0.0F, 1.5F, 0.0F);
		tail_3.addBox(-1.5F, 0.0F, -0.5F, 3, 2, 1, 0.0F);
		setRotateAngle(tail_3, 0.2617993877991494F, 0.0F, 0.0F);
		right_hand = new ModelRenderer(this, 43, 15);
		right_hand.setRotationPoint(0.0F, 0.0F, -4.0F);
		right_hand.addBox(-1.5F, -1.0F, -3.0F, 3, 2, 3, 0.0F);
		left_toe_in = new ModelRenderer(this, 24, 20);
		left_toe_in.setRotationPoint(0.0F, 4.0F, 0.0F);
		left_toe_in.addBox(-1.0F, -0.5F, -3.0F, 1, 2, 2, 0.0F);
		setRotateAngle(left_toe_in, 0.0F, 0.17453292519943295F, 0.0F);
		brow = new ModelRenderer(this, 45, 0);
		brow.setRotationPoint(0.0F, 0.0F, 0.0F);
		brow.addBox(-2.0F, -4.0F, -3.0F, 4, 1, 1, 0.0F);
		right_horn = new ModelRenderer(this, 45, 2);
		right_horn.setRotationPoint(0.0F, 0.0F, 0.0F);
		right_horn.addBox(-0.8F, -6.0F, -1.9F, 1, 2, 1, 0.0F);
		setRotateAngle(right_horn, 0.3490658503988659F, 0.3490658503988659F, 0.0F);
		tail_2 = new ModelRenderer(this, 8, 22);
		tail_2.setRotationPoint(0.0F, 3.0F, 0.0F);
		tail_2.addBox(-0.5F, -0.4F, -0.9F, 1, 2, 2, 0.0F);
		setRotateAngle(tail_2, 0.2617993877991494F, -0.0F, 0.0F);
		head_main = new ModelRenderer(this, 30, 8);
		head_main.setRotationPoint(0.0F, -5.0F, -1.5F);
		head_main.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
		setRotateAngle(head_main, -0.6108652381980153F, -0.0F, 0.0F);
		left_arm_2 = new ModelRenderer(this, 8, 16);
		left_arm_2.setRotationPoint(0.0F, 0.0F, -4.0F);
		left_arm_2.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
		setRotateAngle(left_arm_2, -0.9599310885968813F, 0.0F, 0.0F);
		left_finger_out = new ModelRenderer(this, 56, 17);
		left_finger_out.setRotationPoint(0.8F, -0.5F, -2.5F);
		left_finger_out.addBox(-0.5F, -0.3F, -2.0F, 1, 1, 2, 0.0F);
		setRotateAngle(left_finger_out, 0.5235987755982988F, 0.0F, 0.0F);
		left_hand = new ModelRenderer(this, 30, 17);
		left_hand.setRotationPoint(0.0F, 0.0F, -4.0F);
		left_hand.addBox(-1.5F, -1.0F, -3.0F, 3, 2, 3, 0.0F);
		right_finger_out = new ModelRenderer(this, 28, 9);
		right_finger_out.setRotationPoint(-0.8F, -0.5F, -2.5F);
		right_finger_out.addBox(-0.5F, -0.3F, -2.0F, 1, 1, 2, 0.0F);
		setRotateAngle(right_finger_out, 0.5235987755982988F, -0.0F, 0.0F);
		nose = new ModelRenderer(this, 30, 0);
		nose.setRotationPoint(0.0F, 0.0F, 0.0F);
		nose.addBox(-0.5F, -2.0F, -4.0F, 1, 2, 2, 0.0F);
		setRotateAngle(nose, -0.37175513067479216F, -0.0F, 0.0F);
		jaw_top = new ModelRenderer(this, 55, 11);
		jaw_top.setRotationPoint(0.0F, 0.0F, 0.0F);
		jaw_top.addBox(-1.5F, -2.0F, -3.0F, 3, 2, 1, 0.0F);
		head_crest = new ModelRenderer(this, 21, 11);
		head_crest.setRotationPoint(0.0F, 0.0F, 0.0F);
		head_crest.addBox(-0.5F, -5.0F, -2.0F, 1, 4, 5, 0.0F);
		right_arm_2 = new ModelRenderer(this, 0, 12);
		right_arm_2.setRotationPoint(0.0F, 0.0F, -4.0F);
		right_arm_2.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
		setRotateAngle(right_arm_2, -0.9599310885968813F, -0.0F, 0.0F);
		right_leg_2 = new ModelRenderer(this, 40, 20);
		right_leg_2.setRotationPoint(0.0F, 0.0F, -3.5F);
		right_leg_2.addBox(-1.0F, -0.5F, -1.0F, 2, 6, 2, 0.0F);
		setRotateAngle(right_leg_2, -0.6108652381980153F, 0.0F, 0.0F);
		tail_1 = new ModelRenderer(this, 0, 5);
		tail_1.setRotationPoint(0.0F, 1.5F, 0.2F);
		tail_1.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
		left_toe_out = new ModelRenderer(this, 18, 20);
		left_toe_out.setRotationPoint(0.0F, 4.0F, 0.0F);
		left_toe_out.addBox(0.0F, -0.5F, -3.0F, 1, 2, 2, 0.0F);
		setRotateAngle(left_toe_out, -0.0F, -0.17453292519943295F, 0.0F);
		jaw_bottom = new ModelRenderer(this, 46, 11);
		jaw_bottom.setRotationPoint(0.0F, 0.0F, 0.0F);
		jaw_bottom.addBox(-1.5F, -1.0F, -3.0F, 3, 1, 3, 0.0F);
		setRotateAngle(jaw_bottom, 0.6108652381980153F, -0.0F, 0.0F);
		left_thumb = new ModelRenderer(this, 8, 12);
		left_thumb.setRotationPoint(-1.1F, 0.3F, -0.8F);
		left_thumb.addBox(-0.5F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
		setRotateAngle(left_thumb, 2.705260340591211F, 0.4363323129985824F, 2.443460952792061F);
		body_main = new ModelRenderer(this, 14, 0);
		body_main.setRotationPoint(0.0F, 0.0F, 0.0F);
		body_main.addBox(-3.0F, -5.0F, -3.0F, 6, 5, 4, 0.0F);
		setRotateAngle(body_main, -0.045553093477052F, 0.0F, 0.0F);
		left_horn = new ModelRenderer(this, 11, 0);
		left_horn.setRotationPoint(0.0F, 0.0F, 0.0F);
		left_horn.addBox(-0.2F, -6.0F, -1.9F, 1, 2, 1, 0.0F);
		setRotateAngle(left_horn, 0.3490658503988659F, -0.3490658503988659F, 0.0F);
		left_finger_in = new ModelRenderer(this, 52, 15);
		left_finger_in.setRotationPoint(-0.9F, -0.5F, -2.5F);
		left_finger_in.addBox(-0.5F, -0.3F, -2.0F, 1, 1, 2, 0.0F);
		setRotateAngle(left_finger_in, 0.5235987755982988F, 0.0F, 0.0F);
		right_leg_2.addChild(right_toe_out);
		head_main.addChild(right_cheek);
		body_main.addChild(left_arm_1);
		left_leg1.addChild(left_leg_2);
		right_hand.addChild(right_thumb);
		bum.addChild(left_leg1);
		right_hand.addChild(right_finger_in);
		bum.addChild(right_leg_1);
		body_main.addChild(right_arm_1);
		head_main.addChild(left_cheek);
		right_leg_2.addChild(right_toe_in);
		tail_2.addChild(tail_3);
		right_arm_2.addChild(right_hand);
		left_leg_2.addChild(left_toe_in);
		head_main.addChild(brow);
		head_main.addChild(right_horn);
		tail_1.addChild(tail_2);
		body_main.addChild(head_main);
		left_arm_1.addChild(left_arm_2);
		left_hand.addChild(left_finger_out);
		left_arm_2.addChild(left_hand);
		right_hand.addChild(right_finger_out);
		head_main.addChild(nose);
		head_main.addChild(jaw_top);
		head_main.addChild(head_crest);
		right_arm_1.addChild(right_arm_2);
		right_leg_1.addChild(right_leg_2);
		bum.addChild(tail_1);
		left_leg_2.addChild(left_toe_out);
		head_main.addChild(jaw_bottom);
		left_hand.addChild(left_thumb);
		bum.addChild(body_main);
		head_main.addChild(left_horn);
		left_hand.addChild(left_finger_in);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float scale) {
		bum.render(scale);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float unitPixel, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch, unitPixel, entity);
		EntityOreTroll troll = (EntityOreTroll) entity;
		float flap = MathHelper.sin((troll.ticksExisted) * 0.3F) * 0.8F;
		head_main.rotateAngleY = 0F + MathHelper.sin((rotationYaw / (180F / (float) Math.PI)) * 0.5F);
		head_main.rotateAngleX = -0.6108652381980153F + MathHelper.sin((rotationPitch / (180F / (float) Math.PI)) * 0.5F) + flap * 0.025F;
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAngle, float partialRenderTicks) {
		EntityOreTroll troll = (EntityOreTroll) entity;
		float swing = MathHelper.sin(limbSwing * 0.7F) * 1.2F * limbSwingAngle;
		float flap = MathHelper.sin((troll.ticksExisted) * 0.3F) * 0.8F;
		float flapJaw = MathHelper.sin((troll.ticksExisted) * 0.6F) * 0.8F;
		
		tail_1.rotateAngleZ = flap *0.2F;
		tail_2.rotateAngleZ = flap *0.2F;
		tail_3.rotateAngleZ = flap *0.2F;

		tail_1.rotateAngleX = 0.6108652381980153F - swing * 0.25F;
		tail_2.rotateAngleX = 0.2617993877991494F - swing * 0.5F;
		tail_3.rotateAngleX = 0.2617993877991494F - swing * 0.75F;

		left_arm_1.rotateAngleZ = 0F + flap * 0.125F;
		right_arm_1.rotateAngleZ = 0F - flap * 0.125F;
		
		left_arm_2.rotateAngleX = -0.9599310885968813F + flap * 0.3125F - swing;
		right_arm_2.rotateAngleX = -0.9599310885968813F - flap * 0.3125F + swing;

		left_arm_1.rotateAngleY = -0.3981317007977318F + flap * 0.0625F;
		right_arm_1.rotateAngleY = 0.3981317007977318F - flap * 0.0625F;

		right_leg_1.rotateAngleX = -0.08726646259971647F - swing * 1.5F;
		left_leg1.rotateAngleX = -0.08726646259971647F  + swing * 1.5F;

		right_leg_2.rotateAngleX = -0.3806784082777886F + swing * 1.5F;
		left_leg_2.rotateAngleX = -0.3806784082777886F - swing * 1.5F;

		head_main.rotateAngleX = -0.6108652381980153F + flap * 0.05F;
		
		body_main.rotateAngleX = -0.045553093477052F - flap * 0.05F;
		body_main.rotateAngleY = 0F + swing * 0.5F;

		bum.rotateAngleX = 0.6108652381980153F - swing * 0.125F;
		bum.rotateAngleY = 0F - swing * 1F;
		
		jaw_bottom.rotateAngleX = 0.5235987755982988F + flapJaw * 0.2F;
		
		left_finger_in.rotateAngleX = 0.5235987755982988F - flap * 0.5F;
		left_finger_out.rotateAngleX = 0.5235987755982988F - flap * 0.5F;
		
		right_finger_in.rotateAngleX = 0.5235987755982988F + flap * 0.5F;
		right_finger_out.rotateAngleX = 0.5235987755982988F + flap * 0.5F;
		
		left_thumb.rotateAngleZ = 2.443460952792061F + flap * 0.5F;
		right_thumb.rotateAngleZ = 0.6981317007977318F + flap * 0.5F;
		
		left_hand.rotateAngleX = 0.625F - flap * 0.2F;
		right_hand.rotateAngleX = 0.625F + flap * 0.2F;
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
