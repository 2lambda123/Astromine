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

import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import com.github.mixinors.astromine.AMCommon;
import com.github.mixinors.astromine.client.model.InserterArmModel;
import com.github.mixinors.astromine.common.block.InserterBlock;
import com.github.mixinors.astromine.common.block.entity.InserterBlockEntity;

public class InserterBlockEntityRenderer extends BlockEntityRenderer<InserterBlockEntity> implements ConveyorRenderer<InserterBlockEntity> {
	public InserterBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
		super(blockEntityRenderDispatcher);
	}

	@Override
	public void render(InserterBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		var direction = blockEntity.getCachedState().get(HorizontalFacingBlock.FACING);
		var type = ((InserterBlock) blockEntity.getCachedState().getBlock()).getType();
		var speed = ((InserterBlock) blockEntity.getCachedState().getBlock()).getSpeed();
		var modelInserterArm = new InserterArmModel();

		float position = blockEntity.getRenderAttachmentData()[1] + (blockEntity.getRenderAttachmentData()[0] - blockEntity.getRenderAttachmentData()[1]) * tickDelta;

		matrices.push();
		matrices.translate(0.5, 1.5, 0.5);
		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
		
		if (direction == Direction.SOUTH) {
			matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
		} else if (direction == Direction.EAST) {
			matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
		} else if (direction == Direction.WEST) {
			matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90));
		}

		modelInserterArm.getLowerArm().yaw = (float) Math.toRadians((position / speed) * 180F);

		if (position < speed / 4.0F) {
			modelInserterArm.getLowerArm().pitch = (float) Math.toRadians((position / (speed / 4.0F)) * -30F + 40F);
			modelInserterArm.getMiddleArm().pitch = (float) Math.toRadians((position / (speed / 4.0F)) * -20F + 40F);
		} else if (position >= speed - (speed / 4.0F) && position < speed) {
			modelInserterArm.getLowerArm().pitch = (float) Math.toRadians((position / speed) * 120F - 80F);
			modelInserterArm.getMiddleArm().pitch = (float) Math.toRadians((position / speed) * 80F - 40F);
		} else {
			modelInserterArm.getLowerArm().pitch = (float) Math.toRadians(10);
			modelInserterArm.getMiddleArm().pitch = (float) Math.toRadians(20);
		}

		modelInserterArm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(new Identifier(AMCommon.MOD_ID + ":textures/block/" + type + "_inserter.png"))), light, overlay, 1, 1, 1, 1);
		
		matrices.pop();

		if (!blockEntity.getItemComponent().isEmpty()) {
			matrices.push();
			matrices.translate(0.5, 0, 0.5);
			
			if (direction == Direction.NORTH) {
				matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
			} else if (direction == Direction.EAST) {
				matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
			} else if (direction == Direction.WEST) {
				matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90));
			}

			var lowArmSize = 8 / 16F;
			var midArmSize = 10 / 16F;
			var connectingAngle = modelInserterArm.getMiddleArm().pitch;

			var distance = (float) Math.sqrt(Math.pow(lowArmSize, 2) + Math.pow(midArmSize, 2) - 2 * lowArmSize * midArmSize * Math.cos(connectingAngle));
			var angle = (float) (180 - Math.toDegrees(modelInserterArm.getLowerArm().pitch + modelInserterArm.getMiddleArm().pitch));

			matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion((float) Math.toDegrees(modelInserterArm.getLowerArm().yaw)));
			matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180 + angle));
			matrices.translate(0, (1 + 5 / 16F) - distance, distance - (1F / 16F));
			matrices.scale(0.3F, 0.3F, 0.3F);

			renderItem(blockEntity, blockEntity.getItemComponent().getFirst(), matrices, vertexConsumers);
			
			matrices.pop();
		}
	}
}
