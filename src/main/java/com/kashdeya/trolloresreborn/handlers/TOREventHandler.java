package com.kashdeya.trolloresreborn.handlers;

import java.util.Random;

import com.kashdeya.trolloresreborn.entity.EntityOreTroll;
import com.kashdeya.trolloresreborn.entity.EntitySmallWither;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
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

			if(flag && event.getWorld().rand.nextFloat() < ConfigHandler.CHANCE * (ConfigHandler.FORTUNE_MULTIPLIER ? event.getFortuneLevel() + 1F : 1F))
			{
				Random rand = new Random();
				int low = 0;
				int high = 100;
				int results = rand.nextInt(high-low) + low;

				if ((ConfigHandler.TROLL_PRECENT  > results) && event.getWorld().getGameRules().getBoolean("doTileDrops"))
	        	{
					for (int i = 0; i < ConfigHandler.TROLL_SPAWN; i++) {
						EntityOreTroll troll = new EntityOreTroll(event.getWorld());
						BlockPos pos = event.getPos();
						troll.setPosition(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
						
						ItemStack stack = new ItemStack(catchFuckingShitMojangIdeasThatCanEasilyBeDoneBetter(event.getState().getBlock()), 1, blockMeta);

						if (!stack.isEmpty()) {
							ItemStack stack2 = stack.copy();
							if (stack.hasTagCompound())
								stack2.setTagCompound(stack.getTagCompound());
							troll.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack2);
							troll.setDropChance(EntityEquipmentSlot.MAINHAND, 0F);

							for (int index = 0; index < event.getDrops().size(); ++index) {
								ItemStack stack3 = event.getDrops().get(index);
								ItemStack stack4 = stack3.copy();
								if (stack3.hasTagCompound())
									stack4.setTagCompound(stack3.getTagCompound());
								if(index > troll.inventory.size())
									InventoryHelper.spawnItemStack(event.getWorld(), (int) event.getPos().getX(), (int) event.getPos().getY(), (int) event.getPos().getZ(), stack4);
								else
									troll.setContents(stack4, index);
							}
							event.setDropChance(0F);
						}

						event.getWorld().spawnEntity(troll);
						breakSurroundingBlocks(event.getWorld(), event.getHarvester(), troll);
						troll.onInitialSpawn(event.getWorld().getDifficultyForLocation(troll.getPosition()), (IEntityLivingData) null);
					}
				}
				else if ((results > ConfigHandler.TROLL_PRECENT) && event.getWorld().getGameRules().getBoolean("doTileDrops") && ConfigHandler.ENABLE_WITHER)
	        	{
					EntitySmallWither wither = new EntitySmallWither(event.getWorld());
					BlockPos pos = event.getPos();
					wither.setPosition(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
					event.getWorld().spawnEntity(wither);
					wither.onInitialSpawn(event.getWorld().getDifficultyForLocation(wither.getPosition()), (IEntityLivingData) null);
					if(!(event.getHarvester() instanceof FakePlayer))
						wither.setAttackTarget(event.getHarvester());
	        	}
			}
		}
	}

	private void breakSurroundingBlocks(World world, EntityPlayer player, EntityOreTroll troll) {
		if (!player.isCreative() && !player.isSpectator()) {
			BlockPos trollPos = troll.getPosition();
			for (int x = -1; x < 2; x++)
				for (int y = -1; y < 2; y++)
					for (int z = -1; z < 2; z++) {
						IBlockState state = world.getBlockState(trollPos.add(x, y, z));
						if (trollPos.add(x, y, z) != trollPos.add(0, 1, 0) && state.getBlockHardness(world, trollPos.add(x, y, z)) < ConfigHandler.BLOCK_HARDNESS && state.getBlock() != Blocks.BEDROCK) {
							world.destroyBlock(trollPos.add(x, y, z), true);
							if (state != null && state != Blocks.AIR)
								world.playEvent(null, 2001, new BlockPos(trollPos.add(x, y, z)), Block.getIdFromBlock(state.getBlock()));
							world.notifyNeighborsOfStateChange(trollPos.add(x, y, z), state.getBlock(), true);
						}
					}
		}
	}

	final Block catchFuckingShitMojangIdeasThatCanEasilyBeDoneBetter(Block block) {
		if (block == Blocks.LIT_REDSTONE_ORE) {
			block = Blocks.REDSTONE_ORE;
		}
		return block;
	}
}
