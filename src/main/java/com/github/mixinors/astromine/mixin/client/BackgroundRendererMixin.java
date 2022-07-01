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

package com.github.mixinors.astromine.mixin.client;

import com.github.mixinors.astromine.registry.common.AMWorlds;
import dev.vini2003.hammer.core.api.client.util.InstanceUtil;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
	@Inject(at = @At("HEAD"), method = "applyFog", cancellable = true)
	private static void astromine$applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
		var client = InstanceUtil.getClient();
		
		if (client != null && (AMWorlds.isAstromine(client.world.getRegistryKey()))) {
			if (!AMWorlds.isAtmospheric(client.world.method_40134())) {
				ci.cancel();
			}
		}
	}
	
	@Inject(at = @At("HEAD"), method = "render", cancellable = true)
	private static void astromine$render(Camera camera, float tickDelta, ClientWorld world, int i, float f, CallbackInfo ci) {
		var client = InstanceUtil.getClient();
		
		if (client != null && (AMWorlds.isAstromine(client.world.getRegistryKey()))) {
			if (!AMWorlds.isAtmospheric(client.world.method_40134())) {
				ci.cancel();
			}
		}
	}
}
