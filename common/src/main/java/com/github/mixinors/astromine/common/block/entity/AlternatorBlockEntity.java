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

import com.github.mixinors.astromine.registry.common.AMBlockEntityTypes;
import com.github.mixinors.astromine.registry.common.AMSoundEvents;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;

import com.github.mixinors.astromine.common.block.entity.base.AbstractConveyableBlockEntity;

import java.util.function.Supplier;

public class AlternatorBlockEntity extends AbstractConveyableBlockEntity {
	public boolean right = false;

	public AlternatorBlockEntity() {
		super(AMBlockEntityTypes.ALTERNATOR);
	}

	public AlternatorBlockEntity(Supplier<? extends BlockEntityType<?>> type) {
		super(type);
	}

	@Override
	public void give(ItemStack stack) {
		var copyStack = stack.copy();
		
		var firstStack = items.getFirst();
		var secondStack = items.getSecond();
		
		if (isEmpty()) {
			if (right) {
				items.setSecond(copyStack);
			} else {
				items.setFirst(copyStack);
			}
			right = !right;
		} else if (!firstStack.isEmpty() && secondStack.isEmpty()) {
			items.setSecond(stack);
		} else if (!secondStack.isEmpty() && firstStack.isEmpty()) {
			items.setFirst(stack);
		}

		world.playSound(null, getPos().getX(), getPos().getY(), getPos().getZ(), AMSoundEvents.MACHINE_CLICK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
	}
}
