/*
 * MIT License
 *
 * Copyright (c) 2020 Chainmail Studios
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

package com.github.chainmailstudios.astromine.common.component.inventory;

import com.github.chainmailstudios.astromine.common.component.inventory.base.ItemComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

import com.github.chainmailstudios.astromine.common.utilities.data.predicate.TriPredicate;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SimpleItemComponent implements ItemComponent {
	private final Int2ObjectOpenHashMap<ItemStack> contents = new Int2ObjectOpenHashMap<>();

	private final List<Runnable> listeners = new ArrayList<>();
	private final int size;
	private TriPredicate<@Nullable Direction, ItemStack, Integer> insertPredicate = (direction, stack, slot) -> true;

	private TriPredicate<@Nullable Direction, ItemStack, Integer> extractPredicate = (direction, stack, integer) -> true;


	/** Instantiates a {@link SimpleItemComponent}. */
	protected SimpleItemComponent(int size) {
		this.size = size;

		for (int i = 0; i < size; ++i) {
			contents.put(i, ItemStack.EMPTY);
		}

		this.contents.defaultReturnValue(ItemStack.EMPTY);
	}

	/** Instantiates a {@link SimpleItemComponent}. */
	protected SimpleItemComponent(ItemStack... stacks) {
		this(stacks.length);

		for (int i = 0; i < stacks.length; ++i) {
			setStack(i, stacks[i]);
		}
	}

	/** Instantiates a {@link SimpleItemComponent}. */
	public static SimpleItemComponent of(int size) {
		return new SimpleItemComponent(size);
	}

	/** Instantiates a {@link SimpleItemComponent}. */
	public static SimpleItemComponent of(ItemStack... stacks) {
		return new SimpleItemComponent(stacks);
	}

	/** Returns this component with an added insertion predicate. */
	public SimpleItemComponent withInsertPredicate(TriPredicate<@Nullable Direction, ItemStack, Integer> predicate) {
		TriPredicate<Direction, ItemStack, Integer> triPredicate = this.insertPredicate;
		this.insertPredicate = (direction, stack, integer) -> triPredicate.test(direction, stack, integer) && predicate.test(direction, stack, integer);
		return this;
	}

	/** Returns this component with an added extraction predicate. */
	public SimpleItemComponent withExtractPredicate(TriPredicate<@Nullable Direction, ItemStack, Integer> predicate) {
		TriPredicate<Direction, ItemStack, Integer> triPredicate = this.extractPredicate;
		this.extractPredicate = (direction, stack, integer) -> triPredicate.test(direction, stack, integer) && predicate.test(direction, stack, integer);
		return this;
	}

	/** Override behavior to take {@link #insertPredicate} into account. */
	@Override
	public boolean canInsert(@Nullable Direction direction, ItemStack stack, int slot) {
		return insertPredicate.test(direction, stack, slot) && ItemComponent.super.canInsert(direction, stack, slot);
	}

	/** Override behavior to take {@link #extractPredicate} into account. */
	@Override
	public boolean canExtract(@Nullable Direction direction, ItemStack stack, int slot) {
		return extractPredicate.test(direction, stack, slot) && ItemComponent.super.canExtract(direction, stack, slot);
	}

	/** Returns this component's size. */
	@Override
	public int getSize() {
		return this.size;
	}

	/** Returns this component's contents. */
	@Override
	public Map<Integer, ItemStack> getContents() {
		return this.contents;
	}

	/** Returns this component's listeners. */
	@Override
	public List<Runnable> getListeners() {
		return this.listeners;
	}

	/** Asserts the equality of the objects. */
	@Override
	public boolean equals(Object object) {
		if (this == object) return true;

		if (!(object instanceof SimpleItemComponent)) return false;

		SimpleItemComponent entries = (SimpleItemComponent) object;
		return Objects.equals(contents, entries.contents);
	}

	/** Returns the hash for this volume. */
	@Override
	public int hashCode() {
		return Objects.hash(contents);
	}

	/** Returns this inventory's string representation. */
	@Override
	public String toString() {
		return  getContents().entrySet().stream().map((entry) -> String.format("%s - [%s]", entry.getKey(), entry.getValue().toString())).collect(Collectors.joining(", "));
	}
}
