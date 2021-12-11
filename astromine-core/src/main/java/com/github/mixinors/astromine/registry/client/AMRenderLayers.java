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

package com.github.mixinors.astromine.registry.client;

import com.github.mixinors.astromine.registry.common.AMBlocks;
import me.shedaniel.architectury.event.events.client.ClientLifecycleEvent;
import me.shedaniel.architectury.registry.RenderTypes;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

public class AMRenderLayers {
	public static void init() {
		ClientLifecycleEvent.CLIENT_SETUP.register(client -> {
			register(AMBlocks.AIRLOCK, RenderLayer.getTranslucent());

			register(AMBlocks.ALTAR.get(), RenderLayer.getCutout());
			register(AMBlocks.ALTAR_PEDESTAL.get(), RenderLayer.getCutout());
			register(AMBlocks.SPACE_SLIME_BLOCK.get(), RenderLayer.getTranslucent());
		});
	}

	/**
	 * @param block
	 *        Block instance to be registered
	 * @param renderLayer
	 *        RenderLayer of block instance to be registered
	 *
	 * @return Block instance registered
	 */
	public static <T extends Block> T register(T block, RenderLayer renderLayer) {
		RenderTypes.register(renderLayer, block);
		return block;
	}
}
