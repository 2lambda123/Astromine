package com.github.mixinors.astromine.mixin.common.common;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Ingredient.class)
public interface IngredientAccessor {
	@Accessor
	ItemStack[] getMatchingStacks();
}
