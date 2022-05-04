/*
 * MIT License
 *
 * Copyright (c) 2020 - 2022 Mixinors
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
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
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

public abstract class ExtendedFluid extends FlowableFluid {
	public static final Material INDUSTRIAL_FLUID_MATERIAL = new FabricMaterialBuilder(MapColor.WATER_BLUE).allowsMovement()
			.lightPassesThrough()
			.destroyedByPiston()
			.replaceable()
			.liquid()
			.notSolid().build();

	final int fogColor;
	final int tintColor;

	final boolean infinite;

	RegistrySupplier<Block> block;

	Fluid flowing;
	Fluid still;

	RegistrySupplier<Item> bucket;

	Map<Item, CauldronBehavior> cauldronBehaviorMap;
	RegistrySupplier<Block> cauldron;

	final DamageSource source;

	public ExtendedFluid(int fogColor, int tintColor, boolean infinite, @Nullable DamageSource source) {
		this.fogColor = fogColor;
		this.tintColor = tintColor;
		this.infinite = infinite;
		this.source = source == null ? DamageSource.GENERIC : source;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public DamageSource getSource() {
		return source;
	}
	
	@Override
	public Fluid getStill() {
		return still;
	}
	
	@Override
	public Fluid getFlowing() {
		return flowing;
	}
	
	@Override
	protected boolean isInfinite() {
		return infinite;
	}
	
	public int getFogColor() {
		return fogColor;
	}
	
	public int getTintColor() {
		return tintColor;
	}
	
	public Block getBlock() {
		return block.get();
	}
	
	@Override
	protected void beforeBreakingBlock(WorldAccess world, BlockPos position, BlockState state) {
		var blockEntity = world.getBlockEntity(position);
		Block.dropStacks(state, world, position, blockEntity);
	}
	
	@Override
	public boolean matchesType(Fluid fluid) {
		return fluid == flowing || fluid == still;
	}
	
	@Override
	protected int getFlowSpeed(WorldView world) {
		return 4;
	}
	
	@Override
	protected int getLevelDecreasePerBlock(WorldView world) {
		return 1;
	}
	
	@Override
	public Item getBucketItem() {
		return bucket.get();
	}
	
	@Override
	protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
		return direction == Direction.DOWN && fluid != flowing && fluid != still;
	}
	
	@Override
	public int getTickRate(WorldView world) {
		return 5;
	}
	
	@Override
	protected float getBlastResistance() {
		return 100.0F;
	}
	
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
	
	public static class Builder {
		private int fog = Color.WHITE.toInt();
		private int tint = Color.WHITE.toInt();
		private int damage = 0;

		private boolean isInfinite = false;
		private boolean isToxic = false;

		private String name = "";
		
		private RegistrySupplier<Block> block;

		private Fluid flowing;
		private Fluid still;
		
		private RegistrySupplier<Item> bucket;

		private Map<Item, CauldronBehavior> cauldronBehaviorMap;
		private RegistrySupplier<Block> cauldron;
		
		private DamageSource source;
		
		private ItemGroup group;
		
		private Builder() {
		}
		
		public Builder fog(int fog) {
			this.fog = fog;
			return this;
		}

		public Builder tint(int tint) {
			this.tint = tint;
			return this;
		}
		
		public Builder damage(int damage) {
			this.damage = damage;
			return this;
		}
		
		public Builder infinite(boolean isInfinite) {
			this.isInfinite = isInfinite;
			return this;
		}

		public Builder toxic(boolean isToxic) {
			this.isToxic = isToxic;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder source(DamageSource source) {
			this.source = source;
			return this;
		}
		
		public Builder group(ItemGroup group) {
			this.group = group;
			return this;
		}
		
		public ExtendedFluid build() {
			var flowing = AMFluids.register(name + "_flowing", new Flowing(fog, tint, isInfinite, source));
			var still = AMFluids.register(name, new Still(fog, tint, isInfinite, source));

			flowing.flowing = flowing;
			still.flowing = flowing;
			this.flowing = flowing;

			flowing.still = still;
			still.still = still;
			this.still = still;
			
			var block = AMBlocks.register(name, () -> new FluidBlock(still, AbstractBlock.Settings.of(INDUSTRIAL_FLUID_MATERIAL).noCollision().strength(100.0F).dropsNothing()));
			
			var bucket = AMItems.register(name + "_bucket", () -> new BucketItem(still, (new Item.Settings()).recipeRemainder(Items.BUCKET).maxCount(1).group(group)));

			flowing.block = (RegistrySupplier) block;
			still.block = (RegistrySupplier) block;
			this.block = (RegistrySupplier) block;

			flowing.bucket = (RegistrySupplier) bucket;
			still.bucket = (RegistrySupplier) bucket;
			this.bucket = (RegistrySupplier) bucket;
			
			var cauldronBehaviorMap = CauldronBehavior.createMap();
			var cauldron = AMBlocks.register(name + "_cauldron", () -> new FullCauldronBlock(BlockProperties.copy(Blocks.CAULDRON), cauldronBehaviorMap));

			flowing.cauldronBehaviorMap = cauldronBehaviorMap;
			still.cauldronBehaviorMap = cauldronBehaviorMap;
			this.cauldronBehaviorMap = cauldronBehaviorMap;
			
			flowing.cauldron = (RegistrySupplier) cauldron;
			still.cauldron = (RegistrySupplier) cauldron;
			this.cauldron = (RegistrySupplier) cauldron;

			CauldronLib.registerBehaviorMap(cauldronBehaviorMap);
			CauldronLib.registerFillFromBucketBehavior(this.bucket.get(), this.cauldron.get());
			cauldronBehaviorMap.put(Items.BUCKET, CauldronLib.createEmptyIntoBucketBehavior(this.bucket.get()));
			CauldronFluidContent.registerCauldron(this.cauldron.get(), this.still, FluidConstants.BUCKET, null); // Fabric only! If we're going to do a Forge version, make sure this only runs on Fabric!

			if (Platform.getEnv() == EnvType.CLIENT) {
				ClientUtils.registerExtendedFluid(name, tint, still, flowing);
			}

			return still;
		}
	}
	
	public static class Flowing extends ExtendedFluid {
		public Flowing(int fogColor, int tintColor, boolean isInfinite, @Nullable DamageSource source) {
			super(fogColor, tintColor, isInfinite, source);
		}
		
		@Override
		protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
			super.appendProperties(builder);
			builder.add(LEVEL);
		}
		
		@Override
		public int getLevel(FluidState state) {
			return state.get(LEVEL);
		}
		
		@Override
		public boolean isStill(FluidState state) {
			return false;
		}
	}
	
	public static class Still extends ExtendedFluid {
		public Still(int fogColor, int tintColor, boolean isInfinite, @Nullable DamageSource source) {
			super(fogColor, tintColor, isInfinite, source);
		}
		
		@Override
		public int getLevel(FluidState state) {
			return 8;
		}
		
		@Override
		public boolean isStill(FluidState state) {
			return true;
		}
	}
}