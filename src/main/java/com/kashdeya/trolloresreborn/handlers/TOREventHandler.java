package com.kashdeya.trolloresreborn.handlers;

import java.util.Random;

import com.kashdeya.trolloresreborn.entity.EntityOreTroll;
import com.kashdeya.trolloresreborn.init.TrollOresReborn;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TOREventHandler {
	
	public World lastWorld;
	public int lastX = 0;
	public int lastY = 0;
	public int lastZ = 0;
	
	@SubscribeEvent
	public void onHarvest(HarvestDropsEvent event)
	{
		if(event.isSilkTouching() && ConfigSettings.silkImmunity)
		{
			return;
		}
		
		String blockID = Block.REGISTRY.getNameForObject(event.getState().getBlock()).toString();
		int blockMeta = event.getState().getBlock().getMetaFromState(event.getState());
		boolean custom = ConfigSettings.extraOres.contains(blockID) || ConfigSettings.extraOres.contains(blockID + ":" + blockMeta);
		
		if(!event.getWorld().isRemote && event.getHarvester() != null && (ConfigSettings.fakePlayers || !(event.getHarvester() instanceof FakePlayer)))
		{
			String[] nameParts = event.getState().getBlock().getUnlocalizedName().split("\\.");
			
			boolean flag = event.getState().getBlock() instanceof BlockOre || custom;
			
			if(!flag)
			{
				for(String part : nameParts)
				{
					if(part.toLowerCase().startsWith("ore") || part.toLowerCase().endsWith("ore"))
					{
						flag = true;
						break;
					}
				}
			}

			if(flag && (event.getState().getBlock() == TrollOresReborn.TROLL_ORE || event.getWorld().rand.nextFloat() < ConfigSettings.chance * (ConfigSettings.fortuneMult? event.getFortuneLevel() + 1F : 1F)))
			{
				Random rand = new Random();
				int low = 0;
				int high = 100;
				int results = rand.nextInt(high-low) + low;
				
				if ((results  <= ConfigSettings.silverfishpercent) && event.getWorld().getGameRules().getBoolean("doTileDrops"))
	        	{
					for (int i = 0; i < ConfigSettings.silverfishSpawn; i++) {
					//EntitySilverfish fish = new EntitySilverfish(event.getWorld());
					EntityOreTroll fish = new EntityOreTroll(event.getWorld());
					BlockPos pos = event.getPos();
					fish.setPosition(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
					event.getWorld().spawnEntityInWorld(fish);
					if (ConfigSettings.fishExplosion){
						fish.spawnExplosionParticle();
					}
					if (ConfigSettings.fishSprinting){
						fish.setSprinting(true);
						fish.isSprinting();
					}
					if (ConfigSettings.silentFish){
						fish.setSilent(true);
					}
					fish.setCustomNameTag(ConfigSettings.silverfishName);
					fish.getAlwaysRenderNameTag();
					}
	        	}
				else if ((results >= ConfigSettings.silverfishpercent) && event.getWorld().getGameRules().getBoolean("doTileDrops") && ConfigSettings.enableWither)
	        	{
					EntityWither wither = new EntityWither(event.getWorld());
					BlockPos pos = event.getPos();
					wither.setPosition(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
					event.getWorld().spawnEntityInWorld(wither);
					if (ConfigSettings.witherIgnite){
						wither.ignite();
					}
					wither.renderYawOffset = 75.0F;
					if (ConfigSettings.silentWither){
						wither.setSilent(true);
					}
					wither.setCustomNameTag(ConfigSettings.witherName);
					wither.getAlwaysRenderNameTag();
	        	}
			}
		}
	}
}
