/*
 * MIT License
 *
 * Copyright (c) 2020, 2021 Mixinors
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

package com.github.mixinors.astromine.common.block.entity;

import com.github.mixinors.astromine.common.block.entity.base.ExtendedBlockEntity;
import com.github.mixinors.astromine.common.transfer.storage.SimpleItemStorage;
import com.github.mixinors.astromine.common.transfer.storage.SimpleItemVariantStorage;
import com.github.mixinors.astromine.registry.common.AMBlockEntityTypes;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import com.github.mixinors.astromine.registry.common.AMConfig;
import com.github.mixinors.astromine.common.block.entity.machine.EnergyConsumedProvider;
import com.github.mixinors.astromine.common.block.entity.machine.EnergySizeProvider;
import com.github.mixinors.astromine.common.block.entity.machine.SpeedProvider;
import org.jetbrains.annotations.NotNull;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class BlockPlacerBlockEntity extends ExtendedBlockEntity implements EnergySizeProvider, SpeedProvider, EnergyConsumedProvider {
	private long cooldown = 0L;
	
	public static final int INPUT_SLOT = 0;
	
	public static final int[] INSERT_SLOTS = new int[] { INPUT_SLOT };
	
	public static final int[] EXTRACT_SLOTS = new int[] { };

	public BlockPlacerBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(AMBlockEntityTypes.BLOCK_PLACER, blockPos, blockState);
		
		energyStorage = new SimpleEnergyStorage(getEnergySize(), Long.MAX_VALUE, Long.MAX_VALUE);
		
		itemStorage = new SimpleItemStorage(1).extractPredicate((variant, slot) -> {
			return false;
		}).insertPredicate((variant, slot) -> {
			if (slot != INPUT_SLOT) {
				return false;
			}
			
			return variant.getItem() instanceof BlockItem;
		}).insertSlots(INSERT_SLOTS).extractSlots(EXTRACT_SLOTS);
	}

	@Override
	public long getEnergySize() {
		return AMConfig.get().blockPlacerEnergy;
	}

	@Override
	public long getEnergyConsumed() {
		return AMConfig.get().blockPlacerEnergyConsumed;
	}

	@Override
	public double getMachineSpeed() {
		return AMConfig.get().blockPlacerSpeed;
	}

	@Override
	public void tick() {
		super.tick();

		if (world == null || world.isClient || !shouldRun())
			return;

		if (itemStorage != null && itemStorage != null) {
			var consumed = getEnergyConsumed();
			
			if (energyStorage.getAmount() < consumed) {
				cooldown = 0L;
				
				isActive = false;
			} else {
				if (cooldown >= getMachineSpeed()) {
					try (Transaction transaction = Transaction.openOuter()) {
						if (energyStorage.extract(consumed, transaction) == consumed) {
							var stored = itemStorage.getStack(0);

							var direction = getCachedState().get(HorizontalFacingBlock.FACING);

							var targetPos = pos.offset(direction);

							var targetState = world.getBlockState(targetPos);
							
							if (stored.getItem() instanceof BlockItem blockItem && targetState.isAir()) {
								cooldown = 0;

								var newState = blockItem.getBlock().getDefaultState();
								
								world.setBlockState(targetPos, newState);

								var inputStorage = itemStorage.getStorage(INPUT_SLOT);
								
								itemStorage.extract(inputStorage.getResource(), 1, transaction);
								
								transaction.commit();
							} else {
								isActive = false;
								
								transaction.abort();
							}
						} else {
							isActive = false;
						}
					}
				} else {
					cooldown++;
					
					isActive = true;
				}
			}
		}
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		nbt.putLong("Cooldown", cooldown);
		
		super.writeNbt(nbt);
	}

	@Override
	public void readNbt(@NotNull NbtCompound nbt) {
		cooldown = nbt.getLong("Cooldown");
		
		super.readNbt(nbt);
	}
}
