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

package com.github.mixinors.astromine.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

import com.github.mixinors.astromine.AMCommon;
import com.github.mixinors.astromine.client.model.PrimitiveRocketEntityModel;
import com.github.mixinors.astromine.common.entity.PrimitiveRocketEntity;
import com.github.mixinors.astromine.registry.client.AMEntityModelLayers;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;

public class PrimitiveRocketEntityRenderer extends EntityRenderer<PrimitiveRocketEntity> {
	public static final Identifier ID = AMCommon.id("textures/entity/rocket/primitive_rocket.png");

	private final PrimitiveRocketEntityModel model;

	public PrimitiveRocketEntityRenderer(Context context) {
		super(context);
		this.model = new PrimitiveRocketEntityModel(context.getPart(AMEntityModelLayers.ROCKET));
	}

	@Override
	public void render(PrimitiveRocketEntity rocket, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light) {
		matrices.push();

		matrices.scale(-1.0F, -1.0F, 1.0F);

		matrices.scale(2, 2, 2);

		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));

		model.setAngles(rocket, 0, 0.0F, -0.1F, rocket.getYaw(tickDelta), rocket.getPitch(tickDelta));
		
		var vertexConsumer = provider.getBuffer(model.getLayer( getTexture(rocket)));

		model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

		matrices.pop();

		super.render(rocket, yaw, tickDelta, matrices, provider, light);
	}

	@Override
	public Identifier getTexture(PrimitiveRocketEntity entity) {
		return ID;
	}
}
