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

import com.github.mixinors.astromine.common.component.base.ItemComponent;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * An {@link ItemComponent} wrapped over an {@link SidedInventory}.
 */
public class ItemComponentFromSidedInventory implements ItemComponent {
	SidedInventory inventory;

	List<Runnable> listeners = new ArrayList<>();

	/** Instantiates an {@link ItemComponentFromSidedInventory}. */
	private ItemComponentFromSidedInventory(SidedInventory inventory) {
		this.inventory = inventory;
	}

	/** Instantiates an {@link ItemComponentFromSidedInventory}. */
	public static ItemComponentFromSidedInventory of(SidedInventory inventory) {
		return new ItemComponentFromSidedInventory(inventory);
	}

	/** Returns this component's size. */
	@Override
	public int getSize() {
		return this.inventory.size();
	}

	/** Returns this component's listeners. */
	@Override
	public List<Runnable> getListeners() {
		return this.listeners;
	}

	/** Returns this component's contents. */
	@Override
	public Map<Integer, ItemStack> getContents() {
		Int2ObjectArrayMap<ItemStack> contents = new Int2ObjectArrayMap<>();
		for (var i = 0; i < this.inventory.size(); ++i) {
			contents.put(i, this.inventory.getStack(i));
		}
		contents.defaultReturnValue(ItemStack.EMPTY);
		return contents;
	}

	/** Asserts whether the given stack can be inserted through the specified
	 * direction into the supplied slot. */
	@Override
	public boolean canInsert(@Nullable Direction direction, ItemStack stack, int slot) {
		return this.inventory.isValid(slot, stack) && Arrays.stream(this.inventory.getAvailableSlots(direction)).anyMatch(it -> it == slot);
	}

	/** Asserts whether the given stack can be extracted from the specified
	 * direction from the supplied slot. */
	@Override
	public boolean canExtract(@Nullable Direction direction, ItemStack stack, int slot) {
		return Arrays.stream(this.inventory.getAvailableSlots(direction)).anyMatch(it -> it == slot);
	}

	/* Returns the {@link ItemStack} at the given slot. */
	@Override
	public ItemStack get(int slot) {
		return this.inventory.getStack(slot);
	}

	/** Sets the {@link ItemStack} at the given slot to the specified value. */
	@Override
	public void set(int slot, ItemStack stack) {
		this.inventory.setStack(slot, stack);
	}
}
