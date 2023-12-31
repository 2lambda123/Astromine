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

import com.github.mixinors.astromine.common.component.base.EnergyComponent;
import com.github.mixinors.astromine.common.component.base.ItemComponent;
import com.github.mixinors.astromine.registry.common.AMScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;

import com.github.mixinors.astromine.common.screenhandler.base.block.ComponentBlockEntityEnergyItemScreenHandler;
import com.github.mixinors.astromine.common.widget.blade.HorizontalArrowWidget;
import com.github.vini2003.blade.common.miscellaneous.Position;
import com.github.vini2003.blade.common.miscellaneous.Size;
import com.github.vini2003.blade.common.widget.base.SlotWidget;

import java.util.function.Supplier;

public class CapacitorScreenHandler extends ComponentBlockEntityEnergyItemScreenHandler {
	public CapacitorScreenHandler(int syncId, PlayerEntity player, BlockPos pos) {
		super(AMScreenHandlers.CAPACITOR, syncId, player, pos);
	}

	public CapacitorScreenHandler(Supplier<? extends ScreenHandlerType<?>> type, int syncId, PlayerEntity player, BlockPos pos) {
		super(type, syncId, player, pos);
	}

	@Override
	public void initialize(int width, int height) {
		super.initialize(width, height);

		energyBar.setPosition(Position.of(width/ 2.0F - energyBar.getWidth()/ 2.0F, energyBar.getY()));

		var input = new SlotWidget(0, energyItemBlockEntity);
		input.setPosition(Position.of(mainTab, 12, 26));
		input.setSize(Size.of(18, 18));

		var output = new SlotWidget(1, energyItemBlockEntity);
		output.setPosition(Position.of(mainTab, 146, 26));
		output.setSize(Size.of(18, 18));

		var leftArrow = new HorizontalArrowWidget();
		leftArrow.setPosition(Position.of(input, 28, 0));
		leftArrow.setSize(Size.of(22, 16));
		leftArrow.setLimitSupplier(() -> {
			var stack = ItemComponent.from(energyItemBlockEntity).getFirst();
			
			var energyComponent = EnergyComponent.from(stack);
			
			return energyComponent == null ? 1 : (int) energyComponent.getSize();
		});
		leftArrow.setProgressSupplier(() -> {
			var stack = ItemComponent.from(energyItemBlockEntity).getFirst();
			
			var energyComponent = EnergyComponent.from(stack);
			
			return energyComponent == null ? 0 : (int) energyComponent.getAmount();
		});

		var rightArrow = new HorizontalArrowWidget();
		rightArrow.setPosition(Position.of(output, -34, 0));
		rightArrow.setSize(Size.of(22, 16));
		rightArrow.setLimitSupplier(() -> {
			var stack = ItemComponent.from(energyItemBlockEntity).getSecond();
			
			var energyComponent = EnergyComponent.from(stack);
			
			return energyComponent == null ? 1 : (int) energyComponent.getSize();
		});
		rightArrow.setProgressSupplier(() -> {
			var stack = ItemComponent.from(energyItemBlockEntity).getSecond();
			
			var energyComponent = EnergyComponent.from(stack);
			
			return energyComponent == null ? 0 : (int) energyComponent.getAmount();
		});

		mainTab.addWidget(input);
		mainTab.addWidget(output);
		mainTab.addWidget(leftArrow);
		mainTab.addWidget(rightArrow);
	}
}
