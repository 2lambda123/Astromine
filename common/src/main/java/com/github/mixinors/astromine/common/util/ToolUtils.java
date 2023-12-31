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

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.MiningToolItem;

import java.util.UUID;

public class ToolUtils {
	/** Returns half of the sum of the attack damage of two {@link MiningToolItem}s. */
	public static float getAttackDamage(MiningToolItem first, MiningToolItem second) {
		return (first.getAttackDamage() + second.getAttackDamage()) / 2.0F;
	}

	/** Returns a third of the sum of the attack speed of two {@link MiningToolItem}s. */
	public static float getAttackSpeed(MiningToolItem first, MiningToolItem second) {
		return (getAttackSpeed(first) + getAttackSpeed(second)) / 3.0F;
	}

	/** Returns the attack speed of a {@link MiningToolItem}. */
	private static float getAttackSpeed(MiningToolItem item) {
		return item.getAttributeModifiers(EquipmentSlot.MAINHAND).get(EntityAttributes.GENERIC_ATTACK_SPEED).stream().filter((EntityAttributeModifier modifier) -> modifier.getId().equals(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"))).map(EntityAttributeModifier::getValue).findFirst().orElse(0d).floatValue();
	}
}
