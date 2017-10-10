package com.kashdeya.trolloresreborn.world;

import java.util.Random;

import com.kashdeya.trolloresreborn.init.TrollOresReborn;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldGen
{
	@SubscribeEvent
	public void onGenerate(PopulateChunkEvent.Pre event)
	{
		generateOre(event.getWorld(), new Random(), event.getChunkX() * 16, event.getChunkZ() * 16);
	}
	
	private void generateOre(World world, Random rand, int x, int z)
	{
		for(int k = 0; k < 4; k++)
		{
			int firstBlockXCoord = x + rand.nextInt(16);
			int firstBlockYCoord = rand.nextInt(64);
			int firstBlockZCoord = z + rand.nextInt(16);

			(new WorldGenMinable(TrollOresReborn.TROLL_ORE.getDefaultState(), 3)).generate(world, rand, new BlockPos(firstBlockXCoord, firstBlockYCoord, firstBlockZCoord));
		}
	}
	
}
