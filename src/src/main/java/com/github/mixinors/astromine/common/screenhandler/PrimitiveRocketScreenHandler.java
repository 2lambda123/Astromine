/*
 * MIT License
 *
 * Copyright (c) 2020 - 2022 Mixinors
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

import com.github.mixinors.astromine.AMCommon;
import com.github.mixinors.astromine.common.entity.base.RocketEntity;
import com.github.mixinors.astromine.common.screenhandler.base.entity.ExtendedEntityScreenHandler;
import com.github.mixinors.astromine.registry.common.AMItems;
import com.github.mixinors.astromine.registry.common.AMScreenHandlers;
import dev.vini2003.hammer.core.api.client.texture.BaseTexture;
import dev.vini2003.hammer.core.api.client.texture.PartitionedTexture;
import dev.vini2003.hammer.core.api.common.math.position.Position;
import dev.vini2003.hammer.core.api.common.math.size.Size;
import dev.vini2003.hammer.gui.api.common.widget.bar.FluidBarWidget;
import dev.vini2003.hammer.gui.api.common.widget.button.ButtonWidget;
import dev.vini2003.hammer.gui.api.common.widget.slot.SlotWidget;
import kotlin.Unit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class PrimitiveRocketScreenHandler extends ExtendedEntityScreenHandler {
	public static final BaseTexture GREEN_BUTTON_FOCUSED = new PartitionedTexture(AMCommon.id("textures/widget/green_button_focused.png"), 18.0F, 18.0F, 0.11F, 0.11F, 0.11F, 0.16F);
	public static final BaseTexture GREEN_BUTTON_ON = new PartitionedTexture(AMCommon.id("textures/widget/green_button_on.png"), 18.0F, 18.0F, 0.11F, 0.11F, 0.11F, 0.16F);
	
	public static final BaseTexture RED_BUTTON_FOCUSED = new PartitionedTexture(AMCommon.id("textures/widget/red_button_focused.png"), 18.0F, 18.0F, 0.11F, 0.11F, 0.11F, 0.16F);
	public static final BaseTexture RED_BUTTON_ON = new PartitionedTexture(AMCommon.id("textures/widget/red_button_on.png"), 18.0F, 18.0F, 0.11F, 0.11F, 0.11F, 0.16F);
	
	private RocketEntity rocketEntity;
	
	public PrimitiveRocketScreenHandler(int syncId, PlayerEntity player, int entityId) {
		super(AMScreenHandlers.ROCKET, syncId, player, entityId);
		
		rocketEntity = (RocketEntity) entity;
	}
	
	@Override
	public ItemStack getSymbol() {
		return new ItemStack(AMItems.PRIMITIVE_ROCKET.get());
	}
	
	@Override
	public void initialize(int width, int height) {
		super.initialize(width, height);
		
		
		var launchButton = new ButtonWidget(() -> {
			rocketEntity.tryLaunch(this.getPlayer());
			
			return Unit.INSTANCE;
		});
		
		launchButton.setPosition(new Position(tab, PAD_7, PAD_11));
		launchButton.setSize(new Size(44.0F, 18.0F));
		launchButton.setLabel(new TranslatableText("text.astromine.rocket.launch"));
		launchButton.setDisabled(() -> entity.getDataTracker().get(RocketEntity.IS_RUNNING) || (entity.getFluidStorage().getStorage(0).isResourceBlank() && entity.getFluidStorage().getStorage(1).isResourceBlank()));
		
		launchButton.setFocusedTexture(GREEN_BUTTON_FOCUSED);
		launchButton.setOnTexture(GREEN_BUTTON_ON);
		
		var destroyButton = new ButtonWidget(() -> {
			rocketEntity.tryDisassemble(true);
			
			var player = getPlayer();
			
			player.closeHandledScreen();
			
			return Unit.INSTANCE;
		});
		
		destroyButton.setPosition(new Position(tab, PAD_7, PAD_11 + BAR_HEIGHT - SLOT_HEIGHT));
		destroyButton.setSize(new Size(44.0F, 18.0F));
		destroyButton.setLabel(new TranslatableText("text.astromine.rocket.rud"));
		
		destroyButton.setFocusedTexture(RED_BUTTON_FOCUSED);
		destroyButton.setOnTexture(RED_BUTTON_ON);
		
		fluidBar.setPosition(new Position(tab, TABS_WIDTH - PAD_7 - (BAR_WIDTH + PAD_3 + SLOT_WIDTH + PAD_3 + SLOT_WIDTH + PAD_3 + SLOT_WIDTH + PAD_3 + BAR_WIDTH), PAD_11));
		
		var firstInput = new SlotWidget(RocketEntity.ITEM_INPUT_SLOT_1, entity.getItemStorage());
		firstInput.setPosition(new Position(fluidBar, BAR_WIDTH + PAD_3, 0.0F));
		firstInput.setSize(new Size(SLOT_WIDTH, SLOT_HEIGHT));
		
		var firstOutput = new SlotWidget(RocketEntity.ITEM_OUTPUT_SLOT_1, entity.getItemStorage());
		firstOutput.setPosition(new Position(fluidBar, BAR_WIDTH + PAD_3, BAR_HEIGHT - SLOT_HEIGHT));
		firstOutput.setSize(new Size(SLOT_WIDTH, SLOT_HEIGHT));
		
		var secondFluidBar = new FluidBarWidget();
		secondFluidBar.setPosition(new Position(tab, TABS_WIDTH - PAD_7 - (BAR_WIDTH), PAD_11));
		secondFluidBar.setSize(new Size(BAR_WIDTH, BAR_HEIGHT));
		secondFluidBar.setStorage(entity.getFluidStorage().getStorage(RocketEntity.FLUID_INPUT_SLOT_2));
		secondFluidBar.setSmooth(false);
		
		var secondInput = new SlotWidget(RocketEntity.ITEM_INPUT_SLOT_2, entity.getItemStorage());
		secondInput.setPosition(new Position(secondFluidBar, -SLOT_WIDTH - PAD_3, 0.0F));
		secondInput.setSize(new Size(SLOT_WIDTH, SLOT_HEIGHT));
		
		var secondOutput = new SlotWidget(RocketEntity.ITEM_OUTPUT_SLOT_2, entity.getItemStorage());
		secondOutput.setPosition(new Position(secondFluidBar, -SLOT_WIDTH - PAD_3, BAR_HEIGHT - SLOT_HEIGHT));
		secondOutput.setSize(new Size(SLOT_WIDTH, SLOT_HEIGHT));
		
		var buffer = new SlotWidget(RocketEntity.ITEM_BUFFER_SLOT_1, entity.getItemStorage());
		buffer.setPosition(new Position(firstInput, PAD_3 + SLOT_WIDTH, SLOT_HEIGHT - 4.0F));
		buffer.setSize(new Size(SLOT_WIDTH, SLOT_HEIGHT));
		
		tab.add(buffer);
		
		tab.add(launchButton);
		tab.add(destroyButton);
		
		tab.add(secondFluidBar);
		
		tab.add(firstInput);
		tab.add(firstOutput);
		
		tab.add(secondInput);
		tab.add(secondOutput);
	}
}
