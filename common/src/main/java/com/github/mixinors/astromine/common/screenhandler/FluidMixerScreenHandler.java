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

package com.github.mixinors.astromine.common.screenhandler;

import com.github.mixinors.astromine.registry.common.AMScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import com.github.mixinors.astromine.common.screenhandler.base.block.ComponentBlockEntityEnergyFluidScreenHandler;
import com.github.mixinors.astromine.common.widget.blade.VerticalFluidBarWidget;
import com.github.mixinors.astromine.common.widget.blade.HorizontalArrowWidget;
import com.github.mixinors.astromine.common.block.entity.FluidMixerBlockEntity;
import com.github.vini2003.blade.common.miscellaneous.Position;
import com.github.vini2003.blade.common.miscellaneous.Size;

public class FluidMixerScreenHandler extends ComponentBlockEntityEnergyFluidScreenHandler {
	private final FluidMixerBlockEntity mixer;

	public FluidMixerScreenHandler(int syncId, PlayerEntity player, BlockPos pos) {
		super(AMScreenHandlers.FLUID_MIXER, syncId, player, pos);

		mixer = (FluidMixerBlockEntity) energyFluidBlockEntity;
	}

	@Override
	public void initialize(int width, int height) {
		super.initialize(width, height);

		var secondInputFluidBar = new VerticalFluidBarWidget();
		secondInputFluidBar.setPosition(Position.of(fluidBar, fluidBar.getWidth() + 7, 0));
		secondInputFluidBar.setSize(Size.absolute(fluidBar));
		secondInputFluidBar.setVolumeSupplier(() -> energyFluidBlockEntity.getFluidComponent().getSecond());

		var arrow = new HorizontalArrowWidget();
		arrow.setPosition(Position.of(secondInputFluidBar, secondInputFluidBar.getWidth() + 9, secondInputFluidBar.getHeight()/ 2.0F - 8));
		arrow.setSize(Size.of(22, 16));
		arrow.setLimitSupplier(mixer::getLimit);
		arrow.setProgressSupplier(mixer::getProgress);

		var outputFluidBar = new VerticalFluidBarWidget();
		outputFluidBar.setPosition(Position.of(secondInputFluidBar, secondInputFluidBar.getWidth() + 9 + arrow.getWidth() + 7, 0));
		outputFluidBar.setSize(Size.absolute(fluidBar));
		outputFluidBar.setVolumeSupplier(() -> energyFluidBlockEntity.getFluidComponent().getThird());

		mainTab.addWidget(secondInputFluidBar);
		mainTab.addWidget(arrow);
		mainTab.addWidget(outputFluidBar);
	}
}
