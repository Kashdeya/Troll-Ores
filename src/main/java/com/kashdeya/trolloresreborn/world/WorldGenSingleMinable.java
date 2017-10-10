package com.kashdeya.trolloresreborn.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.google.common.base.Predicate;

public class WorldGenSingleMinable extends WorldGenerator {
	private IBlockState block;
	private Predicate<IBlockState> target;

	public WorldGenSingleMinable(IBlockState block, Predicate<IBlockState> target)
	{
		this.block = block;
		this.target = target;
		}
	
	public WorldGenSingleMinable(IBlockState block)
	{
		this(block, BlockMatcher.forBlock(Blocks.STONE));
		}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		if (world.getBlockState(pos).getBlock().isReplaceableOreGen(this.block, world, pos, this.target)) {
			world.setBlockState(pos, this.block);
			}
		return true;
		}
	}