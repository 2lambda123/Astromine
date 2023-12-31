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

import com.github.mixinors.astromine.common.block.ConveyorBlock;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

import com.github.mixinors.astromine.common.block.entity.DownVerticalConveyorBlockEntity;
import com.github.mixinors.astromine.common.conveyor.Conveyor;
import com.github.mixinors.astromine.common.conveyor.ConveyorTypes;

public class DownwardVerticalConveyorBlockEntityRenderer extends BlockEntityRenderer<DownVerticalConveyorBlockEntity> implements ConveyorRenderer<DownVerticalConveyorBlockEntity> {
	public DownwardVerticalConveyorBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
		super(blockEntityRenderDispatcher);
	}

	@Override
	public void render(DownVerticalConveyorBlockEntity blockEntity, float partialTicks, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int i1) {
		var speed = ((Conveyor) blockEntity.getCachedState().getBlock()).getSpeed();
		var type = ((Conveyor) blockEntity.getCachedState().getBlock()).getType();
		var conveyor = blockEntity.getCachedState().get(ConveyorBlock.CONVEYOR);
		var front = blockEntity.getCachedState().get(ConveyorBlock.FRONT);

		var direction = blockEntity.getCachedState().get(Properties.HORIZONTAL_FACING);

		if (conveyor && blockEntity.isEmpty()) {
			matrixStack.push();
			
			renderSupport(blockEntity, type, -1, 16, 0, matrixStack, vertexConsumerProvider);
			
			matrixStack.pop();
		} else if (conveyor && !front && !blockEntity.isEmpty() && blockEntity.getPosition() > speed) {
			var position = (blockEntity.getRenderAttachmentData()[1] + (blockEntity.getRenderAttachmentData()[0] - blockEntity.getRenderAttachmentData()[1]) * partialTicks);

			matrixStack.push();
			
			if (direction == Direction.NORTH) {
				matrixStack.translate(0, 0, (position / speed) - 2);
			} else if (direction == Direction.SOUTH) {
				matrixStack.translate(0, 0, -(position / speed) + 2);
			} else if (direction == Direction.EAST) {
				matrixStack.translate(-(position / speed) + 2, 0, 0);
			} else if (direction == Direction.WEST) {
				matrixStack.translate((position / speed) - 2, 0, 0);
			}
			
			renderSupport(blockEntity, type, -1, 16, 0, matrixStack, vertexConsumerProvider);
			
			matrixStack.pop();
		} else if (conveyor && front && !blockEntity.isEmpty() && blockEntity.getHorizontalPosition() > 0) {
			var horizontalPosition = (blockEntity.getRenderAttachmentData()[3] + (blockEntity.getRenderAttachmentData()[2] - blockEntity.getRenderAttachmentData()[3]) * partialTicks);

			matrixStack.push();
			
			if (direction == Direction.NORTH) {
				matrixStack.translate(0, 0, (horizontalPosition / speed) - 1);
			} else if (direction == Direction.SOUTH) {
				matrixStack.translate(0, 0, -(horizontalPosition / speed) + 1);
			} else if (direction == Direction.EAST) {
				matrixStack.translate(-(horizontalPosition / speed) + 1, 0, 0);
			} else if (direction == Direction.WEST) {
				matrixStack.translate((horizontalPosition / speed) - 1, 0, 0);
			}
			
			renderSupport(blockEntity, type, -1, 16, 0, matrixStack, vertexConsumerProvider);
			
			matrixStack.pop();
		}

		if (!blockEntity.getWorld().getBlockState(blockEntity.getPos()).isAir() && !blockEntity.isEmpty()) {
			var stack = blockEntity.getItemComponent().getFirst();
			var position = -(blockEntity.getRenderAttachmentData()[1] + (blockEntity.getRenderAttachmentData()[0] - blockEntity.getRenderAttachmentData()[1]) * partialTicks);
			var horizontalPosition = (blockEntity.getRenderAttachmentData()[3] + (blockEntity.getRenderAttachmentData()[2] - blockEntity.getRenderAttachmentData()[3]) * partialTicks);

			if (front && horizontalPosition <= 0) {
				renderSupport(blockEntity, type, position, speed, horizontalPosition, matrixStack, vertexConsumerProvider);
			}

			renderItem(blockEntity, stack, position, speed, horizontalPosition, type, matrixStack, vertexConsumerProvider);
		}
	}
}
