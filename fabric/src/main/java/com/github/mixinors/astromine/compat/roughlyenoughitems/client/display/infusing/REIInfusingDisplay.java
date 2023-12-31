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

package com.github.mixinors.astromine.compat.roughlyenoughitems.client.display.infusing;

import com.github.mixinors.astromine.compat.roughlyenoughitems.client.REIPlugin;
import net.minecraft.util.Identifier;

import com.github.mixinors.astromine.common.recipe.AltarRecipe;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeDisplay;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class REIInfusingDisplay implements RecipeDisplay {
	private List<List<EntryStack>> inputs;
	private List<List<EntryStack>> outputs;
	private Identifier recipeId;

	public REIInfusingDisplay(AltarRecipe recipe) {
		this(EntryStack.ofIngredients(recipe.getPreviewInputs()), Collections.singletonList(Collections.singletonList(EntryStack.create(recipe.getOutput().copy()))), recipe.getId());
	}

	public REIInfusingDisplay(List<List<EntryStack>> inputs, List<List<EntryStack>> outputs, Identifier recipeId) {
		this.inputs = inputs;
		this.outputs = outputs;
		this.recipeId = recipeId;
	}

	@Override
	public List<List<EntryStack>> getInputEntries() {
		return inputs;
	}

	@Override
	public List<List<EntryStack>> getResultingEntries() {
		return outputs;
	}

	@Override
	public Optional<Identifier> getRecipeLocation() {
		return Optional.ofNullable(recipeId);
	}

	@Override
	public Identifier getRecipeCategory() {
		return REIPlugin.INFUSING;
	}
}
