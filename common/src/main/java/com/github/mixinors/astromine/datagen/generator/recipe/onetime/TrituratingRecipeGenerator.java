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

package com.github.mixinors.astromine.datagen.generator.recipe.onetime;

import com.github.mixinors.astromine.common.util.GeneratorUtils;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.github.mixinors.astromine.AMCommon;
import com.github.mixinors.astromine.datagen.generator.recipe.onetime.base.EnergyProcessingRecipeGenerator;
import com.github.mixinors.astromine.common.recipe.TrituratingRecipe;
import me.shedaniel.cloth.api.datagen.v1.RecipeData;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TrituratingRecipeGenerator extends EnergyProcessingRecipeGenerator {
	private final String name;

	public TrituratingRecipeGenerator(String name, Ingredient input, int inputCount, ItemConvertible output, int outputCount, int time, int energy) {
		super(input, inputCount, output, outputCount, time, energy);
		this.name = name;
	}

	public TrituratingRecipeGenerator(String name, Ingredient input, ItemConvertible output, int outputCount, int time, int energy) {
		this(name, input, 1, output, outputCount, time, energy);
	}

	public TrituratingRecipeGenerator(String name, Ingredient input, ItemConvertible output, int time, int energy) {
		this(name, input, 1, output, 1, time, energy);
	}

	@Override
	public Identifier getRecipeId() {
		return AMCommon.id(name);
	}

	@Override
	public void generate(RecipeData recipes) {
		recipes.accept(GeneratorUtils.Providers.createProvider(TrituratingRecipe.Serializer.INSTANCE, getRecipeId(), json -> {
			JsonElement inputJson = input.toJson();
			if (inputJson.isJsonObject()) {
				inputJson.getAsJsonObject().addProperty("count", inputCount);
			}

			json.add("input", inputJson);

			JsonObject outputJson = new JsonObject();
			outputJson.addProperty("item", Registry.ITEM.getId(output.asItem()).toString());
			outputJson.addProperty("count", outputCount);

			json.add("output", outputJson);
			json.addProperty("time", time);
			json.addProperty("energy_input", energy);
		}));
	}

	@Override
	public String getGeneratorName() {
		return "triturating";
	}
}
