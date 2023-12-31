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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;

import com.github.mixinors.astromine.common.conveyor.Conveyable;
import com.github.mixinors.astromine.common.conveyor.Conveyor;
import com.github.mixinors.astromine.common.conveyor.ConveyorConveyable;
import com.github.mixinors.astromine.common.conveyor.ConveyorTypes;

public class DownVerticalConveyorBlockEntity extends ConveyorBlockEntity {
	protected boolean down = false;
	protected int horizontalPosition;
	protected int prevHorizontalPosition;

	public DownVerticalConveyorBlockEntity() {
		super(AMBlockEntityTypes.DOWNWARD_VERTICAL_CONVEYOR);
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
		var block = (Conveyor) state.getBlock();
		
		var direction = state.get(HorizontalFacingBlock.FACING);

		var speed = block.getSpeed();

		if (!isEmpty()) {
			if (state.get(ConveyorBlock.FRONT)) {
				var frontPos = getPos().offset(direction.getOpposite());

				if (world.getBlockEntity(frontPos) instanceof Conveyable conveyable) {
					if (state.get(ConveyorBlock.CONVEYOR)) {
						if (position < speed) {
							handleMovement(conveyable, speed, false);
						} else {
							prevPosition = speed;

							handleMovementHorizontal(conveyable, speed, true);
						}
					} else {
						handleMovementHorizontal(conveyable, speed, true);
					}
				}
			} else if (down) {
				var downPos = getPos().down();

				if (world.getBlockEntity(downPos) instanceof Conveyable conveyable) {
					if (state.get(ConveyorBlock.CONVEYOR)) {
						handleMovement(conveyable, speed * 2, true);
					} else {
						handleMovement(conveyable, speed, true);
					}
				}
			} else {
				setPosition(0);
			}
		} else {
			setPosition(0);
		}
	}

	public void handleMovementHorizontal(Conveyable conveyable, int speed, boolean transition) {
		int accepted = conveyable.accepts(getItemComponent().getFirst());

		if (accepted > 0) {
			if (horizontalPosition < speed) {
				setHorizontalPosition(getHorizontalPosition() + 1);
			} else if (transition) {
				var split = getItemComponent().getFirst().copy();
				split.setCount(Math.min(accepted, split.getCount()));

				items.getFirst().decrement(accepted);
				items.updateListeners();

				conveyable.give(split);
			}
		} else if (conveyable instanceof ConveyorConveyable conveyor) {
			if (horizontalPosition < speed && horizontalPosition + 4 < conveyor.getPosition() && conveyor.getPosition() > 4) {
				setHorizontalPosition(getHorizontalPosition() + 1);
			} else {
				prevHorizontalPosition = horizontalPosition;
			}
		}
	}

	@Override
	public boolean canInsert(Direction direction) {
		return direction == Direction.UP || direction == getCachedState().get(HorizontalFacingBlock.FACING);
	}

	@Override
	public boolean canExtract(Direction direction, ConveyorTypes type) {
		return getCachedState().get(HorizontalFacingBlock.FACING).getOpposite() == direction || direction == Direction.DOWN;
	}

	@Override
	public boolean hasDown() {
		return down;
	}

	@Override
	public void setDown(boolean down) {
		this.down = down;

		markDirty();

		if (!world.isClient) {
			sendPacket((ServerWorld) world, toTag(new CompoundTag()));
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
		if (horizontalPosition == 0) {
			this.prevHorizontalPosition = 0;
		} else {
			this.prevHorizontalPosition = this.horizontalPosition;
		}

		this.horizontalPosition = horizontalPosition;
	}

	@Override
	public void fromTag(BlockState state, CompoundTag compoundTag) {
		super.fromTag(state, compoundTag);

		down = compoundTag.getBoolean("Down");

		horizontalPosition = compoundTag.getInt("HorizontalPosition");
		prevHorizontalPosition = horizontalPosition = compoundTag.getInt("HorizontalPosition");
	}

	@Override
	public CompoundTag toTag(CompoundTag compoundTag) {
		compoundTag.putBoolean("Down", down);

		compoundTag.putInt("HorizontalPosition", horizontalPosition);

		return super.toTag(compoundTag);
	}
}
