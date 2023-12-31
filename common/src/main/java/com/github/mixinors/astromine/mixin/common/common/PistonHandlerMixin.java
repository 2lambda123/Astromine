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

import com.github.mixinors.astromine.registry.common.AMBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonHandler;

@Mixin(PistonHandler.class)
public abstract class PistonHandlerMixin {
	@Shadow
	private static boolean isBlockSticky(Block block) {
		return false;
	}

	@Inject(method = "isBlockSticky(Lnet/minecraft/block/Block;)Z", at = @At("HEAD"), cancellable = true)
	private static void astromine_isBlockSticky(Block block, CallbackInfoReturnable<Boolean> cir) {
		if (block == AMBlocks.SPACE_SLIME_BLOCK.get())
			cir.setReturnValue(true);
	}

	@Inject(method = "isAdjacentBlockStuck(Lnet/minecraft/block/Block;Lnet/minecraft/block/Block;)Z", at = @At("HEAD"), cancellable = true)
	private static void astromine_isAdjacentBlockStuck(Block firstBlock, Block secondBlock, CallbackInfoReturnable<Boolean> cir) {
		if (firstBlock == AMBlocks.SPACE_SLIME_BLOCK.get() && !isBlockSticky(secondBlock))
			cir.setReturnValue(false);
		else if (secondBlock == AMBlocks.SPACE_SLIME_BLOCK.get() && !isBlockSticky(firstBlock))
			cir.setReturnValue(false);
	}
}
