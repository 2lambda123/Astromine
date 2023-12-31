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

package com.github.mixinors.astromine.common.component.compat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import com.github.mixinors.astromine.common.component.base.ItemComponent;

import static java.lang.Integer.min;

/**
 * An {@link Inventory} wrapped over an {@link ItemComponent}.
 */
public interface InventoryFromItemComponent extends Inventory {
	ItemComponent getItemComponent();
	
	/** Instantiates an {@link InventoryFromItemComponent}. */
	static InventoryFromItemComponent of(ItemComponent itemComponent) {
		return () -> itemComponent;
	}

	/** Returns this inventory's size. */
	@Override
	default int size() {
		return getItemComponent().getSize();
	}

	/** Returns the {@link ItemStack} at the given slot. */
	@Override
	default ItemStack getStack(int slot) {
		return getItemComponent().get(slot);
	}

	/** Sets the {@link ItemStack} at the given slot to the specified value. */
	@Override
	default void setStack(int slot, ItemStack stack) {
		getItemComponent().set(slot, stack);
	}

	/** Removes the {@link ItemStack} at the given slot,
	 * or a part of it as per the specified count, and returns it. */
	@Override
	default ItemStack removeStack(int slot, int count) {
		var removed = getItemComponent().remove(slot);

		var returned = removed.copy();

		returned.setCount(min(count, removed.getCount()));

		removed.decrement(count);

		getItemComponent().set(slot, removed);

		return returned;
	}

	/** Removes the {@link ItemStack} at the given slot, and returns it. */
	@Override
	default ItemStack removeStack(int slot) {
		return getItemComponent().remove(slot);
	}

	/** Asserts whether this inventory's contents are all empty or not. */
	@Override
	default boolean isEmpty() {
		return getItemComponent().isEmpty();
	}

	/** Clears this inventory's contents. */
	@Override
	default void clear() {
		getItemComponent().clear();
	}

	/** Marks this inventory as dirt, or, pending update. */
	@Override
	default void markDirty() {
		getItemComponent().updateListeners();
	}

	/** Allow the player to use this inventory by default. */
	@Override
	default boolean canPlayerUse(PlayerEntity player) {
		return true;
	}
}
