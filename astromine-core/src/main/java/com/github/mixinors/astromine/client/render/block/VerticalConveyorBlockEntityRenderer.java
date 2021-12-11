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

package com.github.mixinors.astromine.client.render.block;

import com.github.mixinors.astromine.common.block.entity.VerticalConveyorBlockEntity;
import com.github.mixinors.astromine.common.conveyor.Conveyor;
import com.github.mixinors.astromine.common.conveyor.ConveyorTypes;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class VerticalConveyorBlockEntityRenderer implements BlockEntityRenderer<VerticalConveyorBlockEntity>, ConveyorRenderer<VerticalConveyorBlockEntity> {
	public VerticalConveyorBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
	}

	@Override
	public void render(VerticalConveyorBlockEntity blockEntity, float partialTicks, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int i1) {
		int speed = ((Conveyor) blockEntity.getCachedState().getBlock()).getSpeed();
		ConveyorTypes type = ((Conveyor) blockEntity.getCachedState().getBlock()).getType();

		if (!blockEntity.getWorld().getBlockState(blockEntity.getPos()).isAir() && !blockEntity.isEmpty()) {
			ItemStack stack = blockEntity.getItemComponent().getFirst();
			float position = blockEntity.getRenderAttachmentData()[1] + (blockEntity.getRenderAttachmentData()[0] - blockEntity.getRenderAttachmentData()[1]) * partialTicks;
			float horizontalPosition = blockEntity.getRenderAttachmentData()[3] + (blockEntity.getRenderAttachmentData()[2] - blockEntity.getRenderAttachmentData()[3]) * partialTicks;

			renderSupport(blockEntity, type, position, speed, horizontalPosition, matrixStack, vertexConsumerProvider);

			renderItem(blockEntity, stack, position, speed, horizontalPosition, type, matrixStack, vertexConsumerProvider);
		}
	}
}
