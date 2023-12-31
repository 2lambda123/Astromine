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

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

import com.github.mixinors.astromine.client.render.layer.Layer;
import com.github.mixinors.astromine.common.block.HoloBridgeProjectorBlock;
import com.github.mixinors.astromine.common.block.entity.HoloBridgeProjectorBlockEntity;

public class HoloBridgeBlockEntityRenderer extends BlockEntityRenderer<HoloBridgeProjectorBlockEntity> {
	public HoloBridgeBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(HoloBridgeProjectorBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light, int overlay) {
		BlockState state = entity.getWorld().getBlockState(entity.getPos());

		if (!(state.getBlock() instanceof HoloBridgeProjectorBlock)) {
			return;
		}

		if (entity.hasChild()) {
			var pA = entity.getPos();

			var direction = state.get(HorizontalFacingBlock.FACING);

			var offsetX = direction == Direction.NORTH ? 1 : 0;
			var offsetZ = direction == Direction.WEST ? 1 : 0;

			if (entity.segments == null || entity.segments.size() == 0) {
				return;
			}

			var start = entity.segments.get(0);
			var end = entity.segments.get(entity.segments.size() - 1);

			matrices.push();

			var consumer = provider.getBuffer(Layer.getHolographicBridge());

			var xA = end.getX() - pA.getX();
			var xB = start.getX() - pA.getX();

			var yA = end.getY() - pA.getY();
			var yB = start.getY() - pA.getY();

			var zA = end.getZ() - pA.getZ();
			var zB = start.getZ() - pA.getZ();

			consumer.vertex(matrices.peek().getModel(), xA, yA, zA).color(entity.color.getR(), entity.color.getG(), entity.color.getB(), entity.color.getA()).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(0x00f000f0).normal(matrices.peek().getNormal(), 0, 1, 0).next();
			consumer.vertex(matrices.peek().getModel(), xB, yB, zB).color(entity.color.getR(), entity.color.getG(), entity.color.getB(), entity.color.getA()).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(0x00f000f0).normal(matrices.peek().getNormal(), 0, 1, 0).next();
			consumer.vertex(matrices.peek().getModel(), xB + offsetX, yB, zB + offsetZ).color(entity.color.getR(), entity.color.getG(), entity.color.getB(), entity.color.getA()).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(0x00f000f0).normal(matrices.peek().getNormal(), 0, 1, 0).next();
			consumer.vertex(matrices.peek().getModel(), xA + offsetX, yA, zA + offsetZ).color(entity.color.getR(), entity.color.getG(), entity.color.getB(), entity.color.getA()).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(0x00f000f0).normal(matrices.peek().getNormal(), 0, 1, 0).next();

			matrices.pop();
		}
	}

	@Override
	public boolean rendersOutsideBoundingBox(HoloBridgeProjectorBlockEntity blockEntity) {
		return true;
	}
}
