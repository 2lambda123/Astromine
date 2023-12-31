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
import com.github.mixinors.astromine.registry.common.AMItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import com.github.mixinors.astromine.common.item.base.EnergyItem;
import com.github.mixinors.astromine.registry.common.AMConfig;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class GravityGauntletItem extends EnergyItem {
	private static final Multimap<EntityAttribute, EntityAttributeModifier> ENTITY_ATTRIBUTE_MODIFIERS = HashMultimap.create();

	static {
		ENTITY_ATTRIBUTE_MODIFIERS.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "attack", 4f, EntityAttributeModifier.Operation.ADDITION));
	}

	public GravityGauntletItem(Settings settings, double size) {
		super(settings, size);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		var stack = user.getStackInHand(hand);
		
		if (hand == Hand.OFF_HAND)
			return TypedActionResult.pass(stack);
		
		var offStack = user.getStackInHand(Hand.OFF_HAND);
		
		if (offStack.getItem() == AMItems.GRAVITY_GAUNTLET.get()) {
			var ourEnergyComponent = EnergyComponent.from(stack);
			var theirEnergyComponent = EnergyComponent.from(offStack);
			
			if (ourEnergyComponent.getAmount() > AMConfig.get().gravityGauntletConsumed && theirEnergyComponent.getAmount() > AMConfig.get().gravityGauntletConsumed) {
				user.setCurrentHand(hand);
				return TypedActionResult.success(stack);
			}
		}
		return super.use(world, user, hand);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (world.isClient)
			return stack;
		
		var offStack = user.getStackInHand(Hand.OFF_HAND);
		
		if (offStack.getItem() == AMItems.GRAVITY_GAUNTLET.get()) {
			var ourEnergyComponent = EnergyComponent.from(stack);
			var theirEnergyComponent = EnergyComponent.from(offStack);
			
			if (ourEnergyComponent.getAmount() > AMConfig.get().gravityGauntletConsumed && theirEnergyComponent.getAmount() > AMConfig.get().gravityGauntletConsumed) {
				ourEnergyComponent.take(AMConfig.get().gravityGauntletConsumed);
				theirEnergyComponent.take(AMConfig.get().gravityGauntletConsumed);
				
				stack.getOrCreateTag().putBoolean("Charged", true);
				offStack.getOrCreateTag().putBoolean("Charged", true);
				
				return stack;
			}
		}
		
		return super.finishUsing(stack, world, user);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 30;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (attacker.world.isClient)
			return super.postHit(stack, target, attacker);
		
		var offStack = attacker.getStackInHand(Hand.OFF_HAND);
		
		if (offStack.getItem() == AMItems.GRAVITY_GAUNTLET.get()) {
			if (stack.getOrCreateTag().getBoolean("Charged") && offStack.getOrCreateTag().getBoolean("Charged")) {
				target.takeKnockback(1.0F, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
				
				target.addVelocity(0.0F, 0.5F, 0.0F);
				
				stack.getOrCreateTag().putBoolean("Charged", false);
				offStack.getOrCreateTag().putBoolean("Charged", false);
				
				return true;
			}
		}
		
		return super.postHit(stack, target, attacker);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean("Charged");
	}

	// TODO: dynamic once not broken so only provide when charged
	// What? - 18/05/2021 - 18:05:27
	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		if (slot == EquipmentSlot.MAINHAND) {
			return ENTITY_ATTRIBUTE_MODIFIERS;
		}
		
		return super.getAttributeModifiers(slot);
	}
}
