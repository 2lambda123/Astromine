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

package com.github.mixinors.astromine.compat.techreborn.common.util;

import net.minecraft.util.math.Direction;

import org.jetbrains.annotations.Nullable;
import team.reborn.energy.EnergySide;

public class TREnergyUtils {
	/** Returns the {@link Direction} corresponding to the specified {@link EnergySide}. */
	@Nullable
	public static Direction toDirection(EnergySide side) {
		return switch (side) {
			case NORTH -> Direction.NORTH;
			case SOUTH -> Direction.SOUTH;
			case WEST -> Direction.WEST;
			case EAST -> Direction.EAST;
			case UP -> Direction.UP;
			case DOWN -> Direction.DOWN;
			case UNKNOWN -> null;
		};
	}

	/** Returns the {@link EnergySide} corresponding to the specified {@link Direction}. */
	public static EnergySide toSide(Direction direction) {
		return switch (direction) {
			case NORTH -> EnergySide.NORTH;
			case SOUTH -> EnergySide.SOUTH;
			case WEST -> EnergySide.WEST;
			case EAST -> EnergySide.EAST;
			case UP -> EnergySide.UP;
			case DOWN -> EnergySide.DOWN;
		};
	}
}
