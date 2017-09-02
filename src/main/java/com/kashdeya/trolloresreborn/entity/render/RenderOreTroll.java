package com.kashdeya.trolloresreborn.entity.render;

import com.kashdeya.trolloresreborn.entity.EntityOreTroll;
import com.kashdeya.trolloresreborn.entity.model.ModelOreTroll;
import com.kashdeya.trolloresreborn.ref.Reference;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderOreTroll extends RenderLiving<EntityOreTroll> {
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/entity/ore_troll.png");

	public RenderOreTroll(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelOreTroll(), 0.4F);
        this.addLayer(new LayerOreTroll(this));
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityOreTroll entity) {
		return TEXTURE;
	}
}
