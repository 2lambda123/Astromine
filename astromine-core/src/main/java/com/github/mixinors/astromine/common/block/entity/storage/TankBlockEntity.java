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

package com.github.mixinors.astromine.common.block.entity.storage;

import java.util.function.Supplier;

import com.github.mixinors.astromine.common.block.entity.base.ExtendedBlockEntity;
import com.github.mixinors.astromine.common.config.AMConfig;
import com.github.mixinors.astromine.common.config.entry.tiered.TankConfig;
import com.github.mixinors.astromine.common.provider.config.tiered.TankConfigProvider;
import com.github.mixinors.astromine.common.transfer.storage.SimpleFluidStorage;
import com.github.mixinors.astromine.common.transfer.storage.SimpleItemStorage;
import com.github.mixinors.astromine.common.util.data.tier.MachineTier;
import com.github.mixinors.astromine.registry.common.AMBlockEntityTypes;
import org.jetbrains.annotations.NotNull;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;

public abstract class TankBlockEntity extends ExtendedBlockEntity implements TankConfigProvider {
	private static final int FLUID_INPUT_SLOT = 0;
	
	private static final int FLUID_OUTPUT_SLOT = 0;
	
	private static final int[] FLUID_INSERT_SLOTS = new int[] { FLUID_INPUT_SLOT };
	
	private static final int[] FLUID_EXTRACT_SLOTS = new int[] { FLUID_OUTPUT_SLOT };
	
	private static final int ITEM_INPUT_SLOT = 0;
	
	private static final int ITEM_OUTPUT_SLOT_1 = 1;
	
	private static final int ITEM_OUTPUT_SLOT_2 = 2;
	
	private static final int[] ITEM_INSERT_SLOTS = new int[] { ITEM_INPUT_SLOT };
	
	private static final int[] ITEM_EXTRACT_SLOTS = new int[] {ITEM_OUTPUT_SLOT_1, ITEM_OUTPUT_SLOT_2 };
	
	public TankBlockEntity(Supplier<? extends BlockEntityType<?>> type, BlockPos blockPos, BlockState blockState) {
		super(type, blockPos, blockState);
		
		fluidStorage = new SimpleFluidStorage(1, getFluidStorageSize()).insertPredicate((variant, slot) ->
			slot == FLUID_INPUT_SLOT
		).extractPredicate((variant, slot) ->
			slot == FLUID_OUTPUT_SLOT
		).insertSlots(FLUID_INSERT_SLOTS).extractSlots(FLUID_EXTRACT_SLOTS);
		
		itemStorage = new SimpleItemStorage(3).extractPredicate((variant, slot) ->
			slot == ITEM_OUTPUT_SLOT_1 || slot == ITEM_OUTPUT_SLOT_2
		).insertPredicate((variant, slot) -> {
			if (slot != ITEM_INPUT_SLOT) {
				return false;
			}
			
			return FluidStorage.ITEM.getProvider(variant.getItem()) != null;
		}).insertSlots(ITEM_INSERT_SLOTS).extractSlots(ITEM_EXTRACT_SLOTS);
		
		fluidStorage.getStorage(FLUID_INPUT_SLOT).setCapacity(getFluidStorageSize());
	}

	private Fluid filter = Fluids.EMPTY;

	public Fluid getFilter() {
		return filter;
	}

	public void setFilter(Fluid filter) {
		this.filter = filter;
	}

	@Override
	public void tick() {
		super.tick();

		if (world == null || world.isClient || !shouldRun())
			return;
		
		var unloadStack = itemStorage.getStack(ITEM_INPUT_SLOT);
		var unloadItemStorage = itemStorage.getStorage(ITEM_INPUT_SLOT);
		var unloadFluidStorage = FluidStorage.ITEM.find(unloadStack, ContainerItemContext.ofSingleSlot(itemStorage.getStorage(ITEM_INPUT_SLOT)));
		
		var middleItemStorage = itemStorage.getStorage(ITEM_OUTPUT_SLOT_1);
		
		var loadStack = itemStorage.getStack(ITEM_OUTPUT_SLOT_2);
		var loadFluidStorage = FluidStorage.ITEM.find(loadStack, ContainerItemContext.ofSingleSlot(itemStorage.getStorage(ITEM_OUTPUT_SLOT_2)));
		
		try (var transaction = Transaction.openOuter()) {
			StorageUtil.move(unloadFluidStorage, fluidStorage.getStorage(FLUID_INPUT_SLOT), fluidVariant -> true, FluidConstants.BUCKET, transaction);
			StorageUtil.move(fluidStorage.getStorage(FLUID_OUTPUT_SLOT), loadFluidStorage, fluidVariant -> true, FluidConstants.BUCKET, transaction);
			
			StorageUtil.move(unloadItemStorage, middleItemStorage, (variant) -> {
				var stored = StorageUtil.findStoredResource(unloadFluidStorage, transaction);
				return stored == null || stored.isBlank();
			}, 1, transaction);
			
			transaction.commit();
		}
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		nbt.putString("Fluid", Registry.FLUID.getId(filter).toString());
		
		super.writeNbt(nbt);
	}

	@Override
	public void readNbt(@NotNull NbtCompound nbt) {
		Registry.FLUID.getOrEmpty(new Identifier(nbt.getString("Fluid"))).ifPresent(filter -> this.filter = filter);

		super.readNbt(nbt);
	}

	@Override
	public TankConfig getConfig() {
		return AMConfig.get().tanks;
	}

	public static class Primitive extends TankBlockEntity {
		public Primitive(BlockPos blockPos, BlockState blockState) {
			super(AMBlockEntityTypes.PRIMITIVE_TANK, blockPos, blockState);
		}

		@Override
		public MachineTier getMachineTier() {
			return MachineTier.PRIMITIVE;
		}
	}

	public static class Basic extends TankBlockEntity {
		public Basic(BlockPos blockPos, BlockState blockState) {
			super(AMBlockEntityTypes.BASIC_TANK, blockPos, blockState);
		}

		@Override
		public MachineTier getMachineTier() {
			return MachineTier.BASIC;
		}
	}

	public static class Advanced extends TankBlockEntity {
		public Advanced(BlockPos blockPos, BlockState blockState) {
			super(AMBlockEntityTypes.ADVANCED_TANK, blockPos, blockState);
		}

		@Override
		public MachineTier getMachineTier() {
			return MachineTier.ADVANCED;
		}
	}

	public static class Elite extends TankBlockEntity {
		public Elite(BlockPos blockPos, BlockState blockState) {
			super(AMBlockEntityTypes.ELITE_TANK, blockPos, blockState);
		}

		@Override
		public MachineTier getMachineTier() {
			return MachineTier.ELITE;
		}
	}

	public static class Creative extends TankBlockEntity {
		public Creative(BlockPos blockPos, BlockState blockState) {
			super(AMBlockEntityTypes.CREATIVE_TANK, blockPos, blockState);
		}

		@Override
		public MachineTier getMachineTier() {
			return MachineTier.CREATIVE;
		}

		@Override
		public void tick() {
			super.tick();

			if (fluidStorage.getStorage(FLUID_OUTPUT_SLOT).getResource().getFluid() != Fluids.EMPTY) {
				try (var transaction = Transaction.openOuter()) {
					fluidStorage.getStorage(FLUID_OUTPUT_SLOT).insert(fluidStorage.getStorage(FLUID_OUTPUT_SLOT).getResource(), getFluidStorageSize(), transaction);

					transaction.commit();
				}
			}
		}
	}
}