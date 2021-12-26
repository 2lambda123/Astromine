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

package com.github.mixinors.astromine.common.fluid;

import java.util.Map;

import com.github.mixinors.astromine.common.util.ClientUtils;
import com.github.mixinors.astromine.registry.common.AMBlocks;
import com.github.mixinors.astromine.registry.common.AMFluids;
import com.github.mixinors.astromine.registry.common.AMItems;
import dev.architectury.platform.Platform;
import dev.architectury.registry.block.BlockProperties;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.vini2003.hammer.common.color.Color;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;

import com.shnupbups.cauldronlib.CauldronLib;
import com.shnupbups.cauldronlib.block.FullCauldronBlock;

/**
 * A class representing a {@link Fluid} with
 * extra properties.
 */
public abstract class ExtendedFluid extends FlowableFluid {
	public static final Material INDUSTRIAL_FLUID_MATERIAL = new FabricMaterialBuilder(MapColor.WATER_BLUE).allowsMovement()
			.lightPassesThrough()
			.destroyedByPiston()
			.replaceable()
			.liquid()
			.notSolid().build();

	private final int fogColor;
	private final int tintColor;

	private final boolean isInfinite;

	private RegistrySupplier<Block> block;

	private Fluid flowing;
	private Fluid still;

	private RegistrySupplier<Item> bucket;

	private Map<Item, CauldronBehavior> cauldronBehaviorMap;
	private RegistrySupplier<Block> cauldron;

	private final DamageSource source;

	/**
	 * Instantiates an {@link ExtendedFluid}s.
	 */
	public ExtendedFluid(int fogColor, int tintColor, boolean isInfinite, @Nullable DamageSource source) {
		this.fogColor = fogColor;
		this.tintColor = tintColor;
		this.isInfinite = isInfinite;
		this.source = source == null ? DamageSource.GENERIC : source;
	}

	/**
	 * Instantiates a {@link Builder}.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Returns this fluid's {@link DamageSource}, used when
	 * damaging entities it comes in contact with.
	 */
	public DamageSource getSource() {
		return source;
	}

	/**
	 * Returns this fluid's still form.
	 */
	@Override
	public Fluid getStill() {
		return still;
	}

	/**
	 * Returns this fluid's flowing form.
	 */
	@Override
	public Fluid getFlowing() {
		return flowing;
	}

	/**
	 * Asserts whether this fluid is infinite or not.
	 */
	@Override
	protected boolean isInfinite() {
		return isInfinite;
	}

	/**
	 * Returns this fluid's fog color.
	 */
	public int getFogColor() {
		return fogColor;
	}

	/**
	 * Returns this fluid's tint color.
	 */
	public int getTintColor() {
		return tintColor;
	}

	/**
	 * Returns this fluid's block.
	 */
	public Block getBlock() {
		return block.get();
	}

	/**
	 * Override behavior to mimic {@link WaterFluid}.
	 */
	@Override
	protected void beforeBreakingBlock(WorldAccess world, BlockPos position, BlockState state) {
		BlockEntity blockEntity = world.getBlockEntity(position);
		Block.dropStacks(state, world, position, blockEntity);
	}

	/**
	 * Asserts whether the given fluid equals
	 * this fluid's still or flowable form, or not.
	 */
	@Override
	public boolean matchesType(Fluid fluid) {
		return fluid == flowing || fluid == still;
	}

	/**
	 * Override behavior to mimic {@link WaterFluid}.
	 */
	@Override
	protected int getFlowSpeed(WorldView world) {
		return 4;
	}

	/**
	 * Override behavior to mimic {@link WaterFluid}.
	 */
	@Override
	protected int getLevelDecreasePerBlock(WorldView world) {
		return 1;
	}

	/**
	 * Returns this fluid's {@link Item} representation.
	 */
	@Override
	public Item getBucketItem() {
		return bucket.get();
	}

