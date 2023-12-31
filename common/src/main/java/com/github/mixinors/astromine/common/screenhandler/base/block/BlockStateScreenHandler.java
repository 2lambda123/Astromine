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

package com.github.mixinors.astromine.common.screenhandler.base.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;

import com.github.vini2003.blade.common.handler.BaseScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * This class represents a {@link ScreenHandler} with an associated
 * {@link BlockPos}, {@link Block} and {@link BlockState}
 */
public abstract class BlockStateScreenHandler extends BaseScreenHandler {
	protected BlockPos pos;
	protected Block block;
	protected BlockState state;

	/** Instantiates a {@link BlockStateScreenHandler}. */
	public BlockStateScreenHandler(Supplier<? extends ScreenHandlerType<?>> type, int syncId, PlayerEntity player, BlockPos pos) {
		super(type.get(), syncId, player);

		this.state = player.world.getBlockState(pos);
		this.block = state.getBlock();
		this.pos = pos;
	}

	/** Override behavior to only allow the {@link ScreenHandler} to be open
	 * when possible, and while the associated {@link BlockState} has not
	 * changed. */
	@Override
	public boolean canUse(@Nullable PlayerEntity player) {
		return canUse(ScreenHandlerContext.create(player.world, pos), player, block);
	}
}
