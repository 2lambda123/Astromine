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

package com.github.mixinors.astromine.client.rei.electrolyzing;

import com.github.mixinors.astromine.client.rei.AMRoughlyEnoughItemsPlugin;
import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ElectrolyzingCategory implements DisplayCategory<ElectrolyzingDisplay> {
	private final CategoryIdentifier<? extends ElectrolyzingDisplay> id;
	private final String translationKey;
	private final Renderer logo;

	public ElectrolyzingCategory(CategoryIdentifier<? extends ElectrolyzingDisplay> id, String translationKey, Renderer logo) {
		this.id = id;
		this.translationKey = translationKey;
		this.logo = logo;
	}

	@Override
	public CategoryIdentifier<? extends ElectrolyzingDisplay> getCategoryIdentifier() {
		return id;
	}

	@Override
	public Text getTitle() {
		return new TranslatableText(translationKey);
	}

	@Override
	public Renderer getIcon() {
		return logo;
	}

	@Override
	public List<Widget> setupDisplay(ElectrolyzingDisplay recipeDisplay, Rectangle bounds) {
		List<Widget> widgets = Lists.newArrayList();
		Rectangle innerBounds = new Rectangle(bounds.getCenterX() - 55, bounds.y, 110, bounds.height);
		widgets.add(Widgets.createRecipeBase(innerBounds));
		widgets.addAll(AMRoughlyEnoughItemsPlugin.createEnergyDisplay(new Rectangle(innerBounds.x + 10, bounds.getCenterY() - 23, 12, 48), recipeDisplay.getEnergy(), false, 12500));
		widgets.addAll(AMRoughlyEnoughItemsPlugin.createFluidDisplay(new Rectangle(innerBounds.x + 24, bounds.getCenterY() - 23, 12, 48), recipeDisplay.getInputEntries().get(0), false, 5000));
		widgets.add(Widgets.createArrow(new Point(innerBounds.getX() + 45, innerBounds.getY() + 26)));
		widgets.addAll(AMRoughlyEnoughItemsPlugin.createFluidDisplay(new Rectangle(innerBounds.getMaxX() - 32, bounds.getCenterY() - 23, 12, 48), recipeDisplay.getOutputEntries().get(0), true, 5000));
		widgets.addAll(AMRoughlyEnoughItemsPlugin.createFluidDisplay(new Rectangle(innerBounds.getMaxX() - 32 + 14, bounds.getCenterY() - 23, 12, 48), recipeDisplay.getOutputEntries().get(1), true, 5000));
		return widgets;
	}
}
