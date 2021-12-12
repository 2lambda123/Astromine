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
import dev.vini2003.hammer.common.geometry.position.Position;
import dev.vini2003.hammer.common.geometry.size.Size;
import dev.vini2003.hammer.common.widget.slot.SlotWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;

import com.github.mixinors.astromine.common.screenhandler.base.block.ComponentBlockEntityEnergyItemScreenHandler;
import com.github.mixinors.astromine.common.widget.blade.HorizontalArrowWidget;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHandler;

import java.util.function.Supplier;

public class CapacitorScreenHandler extends ComponentBlockEntityEnergyItemScreenHandler {
	public CapacitorScreenHandler(int syncId, PlayerEntity player, BlockPos position) {
		super(AMScreenHandlers.CAPACITOR, syncId, player, position);
	}

	public CapacitorScreenHandler(Supplier<? extends ScreenHandlerType<?>> type, int syncId, PlayerEntity player, BlockPos position) {
		super(type, syncId, player, position);
	}

	@Override
	public void initialize(int width, int height) {
		super.initialize(width, height);

		energyBar.setPosition( Position.of(width / 2F - energyBar.getWidth() / 2F, energyBar.getY()));

		SlotWidget input = new SlotWidget(0, blockEntity);
		input.setPosition(Position.of(mainTab, 12, 26));
		input.setSize( Size.of(18, 18));

		SlotWidget output = new SlotWidget(1, blockEntity);
		output.setPosition(Position.of(mainTab, 146, 26));
		output.setSize(Size.of(18, 18));

		HorizontalArrowWidget leftArrow = new HorizontalArrowWidget();
		leftArrow.setPosition(Position.of(input, 28, 0));
		leftArrow.setSize(Size.of(22, 16));
		leftArrow.setLimitSupplier(() -> 1);
		leftArrow.setProgressSupplier(() -> {
			ItemStack stack = blockEntity.getItemComponent().getFirst();
			if (!Energy.valid(stack))
				return 0;
			EnergyHandler handler = Energy.of(stack);
			return handler.getEnergy() > 0 && handler.getMaxOutput() > 0 ? 1 : 0;
		});

		HorizontalArrowWidget rightArrow = new HorizontalArrowWidget();
		rightArrow.setPosition(Position.of(output, -34, 0));
		rightArrow.setSize(Size.of(22, 16));
		rightArrow.setLimitSupplier(() -> 1);
		rightArrow.setProgressSupplier(() -> {
			ItemStack stack = blockEntity.getItemComponent().getSecond();
			if (!Energy.valid(stack))
				return 0;
			EnergyHandler handler = Energy.of(stack);
			return handler.getEnergy() < handler.getMaxStored() && blockEntity.getEnergyComponent().getAmount() > 0 && handler.getMaxInput() > 0 ? 1 : 0;
		});

		mainTab.add(input);
		mainTab.add(output);
		mainTab.add(leftArrow);
		mainTab.add(rightArrow);
	}
}