package com.kashdeya.trolloresreborn.init;

import java.util.ArrayList;
import java.util.List;

import com.kashdeya.trolloresreborn.blocks.TrollBlock;
import com.kashdeya.trolloresreborn.ref.Reference;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

	public static ItemBlock TROLL_ORE_ITEM;
	public static Block TROLL_ORE;

	public static void init() {
		// Blocks and Item Blocks
		TROLL_ORE = new TrollBlock();
		TROLL_ORE_ITEM = new ItemBlock(TROLL_ORE);
		TROLL_ORE.setRegistryName(Reference.MOD_ID, "troll_ore").setUnlocalizedName("troll_ore");
		TROLL_ORE_ITEM.setRegistryName(TROLL_ORE.getRegistryName()).setUnlocalizedName("troll_ore");
	}

	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandlerBlocks {
		public static final List<Block> BLOCKS = new ArrayList<Block>();
		public static final List<Item> ITEM_BLOCKS = new ArrayList<Item>();
		//Array Lists here make it easy for you to add new shit
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {
			final Block[] blocks = {
					TROLL_ORE
					};
			final IForgeRegistry<Block> registry = event.getRegistry();
			for (final Block block : blocks) {
				registry.register(block);
				BLOCKS.add(block);
			}
		}

		@SubscribeEvent
		public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
			final ItemBlock[] items = {
					TROLL_ORE_ITEM
					};
			final IForgeRegistry<Item> registry = event.getRegistry();
			for (final ItemBlock item : items) {
				registry.register(item);
				ITEM_BLOCKS.add(item);
			}
		}
	}

}
