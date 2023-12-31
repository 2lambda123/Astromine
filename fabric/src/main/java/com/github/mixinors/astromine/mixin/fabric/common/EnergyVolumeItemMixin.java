package com.github.mixinors.astromine.mixin.fabric.common;

import com.github.mixinors.astromine.common.component.base.EnergyComponent;
import com.github.mixinors.astromine.common.item.base.EnergyItem;
import me.shedaniel.cloth.api.durability.bar.DurabilityBarItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import team.reborn.energy.EnergyStorage;
import team.reborn.energy.EnergyTier;

@Mixin(EnergyItem.class)
public abstract class EnergyVolumeItemMixin implements EnergyStorage, DurabilityBarItem {
	@Shadow public abstract double getSize();
	
	/** Returns this item's size. */
	@Override
	public double getMaxStoredPower() {
		return getSize();
	}

	/** Override behavior to ignore TechReborn's energy tiers. */
	@Override
	public EnergyTier getTier() {
		return EnergyTier.INSANE;
	}

	/** Override behavior to return our progress. */
	@Override
	public double getDurabilityBarProgress(ItemStack stack) {
		if (EnergyComponent.from(stack) == null || getMaxStoredPower() == 0)
			return 0;
		
		return 1 - EnergyComponent.from(stack).getAmount() / getSize();
	}

	/** Override behavior to return true. */
	@Override
	public boolean hasDurabilityBar(ItemStack stack) {
		return true;
	}

	/** Override behavior to return a median red. */
	@Override
	public int getDurabilityBarColor(ItemStack stack) {
		return 0x91261f;
	}
}