	/**
	 * Override behavior to mimic {@link WaterFluid}.
	 */
	@Override
	protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
		return direction == Direction.DOWN && fluid != flowing && fluid != still;
	}

	/**
	 * Override behavior to mimic {@link WaterFluid}.
	 */
	@Override
	public int getTickRate(WorldView world) {
		return 5;
	}

	/**
	 * Override behavior to mimic {@link WaterFluid}.
	 */
	@Override
	protected float getBlastResistance() {
		return 100.0F;
	}

	/**
	 * Returns this fluid's {@link BlockState} representation.
	 */
	@Override
	protected BlockState toBlockState(FluidState state) {
		return block.get().getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
	}

	public Map<Item, CauldronBehavior> getCauldronBehaviorMap() {
		return cauldronBehaviorMap;
	}

	public Block getCauldron() {
		return cauldron.get();
	}

	/**
	 * A builder for {@link ExtendedFluid}s.
	 */
	public static class Builder {
		int fog = Color.Standard.toInt();
		int tint = Color.Standard.toInt();
		int damage = 0;

		boolean isInfinite = false;
		boolean isToxic = false;

		String name = "";

		RegistrySupplier<Block> block;

		Fluid flowing;
		Fluid still;

		RegistrySupplier<Item> bucket;

		Map<Item, CauldronBehavior> cauldronBehaviorMap;
		RegistrySupplier<Block> cauldron;

		DamageSource source;

		ItemGroup group;

		/**
		 * We only want {@link ExtendedFluid#builder()} to
		 * be able to instantiate a {@link Builder}.
		 */
		private Builder() {
		}

		/**
		 * Sets this builder's fluid fog to the specified value.
		 */
		public Builder fog(int fog) {
			this.fog = fog;
			return this;
		}

		/**
		 * Sets this builder's fluid tint to the specified value.
		 */
		public Builder tint(int tint) {
			this.tint = tint;
			return this;
		}

		/**
		 * Sets this builder's damage to the specified value.
		 */
		public Builder damage(int damage) {
			this.damage = damage;
			return this;
		}

		/**
		 * Sets whether this builder's fluid is infinite to the specified value.
		 */
		public Builder infinite(boolean isInfinite) {
			this.isInfinite = isInfinite;
			return this;
		}

		/**
		 * Sets whether this builder's fluid is toxic to the specified value.
		 */
		public Builder toxic(boolean isToxic) {
			this.isToxic = isToxic;
			return this;
		}

		/**
		 * Sets this builder's fluid name to the specified value.
		 */
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		/**
		 * Sets this builder's fluid {@link DamageSource} to the specified value.
		 */
		public Builder source(DamageSource source) {
			this.source = source;
			return this;
		}

		/**
		 * Sets this builder's {@link ItemGroup} to the specified value.
		 */
		public Builder group(ItemGroup group) {
			this.group = group;
			return this;
		}

		/**
		 * Builds this builder's fluid.
		 * Part of the process is delegated to
		 * {@link ClientUtils#registerExtendedFluid(String, int, Fluid, Fluid)},
		 * since rendering registration cannot be done on the server side.
		 */
		public ExtendedFluid build() {
			ExtendedFluid flowing = AMFluids.register(name + "_flowing", new Flowing(fog, tint, isInfinite, source));
			ExtendedFluid still = AMFluids.register(name, new Still(fog, tint, isInfinite, source));

			flowing.flowing = flowing;
			still.flowing = flowing;
			this.flowing = flowing;

			flowing.still = still;
			still.still = still;
			this.still = still;

			RegistrySupplier<Block> block = AMBlocks.register(name, () -> new FluidBlock(still, AbstractBlock.Settings.of(INDUSTRIAL_FLUID_MATERIAL).noCollision().strength(100.0F).dropsNothing()));

			RegistrySupplier<Item> bucket = AMItems.register(name + "_bucket", () -> new BucketItem(still, (new Item.Settings()).recipeRemainder(Items.BUCKET).maxCount(1).group(group)));

			flowing.block = block;
			still.block = block;
			this.block = block;

			flowing.bucket = bucket;
			still.bucket = bucket;
			this.bucket = bucket;

			Map<Item, CauldronBehavior> cauldronBehaviorMap = CauldronBehavior.createMap();
			RegistrySupplier<Block> cauldron = AMBlocks.register(name + "_cauldron", () -> new FullCauldronBlock(BlockProperties.copy(Blocks.CAULDRON), cauldronBehaviorMap));

			flowing.cauldronBehaviorMap = cauldronBehaviorMap;
			still.cauldronBehaviorMap = cauldronBehaviorMap;
			this.cauldronBehaviorMap = cauldronBehaviorMap;
			flowing.cauldron = cauldron;
			still.cauldron = cauldron;
			this.cauldron = cauldron;

			CauldronLib.registerBehaviorMap(cauldronBehaviorMap);
			CauldronLib.registerFillFromBucketBehavior(this.bucket.get(), this.cauldron.get());
			cauldronBehaviorMap.put(Items.BUCKET, CauldronLib.createEmptyIntoBucketBehavior(this.bucket.get()));
			CauldronFluidContent.registerCauldron(this.cauldron.get(), this.still, FluidConstants.BUCKET, null); // Fabric only! If we're gonna do a Forge version, make sure this only runs on Fabric!

			if (Platform.getEnv() == EnvType.CLIENT) {
				ClientUtils.registerExtendedFluid(name, tint, still, flowing);
			}

			return still;
		}
	}

	/**
	 * A flowing version of an {@link ExtendedFluid}.
	 */
	public static class Flowing extends ExtendedFluid {
		/**
		 * Instantiates a {@link Flowing} {@link ExtendedFluid}
		 * s.
		 */
		public Flowing(int fogColor, int tintColor, boolean isInfinite, @Nullable DamageSource source) {
			super(fogColor, tintColor, isInfinite, source);
		}

		/**
		 * Override behavior to add the {@link #LEVEL}
		 * property to the given builder.
		 */
		@Override
		protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
			super.appendProperties(builder);
			builder.add(LEVEL);
		}

		/**
		 * Override behavior to return the fluid
		 * level based on the {@link #LEVEL} property.
		 */
		@Override
		public int getLevel(FluidState state) {
			return state.get(LEVEL);
		}

		/**
		 * Override behavior to always return false,
		 * since this fluid is flowing.
		 */
		@Override
		public boolean isStill(FluidState state) {
			return false;
		}
	}

	/**
	 * A still version of an {@link ExtendedFluid}.
	 */
	public static class Still extends ExtendedFluid {
		/**
		 * Instantiates a {@link Still} {@link ExtendedFluid}
		 * s.
		 */
		public Still(int fogColor, int tintColor, boolean isInfinite, @Nullable DamageSource source) {
			super(fogColor, tintColor, isInfinite, source);
		}

		/**
		 * Override behavior to always return 8,
		 * since this fluid is still.
		 */
		@Override
		public int getLevel(FluidState state) {
			return 8;
		}

		/**
		 * Override behavior to always return true,
		 * since this fluid is still.
		 */
		@Override
		public boolean isStill(FluidState state) {
			return true;
		}
	}
}
