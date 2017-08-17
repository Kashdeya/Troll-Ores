package com.kashdeya.trolloresreborn.blocks;

import java.util.Random;

import com.kashdeya.trolloresreborn.ref.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TrollBlock extends Block {
	
	public TrollBlock()
	{
		super(Material.ROCK);
		this.setHardness(1F);
		this.setResistance(5F);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID.toLowerCase(), "troll_ore"));
		this.setUnlocalizedName(this.getRegistryName().toString());
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
	}
	
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
