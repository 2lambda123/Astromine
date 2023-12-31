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

import com.github.mixinors.astromine.common.block.entity.PressBlockEntity;
import com.github.mixinors.astromine.common.screenhandler.base.block.ComponentBlockEntityEnergyItemScreenHandler;
import com.github.mixinors.astromine.common.widget.blade.HorizontalArrowWidget;
import com.github.mixinors.astromine.common.widget.vanilla.ExtractionSlot;
import com.github.mixinors.astromine.registry.common.AMScreenHandlers;
import com.github.vini2003.blade.common.miscellaneous.Position;
import com.github.vini2003.blade.common.miscellaneous.Size;
import com.github.vini2003.blade.common.widget.base.SlotWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class PressScreenHandler extends ComponentBlockEntityEnergyItemScreenHandler {
	private final PressBlockEntity press;
	
	public PressScreenHandler(int syncId, PlayerEntity player, BlockPos pos) {
		super(AMScreenHandlers.PRESSER, syncId, player, pos);
		
		press = (PressBlockEntity) energyItemBlockEntity;
	}
	
	@Override
	public void initialize(int width, int height) {
		super.initialize(width, height);
		
		var output = new SlotWidget(0, energyItemBlockEntity, ExtractionSlot::new);
		output.setPosition(Position.of(energyBar.getX(), energyBar.getY()));
		output.setSize(Size.of(18, 18));
		
		var input = new SlotWidget(1, energyItemBlockEntity);
		input.setPosition(Position.of(energyBar.getX(), energyBar.getY()));
		input.setSize(Size.of(18, 18));
		
		output.setPosition(Position.of(width/ 2.0F - output.getWidth()/ 2.0F, output.getY()));
		output.setPosition(Position.of(output.getX() + 27, output.getY() + 15));
		
		var arrow = new HorizontalArrowWidget();
		arrow.setPosition(Position.of(output.getX() - 31, output.getY()));
		arrow.setSize(Size.of(22, 16));
		arrow.setLimitSupplier(press::getLimit);
		arrow.setProgressSupplier(press::getProgress);
		
		input.setPosition(Position.of(arrow.getX() - 27, arrow.getY()));
		
		mainTab.addWidget(input);
		mainTab.addWidget(output);
		mainTab.addWidget(arrow);
	}
}
