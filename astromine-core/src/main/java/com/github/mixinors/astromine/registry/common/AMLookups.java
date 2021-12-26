package com.github.mixinors.astromine.registry.common;

import com.github.mixinors.astromine.common.block.entity.*;
import com.github.mixinors.astromine.common.block.entity.base.ExtendedBlockEntity;
import com.github.mixinors.astromine.common.item.base.EnergyStorageItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.entity.BlockEntityType;
import team.reborn.energy.api.EnergyStorage;

public class AMLookups {
	public static void init() {
		ItemStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> {
			if (blockEntity instanceof ExtendedBlockEntity extendedBlockEntity) {
				var itemStorage = extendedBlockEntity.getItemStorage();
				
				var sidings = itemStorage.getSidings();
				
				var siding = sidings[direction.ordinal()];
				
				return switch (siding) {
					case INSERT, EXTRACT, INSERT_EXTRACT -> itemStorage;
					
					default -> null;
				};
			}
			
			return null;
		},
				AMBlockEntityTypes.PRIMITIVE_BUFFER.get(),
				AMBlockEntityTypes.BASIC_BUFFER.get(),
				AMBlockEntityTypes.ADVANCED_BUFFER.get(),
				AMBlockEntityTypes.ELITE_BUFFER.get(),
				AMBlockEntityTypes.CREATIVE_BUFFER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_SOLID_GENERATOR.get(),
				AMBlockEntityTypes.BASIC_SOLID_GENERATOR.get(),
				AMBlockEntityTypes.ADVANCED_SOLID_GENERATOR.get(),
				AMBlockEntityTypes.ELITE_SOLID_GENERATOR.get(),
				
				AMBlockEntityTypes.PRIMITIVE_ELECTRIC_FURNACE.get(),
				AMBlockEntityTypes.BASIC_ELECTRIC_FURNACE.get(),
				AMBlockEntityTypes.ADVANCED_ELECTRIC_FURNACE.get(),
				AMBlockEntityTypes.ELITE_ELECTRIC_FURNACE.get(),
				
				AMBlockEntityTypes.PRIMITIVE_ALLOY_SMELTER.get(),
				AMBlockEntityTypes.BASIC_ALLOY_SMELTER.get(),
				AMBlockEntityTypes.ADVANCED_ALLOY_SMELTER.get(),
				AMBlockEntityTypes.ELITE_ALLOY_SMELTER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_TRITURATOR.get(),
				AMBlockEntityTypes.BASIC_TRITURATOR.get(),
				AMBlockEntityTypes.ADVANCED_TRITURATOR.get(),
				AMBlockEntityTypes.ELITE_TRITURATOR.get(),
				
				AMBlockEntityTypes.PRIMITIVE_PRESSER.get(),
				AMBlockEntityTypes.BASIC_PRESSER.get(),
				AMBlockEntityTypes.ADVANCED_PRESSER.get(),
				AMBlockEntityTypes.ELITE_PRESSER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_WIRE_MILL.get(),
				AMBlockEntityTypes.BASIC_WIRE_MILL.get(),
				AMBlockEntityTypes.ADVANCED_WIRE_MILL.get(),
				AMBlockEntityTypes.ELITE_WIRE_MILL.get(),
				
				AMBlockEntityTypes.PRIMITIVE_SOLIDIFIER.get(),
				AMBlockEntityTypes.BASIC_SOLIDIFIER.get(),
				AMBlockEntityTypes.ADVANCED_SOLIDIFIER.get(),
				AMBlockEntityTypes.ELITE_SOLIDIFIER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_MELTER.get(),
				AMBlockEntityTypes.BASIC_MELTER.get(),
				AMBlockEntityTypes.ADVANCED_MELTER.get(),
				AMBlockEntityTypes.ELITE_MELTER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_CAPACITOR.get(),
				AMBlockEntityTypes.BASIC_CAPACITOR.get(),
				AMBlockEntityTypes.ADVANCED_CAPACITOR.get(),
				AMBlockEntityTypes.ELITE_CAPACITOR.get(),
				
				AMBlockEntityTypes.BLOCK_BREAKER.get(),
				AMBlockEntityTypes.BLOCK_PLACER.get()
		);
		
		FluidStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> {
			if (blockEntity instanceof ExtendedBlockEntity extendedBlockEntity) {
				var fluidStorage = extendedBlockEntity.getFluidStorage();
				
				var sidings = fluidStorage.getSidings();
				
				var siding = sidings[direction.ordinal()];
				
				return switch (siding) {
					case INSERT, EXTRACT, INSERT_EXTRACT -> fluidStorage;
					
					default -> null;
				};
			}
			
			return null;
		},
				AMBlockEntityTypes.PRIMITIVE_TANK.get(),
				AMBlockEntityTypes.BASIC_TANK.get(),
				AMBlockEntityTypes.ADVANCED_TANK.get(),
				AMBlockEntityTypes.ELITE_TANK.get(),
				
				AMBlockEntityTypes.PRIMITIVE_LIQUID_GENERATOR.get(),
				AMBlockEntityTypes.BASIC_LIQUID_GENERATOR.get(),
				AMBlockEntityTypes.ADVANCED_LIQUID_GENERATOR.get(),
				AMBlockEntityTypes.ELITE_LIQUID_GENERATOR.get(),
				
				AMBlockEntityTypes.PRIMITIVE_ELECTROLYZER.get(),
				AMBlockEntityTypes.BASIC_ELECTROLYZER.get(),
				AMBlockEntityTypes.ADVANCED_ELECTROLYZER.get(),
				AMBlockEntityTypes.ELITE_ELECTROLYZER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_REFINERY.get(),
				AMBlockEntityTypes.BASIC_REFINERY.get(),
				AMBlockEntityTypes.ADVANCED_REFINERY.get(),
				AMBlockEntityTypes.ELITE_REFINERY.get(),
				
