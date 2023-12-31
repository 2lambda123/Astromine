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

package com.github.mixinors.astromine.common.util;

import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import com.mojang.datafixers.util.Pair;

import com.google.common.collect.Lists;
import java.util.Collection;

/**
 * Originally from {@see https://www.youtube.com/watch?v=I9Qn_oIx6Oo}.
 */
public class VoxelShapeUtils {
	private static final double CENTER = 0.5;

	private static final double NINETY_DEGREES = Math.toRadians(90);
	private static final double ONE_HUNDRED_EIGHTY_DEGREES = Math.toRadians(180);
	private static final double TWO_HUNDRED_SEVENTY_DEGREES = Math.toRadians(270);

	/** Returns an union of all the given {@link VoxelShape}s. */
	public static VoxelShape union(VoxelShape... shapes) {
		return union(Lists.newArrayList(shapes));
	}

	/** Returns an union of all the given {@link VoxelShape}s. */
	public static VoxelShape union(Collection<VoxelShape> shapes) {
		var collision = VoxelShapes.empty();
		
		for (var shape : shapes) {
			collision = VoxelShapes.union(shape, collision);
		}
		
		return collision;
	}

	/** Returns the given {@link VoxelShape} rotated the supplied radians in the specified {@link Direction.Axis}. */
	public static VoxelShape rotate(Direction.Axis axis, double radians, VoxelShape shape) {
		var collision = VoxelShapes.empty();

		for (var box : shape.getBoundingBoxes()) {
			var min = switch (axis) {
				case X -> rotatePoint(box.minY, box.minZ, radians);
				case Z -> rotatePoint(box.minX, box.minY, radians);
				default -> rotatePoint(box.minX, box.minZ, radians);
			};
			
			var max = switch (axis) {
				case X -> rotatePoint(box.maxY, box.maxZ, radians);
				case Z -> rotatePoint(box.maxX, box.maxY, radians);
				default -> rotatePoint(box.maxX, box.maxZ, radians);
			};
			
			collision = VoxelShapes.union(
					collision,
					switch (axis) {
						case X -> VoxelShapes.cuboid(box.minX, min.getFirst(), min.getSecond(), box.maxX, max.getFirst(), max.getSecond());
						case Z -> VoxelShapes.cuboid(min.getFirst(), min.getSecond(), box.minZ, max.getFirst(), max.getSecond(), box.maxZ);
						default -> VoxelShapes.cuboid(min.getFirst(), box.minY, min.getSecond(), max.getFirst(), box.maxY, max.getSecond());
					});
		}
		
		return collision;
	}

	/** Returns the given {@link VoxelShape}s rotated the supplied radians in the specified {@link Direction.Axis}. */
	public static VoxelShape rotate(Direction.Axis axis, double radians, Collection<VoxelShape> shapes) {
		var collision = VoxelShapes.empty();
		
		for (var shape : shapes) {
			collision = VoxelShapes.union(collision, rotate(axis, radians, shape));
		}
		
		return collision;
	}

	/** Returns the given {@link VoxelShape}s rotated the supplied radians in the specified {@link Direction.Axis}. */
	public static VoxelShape rotate(Direction.Axis axis, double radians, VoxelShape... shapes) {
		return rotate(axis, radians, Lists.newArrayList(shapes));
	}

	/** Returns the given {@link VoxelShape} rotated towards the specified {@link Direction}. */
	public static VoxelShape rotate(Direction direction, VoxelShape shape) {
		return switch (direction) {
			case EAST -> rotateNinety(Direction.Axis.Y, shape);
			case SOUTH -> rotateOneHundredAndEighty(Direction.Axis.Y, shape);
			case WEST -> rotateTwoHundredAndSeventy(Direction.Axis.Y, shape);
			default -> shape;
		};
	}

	/** Rotates the given points according to the specified rotation. */
	private static Pair<Double, Double> rotatePoint(double p1, double p2, double rotation) {
		return rotatePoint(p1, p2, rotation, CENTER);
	}

	/** Rotates the given points according to the specified rotation. */
	private static Pair<Double, Double> rotatePoint(double p1, double p2, double rotation, double center) {
		return Pair.of(((p1 - center) * Math.cos(rotation) - ((p2 - center) * Math.sin(rotation))) + center, ((p1 - center) * Math.sin(rotation)) + ((p2 - center) * Math.cos(rotation)) + center);
	}

	/** Returns the given {@link VoxelShape}s rotated 90 degrees in the specified {@link Direction.Axis}. */
	public static VoxelShape rotateNinety(Direction.Axis axis, Collection<VoxelShape> shapes) {
		return rotate(axis, NINETY_DEGREES, shapes);
	}

	/** Returns the given {@link VoxelShape}s rotated 90 degrees in the specified {@link Direction.Axis}. */
	public static VoxelShape rotateNinety(Direction.Axis axis, VoxelShape... shapes) {
		return rotate(axis, NINETY_DEGREES, shapes);
	}

	/** Returns the given {@link VoxelShape}s rotated 180 degrees in the specified {@link Direction.Axis}. */
	public static VoxelShape rotateOneHundredAndEighty(Direction.Axis axis, Collection<VoxelShape> shapes) {
		return rotate(axis, ONE_HUNDRED_EIGHTY_DEGREES, shapes);
	}

	/** Returns the given {@link VoxelShape}s rotated 180 degrees in the specified {@link Direction.Axis}. */
	public static VoxelShape rotateOneHundredAndEighty(Direction.Axis axis, VoxelShape... shapes) {
		return rotate(axis, ONE_HUNDRED_EIGHTY_DEGREES, shapes);
	}

	/** Returns the given {@link VoxelShape}s rotated 270 degrees in the specified {@link Direction.Axis}. */
	public static VoxelShape rotateTwoHundredAndSeventy(Direction.Axis axis, Collection<VoxelShape> shapes) {
		return rotate(axis, TWO_HUNDRED_SEVENTY_DEGREES, shapes);
	}

	/** Returns the given {@link VoxelShape}s rotated 270 degrees in the specified {@link Direction.Axis}. */
	public static VoxelShape rotateTwoHundredAndSeventy(Direction.Axis axis, VoxelShape... shapes) {
		return rotate(axis, TWO_HUNDRED_SEVENTY_DEGREES, shapes);
	}
}
