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

import com.github.mixinors.astromine.registry.common.AMBlockEntityTypes;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

import com.github.mixinors.astromine.common.block.entity.base.ComponentFluidBlockEntity;
import com.github.mixinors.astromine.common.block.transfer.TransferType;
import com.github.mixinors.astromine.common.component.base.FluidComponent;

public class DrainBlockEntity extends ComponentFluidBlockEntity implements Tickable {
	public DrainBlockEntity() {
		super(AMBlockEntityTypes.DRAIN);

		for (var direction : Direction.values()) {
			transfer.getFluidEntry().set(direction, TransferType.INPUT);
		}
	}

	@Override
	public FluidComponent createFluidComponent() {
		return FluidComponent.of(1).withInsertPredicate((direction, volume, slot) -> {
			return tickRedstone();
		}).withSizes(Long.MAX_VALUE);
	}

	@Override
	public void tick() {
		if (!(world instanceof ServerWorld))
			return;
		
		var volume = fluids.getFirst();
		
		volume.setFluid(Fluids.EMPTY);
		volume.setAmount(0L);
	}
}