				AMBlockEntityTypes.PRIMITIVE_FLUID_MIXER.get(),
				AMBlockEntityTypes.BASIC_FLUID_MIXER.get(),
				AMBlockEntityTypes.ADVANCED_FLUID_MIXER.get(),
				AMBlockEntityTypes.ELITE_FLUID_MIXER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_SOLIDIFIER.get(),
				AMBlockEntityTypes.BASIC_SOLIDIFIER.get(),
				AMBlockEntityTypes.ADVANCED_SOLIDIFIER.get(),
				AMBlockEntityTypes.ELITE_SOLIDIFIER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_MELTER.get(),
				AMBlockEntityTypes.BASIC_MELTER.get(),
				AMBlockEntityTypes.ADVANCED_MELTER.get(),
				AMBlockEntityTypes.ELITE_MELTER.get(),
				
				AMBlockEntityTypes.FLUID_EXTRACTOR.get(),
				AMBlockEntityTypes.FLUID_INSERTER.get(),
				
				AMBlockEntityTypes.DRAIN.get()
				);
		
		EnergyStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> {
			if (blockEntity instanceof ExtendedBlockEntity extendedBlockEntity) {
				return extendedBlockEntity.getEnergyStorage();
			}
			
			return null;
		},
				AMBlockEntityTypes.PRIMITIVE_SOLID_GENERATOR.get(),
				AMBlockEntityTypes.BASIC_SOLID_GENERATOR.get(),
				AMBlockEntityTypes.ADVANCED_SOLID_GENERATOR.get(),
				AMBlockEntityTypes.ELITE_SOLID_GENERATOR.get(),
				
				AMBlockEntityTypes.PRIMITIVE_LIQUID_GENERATOR.get(),
				AMBlockEntityTypes.BASIC_LIQUID_GENERATOR.get(),
				AMBlockEntityTypes.ADVANCED_LIQUID_GENERATOR.get(),
				AMBlockEntityTypes.ELITE_LIQUID_GENERATOR.get(),
				
				AMBlockEntityTypes.PRIMITIVE_ELECTRIC_FURNACE.get(),
				AMBlockEntityTypes.BASIC_ELECTRIC_FURNACE.get(),
				AMBlockEntityTypes.ADVANCED_ELECTRIC_FURNACE.get(),
				AMBlockEntityTypes.ELITE_ELECTRIC_FURNACE.get(),
				
				AMBlockEntityTypes.PRIMITIVE_ALLOY_SMELTER.get(),
				AMBlockEntityTypes.BASIC_ALLOY_SMELTER.get(),
				AMBlockEntityTypes.ADVANCED_ALLOY_SMELTER.get(),
				AMBlockEntityTypes.ELITE_ALLOY_SMELTER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_TRITURATOR.get(),
				AMBlockEntityTypes.BASIC_TRITURATOR.get(),
				AMBlockEntityTypes.ADVANCED_TRITURATOR.get(),
				AMBlockEntityTypes.ELITE_TRITURATOR.get(),
				
				AMBlockEntityTypes.PRIMITIVE_PRESSER.get(),
				AMBlockEntityTypes.BASIC_PRESSER.get(),
				AMBlockEntityTypes.ADVANCED_PRESSER.get(),
				AMBlockEntityTypes.ELITE_PRESSER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_WIRE_MILL.get(),
				AMBlockEntityTypes.BASIC_WIRE_MILL.get(),
				AMBlockEntityTypes.ADVANCED_WIRE_MILL.get(),
				AMBlockEntityTypes.ELITE_WIRE_MILL.get(),
				
				AMBlockEntityTypes.PRIMITIVE_ELECTROLYZER.get(),
				AMBlockEntityTypes.BASIC_ELECTROLYZER.get(),
				AMBlockEntityTypes.ADVANCED_ELECTROLYZER.get(),
				AMBlockEntityTypes.ELITE_ELECTROLYZER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_REFINERY.get(),
				AMBlockEntityTypes.BASIC_REFINERY.get(),
				AMBlockEntityTypes.ADVANCED_REFINERY.get(),
				AMBlockEntityTypes.ELITE_REFINERY.get(),
				
				AMBlockEntityTypes.PRIMITIVE_FLUID_MIXER.get(),
				AMBlockEntityTypes.BASIC_FLUID_MIXER.get(),
				AMBlockEntityTypes.ADVANCED_FLUID_MIXER.get(),
				AMBlockEntityTypes.ELITE_FLUID_MIXER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_SOLIDIFIER.get(),
				AMBlockEntityTypes.BASIC_SOLIDIFIER.get(),
				AMBlockEntityTypes.ADVANCED_SOLIDIFIER.get(),
				AMBlockEntityTypes.ELITE_SOLIDIFIER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_MELTER.get(),
				AMBlockEntityTypes.BASIC_MELTER.get(),
				AMBlockEntityTypes.ADVANCED_MELTER.get(),
				AMBlockEntityTypes.ELITE_MELTER.get(),
				
				AMBlockEntityTypes.PRIMITIVE_CAPACITOR.get(),
				AMBlockEntityTypes.BASIC_CAPACITOR.get(),
				AMBlockEntityTypes.ADVANCED_CAPACITOR.get(),
				AMBlockEntityTypes.ELITE_CAPACITOR.get(),
				AMBlockEntityTypes.CREATIVE_CAPACITOR.get(),
				
				AMBlockEntityTypes.FLUID_EXTRACTOR.get(),
				AMBlockEntityTypes.FLUID_INSERTER.get(),
				
				AMBlockEntityTypes.BLOCK_BREAKER.get(),
				AMBlockEntityTypes.BLOCK_PLACER.get()
		);
	}
}
