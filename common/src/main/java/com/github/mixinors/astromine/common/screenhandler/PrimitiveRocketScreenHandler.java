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

import com.github.mixinors.astromine.registry.common.AMItems;
import com.github.mixinors.astromine.registry.common.AMScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import com.github.mixinors.astromine.common.screenhandler.base.entity.ComponentEntityFluidItemScreenHandler;
import com.github.mixinors.astromine.common.widget.blade.VerticalFluidBarWidget;
import com.github.mixinors.astromine.common.entity.base.RocketEntity;
import com.github.vini2003.blade.common.miscellaneous.Position;
import com.github.vini2003.blade.common.miscellaneous.Size;
import com.github.vini2003.blade.common.widget.base.ButtonWidget;
import com.github.vini2003.blade.common.widget.base.SlotWidget;

public class PrimitiveRocketScreenHandler extends ComponentEntityFluidItemScreenHandler {
	public PrimitiveRocketScreenHandler(int syncId, PlayerEntity player, int entityId) {
		super(AMScreenHandlers.ROCKET, syncId, player, entityId);
	}

	@Override
	public ItemStack getSymbol() {
		return new ItemStack(AMItems.PRIMITIVE_ROCKET.get());
	}

	@Override
	public void initialize(int width, int height) {
		super.initialize(width, height);

		var launchButtonWidget = new ButtonWidget(() -> {
			((RocketEntity) fluidItemEntity).tryLaunch(this.getPlayer());

			return null;
		});

		launchButtonWidget.setPosition(Position.of(mainTab, 3 + 4, 11));
		launchButtonWidget.setSize(Size.of(48, 18));
		launchButtonWidget.setLabel(new TranslatableText("text.astromine.rocket.launch"));
		launchButtonWidget.setDisabled(() -> fluidItemEntity.getDataTracker().get(RocketEntity.IS_RUNNING) || (fluidItemEntity.getFluidComponent().getFirst().smallerOrEqualThan(0L) && fluidItemEntity.getFluidComponent().getSecond().smallerOrEqualThan(0L)));

		var abortButtonWidget = new ButtonWidget(() -> {
			((RocketEntity) fluidItemEntity).tryDisassemble(true);

			return null;
		});

		abortButtonWidget.setPosition(Position.of(mainTab, 3 + 4, 11 + fluidBar.getHeight() - 18));
		abortButtonWidget.setSize(Size.of(48, 18));
		abortButtonWidget.setLabel(new TranslatableText("text.astromine.rocket.destroy").formatted(Formatting.RED));

		fluidBar.setPosition(Position.of(width/ 2.0F - fluidBar.getWidth()/ 2.0F + 2, fluidBar.getY()));

		var firstInput = new SlotWidget(0, fluidItemEntity);
		firstInput.setPosition(Position.of(fluidBar, -18 - 3, 0));
		firstInput.setSize(Size.of(18, 18));

		var firstOutput = new SlotWidget(1, fluidItemEntity);
		firstOutput.setPosition(Position.of(fluidBar, -18 - 3, fluidBar.getHeight() - 18));
		firstOutput.setSize(Size.of(18, 18));

		var secondFluidBar = new VerticalFluidBarWidget();
		secondFluidBar.setPosition(Position.of(fluidBar, 24 + 18 + 3 + 3, 0));
		secondFluidBar.setSize(Size.of(24F, 48F));
		secondFluidBar.setVolumeSupplier(() -> fluidItemEntity.getFluidComponent().getSecond());

		var secondInput = new SlotWidget(2, fluidItemEntity);
		secondInput.setPosition(Position.of(secondFluidBar, -18 - 3, 0));
		secondInput.setSize(Size.of(18, 18));

		var secondOutput = new SlotWidget(3, fluidItemEntity);
		secondOutput.setPosition(Position.of(secondFluidBar, -18 - 3, secondFluidBar.getHeight() - 18));
		secondOutput.setSize(Size.of(18, 18));

		mainTab.addWidget(launchButtonWidget);
		mainTab.addWidget(abortButtonWidget);

		mainTab.addWidget(secondFluidBar);

		mainTab.addWidget(firstInput);
		mainTab.addWidget(firstOutput);

		mainTab.addWidget(secondInput);
		mainTab.addWidget(secondOutput);
	}
}
