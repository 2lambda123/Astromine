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

import com.github.mixinors.astromine.common.component.base.AtmosphereComponent;
import com.github.mixinors.astromine.registry.common.AMBlockEntityTypes;
import net.minecraft.block.FacingBlock;
import net.minecraft.server.world.ServerWorld;

import com.github.mixinors.astromine.common.block.entity.base.ComponentEnergyFluidBlockEntity;
import com.github.mixinors.astromine.common.component.base.EnergyComponent;
import com.github.mixinors.astromine.common.component.base.FluidComponent;
import com.github.mixinors.astromine.common.volume.fluid.FluidVolume;
import com.github.mixinors.astromine.registry.common.AMConfig;
import com.github.mixinors.astromine.common.block.entity.machine.EnergyConsumedProvider;
import com.github.mixinors.astromine.common.block.entity.machine.EnergySizeProvider;
import com.github.mixinors.astromine.common.block.entity.machine.FluidSizeProvider;
import com.github.mixinors.astromine.common.block.entity.machine.SpeedProvider;

public class VentBlockEntity extends ComponentEnergyFluidBlockEntity implements FluidSizeProvider, EnergySizeProvider, SpeedProvider, EnergyConsumedProvider {
	public VentBlockEntity() {
		super(AMBlockEntityTypes.VENT);

		getFluidComponent().getFirst().setSize(AMConfig.get().ventFluid);
	}

	@Override
	public FluidComponent createFluidComponent() {
		return FluidComponent.of(this, 1).withSizes(getFluidSize());
	}

	@Override
	public EnergyComponent createEnergyComponent() {
		return EnergyComponent.of(getEnergySize());
	}

	@Override
	public double getEnergySize() {
		return AMConfig.get().ventEnergy;
	}

	@Override
	public long getFluidSize() {
		return AMConfig.get().ventFluid;
	}

	@Override
	public double getMachineSpeed() {
		return AMConfig.get().ventSpeed;
	}

	@Override
	public double getEnergyConsumed() {
		return AMConfig.get().ventEnergyConsumed;
	}

	@Override
	public void tick() {
		super.tick();
		
		if (!(world instanceof ServerWorld) || !tickRedstone())
			return;
		
		if (energy.hasStored(128)) {
			var position = getPos();

			var direction = world.getBlockState(position).get(FacingBlock.FACING);

			var output = position.offset(direction);

			if (energy.hasStored(getEnergyConsumed()) && (world.getBlockState(output).isAir() || world.getBlockState(output).isSideSolidFullSquare(world, pos, direction.getOpposite()))) {
				var atmosphereComponent = AtmosphereComponent.from(world.getChunk(getPos()));

				var centerVolume = fluids.getFirst();

				if (AtmosphereComponent.isInChunk(world.getChunk(output).getPos(), pos)) {
					var sideVolume = atmosphereComponent.get(output);

					if ((sideVolume.test(centerVolume.getFluid())) && sideVolume.smallerThan(centerVolume.getAmount())) {
						centerVolume.give(sideVolume, FluidVolume.BUCKET / 9L);

						atmosphereComponent.add(output, sideVolume);

						energy.take(getEnergyConsumed());

						tickActive();
					} else {
						tickInactive();
					}
				} else {
					var neighborPos = AtmosphereComponent.getNeighborFromPos(world.getChunk(output).getPos(), output);

					var neighborAtmosphereComponent = AtmosphereComponent.from(world.getChunk(neighborPos.x, neighborPos.z));

					var sideVolume = neighborAtmosphereComponent.get(output);

					if ((centerVolume.test(sideVolume.getFluid())) && sideVolume.smallerThan(centerVolume.getAmount())) {
						centerVolume.give(sideVolume, FluidVolume.BUCKET / 9L);

						neighborAtmosphereComponent.add(output, sideVolume);

						energy.take(getEnergyConsumed());

						tickActive();
					} else {
						tickInactive();
					}
				}
			} else {
				tickInactive();
			}
		} else {
			tickInactive();
		}
	}
}
