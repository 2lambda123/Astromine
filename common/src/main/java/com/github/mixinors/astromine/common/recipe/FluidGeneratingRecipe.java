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

package com.github.mixinors.astromine.common.recipe;

import com.github.mixinors.astromine.AMCommon;
import com.github.mixinors.astromine.common.recipe.type.HiddenRecipeType;
import com.github.mixinors.astromine.mixin.common.common.RecipeManagerAccessor;
import com.github.mixinors.astromine.registry.common.AMBlocks;
import me.shedaniel.architectury.core.AbstractRecipeSerializer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import com.github.mixinors.astromine.common.component.base.FluidComponent;
import com.github.mixinors.astromine.common.recipe.base.EnergyGeneratingRecipe;
import com.github.mixinors.astromine.common.recipe.ingredient.FluidIngredient;
import com.github.mixinors.astromine.common.util.DoubleUtils;
import com.github.mixinors.astromine.common.util.IntegerUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class FluidGeneratingRecipe implements Recipe<Inventory>, EnergyGeneratingRecipe<Inventory> {
	private final Identifier identifier;
	private final FluidIngredient firstInput;
	private final double energyOutput;
	private final int time;

	private static final Map<World, FluidGeneratingRecipe[]> RECIPE_CACHE = new HashMap<>();

	public FluidGeneratingRecipe(Identifier identifier, FluidIngredient firstInput, double energyOutput, int time) {
		this.identifier = identifier;
		this.firstInput = firstInput;
		this.energyOutput = energyOutput;
		this.time = time;
	}

	public static boolean allows(World world, FluidComponent fluidComponent) {
		if (RECIPE_CACHE.get(world) == null && world.getRecipeManager() instanceof RecipeManagerAccessor accessor) {
			RECIPE_CACHE.put(world,
					accessor.callGetAllOfType(Type.INSTANCE)
							.values()
							.stream()
							.map(FluidGeneratingRecipe.class::cast)
							.toArray(FluidGeneratingRecipe[]::new));
		}

		for (var recipe : RECIPE_CACHE.get(world)) {
			if (recipe.allows(fluidComponent)) {
				return true;
			}
		}

		return false;
	}

	public static Optional<FluidGeneratingRecipe> matching(World world, FluidComponent fluidComponent) {
		if (RECIPE_CACHE.get(world) == null && world.getRecipeManager() instanceof RecipeManagerAccessor accessor) {
			RECIPE_CACHE.put(world,
					accessor.callGetAllOfType(Type.INSTANCE)
							.values()
							.stream()
							.map(FluidGeneratingRecipe.class::cast)
							.toArray(FluidGeneratingRecipe[]::new));
		}

		for (var recipe : RECIPE_CACHE.get(world)) {
			if (recipe.allows(fluidComponent)) {
				return Optional.of(recipe);
			}
		}

		return Optional.empty();
	}

	public boolean matches(FluidComponent fluidComponent) {
		if (fluidComponent.getSize() < 1) {
			return false;
		}

		return firstInput.test(fluidComponent.getFirst());
	}

	public boolean allows(FluidComponent fluidComponent) {
		if (fluidComponent.getSize() < 1) {
			return false;
		}

		return firstInput.testWeak(fluidComponent.getFirst());
	}

	@Override
	public boolean matches(Inventory inventory, World world) {
		return false;
	}

	@Override
	public ItemStack craft(Inventory inventory) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean fits(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getOutput() {
		return ItemStack.EMPTY;
	}

	@Override
	public Identifier getId() {
		return identifier;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}

	@Override
	public RecipeType<?> getType() {
		return Type.INSTANCE;
	}

	@Override
	public ItemStack getRecipeKindIcon() {
		return new ItemStack(AMBlocks.ADVANCED_LIQUID_GENERATOR.get());
	}

	public FluidIngredient getFirstInput() {
		return firstInput;
	}

	public int getTime() {
		return time;
	}

	@Override
	public double getEnergyOutput() {
		return energyOutput;
	}

	public static final class Serializer extends AbstractRecipeSerializer<FluidGeneratingRecipe> {
		public static final Identifier ID = AMCommon.id("fluid_generating");

		public static final Serializer INSTANCE = new Serializer();

		private Serializer() {}

		@Override
		public FluidGeneratingRecipe read(Identifier identifier, JsonObject object) {
			FluidGeneratingRecipe.Format format = new Gson().fromJson(object, FluidGeneratingRecipe.Format.class);

			return new FluidGeneratingRecipe(
					identifier,
					FluidIngredient.fromJson(format.firstInput),
					DoubleUtils.fromJson(format.energyOutput),
					IntegerUtils.fromJson(format.time)
			);
		}

		@Override
		public FluidGeneratingRecipe read(Identifier identifier, PacketByteBuf buffer) {
			return new FluidGeneratingRecipe(
					identifier,
					FluidIngredient.fromPacket(buffer),
					DoubleUtils.fromPacket(buffer),
					IntegerUtils.fromPacket(buffer)
			);
		}

		@Override
		public void write(PacketByteBuf buffer, FluidGeneratingRecipe recipe) {
			recipe.firstInput.toPacket(buffer);
			DoubleUtils.toPacket(buffer, recipe.energyOutput);
			IntegerUtils.toPacket(buffer, recipe.time);
		}
	}

	public static final class Type implements HiddenRecipeType<FluidGeneratingRecipe> {
		public static final Type INSTANCE = new Type();

		private Type() {}
	}

	public static final class Format {
		@SerializedName("input")
		JsonElement firstInput;

		@SerializedName("energy_output")
		JsonElement energyOutput;

		JsonElement time;

		@Override
		public String toString() {
			return "Format{" + "firstInput=" + firstInput + ", energyOutput=" + energyOutput + ", time=" + time + '}';
		}
	}
}
