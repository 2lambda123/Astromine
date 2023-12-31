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

package com.github.mixinors.astromine.common.item;

import com.github.mixinors.astromine.common.component.base.EnergyComponent;
import com.github.mixinors.astromine.mixin.common.common.PickaxeItemAccessor;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.github.mixinors.astromine.common.item.base.EnergyItem;
import com.github.mixinors.astromine.registry.common.AMConfig;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

public class DrillItem extends EnergyItem implements Vanishable, EnchantableToolItem {
	private final ToolMaterial material;
	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
	
	private final PickaxeItem pickaxe;
	private final ShovelItem shovel;

	public DrillItem(ToolMaterial material, float attackDamage, float attackSpeed, double size, Settings settings) {
		super(settings, size);
		
		this.pickaxe = PickaxeItemAccessor.init(material, (int) attackDamage, attackSpeed, settings);
		this.shovel = new ShovelItem(material, attackDamage, attackSpeed, settings);
		
		this.material = material;

		var builder = ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder();

		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", attackDamage, EntityAttributeModifier.Operation.ADDITION));
		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", attackSpeed, EntityAttributeModifier.Operation.ADDITION));

		this.attributeModifiers = builder.build();
	}

	@Override
	public int getEnchantability() {
		return material.getEnchantability();
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.world.isClient) {
			var energyComponent = EnergyComponent.from(stack);
			energyComponent.take(getEnergyConsumed() * AMConfig.get().drillEntityHitMultiplier);
		}

		return true;
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
			var energyComponent = EnergyComponent.from(stack);
			energyComponent.take(getEnergyConsumed());
		}

		return true;
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
	}
	
	@Override
	public boolean isEffectiveOn(BlockState state) {
		float pickaxeSpeed = pickaxe.getMiningSpeedMultiplier(ItemStack.EMPTY, state);
		float shovelSpeed = shovel.getMiningSpeedMultiplier(ItemStack.EMPTY, state);
		
		return pickaxeSpeed > 1.0F || shovelSpeed > 1.0F;
	}

	@Override
	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		float pickaxeSpeed = pickaxe.getMiningSpeedMultiplier(ItemStack.EMPTY, state);
		float shovelSpeed = shovel.getMiningSpeedMultiplier(ItemStack.EMPTY, state);
		
		return Math.max(pickaxeSpeed, shovelSpeed);
	}
	
	private double getEnergyConsumed() {
		return AMConfig.get().drillConsumed * material.getMiningSpeedMultiplier();
	}
	
	// TODO: Reimplement this on Forge module!
	
//	@Override
//	public float postProcessMiningSpeed(Tag<Item> tag, BlockState state, ItemStack stack, LivingEntity user, float currentSpeed, boolean isEffective) {
//		return Energy.of(stack).getEnergy() <= getEnergy() ? 0F : currentSpeed;
//	}
//
//	@Override
//	public float getMiningSpeedMultiplier(Tag<Item> tag, BlockState state, ItemStack stack, LivingEntity user) {
//		return material.getMiningSpeedMultiplier();
//	}
//
//	@Override
//	public int getMiningLevel(Tag<Item> tag, BlockState state, ItemStack stack, LivingEntity user) {
//		return material.getMiningLevel();
//	}
}
