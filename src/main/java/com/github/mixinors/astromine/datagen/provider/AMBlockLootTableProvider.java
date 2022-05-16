/*
 * MIT License
 *
 * Copyright (c) 2020 - 2022 Mixinors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mixinors.astromine.datagen.provider;

import com.github.mixinors.astromine.common.block.base.BlockWithEntity;
import com.github.mixinors.astromine.datagen.AMDatagen;
import com.github.mixinors.astromine.datagen.family.block.AMBlockFamilies;
import com.github.mixinors.astromine.datagen.family.material.MaterialFamilies;
import com.github.mixinors.astromine.datagen.family.material.MaterialFamily;
import com.github.mixinors.astromine.datagen.family.material.variant.ItemVariant;
import com.github.mixinors.astromine.registry.common.AMBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTablesProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyNameLootFunction;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

import java.util.List;

public class AMBlockLootTableProvider extends FabricBlockLootTablesProvider {
	public static final List<Block> DROPS_SELF = List.of(
			AMBlocks.BLAZING_ASTEROID_STONE.get(),
			
			AMBlocks.FLUID_PIPE.get(),
			
			AMBlocks.ITEM_CONDUIT.get(),
			
			AMBlocks.PRIMITIVE_ENERGY_CABLE.get(),
			AMBlocks.BASIC_ENERGY_CABLE.get(),
			AMBlocks.ADVANCED_ENERGY_CABLE.get(),
			AMBlocks.ELITE_ENERGY_CABLE.get(),
			
			AMBlocks.HOLOGRAPHIC_BRIDGE_PROJECTOR.get(),
			
			AMBlocks.NUCLEAR_WARHEAD.get(),
			
			AMBlocks.AIRLOCK.get(),
			
			AMBlocks.DRAIN.get()
	);
	
	public AMBlockLootTableProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}
	
	public static LootTable.Builder machineDrops(Block drop) {
		if (drop instanceof BlockWithEntity machine && machine.saveTagToDroppedItem()) {
			LootTable.Builder builder = LootTable.builder().pool(addSurvivesExplosionCondition(machine, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f)).with(ItemEntry.builder(machine))));
			
			CopyNbtLootFunction.Builder copyNbtBuilder = CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY);
			BlockWithEntity.SavedData savedData = machine.getSavedDataForDroppedItem();
			if (savedData.redstoneControl()) {
				copyNbtBuilder = copyNbtBuilder.withOperation("RedstoneControl", "BlockEntityTag.RedstoneControl");
			}
			if (savedData.energyStorage()) {
				copyNbtBuilder = copyNbtBuilder.withOperation("EnergyStorage", "BlockEntityTag.EnergyStorage");
			}
			if (savedData.itemStorage()) {
				copyNbtBuilder = copyNbtBuilder.withOperation("ItemStorage", "BlockEntityTag.ItemStorage");
			}
			if (savedData.fluidStorage()) {
				copyNbtBuilder = copyNbtBuilder.withOperation("FluidStorage", "BlockEntityTag.FluidStorage");
			}
			
			builder.apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY)).apply(copyNbtBuilder);
			return builder;
		}
		return drops(drop);
	}
	
	@Override
	protected void generateBlockLootTables() {
		MaterialFamilies.getFamilies().filter(MaterialFamily::shouldGenerateLootTables).forEachOrdered((family) ->
				family.getBlockVariants().forEach((variant, block) -> {
					if (family.shouldGenerateLootTable(variant)) {
						switch (variant) {
							case BLOCK, RAW_ORE_BLOCK -> addDrop(block);
							case STONE_ORE, DEEPSLATE_ORE, NETHER_ORE -> {
								Item drop;
								if (family.hasVariant(ItemVariant.RAW_ORE)) {
									drop = family.getVariant(ItemVariant.RAW_ORE);
								} else {
									drop = family.getBaseItem();
								}
								addDrop(block, (b) -> oreDrops(block, drop));
							}
							case METEOR_ORE -> this.addDrop(block, oreDrops(block, family.getVariant(ItemVariant.METEOR_ORE_CLUSTER)));
							case ASTEROID_ORE -> this.addDrop(block, oreDrops(block, family.getVariant(ItemVariant.ASTEROID_ORE_CLUSTER)));
						}
					}
				})
		);
		
		AMBlockFamilies.getFamilies().forEachOrdered((family) -> {
			addDrop(family.getBaseBlock());
			family.getVariants().forEach((variant, block) -> {
				switch (variant) {
					case DOOR -> addDoorDrop(block);
					case SLAB -> addDrop(block, BlockLootTableGenerator::slabDrops);
					default -> addDrop(block);
				}
			});
		});
		
		DROPS_SELF.forEach(this::addDrop);
		
		addDoorDrop(AMBlocks.AIRLOCK.get());
		
		AMDatagen.MACHINES.forEach((block) -> this.addDrop(block, machineDrops(block)));
	}
}