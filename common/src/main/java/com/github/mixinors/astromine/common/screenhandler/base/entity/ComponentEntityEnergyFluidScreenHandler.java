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

package com.github.mixinors.astromine.common.screenhandler.base.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandlerType;

import com.github.mixinors.astromine.common.entity.base.ComponentEnergyFluidEntity;
import com.github.mixinors.astromine.common.widget.blade.VerticalEnergyBarWidget;
import com.github.mixinors.astromine.common.widget.blade.VerticalFluidBarWidget;
import com.github.vini2003.blade.common.miscellaneous.Position;
import com.github.vini2003.blade.common.miscellaneous.Size;

import java.util.function.Supplier;

/**
 * A {@link ComponentEntityScreenHandler}
 * with an attached {@link ComponentEnergyFluidEntity}.
 */
public abstract class ComponentEntityEnergyFluidScreenHandler extends ComponentEntityScreenHandler {
	protected ComponentEnergyFluidEntity energyFluidEntity;

	protected VerticalFluidBarWidget fluidBar;

	protected VerticalEnergyBarWidget energyBar;

	/** Instantiates a {@link ComponentEntityEnergyFluidScreenHandler}. */
	public ComponentEntityEnergyFluidScreenHandler(Supplier<? extends ScreenHandlerType<?>> type, int syncId, PlayerEntity player, int entityId) {
		super(type, syncId, player, entityId);

		energyFluidEntity = (ComponentEnergyFluidEntity) player.world.getEntityById(entityId);
	}

	/** Override behavior to add energy and fluid bars. */
	@Override
	public void initialize(int width, int height) {
		super.initialize(width, height);

		energyBar = new VerticalEnergyBarWidget();
		energyBar.setPosition(Position.of(mainTab, 7, 11));
		energyBar.setSize(Size.of(24, 48));
		energyBar.setVolumeSupplier(() -> energyFluidEntity.getEnergyComponent().getVolume());

		fluidBar = new VerticalFluidBarWidget();
		fluidBar.setPosition(Position.of(energyBar, energyBar.getWidth() + 7, 0));
		fluidBar.setSize(Size.of(energyBar.getWidth(), energyBar.getHeight()));
		fluidBar.setVolumeSupplier(() -> energyFluidEntity.getFluidComponent().getFirst());

		mainTab.addWidget(energyBar);
		mainTab.addWidget(fluidBar);
	}
}
