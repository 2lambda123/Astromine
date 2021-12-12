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

package com.github.mixinors.astromine.client.rei;

import com.github.mixinors.astromine.AMCommon;
import com.github.mixinors.astromine.client.rei.alloysmelting.AlloySmeltingCategory;
import com.github.mixinors.astromine.client.rei.alloysmelting.AlloySmeltingDisplay;
import com.github.mixinors.astromine.client.rei.electricsmelting.ElectricSmeltingCategory;
import com.github.mixinors.astromine.client.rei.electricsmelting.ElectricSmeltingDisplay;
import com.github.mixinors.astromine.client.rei.electrolyzing.ElectrolyzingCategory;
import com.github.mixinors.astromine.client.rei.electrolyzing.ElectrolyzingDisplay;
import com.github.mixinors.astromine.client.rei.fluidgenerating.FluidGeneratingCategory;
import com.github.mixinors.astromine.client.rei.fluidgenerating.FluidGeneratingDisplay;
import com.github.mixinors.astromine.client.rei.fluidmixing.FluidMixingCategory;
import com.github.mixinors.astromine.client.rei.fluidmixing.FluidMixingDisplay;
import com.github.mixinors.astromine.client.rei.infusing.InfusingCategory;
import com.github.mixinors.astromine.client.rei.infusing.InfusingDisplay;
import com.github.mixinors.astromine.client.rei.melting.MeltingCategory;
import com.github.mixinors.astromine.client.rei.melting.MeltingDisplay;
import com.github.mixinors.astromine.client.rei.pressing.PressingCategory;
import com.github.mixinors.astromine.client.rei.pressing.PressingDisplay;
import com.github.mixinors.astromine.client.rei.refining.RefiningCategory;
import com.github.mixinors.astromine.client.rei.refining.RefiningDisplay;
import com.github.mixinors.astromine.client.rei.solidgenerating.SolidGeneratingCategory;
import com.github.mixinors.astromine.client.rei.solidgenerating.SolidGeneratingDisplay;
import com.github.mixinors.astromine.client.rei.solidifying.SolidifyingCategory;
import com.github.mixinors.astromine.client.rei.solidifying.SolidifyingDisplay;
import com.github.mixinors.astromine.client.rei.triturating.TrituratingCategory;
import com.github.mixinors.astromine.client.rei.triturating.TrituratingDisplay;
import com.github.mixinors.astromine.client.rei.wiremilling.WireMillingCategory;
import com.github.mixinors.astromine.client.rei.wiremilling.WireMillingDisplay;
import com.github.mixinors.astromine.client.render.sprite.SpriteRenderer;
import com.github.mixinors.astromine.common.recipe.*;
import com.github.mixinors.astromine.common.util.ClientUtils;
import com.github.mixinors.astromine.common.util.FluidUtils;
import com.github.mixinors.astromine.common.util.NumberUtils;
import com.github.mixinors.astromine.common.volume.fluid.FluidVolume;
import com.github.mixinors.astromine.registry.common.AMBlocks;
import com.google.common.collect.ImmutableList;
import dev.architectury.fluid.FluidStack;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.AbstractRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.util.ClientEntryStacks;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.impl.client.gui.widget.EntryWidget;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class AMRoughlyEnoughItemsPlugin implements REIClientPlugin {
	private static final Identifier ENERGY_BACKGROUND = AMCommon.id("textures/widget/energy_volume_fractional_vertical_bar_background_thin.png");
	private static final Identifier ENERGY_FOREGROUND = AMCommon.id("textures/widget/energy_volume_fractional_vertical_bar_foreground_thin.png");

	public static final CategoryIdentifier<InfusingDisplay> INFUSING = CategoryIdentifier.of(AMCommon.id("infusing"));
	public static final CategoryIdentifier<TrituratingDisplay> TRITURATING = CategoryIdentifier.of(AMCommon.id("triturating"));
	public static final CategoryIdentifier<ElectricSmeltingDisplay> ELECTRIC_SMELTING = CategoryIdentifier.of(AMCommon.id("electric_smelting"));
	public static final CategoryIdentifier<FluidGeneratingDisplay> LIQUID_GENERATING = CategoryIdentifier.of(AMCommon.id("fluid_generating"));
	public static final CategoryIdentifier<SolidGeneratingDisplay> SOLID_GENERATING = CategoryIdentifier.of(AMCommon.id("solid_generating"));
	public static final CategoryIdentifier<FluidMixingDisplay> FLUID_MIXING = CategoryIdentifier.of(AMCommon.id("fluid_mixing"));
	public static final CategoryIdentifier<ElectrolyzingDisplay> ELECTROLYZING = CategoryIdentifier.of(AMCommon.id("electrolyzing"));
	public static final CategoryIdentifier<RefiningDisplay> REFINING = CategoryIdentifier.of(AMCommon.id("refining"));
	public static final CategoryIdentifier<PressingDisplay> PRESSING = CategoryIdentifier.of(AMCommon.id("pressing"));
	public static final CategoryIdentifier<MeltingDisplay> MELTING = CategoryIdentifier.of(AMCommon.id("melting"));
	public static final CategoryIdentifier<WireMillingDisplay> WIREMILLING = CategoryIdentifier.of(AMCommon.id("wire_milling"));
	public static final CategoryIdentifier<AlloySmeltingDisplay> ALLOY_SMELTING = CategoryIdentifier.of(AMCommon.id("alloy_smelting"));
	public static final CategoryIdentifier<SolidifyingDisplay> SOLIDIFYING = CategoryIdentifier.of(AMCommon.id("solidifying"));

	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(new InfusingCategory());
		registry.add(new SolidifyingCategory(),
			new TrituratingCategory(),
			new ElectricSmeltingCategory(),
			new FluidGeneratingCategory(),
			new SolidGeneratingCategory(),
			new PressingCategory(),
			new MeltingCategory(),
			new WireMillingCategory(),
			new AlloySmeltingCategory(),
			new FluidMixingCategory(FLUID_MIXING, "category.astromine.fluid_mixing", EntryStacks.of(AMBlocks.ADVANCED_FLUID_MIXER.get())),
			new ElectrolyzingCategory(ELECTROLYZING, "category.astromine.electrolyzing", EntryStacks.of(AMBlocks.ADVANCED_ELECTROLYZER.get())),
			new RefiningCategory(REFINING, "category.astromine.refining", EntryStacks.of(AMBlocks.ADVANCED_REFINERY.get())));

		registry.addWorkstations(INFUSING, EntryStacks.of(AMBlocks.ALTAR.get()));
		registry.addWorkstations(TRITURATING, EntryStacks.of(AMBlocks.PRIMITIVE_TRITURATOR.get()), EntryStacks.of(AMBlocks.BASIC_TRITURATOR.get()), EntryStacks.of(AMBlocks.ADVANCED_TRITURATOR.get()), EntryStacks.of(AMBlocks.ELITE_TRITURATOR.get()));
		registry.addWorkstations(ELECTRIC_SMELTING, EntryStacks.of(AMBlocks.PRIMITIVE_ELECTRIC_FURNACE.get()), EntryStacks.of(AMBlocks.BASIC_ELECTRIC_FURNACE.get()), EntryStacks.of(AMBlocks.ADVANCED_ELECTRIC_FURNACE.get()), EntryStacks.of(AMBlocks.ELITE_ELECTRIC_FURNACE.get()));
		registry.addWorkstations(LIQUID_GENERATING, EntryStacks.of(AMBlocks.PRIMITIVE_LIQUID_GENERATOR.get()), EntryStacks.of(AMBlocks.BASIC_LIQUID_GENERATOR.get()), EntryStacks.of(AMBlocks.ADVANCED_LIQUID_GENERATOR.get()), EntryStacks.of(AMBlocks.ELITE_LIQUID_GENERATOR.get()));
		registry.addWorkstations(SOLID_GENERATING, EntryStacks.of(AMBlocks.PRIMITIVE_SOLID_GENERATOR.get()), EntryStacks.of(AMBlocks.BASIC_SOLID_GENERATOR.get()), EntryStacks.of(AMBlocks.ADVANCED_SOLID_GENERATOR.get()), EntryStacks.of(AMBlocks.ELITE_SOLID_GENERATOR.get()));
		registry.addWorkstations(FLUID_MIXING, EntryStacks.of(AMBlocks.PRIMITIVE_FLUID_MIXER.get()), EntryStacks.of(AMBlocks.BASIC_FLUID_MIXER.get()), EntryStacks.of(AMBlocks.ADVANCED_FLUID_MIXER.get()), EntryStacks.of(AMBlocks.ELITE_FLUID_MIXER.get()));
		registry.addWorkstations(ELECTROLYZING, EntryStacks.of(AMBlocks.PRIMITIVE_ELECTROLYZER.get()), EntryStacks.of(AMBlocks.BASIC_ELECTROLYZER.get()), EntryStacks.of(AMBlocks.ADVANCED_ELECTROLYZER.get()), EntryStacks.of(AMBlocks.ELITE_ELECTROLYZER.get()));
		registry.addWorkstations(REFINING, EntryStacks.of(AMBlocks.PRIMITIVE_REFINERY.get()), EntryStacks.of(AMBlocks.BASIC_REFINERY.get()), EntryStacks.of(AMBlocks.ADVANCED_REFINERY.get()), EntryStacks.of(AMBlocks.ELITE_REFINERY.get()));
		registry.addWorkstations(PRESSING, EntryStacks.of(AMBlocks.PRIMITIVE_PRESSER.get()), EntryStacks.of(AMBlocks.BASIC_PRESSER.get()), EntryStacks.of(AMBlocks.ADVANCED_PRESSER.get()), EntryStacks.of(AMBlocks.ELITE_PRESSER.get()));
		registry.addWorkstations(MELTING, EntryStacks.of(AMBlocks.PRIMITIVE_MELTER.get()), EntryStacks.of(AMBlocks.BASIC_MELTER.get()), EntryStacks.of(AMBlocks.ADVANCED_MELTER.get()), EntryStacks.of(AMBlocks.ELITE_MELTER.get()));
		registry.addWorkstations(WIREMILLING, EntryStacks.of(AMBlocks.PRIMITIVE_WIREMILL.get()), EntryStacks.of(AMBlocks.BASIC_WIREMILL.get()), EntryStacks.of(AMBlocks.ADVANCED_WIREMILL.get()), EntryStacks.of(AMBlocks.ELITE_WIREMILL.get()));
		registry.addWorkstations(ALLOY_SMELTING, EntryStacks.of(AMBlocks.PRIMITIVE_ALLOY_SMELTER.get()), EntryStacks.of(AMBlocks.BASIC_ALLOY_SMELTER.get()), EntryStacks.of(AMBlocks.ADVANCED_ALLOY_SMELTER.get()), EntryStacks.of(AMBlocks.ELITE_ALLOY_SMELTER.get()));
		registry.addWorkstations(SOLIDIFYING, EntryStacks.of(AMBlocks.PRIMITIVE_SOLIDIFIER.get()), EntryStacks.of(AMBlocks.BASIC_SOLIDIFIER.get()), EntryStacks.of(AMBlocks.ADVANCED_SOLIDIFIER.get()), EntryStacks.of(AMBlocks.ELITE_SOLIDIFIER.get()));
			
		registry.removePlusButton(LIQUID_GENERATING);
		registry.setPlusButtonArea(SOLID_GENERATING, bounds -> new Rectangle(bounds.getCenterX() - 55 + 110 - 16, bounds.getMaxY() - 16, 10, 10));
		registry.removePlusButton(FLUID_MIXING);
		registry.removePlusButton(ELECTROLYZING);
		registry.removePlusButton(REFINING);
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		registry.registerFiller(AltarRecipe.class, InfusingDisplay::new);
		registry.registerFiller(TrituratingRecipe.class, TrituratingDisplay::new);
		registry.registerFiller(SmeltingRecipe.class, ElectricSmeltingDisplay::new);
		registry.registerFiller(FluidGeneratingRecipe.class, FluidGeneratingDisplay::new);
		registry.registerFiller(FluidMixingRecipe.class, FluidMixingDisplay::new);
		registry.registerFiller(ElectrolyzingRecipe.class, ElectrolyzingDisplay::new);
		registry.registerFiller(RefiningRecipe.class, RefiningDisplay::new);
		registry.registerFiller(PressingRecipe.class, PressingDisplay::new);
		registry.registerFiller(MeltingRecipe.class, MeltingDisplay::new);
		registry.registerFiller(WireMillingRecipe.class, WireMillingDisplay::new);
		registry.registerFiller(AlloySmeltingRecipe.class, AlloySmeltingDisplay::new);
		registry.registerFiller(SolidifyingRecipe.class, SolidifyingDisplay::new);

		registry.registerFiller(WireCuttingRecipe.class, recipe -> {
			return new DefaultCustomDisplay(recipe, EntryIngredients.ofIngredients(ImmutableList.of(recipe.getInput(), recipe.getTool())), Collections.singletonList(EntryIngredients.of(recipe.getOutput())));
		});

		for (Map.Entry<Item, Integer> entry : AbstractFurnaceBlockEntity.createFuelTimeMap().entrySet()) {
			if (!(entry.getKey() instanceof BucketItem) && entry != null && entry.getValue() > 0) {
				registry.add(new SolidGeneratingDisplay((entry.getValue() / 2F * 5) / (entry.getValue() / 2F) * 6, Collections.singletonList(EntryIngredients.of(entry.getKey())), null, (entry.getValue() / 2) / 6.0));
			}
		}
	}

	public static EntryStack<FluidStack> convertToEntryStack(FluidVolume volume) {
		return EntryStacks.of(volume.getFluid(), volume.getAmount());
	}

	public static List<Widget> createEnergyDisplay(Rectangle bounds, double energy, boolean generating, long speed) {
		return Collections.singletonList(new EnergyEntryWidget(bounds, speed, generating).entry(ClientEntryStacks.of(new AbstractRenderer() {
			@Override
			public void render(MatrixStack matrices, Rectangle bounds, int mouseX, int mouseY, float delta) {}

			@Override
			public @Nullable Tooltip getTooltip(Point mouse) {
				return Tooltip.create(mouse, new TranslatableText("text.astromine.energy"), new LiteralText(NumberUtils.shorten(energy, "") + "E").formatted(Formatting.GRAY), new LiteralText("Astromine").formatted(Formatting.BLUE, Formatting.ITALIC));
			}
		})).notFavoritesInteractable());
	}

	public static List<Widget> createFluidDisplay(Rectangle bounds, List<EntryStack<?>> fluidStacks, boolean generating, long speed) {
		EntryWidget entry = new FluidEntryWidget(bounds, speed, generating).entries(fluidStacks);
		if (generating)
			entry.markOutput();
		else entry.markInput();
		return Collections.singletonList(entry);
	}

	private static class EnergyEntryWidget extends EntryWidget {
		private final long speed;
		private final boolean generating;

		protected EnergyEntryWidget(Rectangle rectangle, long speed, boolean generating) {
			super(new Point(rectangle.x, rectangle.y));
			this.getBounds().setBounds(rectangle);
			this.speed = speed;
			this.generating = generating;
		}

		@Override
		protected void drawBackground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			if (background) {
				Rectangle bounds = getBounds();
				ClientUtils.getInstance().getTextureManager().bindTexture(ENERGY_BACKGROUND);
				DrawableHelper.drawTexture(matrices, bounds.x, bounds.y, 0, 0, bounds.width, bounds.height, bounds.width, bounds.height);
				ClientUtils.getInstance().getTextureManager().bindTexture(ENERGY_FOREGROUND);
				int height;
				if (generating)
					height = bounds.height - MathHelper.ceil((System.currentTimeMillis() / (speed / bounds.height) % bounds.height) / 1f);
				else height = MathHelper.ceil((System.currentTimeMillis() / (speed / bounds.height) % bounds.height) / 1f);
				DrawableHelper.drawTexture(matrices, bounds.x, bounds.y + height, 0, height, bounds.width - 1, bounds.height - height - 1, bounds.width, bounds.height);
			}
		}

		@Override
		protected void drawCurrentEntry(MatrixStack matrices, int mouseX, int mouseY, float delta) {}
	}

	private static class FluidEntryWidget extends EntryWidget {
		private final long speed;
		private final boolean generating;

		protected FluidEntryWidget(Rectangle rectangle, long speed, boolean generating) {
			super(new Point(rectangle.x, rectangle.y));
			this.getBounds().setBounds(rectangle);
			this.speed = speed;
			this.generating = generating;
		}

		@Override
		protected void drawBackground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			if (background) {
				Rectangle bounds = getBounds();
				ClientUtils.getInstance().getTextureManager().bindTexture(ENERGY_BACKGROUND);
				DrawableHelper.drawTexture(matrices, bounds.x, bounds.y, 0, 0, bounds.width, bounds.height, bounds.width, bounds.height);
			}
		}

		@Override
		protected void drawCurrentEntry(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			EntryStack<?> entry = getCurrentEntry();
			if (entry.getType() == VanillaEntryTypes.FLUID && !entry.isEmpty()) {
				Rectangle bounds = getBounds();
				int height;
				if (!generating)
					height = bounds.height - MathHelper.ceil((System.currentTimeMillis() / (speed / bounds.height) % bounds.height) / 1f);
				else height = MathHelper.ceil((System.currentTimeMillis() / (speed / bounds.height) % bounds.height) / 1f);
				VertexConsumerProvider.Immediate consumers = ClientUtils.getInstance().getBufferBuilders().getEntityVertexConsumers();
				Fluid fluid = entry.<FluidStack>castValue().getFluid();
				SpriteRenderer.beginPass().setup(consumers, RenderLayer.getSolid()).sprite(FluidUtils.getSprite(fluid)).color(FluidUtils.getColor(ClientUtils.getPlayer(), fluid)).light(0x00f000f0).overlay(OverlayTexture.DEFAULT_UV).alpha(
					0xff).normal(matrices.peek().getNormalMatrix(), 0, 0, 0).position(matrices.peek().getPositionMatrix(), bounds.x + 1, bounds.y + bounds.height - height + 1, bounds.x + bounds.width - 1, bounds.y + bounds.height - 1, getZOffset() + 1).next(
					PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
				consumers.draw();
			}
		}
	}
}