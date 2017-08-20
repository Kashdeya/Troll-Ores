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
		if(event.isSilkTouching() && ConfigHandler.SILK_IMMUNITY)
		{
			return;
		}
		
		String blockID = Block.REGISTRY.getNameForObject(event.getState().getBlock()).toString();
		int blockMeta = event.getState().getBlock().getMetaFromState(event.getState());
		boolean custom = ConfigHandler.EXTRA_ORES.contains(blockID) || ConfigHandler.EXTRA_ORES.contains(blockID + ":" + blockMeta);
		
		if(!event.getWorld().isRemote && event.getHarvester() != null && (ConfigHandler.FAKE_PLAYERS || !(event.getHarvester() instanceof FakePlayer)))
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

			if(flag && (event.getState().getBlock() == TrollOresReborn.TROLL_ORE || event.getWorld().rand.nextFloat() < ConfigHandler.CHANCE * (ConfigHandler.FORTUNE_MULTIPLIER ? event.getFortuneLevel() + 1F : 1F)))
			{
				Random rand = new Random();
				int low = 0;
				int high = 100;
				int results = rand.nextInt(high-low) + low;
				
				if ((results  <= ConfigHandler.TROLL_PRECENT) && event.getWorld().getGameRules().getBoolean("doTileDrops"))
	        	{
					for (int i = 0; i < ConfigHandler.TROLL_SPAWN; i++) {
					EntityOreTroll troll = new EntityOreTroll(event.getWorld());
					BlockPos pos = event.getPos();
					troll.setPosition(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
					event.getWorld().spawnEntityInWorld(troll);
					if (ConfigHandler.TROLL_EXPLOSION){
						troll.spawnExplosionParticle();
					}
					if (ConfigHandler.TROLL_SPRINTING){
						troll.setSprinting(true);
						troll.isSprinting();
					}
					if (ConfigHandler.SILENT_TROLL){
						troll.setSilent(true);
					}
					troll.setCustomNameTag(ConfigHandler.TROLL_NAME);
					troll.getAlwaysRenderNameTag();
					}
	        	}
				else if ((results >= ConfigHandler.TROLL_PRECENT) && event.getWorld().getGameRules().getBoolean("doTileDrops") && ConfigHandler.ENABLE_WITHER)
	        	{
					EntityWither wither = new EntityWither(event.getWorld());
					BlockPos pos = event.getPos();
					wither.setPosition(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
					event.getWorld().spawnEntityInWorld(wither);
					if (ConfigHandler.WITHER_IGNITE){
						wither.ignite();
					}
					wither.renderYawOffset = 75.0F;
					if (ConfigHandler.SILENT_WITHER){
						wither.setSilent(true);
					}
					wither.setCustomNameTag(ConfigHandler.WITHER_NAME);
					wither.getAlwaysRenderNameTag();
	        	}
			}
		}
	}
}
