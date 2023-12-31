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

package com.github.mixinors.astromine.common.entity.base;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

import com.github.mixinors.astromine.common.component.base.EnergyComponent;
import com.github.mixinors.astromine.common.component.base.FluidComponent;

/**
 * A {@link ComponentEntity} with an attached {@link EnergyComponent}
 * and {@link FluidComponent}.
 */
public abstract class ComponentEnergyFluidEntity extends ComponentEntity {
	/** Instantiates a {@link ComponentEnergyFluidEntity}. */
	public ComponentEnergyFluidEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	/** Returns the {@link EnergyComponent} to be attached. */
	public abstract EnergyComponent createEnergyComponent();

	/** Returns the {@link FluidComponent} to be attached. */
	public abstract FluidComponent createFluidComponent();

	/** Returns the attached {@link EnergyComponent}. */
	public EnergyComponent getEnergyComponent() {
		return EnergyComponent.from(this);
	}

	/** Returns the attached {@link FluidComponent}. */
	public FluidComponent getFluidComponent() {
		return FluidComponent.from(this);
	}
}
