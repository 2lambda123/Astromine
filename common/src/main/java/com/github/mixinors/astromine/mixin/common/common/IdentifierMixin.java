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

package com.github.mixinors.astromine.mixin.common.common;

import com.github.mixinors.astromine.AMCommon;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.Identifier;

import com.github.mixinors.astromine.common.registry.IdentifierFixRegistry;
import com.github.mixinors.astromine.registry.common.AMConfig;

@Mixin(Identifier.class)
public class IdentifierMixin {
	@Mutable
	@Shadow
	@Final
	protected String path;
	@Shadow
	@Final
	protected String namespace;

	@Inject(method = "<init>([Ljava/lang/String;)V", at = @At("RETURN"))
	private void astromine_init(String[] strings, CallbackInfo ci) {
		if (namespace.equals(AMCommon.MOD_ID) && AMConfig.get().compatibilityMode && IdentifierFixRegistry.INSTANCE.containsKey(path)) {
			var oldPath = path;
			path = IdentifierFixRegistry.INSTANCE.get(path);
			
			AMCommon.LOGGER.info(String.format("Fixed identifier path from %s to %s.", oldPath, path));
		}
	}
}
