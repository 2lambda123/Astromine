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

package com.github.mixinors.astromine.common.item.base;

import com.github.mixinors.astromine.common.component.base.EnergyComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import com.github.mixinors.astromine.common.volume.energy.EnergyVolume;

/**
 * An {@link Item} with an attached {@link EnergyVolume}.
 */
public class EnergyItem extends Item {
	private final double size;

	/** Instantiates an {@link EnergyItem}s. */
	protected EnergyItem(Item.Settings settings, double size) {
		super(settings);

		this.size = size;
	}

	/** Instantiates an {@link EnergyItem}. */
	public static EnergyItem ofCreative(Item.Settings settings) {
		return new EnergyItem(settings, Double.MAX_VALUE);
	}

	/** Instantiates an {@link EnergyItem}s. */
	public static EnergyItem of(Settings settings, double size) {
		return new EnergyItem(settings, size);
	}

	/** Returns this item's size. */
	public double getSize() {
		return size;
	}

	/** Override behavior to add instances of {@link EnergyItem}
	 * as {@link ItemStack}s to {@link ItemGroup}s with full energy. */
	@Override
	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
		super.appendStacks(group, stacks);

		if (this.isIn(group)) {
			var stack = new ItemStack(this);

			EnergyComponent.from(stack).setAmount(getSize());

			stacks.add(stack);
		}
	}
	
	// TODO: Reimplement this on Forge module!
	
//	/** returns this item's size. */
//	@Override
//	public double getMaxStoredPower() {
//		return getSize();
//	}
//
//	/** Override behavior to ignore TechReborn's energy tiers. */
//	@Override
//	public EnergyTier getTier() {
//		return EnergyTier.INSANE;
//	}
//
//	/** Override behavior to return our progress. */
//	@Override
//	public double getDurabilityBarProgress(ItemStack stack) {
//		if (!Energy.valid(stack) || getMaxStoredPower() == 0)
//			return 0;
//		return 1 - Energy.of(stack).getEnergy() / getMaxStoredPower();
//	}
//
//	/** Override behavior to return true. */
//	@Override
//	public boolean hasDurabilityBar(ItemStack stack) {
//		return true;
//	}
//
//	/** Override behavior to return a median red. */
//	@Override
//	public int getDurabilityBarColor(ItemStack stack) {
//		return 0x91261f;
//	}
}
