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

package com.github.mixinors.astromine.common.block.entity;

import com.github.mixinors.astromine.common.block.ConveyorBlock;
import com.github.mixinors.astromine.common.component.base.ItemComponent;
import com.github.mixinors.astromine.registry.common.AMBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;

import com.github.mixinors.astromine.common.conveyor.Conveyable;
import com.github.mixinors.astromine.common.conveyor.Conveyor;
import com.github.mixinors.astromine.common.conveyor.ConveyorConveyable;
import com.github.mixinors.astromine.common.conveyor.ConveyorTypes;

import java.util.function.Supplier;

public class VerticalConveyorBlockEntity extends ConveyorBlockEntity {
	protected boolean up = false;

	protected int horizontalPosition;
	protected int prevHorizontalPosition;

	public VerticalConveyorBlockEntity() {
		super(AMBlockEntityTypes.VERTICAL_CONVEYOR);
	}

	public VerticalConveyorBlockEntity(Supplier<? extends BlockEntityType<?>> type) {
		super(type);
	}

	@Override
	public ItemComponent createItemComponent() {
		return ItemComponent.of(1).withListener((inventory) -> {
			if (world instanceof ServerWorld serverWorld) {
				sendPacket(serverWorld, toTag(new CompoundTag()));
			}
		});
	}

	@Override
	public void tick() {
		var state = getCachedState();
		var block = state.getBlock();
		
		var direction = state.get(HorizontalFacingBlock.FACING);
		var speed = ((Conveyor) block).getSpeed();

		if (!isEmpty()) {
			if (state.get(ConveyorBlock.CONVEYOR)) {
				var conveyorPos = getPos().offset(direction).up();
				
				if (world.getBlockEntity(conveyorPos) instanceof Conveyable conveyable) {
					if (position < speed) {
						handleMovement(conveyable, speed, false);
					} else {
						prevPosition = speed;
						handleMovementHorizontal(conveyable, speed, true);
					}
				}
			} else if (up) {
				var upPos = getPos().up();
				
				if (world.getBlockEntity(upPos) instanceof Conveyable conveyable) {
					handleMovement(conveyable, speed, true);
				}
			} else {
				setPosition(0);
			}
		} else {
			setPosition(0);
		}
	}

	public void handleMovementHorizontal(Conveyable conveyable, int speed, boolean transition) {
		int accepted = conveyable.accepts(items.getFirst());

		if (accepted > 0) {
			if (horizontalPosition < speed) {
				setHorizontalPosition(getHorizontalPosition() + 2);
			} else if (transition) {
				var split = items.getFirst().copy();
				split.setCount(Math.min(accepted, split.getCount()));

				items.getFirst().decrement(accepted);
				items.updateListeners();

				conveyable.give(split);
			}
		} else if (conveyable instanceof ConveyorConveyable conveyor) {
			if (horizontalPosition < speed && horizontalPosition + 4 < conveyor.getPosition() && conveyor.getPosition() > 4) {
				setHorizontalPosition(getHorizontalPosition() + 2);
			} else {
				prevHorizontalPosition = horizontalPosition;
			}
		}
	}

	@Override
	public boolean canInsert(Direction direction) {
		return !getCachedState().get(ConveyorBlock.FRONT) && direction == Direction.DOWN || direction == getCachedState().get(HorizontalFacingBlock.FACING).getOpposite();
	}

	@Override
	public boolean canExtract(Direction direction, ConveyorTypes type) {
		return type == ConveyorTypes.NORMAL ? getCachedState().get(HorizontalFacingBlock.FACING) == direction : direction == Direction.UP;
	}

	public boolean hasUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;

		markDirty();
		
		if (world instanceof ServerWorld serverWorld) {
			sendPacket(serverWorld, toTag(new CompoundTag()));
		}
	}

	@Override
	public int[] getRenderAttachmentData() {
		return new int[]{ position, prevPosition, horizontalPosition, prevHorizontalPosition };
	}

	public int getHorizontalPosition() {
		return horizontalPosition;
	}

	public void setHorizontalPosition(int horizontalPosition) {
		if (horizontalPosition == 0)
			this.prevHorizontalPosition = 0;
		else this.prevHorizontalPosition = this.horizontalPosition;

		this.horizontalPosition = horizontalPosition;
	}

	@Override
	public void fromTag(BlockState state, CompoundTag compoundTag) {
		super.fromTag(state, compoundTag);

		up = compoundTag.getBoolean("Up");

		horizontalPosition = compoundTag.getInt("HorizontalPosition");
		prevHorizontalPosition = horizontalPosition = compoundTag.getInt("HorizontalPosition");
	}

	@Override
	public CompoundTag toTag(CompoundTag compoundTag) {
		compoundTag.putBoolean("Up", up);

		compoundTag.putInt("HorizontalPosition", horizontalPosition);

		return super.toTag(compoundTag);
	}
}
